package com.alexsnowm.grocerycomparer.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class State {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @Size(min = 2, max = 2)
    private String name;

    @OneToMany
    @JoinColumn(name = "state_id")
    private List<Store> stores = new ArrayList<>();

    public State() {}

    public State(String name) {
        this.name = name;
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

    public List<Store> getStores() {
        return stores;
    }

    public void addStore(Store store) {
        stores.add(store);
    }
}
