package com.alexsnowm.grocerycomparer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Store {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, message = "Name required")
    private String name;

    private String street;
    private String city;

    @ManyToOne
    private State state;

    private String zipcode;
    private String tel;
    private String website;
    private String notes;

    public Store(String name, String street, String city, String zipcode, String tel, String website, String notes) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.tel = tel;
        this.website = website;
        this.notes = notes;
    }

    public Store() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
