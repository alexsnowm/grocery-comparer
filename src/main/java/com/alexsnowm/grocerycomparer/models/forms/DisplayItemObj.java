package com.alexsnowm.grocerycomparer.models.forms;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.Price;

public class DisplayItemObj {

    private Item item;
    private Price price;

    public DisplayItemObj(Item item, Price price) {
        this.item = item;
        this.price = price;
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
}
