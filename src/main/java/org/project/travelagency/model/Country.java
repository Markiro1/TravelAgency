package org.project.travelagency.model;

public enum Country {
    UKRAINE("Ukraine"),
    USA("United States of America"),
    ARGENTINA("Argentina"),
    AUSTRALIA("Australia"),
    AUSTRIA("Austria"),
    BELGIUM("Belgium"),
    BRAZIL("Brazil"),
    COLOMBIA("Colombia"),
    CROATIA("Croatia"),
    EGYPT("Egypt"),
    FINLAND("Finland"),
    FRANCE("France"),
    GERMANY("Germany"),
    GREECE("Greece"),
    ITALY("Italy"),
    LUXEMBOURG("Luxembourg"),
    MONACO("Monaco"),
    SPAIN("Spain"),
    SWEDEN("Sweden"),
    SWITZERLAND("Switzerland");

    private String country;

    Country(String country) {
        this.country = country;
    }
}