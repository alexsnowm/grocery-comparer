package com.alexsnowm.grocerycomparer.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
}
