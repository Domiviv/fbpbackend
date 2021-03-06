package com.project.backend.fermeblanchepierre.controllers;

import com.project.backend.fermeblanchepierre.entities.Allergen;
import com.project.backend.fermeblanchepierre.entities.Item;
import com.project.backend.fermeblanchepierre.entities.ItemContains;
import com.project.backend.fermeblanchepierre.services.ItemContainsServiceImpl;
import com.project.backend.fermeblanchepierre.services.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/item")
public class ItemController {

    // Déclaration des services appelés
    @Autowired
    private ItemServiceImpl iSI;

    @Autowired
    private ItemContainsServiceImpl iCSI;


    // GET MAPPINGS

    @GetMapping("/all")
    private List<Item> getAllItems() {
        return iSI.getAllItems();
    }

    @GetMapping("/{id}")
    private Item getItemById(@PathVariable Integer id) {
        return iSI.getItemById(id);
    }


    // POST MAPPINGS

    // Récupère la liste de tous les produits
    @PostMapping("/add-list")
    private List<Item> addItemList(@RequestBody List<Item> itemList) {
        return iSI.addItemList(itemList);
    }

    // Récupère un produit sur base de son id
    @PostMapping("/add")
    private Boolean addItem(@RequestBody Item item) {

        // CREATE & SAVE NEW ITEM
        Item newItem = new Item();
        newItem.setLabel(item.getLabel());
        newItem.setDescr(item.getLabel());
        newItem.setPrice(item.getPrice());
        newItem.setQt(item.getQt());
        newItem.setMeasure(item.getMeasure());

        Item forAllergens = iSI.addItem(newItem);

        // CREATE & SAVE ITEMCONTAINS FOR ALLERGENS;
        if(forAllergens != null) {
            if(item.getAllergens() != null) {
                ItemContains itemContains = new ItemContains();
                itemContains.setIdItem(forAllergens.getIdItem());
                for(Allergen allergen: item.getAllergens()){
                    itemContains.setIdAllergen(allergen.getIdAllergen());
                    iCSI.addItemContains(itemContains);
                }
            }
        }
        // IF NO ITEM CREATED
        else{ return false; }
        return true;
    }


    // DELETE MAPPINGS
    // Supprime produit sur base de son id
    @DeleteMapping("/delete/{id}")
    private String deleteItemById(@PathVariable Integer id) {
        return iSI.deleteItemById(id);
    }

    // Vide la database des produits
    @DeleteMapping("/delete/all")
    private String deleteAllItems() {
        return iSI.deleteAllItems();
    }


    // PUT MAPPINGS

    // Modifie un produit sur base de son id
    @PutMapping("/update/{id}")
    private Boolean updateItemById(@PathVariable Integer id, @RequestBody Item newItem) {
        return iSI.updateItemById(id, newItem);
    }

}

