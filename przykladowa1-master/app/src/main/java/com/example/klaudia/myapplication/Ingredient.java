package com.example.klaudia.myapplication;

import java.util.ArrayList;

/**
 * Created by Klaudia on 2017-04-09.
 */

public class Ingredient
{
    // not uet used
    private int id;
    private String aisle;
    private String imageURL;
    private String unitShort;
    private String unitLong;

    // used
    private String name;
    private double amount;
    private String unit;
    private String originalString;
    private ArrayList<String> metaInformation;

    public Ingredient(int id, String aisle, String image, String name, double amount, String unit, String unitShort, String unitLong, String originalString, ArrayList<String> metaInformation)
    {
        this.id = id;
        this.aisle = aisle;
        this.imageURL = image;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.unitShort = unitShort;
        this.unitLong = unitLong;
        this.originalString = originalString;
        this.metaInformation = metaInformation;
    }

    public int getId() {return id;}

    public String getAisle() {return aisle;}

    public String getImageURL() {return imageURL;}

    public String getUnitShort() {return unitShort;}

    public String getUnitLong() {return unitLong;}

    public String getName() {return name;}

    public double getAmount() {return amount;}

    public String getUnit() {return unit;}

    public String getOriginalString() {return originalString;}

    public ArrayList<String> getMetaInformation() {return metaInformation;}
}
