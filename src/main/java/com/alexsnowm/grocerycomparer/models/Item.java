package com.alexsnowm.grocerycomparer.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Name required")
    private String name;

//    TODO - Store prices as a list
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;

    @ManyToMany(mappedBy = "items")
    private List<ItemMeasure> measures;

    @ManyToMany(mappedBy = "items")
    private List<Store> stores;

    private String aisle;
    private String notes;

    public Item(String name, BigDecimal price, String aisle, String notes) {
        this.name = name;
        this.price = price;
        this.aisle = aisle;
        this.notes = notes;
    }

    public Item() {

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ItemMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<ItemMeasure> measures) {
        this.measures = measures;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
