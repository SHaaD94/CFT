package com.shaad.client.entities;

public class Office {
    public Office() {
    }

    public Office(String city, String address, String workingHours, Bank bank) {
        this.city = city;
        this.address = address;
        this.workingHours = workingHours;
        this.bank = bank;
    }

    private int id;

    private Bank bank;

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    private String city;

    private String address;

    private String workingHours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }
        Office office = (Office) obj;
        return office.getAddress().equals(getAddress())
                && office.getCity().equals(getCity())
                && office.getBank().equals(getBank())
                && office.getWorkingHours().equals(getWorkingHours());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 67 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 67 * hash + (this.workingHours != null ? this.workingHours.hashCode() : 0);
        hash = 67 * hash + (this.bank != null ? this.bank.hashCode() : 0);
        return hash;
    }
}
