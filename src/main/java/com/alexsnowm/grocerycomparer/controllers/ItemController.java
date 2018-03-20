package com.alexsnowm.grocerycomparer.controllers;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.ItemMeasure;
import com.alexsnowm.grocerycomparer.models.Price;
import com.alexsnowm.grocerycomparer.models.Store;
import com.alexsnowm.grocerycomparer.models.data.ItemDao;
import com.alexsnowm.grocerycomparer.models.data.PriceDao;
import com.alexsnowm.grocerycomparer.models.data.StoreDao;
import com.alexsnowm.grocerycomparer.models.forms.CreateItemForm;
import com.alexsnowm.grocerycomparer.models.forms.DisplayItemObj;
import com.alexsnowm.grocerycomparer.models.forms.UpdateItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "items")
public class ItemController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private PriceDao priceDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        Iterable<Item> allItems = itemDao.findAll();

        List<DisplayItemObj> dispItems = new ArrayList<>();
        DisplayItemObj iObj;
        for (Item item : allItems) {
            int matchPriceId = item.getPriceId();

            if (matchPriceId == 0) {
                iObj = new DisplayItemObj(item, new Price(new Store()));
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
        model.addAttribute("itemMeasures", ItemMeasure.values());
        model.addAttribute("stores", storeDao.findAll());

        return "item/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreateItemForm(@ModelAttribute @Valid CreateItemForm theForm, Errors errors, @RequestParam int storeId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Item");
            model.addAttribute("itemMeasures", ItemMeasure.values());
            model.addAttribute("stores", storeDao.findAll());

            return "item/create";
        }

        Item newItem = new Item();
        newItem.setName(theForm.getItemName());
        newItem.setNotes(theForm.getItemNotes());

        BigDecimal priceNum = theForm.getPriceNumber();
        if (priceNum != null) {
            Price newPrice = new Price();
            newPrice.setOrigNum(priceNum);
            newPrice.setConvNum(priceNum);
            newPrice.setAisle(theForm.getPriceAisle());
            newPrice.setCurrMeasure(theForm.getMeasure());
            Store st = storeDao.findOne(storeId);
            newPrice.setStore(st);

            itemDao.save(newItem);

            newItem = itemDao.findOne(newItem.getId());
            newPrice.setItem(newItem);

            String dp = "$" + newPrice.getOrigNum().setScale(2).toPlainString() + " / " + newPrice.getCurrMeasure().getName();
            newPrice.setDispOrigPrice(dp);
            newPrice.setDispConvPrice(dp);

            priceDao.save(newPrice);
            newItem.setPriceId(newPrice.getId());
        }

        itemDao.save(newItem);

        return "redirect:";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String displayUpdateItemForm(@PathVariable int id, Model model) {

        Item item = itemDao.findOne(id);

        model.addAttribute("title", "Update " + item.getName());
        model.addAttribute(new UpdateItemForm(item.getName(), item.getNotes()));
        model.addAttribute("itemMeasures", ItemMeasure.values());
        model.addAttribute("stores", storeDao.findAll());

        return "item/update";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String processUpdateItemForm(@PathVariable int id, @ModelAttribute @Valid UpdateItemForm theForm, Errors errors, @RequestParam int storeId, Model model) {

        Item item = itemDao.findOne(id);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Update " + item.getName());
            model.addAttribute("itemMeasures", ItemMeasure.values());
            model.addAttribute("stores", storeDao.findAll());

            return "item/update";
        }

        item.setName(theForm.getItemName());
        item.setNotes(theForm.getItemNotes());

        BigDecimal priceNum = theForm.getPriceNumber();
        if (priceNum != null) {

            int itemBestPrice = item.getPriceId();
            ItemMeasure formMeasure = theForm.getMeasure();
            String priceAisle = theForm.getPriceAisle();

            if (itemBestPrice == 0) {
                Price newPrice = new Price();
                newPrice.setOrigNum(priceNum);
                newPrice.setConvNum(priceNum);
                newPrice.setAisle(priceAisle);
                newPrice.setCurrMeasure(formMeasure);
                Store st = storeDao.findOne(storeId);
                newPrice.setStore(st);
                newPrice.setItem(item);

                String dp = "$" + newPrice.getOrigNum().setScale(2).toPlainString() + " / " + newPrice.getCurrMeasure().getName();
                newPrice.setDispOrigPrice(dp);
                newPrice.setDispConvPrice(dp);

                priceDao.save(newPrice);
                item.setPriceId(newPrice.getId());
                itemDao.save(item);

                return "redirect:/items/view/" + id;
            }

            if ( ! formMeasure.checkCompatible( priceDao.findOne(itemBestPrice).getCurrMeasure() ) ) {
                model.addAttribute("title", "Update " + item.getName());
//                TODO: error message for incompatible unit of measure
                model.addAttribute("itemMeasures", ItemMeasure.values());
                model.addAttribute("stores", storeDao.findAll());

                return "item/update";
            }

            Price newPrice = new Price();
            newPrice.setOrigNum(priceNum);
            newPrice.setConvNum(priceNum);
            newPrice.setAisle(priceAisle);
            newPrice.setCurrMeasure(formMeasure);
            Store st = storeDao.findOne(storeId);
            newPrice.setStore(st);
            newPrice.setItem(item);

            String op = "$" + newPrice.getOrigNum().setScale(2).toPlainString() + " / " + newPrice.getCurrMeasure().getName();
            newPrice.setDispOrigPrice(op);

            newPrice.convertMeasure(priceDao.findOne(itemBestPrice).getCurrMeasure());
            if (newPrice.getConvNum().compareTo(new BigDecimal("0")) == 0) {
                String cp = "$" + newPrice.getConvNum().setScale(2).toPlainString() + " / " + newPrice.getCurrMeasure().getName();
                newPrice.setDispConvPrice(cp);
            } else {
                String cp = "$" + newPrice.getConvNum().setScale(2, RoundingMode.CEILING).toPlainString() + " / " + newPrice.getCurrMeasure().getName();
                newPrice.setDispConvPrice(cp);
            }

            priceDao.save(newPrice);

            HashMap<Store, Price> storesCurrPrices = new HashMap<>();
            List<Price> itemPriceList = item.getPrices();
            for (Price price : itemPriceList) {
                Store store = price.getStore();
                if (! storesCurrPrices.containsKey(store)) {
                    storesCurrPrices.put(store, price);
                }
            }

            itemBestPrice = newPrice.getId();
            BigDecimal newBestPrice = newPrice.getConvNum();
            for (Map.Entry<Store, Price> price : storesCurrPrices.entrySet()) {
                BigDecimal currPrice = price.getValue().getConvNum();
                if (currPrice.compareTo(newBestPrice) == -1) {
                    newBestPrice = currPrice;
                    itemBestPrice = price.getValue().getId();
                }
            }
            item.setPriceId(itemBestPrice);
        }

        itemDao.save(item);

        return "redirect:/items/view/" + id;
    }
}
