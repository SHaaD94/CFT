package com.shaad.server;

import com.shaad.server.dao.BankDAO;
import com.shaad.server.dao.OfficeDAO;
import com.shaad.server.entities.Bank;
import com.shaad.server.entities.Office;
import com.shaad.server.xml_elements.BankListXml;
import com.shaad.server.xml_elements.BankXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Synchronizer {

    private Logger logger = Logger.getLogger(Synchronizer.class.getName());

    @Autowired
    private BankDAO bankDAO;

    @Autowired
    private OfficeDAO officeDAO;

    private boolean isSynchroinized = false;

    private final String WEB_SERVICE_URL;

    private final int SCHEDULE_DELAY;
    private final ScheduledExecutorService SCHEDULER;

    @Autowired
    public Synchronizer(@Value("${app.web-service-url}") String web_service_url,
                        @Value("${app.schedule-delay}") Integer schedule_delay) {
        WEB_SERVICE_URL = web_service_url;
        SCHEDULE_DELAY = schedule_delay;
        SCHEDULER = Executors.newScheduledThreadPool(SCHEDULE_DELAY);
        initScheduler();
    }

    public void initScheduler() {
        SCHEDULER.schedule(new SynchTask(), 0, TimeUnit.MINUTES);
    }

    public void shutdownScheduler() {
        SCHEDULER.shutdown();
    }

    private BankListXml getBankList() {
        HttpURLConnection connection = null;
        String xmlResponse = null;
        try {
            URL url = new URL(WEB_SERVICE_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            xmlResponse = response.toString();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to connect", e);
            xmlResponse = null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (null == xmlResponse) {
            return null;
        }

        BankListXml bankListXml = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BankListXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            bankListXml = (BankListXml) unmarshaller.unmarshal(new StringReader(xmlResponse));
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Failed to connect to Web Service on " + WEB_SERVICE_URL, e);
        }
        return bankListXml;
    }

    private List<Bank> parseIntoBankList(BankListXml bankListXml) {
        if (null == bankListXml) {
            return new ArrayList<Bank>();
        }
        List<BankXml> webServiceBankListTemp = bankListXml.getBankXmls();
        Map<Bank, List<Office>> webServiceBankMap = new HashMap<Bank, List<Office>>();
        for (BankXml bankXml : webServiceBankListTemp) {
            List<Office> officeList;
            Bank bank = new Bank(bankXml.getName());
            if (!webServiceBankMap.containsKey(bank)) {
                officeList = new ArrayList<Office>();
            } else {
                officeList = webServiceBankMap.get(bank);
            }
            officeList.add(new Office(bankXml.getCity(), bankXml.getAddress(), bankXml.getWorkingHours(), bank));
            webServiceBankMap.put(bank, officeList);
            bank.setOfficeList(officeList);
        }
        return new ArrayList<Bank>(webServiceBankMap.keySet());
    }

    public void synchronizeDataBase() {
        List<Bank> webServiceBankList = parseIntoBankList(getBankList());
        if (null == webServiceBankList || webServiceBankList.isEmpty()) {
            isSynchroinized = false;
            return;
        }
        List<Bank> currentBankList = bankDAO.getAllBank();
        List<Bank> resultList = Bank.mergeBankLists(new ArrayList<Bank>(webServiceBankList),
                new ArrayList<Bank>(currentBankList));


        for (Bank currentBank : currentBankList) {
            if (resultList.contains(currentBank)) {
                List<Office> currentOfficeList = currentBank.getOfficeList();
                List<Office> resultOfficeList = resultList.get(resultList.indexOf(currentBank)).getOfficeList();
                for (Office office : currentOfficeList) {
                    if (resultOfficeList.contains(office)) {
                        resultOfficeList.remove(office);
                    } else {
                        if (office.getId() != 0) {
                            officeDAO.delete(office.getId());
                        }
                    }
                }
                for (Office office : resultOfficeList) {
                    office.setBank(currentBank);
                    officeDAO.save(office);
                }
                resultList.remove(currentBank);
            } else {
                if (currentBank.getId() != 0) {
                    bankDAO.delete(currentBank.getId());
                }
            }
        }
        for (Bank bank : resultList) {
            Bank tempBank = new Bank(bank.getName());
            bankDAO.save(tempBank);
            for (Office office : bank.getOfficeList()) {
                office.setBank(tempBank);
                officeDAO.save(office);
            }
        }
        isSynchroinized = true;
    }

    private class SynchTask implements Runnable {
        public void run() {
            try {
                synchronizeDataBase();
            } finally {
                if (isSynchroinized) {
                    logger.info("Database synchronized " + new Date().toString());
                }
                SCHEDULER.schedule(new SynchTask(), SCHEDULE_DELAY, TimeUnit.MINUTES);
            }
        }
    }

}
