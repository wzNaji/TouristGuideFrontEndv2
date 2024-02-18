package com.kea.touristguidefrontendv2.model;

import java.util.ArrayList;
import java.util.List;

public class Attraction {

    private String name;
    private String description;
    private String city;

    private List<String> tags;

    public Attraction(String name, String description, List<String> tags, String city) {
        this.name = name;
        this.description = description;
        this.tags = new ArrayList<>(tags);
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void addTags(String tag) {
        tags.add(tag);
    }
    public void deleteTag(String tagToDelete) {
        tags.removeIf(tag -> tag.equalsIgnoreCase(tagToDelete));
    }
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Attraction() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", tags=" + tags +
                '}';
    }
}
