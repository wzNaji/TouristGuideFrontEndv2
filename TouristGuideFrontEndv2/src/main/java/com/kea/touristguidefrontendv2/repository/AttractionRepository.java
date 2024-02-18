package com.kea.touristguidefrontendv2.repository;

import com.kea.touristguidefrontendv2.model.Attraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository

public class AttractionRepository {

    private final List<Attraction> attractions;

    public AttractionRepository() {
        attractions = new ArrayList<>();
        attractions.add(new Attraction("Tårn", "Højt tårn"));
        attractions.add(new Attraction("Hus", "Flot"));
    }

    // GET ALL
    public List<Attraction> getAttractions() {
        return attractions;
    }

    // GET BY NAME
    public Attraction getByName(String name) {
        for (Attraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    // ADD TO LIST
    public void addAttraction(String name, String description) {
        Attraction attraction = new Attraction();
        attraction.setName(name);
        attraction.setDescription(description);
        attractions.add(attraction);
    }

    // UPDATE ATTRACTION
    public void updateAttraction(String name, String description) {
        Attraction attraction = getByName(name);
        attraction.setDescription(description);
    }

    // DELETE
    public void deleteAttraction(String name) {
        Attraction attraction = getByName(name);
        attractions.remove(attraction);
    }

}
