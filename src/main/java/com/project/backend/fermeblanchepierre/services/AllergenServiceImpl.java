package com.project.backend.fermeblanchepierre.services;

import com.project.backend.fermeblanchepierre.entities.Allergen;
import com.project.backend.fermeblanchepierre.repositories.AllergenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;



@Service
public class AllergenServiceImpl implements AllergenService{
    @Autowired
    private AllergenRepository aR;

    public Set<Allergen> getAllergens() {
        return new HashSet<>((Collection<Allergen>) aR.findAll());
    }

    public Allergen getAllergenById(Integer id){
        return aR.findById(id).orElse(null);
    }

    public String deleteAllergenById(Integer id){
        try {
            aR.deleteById(id);
            return "Allergen removed";
        }catch (EmptyResultDataAccessException e){
            return "The allergen you want to delete does not exist";
        }
    }

    public String deleteAllAllergens() {
        try {
            aR.deleteAll();
            return "Allergens table is cleared";
        }catch (EmptyResultDataAccessException e){
            return "Allergens clear function failed";
        }
    }

    public Allergen save(Allergen allergen) { return aR.save(allergen); }

    public Allergen updateAllergenById(Integer id, Allergen newAllergen) {
        Allergen oldAllergen = aR.findById(id).orElse(null);
        oldAllergen.setLabel(newAllergen.getLabel());
        return aR.save(oldAllergen);
    }

    public Iterable<Allergen> saveAll(Set<Allergen> allergenList) { return aR.saveAll(allergenList); }

}
