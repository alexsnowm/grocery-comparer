package com.alexsnowm.grocerycomparer.models;

public enum ItemMeasure {

    ITEM ("item"),
    PIECE ("piece"),
    LB ("lb"),
    OZ ("oz"),
    KG ("kg"),
    G ("g"),
    MG ("mg"),
    GAL ("gal"),
    QT ("qt"),
    PT ("pt"),
    FL_OZ ("fl oz"),
    ML ("mL"),
    L ("L"),
    CUP ("cup"),
    TBSP ("tbsp"),
    TSP ("tsp");

    private final String name;

    ItemMeasure(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
