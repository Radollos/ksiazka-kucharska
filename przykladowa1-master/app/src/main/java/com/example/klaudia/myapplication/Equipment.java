package com.example.klaudia.myapplication;

/**
 * Created by Radek on 2017-05-09.
 */

public class Equipment {

    private int id;
    private String name;
    private String image;

    public Equipment(int id, String name, String image){
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
