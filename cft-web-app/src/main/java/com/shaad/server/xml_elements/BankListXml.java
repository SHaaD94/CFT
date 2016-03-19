package com.shaad.server.xml_elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "bankList")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankListXml {
    @XmlElement(name = "bank")
    private List<BankXml> bankXmls;

    public List<BankXml> getBankXmls() {
        return bankXmls;
    }

    public void setBankXmls(List<BankXml> bankXmls) {
        this.bankXmls = bankXmls;
    }
}