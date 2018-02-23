package com.alexsnowm.grocerycomparer.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Price {

    @Id
    @GeneratedValue
    private int id;

    @Digits(integer = 7, fraction = 2, message = "Enter number to no more than 2 decimal places")
    private BigDecimal number;

    @ManyToOne
    private ItemMeasure measure;

    @ManyToOne
    private Store store;

    private String aisle;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date postedAt = new Date();

    private String dispPrice;

    @ManyToOne
    private Item item;

    public Price() {}

    public Price(BigDecimal number, String aisle, String dispPrice) {
        this.number = number;
        this.aisle = aisle;
        this.dispPrice = dispPrice;
    }

    public Price(Store store) {
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public ItemMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(ItemMeasure measure) {
        this.measure = measure;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public String getDispPrice() {

        String priceString = dispPrice;
        if (priceString == null) {
            priceString = "";
        }

        return priceString;
    }

    public void setDispPrice(String dispPrice) {
        this.dispPrice = dispPrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
