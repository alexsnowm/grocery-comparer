package com.alexsnowm.grocerycomparer.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Price {

    @Id
    @GeneratedValue
    private int id;

    private BigDecimal origNum;
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

        MathContext precision = new MathContext(12, RoundingMode.FLOOR);

        if (toMeasure.equals(ItemMeasure.G)) {
            toG(precision);
        } else if (toMeasure.equals(ItemMeasure.LB)) {
            toLB(precision);
        } else if (toMeasure.equals(ItemMeasure.OZ)) {
            toOZ(precision);
        } else if (toMeasure.equals(ItemMeasure.KG)) {
            toKG(precision);
        } else if (toMeasure.equals(ItemMeasure.MG)) {
            toMG(precision);
        }

        else if (toMeasure.equals(ItemMeasure.ML)) {
            toML(precision);
        } else if (toMeasure.equals(ItemMeasure.FL_OZ)) {
            toFL_OZ(precision);
        } else if (toMeasure.equals(ItemMeasure.L)) {
            toL(precision);
        } else if (toMeasure.equals(ItemMeasure.GAL)) {
            toGAL(precision);
        } else if (toMeasure.equals(ItemMeasure.QT)) {
            toQT(precision);
        } else if (toMeasure.equals(ItemMeasure.PT)) {
            toPT(precision);
        } else if (toMeasure.equals(ItemMeasure.CUP)) {
            toCUP(precision);
        } else if (toMeasure.equals(ItemMeasure.TBSP)) {
            toTBSP(precision);
        } else if (toMeasure.equals(ItemMeasure.TSP)) {
            toTSP(precision);
        }
    }

    public void toG(MathContext precision) {
        if (currMeasure.equals(ItemMeasure.LB)) {
            this.convNum = convNum.divide(new BigDecimal("453.59237"), precision);
        } else if (currMeasure.equals(ItemMeasure.OZ)) {
            this.convNum = convNum.divide(new BigDecimal("28.349523125"), precision);
        } else if (currMeasure.equals(ItemMeasure.KG)) {
            this.convNum = convNum.divide(new BigDecimal("1000"), precision);
        } else if (currMeasure.equals(ItemMeasure.MG)) {
            this.convNum = convNum.multiply(new BigDecimal("1000"));
        }
        setCurrMeasure(ItemMeasure.G);
    }

    public void toLB(MathContext precision) {
        toG(precision);
        this.convNum = convNum.multiply(new BigDecimal("453.59237"));
        setCurrMeasure(ItemMeasure.LB);
    }

    public void toOZ(MathContext precision) {
        toG(precision);
        this.convNum = convNum.multiply(new BigDecimal("28.349523125"));
        setCurrMeasure(ItemMeasure.OZ);
    }

    public void toKG(MathContext precision) {
        toG(precision);
        this.convNum = convNum.multiply(new BigDecimal("1000"));
        setCurrMeasure(ItemMeasure.KG);
    }

    public void toMG(MathContext precision) {
        toG(precision);
        this.convNum = convNum.divide(new BigDecimal("1000"), precision);
        setCurrMeasure(ItemMeasure.KG);
    }

    public void toML(MathContext precision) {
        if (currMeasure.equals(ItemMeasure.FL_OZ)) {
            this.convNum = convNum.divide(new BigDecimal("29.5735295625"), precision);
        } else if (currMeasure.equals(ItemMeasure.L)) {
            this.convNum = convNum.divide(new BigDecimal("1000"), precision);
        } else if (currMeasure.equals(ItemMeasure.GAL)) {
            this.convNum = convNum.divide(new BigDecimal("3785.411784"), precision);
        } else if (currMeasure.equals(ItemMeasure.QT)) {
            this.convNum = convNum.multiply(new BigDecimal("946.352946"), precision);
        } else if (currMeasure.equals(ItemMeasure.PT)) {
            this.convNum = convNum.divide(new BigDecimal("473.176473"), precision);
        } else if (currMeasure.equals(ItemMeasure.CUP)) {
            this.convNum = convNum.divide(new BigDecimal("236.5882365"), precision);
        } else if (currMeasure.equals(ItemMeasure.TBSP)) {
            this.convNum = convNum.divide(new BigDecimal("14.78676478125"), precision);
        } else if (currMeasure.equals(ItemMeasure.TSP)) {
            this.convNum = convNum.divide(new BigDecimal("4.92892159375"), precision);
        }
        setCurrMeasure(ItemMeasure.ML);
    }

    public void toFL_OZ(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("29.5735295625"));
        setCurrMeasure(ItemMeasure.FL_OZ);
    }

    public void toL(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("1000"));
        setCurrMeasure(ItemMeasure.L);
    }

    public void toGAL(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("3785.411784"));
        setCurrMeasure(ItemMeasure.GAL);
    }

    public void toQT(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("946.352946"));
        setCurrMeasure(ItemMeasure.QT);
    }

    public void toPT(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("473.176473"));
        setCurrMeasure(ItemMeasure.PT);
    }

    public void toCUP(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("236.5882365"));
        setCurrMeasure(ItemMeasure.CUP);
    }

    public void toTBSP(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("14.78676478125"));
        setCurrMeasure(ItemMeasure.TBSP);
    }

    public void toTSP(MathContext precision) {
        toML(precision);
        this.convNum = convNum.multiply(new BigDecimal("4.92892159375"));
        setCurrMeasure(ItemMeasure.TSP);
    }
}
