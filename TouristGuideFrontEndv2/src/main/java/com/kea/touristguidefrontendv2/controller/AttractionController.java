package com.kea.touristguidefrontendv2.controller;

import com.kea.touristguidefrontendv2.model.Attraction;
import com.kea.touristguidefrontendv2.service.AttractionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("")
    public String displayAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAttractions();
        if (attractions.isEmpty()) {
            return "redirect:/errorPage";
        }
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
        if (attraction.getName().isEmpty() || attraction.getName() == null) {
            return "redirect:/errorPage";
        }
        attractionService.addAttraction(attraction.getName(),
                attraction.getDescription(), attraction.getTags(), attraction.getCity());
        return "redirect:/";
    }
/*

    @GetMapping("/edit/{name}")
    public String displayEditForm(@PathVariable String name, Model model) {
        Attraction attraction = attractionService.getByName(name);
        if (attraction == null) {
            return "redirect:/errorPage";
        }
        model.addAttribute("attraction",attraction);
        return "editForm";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute Attraction attraction) {
        if (attraction == null || attraction.getName().isEmpty()) {
            return "redirect:/errorPage";
        }
        attractionService.updateAttraction(attraction.getName(), attraction.getDescription(),
                attraction.getTags(), attraction.getCity());

        return "redirect:/";
    }

    @GetMapping("/tags/{name}")
    public String displayTags(@PathVariable String name, Model model) {
        Attraction attraction = attractionService.getByName(name);
        if (attraction == null) {
            return "errorPage";
        }
        model.addAttribute("attraction", attraction);
        return "tags";
    }

    @GetMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        attractionService.deleteAttraction(name);
        if (attractionService.getByName(name) != null) {
            return "redirect:/errorPage";
        }
        return "redirect:/";
    }
    @GetMapping("/errorPage")
    public String displayErrorPage() {
        return "errorPage";
    }

 */
}
