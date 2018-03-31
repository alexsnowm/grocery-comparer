package com.alexsnowm.grocerycomparer.models.forms;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.ItemMeasure;
import com.alexsnowm.grocerycomparer.models.Price;

public class DisplayItemObj {

    private Item item;
    private Price price;
    private int priceId;
    private Iterable<Price> priceList;

    public DisplayItemObj(Item item, Price price) {
        this.item = item;
        this.price = price;
    }

    public DisplayItemObj(Item item, Price price, Iterable<Price> priceList) {
        this.item = item;
        this.price = price;
        this.priceList = priceList;
    }

    public DisplayItemObj() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public Iterable<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(Iterable<Price> priceList) {
        this.priceList = priceList;
    }
}
