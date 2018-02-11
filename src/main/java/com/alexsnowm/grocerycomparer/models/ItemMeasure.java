package com.alexsnowm.grocerycomparer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemMeasure {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String measure;

    @ManyToMany
    private List<Item> items = new ArrayList<>();

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

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
