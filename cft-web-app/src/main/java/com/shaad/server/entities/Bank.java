package com.shaad.server.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BANKS")
public class Bank {
    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)*/
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    /*@JoinColumn(name = "BANK_ID")*/
    private List<Office> officeList;

    public List<Office> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<Office> officeList) {
        this.officeList = officeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Bank bank = (Bank) obj;
        return bank.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public static List<Bank> mergeBankLists(List<Bank> bankList1, List<Bank> bankList2) {
        List<Bank> result = new ArrayList<Bank>();
        for (Bank firstBank : bankList1) {
            Bank resultBank = firstBank;
            int indexOfBankInSecondList = bankList2.indexOf(firstBank);
            if (indexOfBankInSecondList != -1) {
                Bank bankFromSecondList = bankList2.get(indexOfBankInSecondList);
                Bank temp = new Bank();
                temp.setId(bankFromSecondList.getId());
                temp.setName(bankFromSecondList.getName());
                temp.setOfficeList(mergeOfficeLists(new ArrayList<Office>(resultBank.getOfficeList()),
                        new ArrayList<Office>(bankFromSecondList.getOfficeList())));
                resultBank = temp;
                bankList2.remove(bankFromSecondList);
            }
            result.add(resultBank);
        }
        return result;
    }

    public static List<Office> mergeOfficeLists(List<Office> officeList1, List<Office> officeList2) {
        List<Office> result = new ArrayList<Office>();

        for (Office firstOffice : officeList1) {
            Office resultOffice = firstOffice;
            int indexOfOfficeInSecondList = officeList2.indexOf(firstOffice);
            if (indexOfOfficeInSecondList != -1) {
                Office officeFromSecondList = officeList2.get(indexOfOfficeInSecondList);
                resultOffice = officeFromSecondList;
                officeList2.remove(officeFromSecondList);
            }
            result.add(resultOffice);
        }
        return result;
    }
}
