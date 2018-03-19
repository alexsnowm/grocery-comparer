package com.alexsnowm.grocerycomparer.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private int priceId;
    private String notes;

    @OneToMany
    @JoinColumn(name = "item_id")
    @OrderBy("postedAt DESC")
    private List<Price> prices = new ArrayList<>();

    public Item(String name, String notes) {
        this.name = name;
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

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void addPrice(Price price) {
        prices.add(price);
    }
}
