package com.alexsnowm.grocerycomparer.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Store {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Name required")
    private String name;

    private String street;
    private String city;

    @ManyToOne
    private State state;

    private String zipcode;
    private String tel;
    private String website;
    private String notes;
    private String address;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id")
    @OrderBy("postedAt DESC")
    private List<Price> prices = new ArrayList<>();

    public Store(String name, String street, String city, String zipcode, String tel, String website, String notes, String address) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.tel = tel;
        this.website = website;
        this.notes = notes;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void addPrice(Price price) {
        prices.add(price);
    }
}
