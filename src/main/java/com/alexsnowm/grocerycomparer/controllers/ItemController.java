package com.alexsnowm.grocerycomparer.controllers;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.ItemMeasure;
import com.alexsnowm.grocerycomparer.models.Price;
import com.alexsnowm.grocerycomparer.models.Store;
import com.alexsnowm.grocerycomparer.models.data.ItemDao;
import com.alexsnowm.grocerycomparer.models.data.ItemMeasureDao;
import com.alexsnowm.grocerycomparer.models.data.PriceDao;
import com.alexsnowm.grocerycomparer.models.data.StoreDao;
import com.alexsnowm.grocerycomparer.models.forms.CreateItemForm;
import com.alexsnowm.grocerycomparer.models.forms.DisplayItemObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "items")
public class ItemController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private PriceDao priceDao;

    @Autowired
    private ItemMeasureDao itemMeasureDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        Iterable<Item> allItems = itemDao.findAll();

        List<DisplayItemObj> dispItems = new ArrayList<>();
        DisplayItemObj iObj;
        for (Item item : allItems) {
            int matchPriceId = item.getPriceId();

            if (matchPriceId == 0) {
                iObj = new DisplayItemObj(item, new Price());
                dispItems.add(iObj);
            } else {
                Price matchPrice = priceDao.findOne(matchPriceId);
                iObj = new DisplayItemObj(item, matchPrice);
                dispItems.add(iObj);
            }
        }

        model.addAttribute("title", "All Items");
        model.addAttribute("dispItems", dispItems);

        return "item/index";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(@PathVariable int id, Model model) {

        Item item = itemDao.findOne(id);
        List<Price> priceList = item.getPrices();

        int matchPriceId = item.getPriceId();
        DisplayItemObj iObj;
        if (matchPriceId == 0) {
            iObj = new DisplayItemObj(item, new Price(new Store()));
        } else {
            Price matchPrice = priceDao.findOne(matchPriceId);
            iObj = new DisplayItemObj(item, matchPrice);
        }

        model.addAttribute("title", item.getName());
        model.addAttribute("dispItem", iObj);
        model.addAttribute("priceList", priceList);

        return "item/view";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String displayCreateItemForm(Model model) {
        model.addAttribute("title", "Create Item");
        model.addAttribute(new CreateItemForm());
        model.addAttribute("itemMeasures", itemMeasureDao.findAll());
        model.addAttribute("stores", storeDao.findAll());

        return "item/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreateItemForm(@ModelAttribute @Valid CreateItemForm theForm, Errors errors, @RequestParam int measureId, @RequestParam int storeId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Item");
            model.addAttribute("itemMeasures", itemMeasureDao.findAll());
            model.addAttribute("stores", storeDao.findAll());

            return "item/create";
        }

        Item newItem = new Item();
        newItem.setName(theForm.getItemName());
        newItem.setNotes(theForm.getItemNotes());

        BigDecimal priceNum = theForm.getPriceNumber();
        if (priceNum != null) {
            Price newPrice = new Price();
            newPrice.setNumber(priceNum);
            newPrice.setAisle(theForm.getPriceAisle());
            ItemMeasure im = itemMeasureDao.findOne(measureId);
            newPrice.setMeasure(im);
            Store st = storeDao.findOne(storeId);
            newPrice.setStore(st);

            itemDao.save(newItem);

            newItem = itemDao.findOne(newItem.getId());
            newPrice.setItem(newItem);

            String dp = "$" + newPrice.getNumber() + " / " + newPrice.getMeasure().getMeasure();
            newPrice.setDispPrice(dp);

            priceDao.save(newPrice);

            newItem.setPriceId(newPrice.getId());
        }

        itemDao.save(newItem);

        return "redirect:";
    }
}
