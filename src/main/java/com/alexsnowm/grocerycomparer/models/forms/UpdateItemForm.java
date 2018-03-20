package com.alexsnowm.grocerycomparer.models.forms;

import com.alexsnowm.grocerycomparer.models.ItemMeasure;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

public class UpdateItemForm {

    private String itemName;

    @Digits(integer = 6, fraction = 2, message = "Enter number to no more than 2 decimal places")
    private BigDecimal priceNumber;

    private ItemMeasure measure;
    private String priceAisle;
    private String itemNotes;

    public UpdateItemForm() {
    }

    public UpdateItemForm(String itemName, String itemNotes) {
        this.itemName = itemName;
        this.itemNotes = itemNotes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPriceNumber() {
        return priceNumber;
    }

    public void setPriceNumber(BigDecimal priceNumber) {
        this.priceNumber = priceNumber;
    }

    public ItemMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(ItemMeasure measure) {
        this.measure = measure;
    }

    public String getPriceAisle() {
        return priceAisle;
    }

    public void setPriceAisle(String priceAisle) {
        this.priceAisle = priceAisle;
    }

    public String getItemNotes() {
        return itemNotes;
    }

    public void setItemNotes(String itemNotes) {
        this.itemNotes = itemNotes;
    }
}
