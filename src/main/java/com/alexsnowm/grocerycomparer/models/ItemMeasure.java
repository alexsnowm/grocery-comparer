package com.alexsnowm.grocerycomparer.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemMeasure {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String measure;

    @OneToMany
    @JoinColumn(name = "measure_id")
    private List<Price> prices = new ArrayList<>();

    public ItemMeasure() {}

    public ItemMeasure(String measure) {
        this.measure = measure;
    }

    public int getId() {
        return id;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void addPrice(Price price) {
        prices.add(price);
    }
}
