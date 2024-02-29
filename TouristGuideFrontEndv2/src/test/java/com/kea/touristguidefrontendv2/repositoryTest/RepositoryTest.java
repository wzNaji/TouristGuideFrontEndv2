package com.kea.touristguidefrontendv2.repositoryTest;

import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.repository.AttractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class RepositoryTest {

    private AttractionRepository attractionRepository;

    @BeforeEach
    void setUp() {
        attractionRepository = new AttractionRepository();
    }

    @Test
    void testGetAttractions() {
        List<Attraction> attractions = attractionRepository.getAttractions();
        assertNotNull(attractions);
        assertFalse(attractions.isEmpty());
        assertEquals(2, attractions.size());
    }

    @Test
    void testGetByNameFound() {
        Attraction attraction = attractionRepository.getByName("Tårn");
        assertNotNull(attraction);
        assertEquals("Tårn", attraction.getName());
    }

    @Test
    void testGetByNameNotFound() {
        Attraction attraction = attractionRepository.getByName("NonExisting");
        assertNull(attraction);
    }

    @Test
    void testAddAttraction() {
        attractionRepository.addAttraction("NewAttraction", "Description", Arrays.asList("Tag1", "Tag2"), "City");
        Attraction newAttraction = attractionRepository.getByName("NewAttraction");
        assertNotNull(newAttraction);
        assertEquals("NewAttraction", newAttraction.getName());
    }

    @Test
    void testUpdateAttraction() {
        String updatedDescription = "Updated Description";
        attractionRepository.updateAttraction("Tårn", updatedDescription, Arrays.asList("Updated", "Tags"), "Updated City");
        Attraction updatedAttraction = attractionRepository.getByName("Tårn");
        assertNotNull(updatedAttraction);
        assertEquals(updatedDescription, updatedAttraction.getDescription());
    }

    @Test
    void testDeleteAttraction() {
        attractionRepository.deleteAttraction("Lars");
        assertNull(attractionRepository.getByName("Lars"));
    }
}
