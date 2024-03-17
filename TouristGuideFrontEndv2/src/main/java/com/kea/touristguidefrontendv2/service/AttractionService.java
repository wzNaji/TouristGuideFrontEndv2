package com.kea.touristguidefrontendv2.service;

import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.repository.AttractionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    public List<Attraction> getAttractions() {
        if (attractionRepository.getAttractions().isEmpty()) {
            return new ArrayList<>();
        }
        return attractionRepository.getAttractions();
    }

    public Attraction getByName(String name) {
        return attractionRepository.getByName(name);
    }

    public void addAttraction(String name, String description, List<String> tags, String city) {
        attractionRepository.addAttraction(name, description, tags,city);
    }

    public void updateAttraction(String name, String description, List<String> tags,String city) {
        attractionRepository.updateAttraction(name,description,tags,city);
    }
/*
    public void deleteAttraction(String name) {
        attractionRepository.deleteAttraction(name);
    }

 */

}
