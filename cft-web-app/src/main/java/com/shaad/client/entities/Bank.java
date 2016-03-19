package com.shaad.client.entities;

import java.util.List;

public class Bank {
    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    private int id;

    private String name;

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
}
