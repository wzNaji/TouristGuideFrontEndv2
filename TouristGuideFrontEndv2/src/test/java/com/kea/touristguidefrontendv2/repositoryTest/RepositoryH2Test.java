/*package com.kea.touristguidefrontendv2.repositoryTest;

import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.repository.AttractionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RepositoryH2Test {

    @Autowired
    private AttractionRepository attractionRepository;

    @Test
    void testAddAttraction() {
        // Arrange
        String name = "Test Attraction";
        String description = "This is a test description";
        String city = "Copenhagen";
        List<String> tags = Arrays.asList("Family friendly", "Historic");

        // Act
        attractionRepository.addAttraction(name, description, tags, city);
        Attraction fetchedAttraction = attractionRepository.getByName(name);

        // Assert
        assertNotNull(fetchedAttraction, "The fetched attraction should not be null.");
        assertEquals(name, fetchedAttraction.getName(), "The name of the attraction does not match.");
        assertEquals(description, fetchedAttraction.getDescription(), "The description of the attraction does not match.");
        assertEquals(city, fetchedAttraction.getCity(), "The city of the attraction does not match.");

        // Additional check to ensure all tags are correctly linked to the attraction
        assertNotNull(fetchedAttraction.getTags(), "The tags list should not be null.");
        assertFalse(fetchedAttraction.getTags().isEmpty(), "The tags list should not be empty.");
        assertTrue(fetchedAttraction.getTags().containsAll(tags), "The fetched tags do not match the expected values.");

    }
}

 */