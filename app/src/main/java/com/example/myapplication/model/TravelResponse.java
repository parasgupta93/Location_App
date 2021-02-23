package com.example.myapplication.model;

import java.util.ArrayList;

public class TravelResponse {
    private String cust_name;
    ArrayList< LocationStructure > locations = new ArrayList < LocationStructure>();


    // Getter Methods

    public String getCust_name() {
        return cust_name;
    }

    // Setter Methods

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public ArrayList<LocationStructure> getLocations() {
        return locations;
    }

    public void setArticles(ArrayList<LocationStructure> articles) {
        this.locations = locations;
    }
}
