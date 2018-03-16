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

    public boolean checkCompatible(ItemMeasure compareMeasure) {

        if ( ( this.equals(LB) || this.equals(OZ) || this.equals(KG) || this.equals(G) || this.equals(MG) ) && ( compareMeasure.equals(LB) || compareMeasure.equals(OZ) || compareMeasure.equals(KG) || compareMeasure.equals(G) || compareMeasure.equals(MG) ) ) {
            return true;
        }

        if ( ( this.equals(GAL) || this.equals(QT) || this.equals(PT) || this.equals(FL_OZ) || this.equals(ML) || this.equals(L) || this.equals(CUP) || this.equals(TBSP) || this.equals(TSP) ) && ( compareMeasure.equals(GAL) || compareMeasure.equals(QT) || compareMeasure.equals(PT) || compareMeasure.equals(FL_OZ) || compareMeasure.equals(ML) || compareMeasure.equals(L) || compareMeasure.equals(CUP) || compareMeasure.equals(TBSP) || compareMeasure.equals(TSP) ) ) {
            return true;
        }

        return false;
    }
}
