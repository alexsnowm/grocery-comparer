package com.alexsnowm.grocerycomparer.controllers;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.data.ItemDao;
import com.alexsnowm.grocerycomparer.models.data.ItemMeasureDao;
import com.alexsnowm.grocerycomparer.models.data.StoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "items")
public class ItemController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemMeasureDao itemMeasureDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "All Items");
        model.addAttribute("items", itemDao.findAll());

        return "item/index";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String displayCreateStoreForm(Model model) {
        model.addAttribute("title", "Create Item");
        model.addAttribute(new Item());
        model.addAttribute("states", storeDao.findAll());

        return "item/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreateStoreForm(@ModelAttribute @Valid Item item, Errors errors, @RequestParam int storeid, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Item");
            model.addAttribute("stores", storeDao.findAll());

            return "item/create";
        }
//        Store st = storeDao.findOne(storeid);
//        item.setStores(st);
        itemDao.save(item);

        return "redirect:";
    }
}
