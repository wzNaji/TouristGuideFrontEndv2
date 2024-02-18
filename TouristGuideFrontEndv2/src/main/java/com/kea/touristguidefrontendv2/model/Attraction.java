package com.kea.touristguidefrontendv2.model;

public class Attraction {

    private String name;
    private String description;

    public Attraction(String name, String description) {
        this.name = name;
        this.description = description;
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
                '}';
    }
}
