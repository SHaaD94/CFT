package com.shaad.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "OFFICES")
public class Office {
    public Office() {
    }

    public Office(String city, String address, String workingHours, Bank bank) {
        this.city = city;
        this.address = address;
        this.workingHours = workingHours;
        this.bank = bank;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BANK_ID")
    @JsonBackReference
    private Bank bank;

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    @Column(name = "CITY")
    private String city;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "WORKING_HOURS")
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
