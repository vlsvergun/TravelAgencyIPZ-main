package com.example.travelagencyipz.model;

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
    ROMANIA("Romania"),
    SPAIN("Spain"),
    SWEDEN("Sweden"),
    SWITZERLAND("Switzerland"),
    TURKEY("Turkey");

    private String country;

    Country(String country) {
        this.country = country;
    }

    public String getUrl() {
        return country;
    }
}
