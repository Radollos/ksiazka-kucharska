package com.example.klaudia.myapplication;

import java.util.ArrayList;

/**
 * Created by Radek on 2017-05-09.
 */

public class InstructionStep {
    private int number;
    private String step;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Equipment> equipment;


    public InstructionStep(int number, String step, ArrayList<Ingredient> ingredients, ArrayList<Equipment> equipment){
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.equipment = equipment;
    }

    public int getNumber() {
        return number;
    }

    public String getStep() {
        return step;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }
}
