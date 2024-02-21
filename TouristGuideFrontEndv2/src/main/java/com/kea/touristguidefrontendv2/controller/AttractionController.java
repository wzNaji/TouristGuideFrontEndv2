package com.kea.touristguidefrontendv2.controller;

import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.service.AttractionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("/attractions")
    public String displayAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

    @GetMapping("/add")
    public String displayAddForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        return "addForm";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute Attraction attraction) {
        if (attraction.getName().isEmpty()) {
            return "errorPage";
        }
        attractionService.addAttraction(attraction.getName(),
                attraction.getDescription(), attraction.getTags(), attraction.getCity());
        return "redirect:/attractions";
    }


    @GetMapping("/edit/{name}")
    public String displayEditForm(@PathVariable String name, Model model) {
        model.addAttribute("attraction", attractionService.getByName(name));
        return "editForm";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute Attraction attraction) {
        attractionService.updateAttraction(attraction.getName(), attraction.getDescription(),
                attraction.getTags(), attraction.getCity());

        return "redirect:/attractions";
    }

    @GetMapping("/tags/{name}")
    public String displayTags(@PathVariable String name, Model model) {
        Attraction attraction = attractionService.getByName(name);
        model.addAttribute("attraction", attraction);
        return "tags";
    }

    @GetMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        attractionService.deleteAttraction(name);
        return "redirect:/attractions";
    }


}
