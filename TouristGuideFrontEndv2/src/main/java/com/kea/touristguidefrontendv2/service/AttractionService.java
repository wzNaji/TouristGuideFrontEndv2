package com.kea.touristguidefrontendv2.service;

import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.repository.AttractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    public List<Attraction> getAttractions() {
        return attractionRepository.getAttractions();
    }

    public Attraction getByName(String name) {
        return attractionRepository.getByName(name);
    }

    public void addAttraction(String name, String description) {
        attractionRepository.addAttraction(name, description);
    }

    public void updateAttraction(String name, String description) {
        attractionRepository.updateAttraction(name,description);
    }

    public void deleteAttraction(String name) {
        attractionRepository.deleteAttraction(name);
    }

}