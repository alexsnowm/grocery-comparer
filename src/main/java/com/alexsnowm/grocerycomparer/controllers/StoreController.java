package com.alexsnowm.grocerycomparer.controllers;

import com.alexsnowm.grocerycomparer.models.Item;
import com.alexsnowm.grocerycomparer.models.Price;
import com.alexsnowm.grocerycomparer.models.State;
import com.alexsnowm.grocerycomparer.models.Store;
import com.alexsnowm.grocerycomparer.models.data.ItemDao;
import com.alexsnowm.grocerycomparer.models.data.StateDao;
import com.alexsnowm.grocerycomparer.models.data.StoreDao;
import com.alexsnowm.grocerycomparer.models.forms.DisplayItemObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "stores")
public class StoreController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "All Stores");
        model.addAttribute("stores", storeDao.findAll());

        return "store/index";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewStore(@PathVariable int id, Model model) {

        Store store = storeDao.findOne(id);

        List<Price> priceList = store.getPrices();

        HashMap<Item, Price> storesCurrPrices = new HashMap<>();
        for (Price price : priceList) {
            Item item = price.getItem();
            if (! storesCurrPrices.containsKey(item)) {
                storesCurrPrices.put(item, price);
            }
        }

        Collection<Price> priceCollection = storesCurrPrices.values();
        priceList = new ArrayList(priceCollection);

        priceList.sort((Price o1, Price o2) -> o1.getItem().getName().compareToIgnoreCase(o2.getItem().getName()));

        model.addAttribute("title", store.getName());
        model.addAttribute("store", store);
        model.addAttribute("priceList", priceList);

        return "store/view";
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

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String displayUpdateStoreForm(@PathVariable int id, Model model) {

        Store store = storeDao.findOne(id);

        model.addAttribute("title", "Update " + store.getName());
        model.addAttribute("store", store);
        model.addAttribute("states", stateDao.findAll());

        return "store/update";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String processUpdateStoreForm(@PathVariable int id, @ModelAttribute @Valid Store newStore, Errors errors, @RequestParam int stateId, Model model) {

        Store store = storeDao.findOne(id);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Update " + store.getName());
            model.addAttribute("states", stateDao.findAll());

            return "store/update";
        }

        State st = stateDao.findOne(stateId);
        store.setState(st);

        store.setName(newStore.getName());
        store.setStreet(newStore.getStreet());
        store.setCity(newStore.getCity());
        store.setZipcode(newStore.getZipcode());
        store.setTel(newStore.getTel());
        store.setWebsite(newStore.getWebsite());
        store.setNotes(newStore.getNotes());

        String addressStr = "";
        boolean streetFill = ! store.getStreet().isEmpty();
        boolean cityFill = ! store.getCity().isEmpty();
        boolean stateFill = ! store.getState().getName().isEmpty();
        boolean zipcodeFill = ! store.getZipcode().isEmpty();
        if (streetFill) {
            addressStr += store.getStreet();
            if (cityFill || stateFill || zipcodeFill) {
                addressStr += ", ";
            }
        } if (cityFill) {
            addressStr += store.getCity();
            if (stateFill) {
                addressStr += ", ";
            } else if (zipcodeFill) {
                addressStr += " ";
            }
        } if (stateFill) {
            addressStr += store.getState().getName();
            if (zipcodeFill) {
                addressStr += " ";
            }
        } if (zipcodeFill) {
            addressStr += store.getZipcode();
        }
        store.setAddress(addressStr);

        storeDao.save(store);

        return "redirect:/stores/view/" + id;
    }

    @RequestMapping(value = "update/{id}/delete", method = RequestMethod.GET)
    public String displayRemoveStoreConfirm(@PathVariable int id, Model model) {

        Store store = storeDao.findOne(id);

        model.addAttribute("title", "Confirm deletion of " + store.getName() + "?");
        model.addAttribute("storeObj", store);

        return "store/delete";
    }

    @RequestMapping(value = "update/{id}/delete", method = RequestMethod.POST)
    public String processRemoveStoreConfirm(@PathVariable int id) {

        Store store = storeDao.findOne(id);

        List<Price> priceList = store.getPrices();
        List<Integer> storePriceIntList = new ArrayList<>();
        for (Price price : priceList) {
            storePriceIntList.add(price.getId());
        }
        storeDao.delete(id);

        Iterable<Item> allItems = itemDao.findAll();
        for (Item item : allItems) {
            if (storePriceIntList.contains(item.getPriceId())) {
                List<Price> itemPriceList = item.getPrices();
                if (itemPriceList.isEmpty()) {
                    item.setPriceId(0);
                    itemDao.save(item);
                } else {
                    HashMap<Store, Price> storesCurrPrices = new HashMap<>();
                    for (Price price : itemPriceList) {
                        Store offeringStore = price.getStore();
                        if (! storesCurrPrices.containsKey(offeringStore)) {
                            storesCurrPrices.put(offeringStore, price);
                        }
                    }

                    Price mostRecentPrice = itemPriceList.get(0);
                    int itemBestPrice = mostRecentPrice.getId();
                    BigDecimal newBestPrice = mostRecentPrice.getConvNum();
                    for (Map.Entry<Store, Price> price : storesCurrPrices.entrySet()) {
                        BigDecimal currPrice = price.getValue().getConvNum();
                        if (currPrice.compareTo(newBestPrice) == -1) {
                            newBestPrice = currPrice;
                            itemBestPrice = price.getValue().getId();
                        }
                    }
                    item.setPriceId(itemBestPrice);
                    itemDao.save(item);
                }
            }
        }

        return "redirect:/stores";
    }
}
