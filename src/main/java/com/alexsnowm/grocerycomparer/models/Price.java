package com.alexsnowm.grocerycomparer.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Price {

    @Id
    @GeneratedValue
    private int id;

    private BigDecimal origNum;

    @Column(precision = 18, scale = 12)
    private BigDecimal convNum;

    private ItemMeasure currMeasure;

    @ManyToOne
    private Store store;

    private String aisle;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date postedAt = new Date();

    private String dispOrigPrice;
    private String dispConvPrice;

    @ManyToOne
    private Item item;

    public Price() {}

    public Price(BigDecimal number, String aisle, String dispPrice) {
        this.origNum = number;
        this.aisle = aisle;
        this.dispOrigPrice = dispOrigPrice;
    }

    public Price(Store store) {
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getOrigNum() {
        return origNum;
    }

    public void setOrigNum(BigDecimal origNum) {
        this.origNum = origNum;
    }

    public BigDecimal getConvNum() {
        return convNum;
    }

    public void setConvNum(BigDecimal convNum) {
        this.convNum = convNum;
    }

    public ItemMeasure getCurrMeasure() {
        return currMeasure;
    }

    public void setCurrMeasure(ItemMeasure currMeasure) {
        this.currMeasure = currMeasure;
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

    public String getDispOrigPrice() {

        String priceString = dispOrigPrice;
        if (priceString == null) {
            priceString = "";
        }

        return priceString;
    }

    public void setDispOrigPrice(String dispOrigPrice) {
        this.dispOrigPrice = dispOrigPrice;
    }

    public String getDispConvPrice() {

        String priceString = dispConvPrice;
        if (priceString == null) {
            priceString = "";
        }

        return priceString;
    }

    public void setDispConvPrice(String dispConvPrice) {
        this.dispConvPrice = dispConvPrice;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void convertMeasure(ItemMeasure toMeasure) {

        if (toMeasure.equals(ItemMeasure.MG)) {
            toMG();
        } else if (toMeasure.equals(ItemMeasure.LB)) {
            toLB();
        } else if (toMeasure.equals(ItemMeasure.OZ)) {
            toOZ();
        } else if (toMeasure.equals(ItemMeasure.KG)) {
            toKG();
        } else if (toMeasure.equals(ItemMeasure.G)) {
            toG();
        }

        else if (toMeasure.equals(ItemMeasure.ML)) {
            toML();
        } else if (toMeasure.equals(ItemMeasure.FL_OZ)) {
            toFL_OZ();
        } else if (toMeasure.equals(ItemMeasure.L)) {
            toL();
        } else if (toMeasure.equals(ItemMeasure.GAL)) {
            toGAL();
        } else if (toMeasure.equals(ItemMeasure.QT)) {
            toQT();
        } else if (toMeasure.equals(ItemMeasure.PT)) {
            toPT();
        } else if (toMeasure.equals(ItemMeasure.CUP)) {
            toCUP();
        } else if (toMeasure.equals(ItemMeasure.TBSP)) {
            toTBSP();
        } else if (toMeasure.equals(ItemMeasure.TSP)) {
            toTSP();
        }
    }

    public void toMG() {

        int scale = 12;
        RoundingMode roundingMode = RoundingMode.FLOOR;

        if (currMeasure.equals(ItemMeasure.LB)) {
            this.convNum = convNum.divide(new BigDecimal("453592.37"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.OZ)) {
            this.convNum = convNum.divide(new BigDecimal("28349.523125"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.KG)) {
            this.convNum = convNum.divide(new BigDecimal("1000000"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.G)) {
            this.convNum = convNum.divide(new BigDecimal("1000"), scale, roundingMode);
        }
        setCurrMeasure(ItemMeasure.MG);
    }

    public void toLB() {
        toMG();
        this.convNum = convNum.multiply(new BigDecimal("453592.37"));
        setCurrMeasure(ItemMeasure.LB);
    }

    public void toOZ() {
        toMG();
        this.convNum = convNum.multiply(new BigDecimal("28349.523125"));
        setCurrMeasure(ItemMeasure.OZ);
    }

    public void toKG() {
        toMG();
        this.convNum = convNum.multiply(new BigDecimal("1000000"));
        setCurrMeasure(ItemMeasure.KG);
    }

    public void toG() {
        toMG();
        this.convNum = convNum.multiply(new BigDecimal("1000"));
        setCurrMeasure(ItemMeasure.G);
    }

    public void toML() {

        int scale = 12;
        RoundingMode roundingMode = RoundingMode.FLOOR;

        if (currMeasure.equals(ItemMeasure.FL_OZ)) {
            this.convNum = convNum.divide(new BigDecimal("29.5735295625"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.L)) {
            this.convNum = convNum.divide(new BigDecimal("1000"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.GAL)) {
            this.convNum = convNum.divide(new BigDecimal("3785.411784"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.QT)) {
            this.convNum = convNum.divide(new BigDecimal("946.352946"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.PT)) {
            this.convNum = convNum.divide(new BigDecimal("473.176473"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.CUP)) {
            this.convNum = convNum.divide(new BigDecimal("236.5882365"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.TBSP)) {
            this.convNum = convNum.divide(new BigDecimal("14.78676478125"), scale, roundingMode);
        } else if (currMeasure.equals(ItemMeasure.TSP)) {
            this.convNum = convNum.divide(new BigDecimal("4.92892159375"), scale, roundingMode);
        }
        setCurrMeasure(ItemMeasure.ML);
    }

    public void toFL_OZ() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("29.5735295625"));
        setCurrMeasure(ItemMeasure.FL_OZ);
    }

    public void toL() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("1000"));
        setCurrMeasure(ItemMeasure.L);
    }

    public void toGAL() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("3785.411784"));
        setCurrMeasure(ItemMeasure.GAL);
    }

    public void toQT() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("946.352946"));
        setCurrMeasure(ItemMeasure.QT);
    }

    public void toPT() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("473.176473"));
        setCurrMeasure(ItemMeasure.PT);
    }

    public void toCUP() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("236.5882365"));
        setCurrMeasure(ItemMeasure.CUP);
    }

    public void toTBSP() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("14.78676478125"));
        setCurrMeasure(ItemMeasure.TBSP);
    }

    public void toTSP() {
        toML();
        this.convNum = convNum.multiply(new BigDecimal("4.92892159375"));
        setCurrMeasure(ItemMeasure.TSP);
    }
}
