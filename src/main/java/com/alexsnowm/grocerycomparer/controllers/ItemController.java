package com.alexsnowm.grocerycomparer.controllers;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.ItemMeasure;
import com.alexsnowm.grocerycomparer.models.Price;
import com.alexsnowm.grocerycomparer.models.Store;
import com.alexsnowm.grocerycomparer.models.data.ItemDao;
import com.alexsnowm.grocerycomparer.models.data.ItemMeasureDao;
import com.alexsnowm.grocerycomparer.models.data.PriceDao;
import com.alexsnowm.grocerycomparer.models.data.StoreDao;
import com.alexsnowm.grocerycomparer.models.forms.DisplayItemObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

        int itemCount = (int) itemDao.count();
        Price[] bestPrices = new Price[itemCount];
        int i = 0;
        for (Item item : allItems) {
            int matchPriceId = item.getPriceId();

            if (matchPriceId == 0) {
                bestPrices[i] = new Price(new Store());
                i++;
            } else {
                bestPrices[i] = priceDao.findOne(matchPriceId);
                i++;
            }
        }

//        List<DisplayItemObj> dispItems = new ArrayList<>();
//        for (Item item : allItems) {
//            int matchPriceId = item.getPriceId();
//
//            if (matchPriceId == 0) {
//                DisplayItemObj iObj = new DisplayItemObj(item, new Price());
//                dispItems.add(iObj);
//            } else {
//                Price matchPrice = priceDao.findOne(matchPriceId);
//                DisplayItemObj iObj = new DisplayItemObj(item, matchPrice);
//                dispItems.add(iObj);
//            }
//        }

        model.addAttribute("title", "All Items");
//        model.addAttribute("dispItems", dispItems);
        model.addAttribute("dispItems", allItems);
        model.addAttribute("bestPrices", bestPrices);

        return "item/index";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String displayCreateItemForm(Model model) {
        model.addAttribute("title", "Create Item");
        model.addAttribute("item", new Item());
        model.addAttribute("price", new Price());
        model.addAttribute("measures", itemMeasureDao.findAll());
        model.addAttribute("stores", storeDao.findAll());

        return "item/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreateItemForm(@ModelAttribute @Valid Item newItem, @ModelAttribute @Valid Price newPrice, Errors errors, @RequestParam int measureId, @RequestParam int storeId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Item");
            model.addAttribute("measures", itemMeasureDao.findAll());
            model.addAttribute("stores", storeDao.findAll());

            return "item/create";
        }

        if (newPrice.getNumber() != null) {
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
