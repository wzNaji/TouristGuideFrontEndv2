package com.kea.touristguidefrontendv2.ControllerTest;

import com.kea.touristguidefrontendv2.controller.AttractionController;
import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AttractionController.class)
public class AttractionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttractionService attractionService;

    @Test
    void testDisplayAttractionsWithNonEmptyList() throws Exception {
        // Arrange
        List<Attraction> mockAttractions = List.of(new Attraction());
        when(attractionService.getAttractions()).thenReturn(mockAttractions);

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions"))
                .andExpect(model().attribute("attractions", is(mockAttractions)));

        verify(attractionService).getAttractions();
    }

    @Test
    void testDisplayAttractionsWithEmptyList() throws Exception {
        // Arrange
        when(attractionService.getAttractions()).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errorPage"));

        verify(attractionService).getAttractions();
    }
    @Test
    void testDisplayAddForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addForm"))
                .andExpect(model().attributeExists("attraction"));
    }

    @Test
    void testSaveAttraction() throws Exception {
        Attraction attraction = new Attraction();
        attraction.setName("Test Attraction");
        attraction.setDescription("Test Description");
        attraction.setTags(List.of("Test Tag", "Test Tag 2"));
        attraction.setCity("Test City");

        mockMvc.perform(post("/save")
                        .flashAttr("attraction", attraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(attractionService).addAttraction(eq("Test Attraction"),
                        eq("Test Description"), eq(List.of("Test Tag",
                        "Test Tag 2")), eq("Test City"));
    }

    @Test
    void testDisplayEditForm() throws Exception {
        when(attractionService.getByName("Test Attraction")).thenReturn(new Attraction());

        mockMvc.perform(get("/edit/{name}", "Test Attraction"))
                .andExpect(status().isOk())
                .andExpect(view().name("editForm"))
                .andExpect(model().attributeExists("attraction"));
    }

    @Test
    void testUpdateAttraction() throws Exception {
        mockMvc.perform(post("/update")
                        .flashAttr("attraction", new Attraction("Test Name", "Test Description", List.of("Test Tag"),"Test City")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(attractionService).updateAttraction(anyString(), anyString(), anyList(), anyString());
    }

    @Test
    void testDisplayTags() throws Exception {
        when(attractionService.getByName("Test Attraction")).thenReturn(new Attraction());

        mockMvc.perform(get("/tags/{name}", "Test Attraction"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attributeExists("attraction"));
    }

    @Test
    void testDeleteAttraction() throws Exception {
        when(attractionService.getByName("Test Attraction")).thenReturn(null);

        mockMvc.perform(get("/delete/{name}", "Test Attraction"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(attractionService).deleteAttraction("Test Attraction");
    }

    @Test
    void testDisplayErrorPage() throws Exception {
        mockMvc.perform(get("/errorPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"));
    }
}

