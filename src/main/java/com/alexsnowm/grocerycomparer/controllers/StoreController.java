package com.alexsnowm.grocerycomparer.controllers;

import com.alexsnowm.grocerycomparer.models.State;
import com.alexsnowm.grocerycomparer.models.Store;
import com.alexsnowm.grocerycomparer.models.data.StateDao;
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
@RequestMapping(value = "stores")
public class StoreController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StateDao stateDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "All Stores");
        model.addAttribute("stores", storeDao.findAll());

        return "store/index";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String displayCreateStoreForm(Model model) {
        model.addAttribute("title", "Create Store");
        model.addAttribute(new Store());
        model.addAttribute("states", stateDao.findAll());

        return "store/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String processCreateStoreForm(@ModelAttribute @Valid Store newStore, Errors errors, @RequestParam int stateId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Store");
            model.addAttribute("states", stateDao.findAll());

            return "store/create";
        }

        State st = stateDao.findOne(stateId);
        newStore.setState(st);

        String addressStr = "";
        boolean streetFill = ! newStore.getStreet().isEmpty();
        boolean cityFill = ! newStore.getCity().isEmpty();
        boolean stateFill = ! newStore.getState().getName().isEmpty();
        boolean zipcodeFill = ! newStore.getZipcode().isEmpty();
        if (streetFill) {
            addressStr += newStore.getStreet();
            if (cityFill || stateFill || zipcodeFill) {
                addressStr += ", ";
            }
        } if (cityFill) {
            addressStr += newStore.getCity();
            if (stateFill) {
                addressStr += ", ";
            } else if (zipcodeFill) {
                addressStr += " ";
            }
        } if (stateFill) {
            addressStr += newStore.getState().getName();
            if (zipcodeFill) {
                addressStr += " ";
            }
        } if (zipcodeFill) {
            addressStr += newStore.getZipcode();
        }
        newStore.setAddress(addressStr);

        storeDao.save(newStore);

        return "redirect:";
    }
}
