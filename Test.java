package com.example.admin.myrecipes;

/**
 * Created by Admin on 2017-05-04.
 */

public class Test {

    public String name;

    public Test (String n) {
        String name = n;
        MyRecipe myRecipe = new MyRecipe();
        myRecipe.makeRadioButtons(n);
    }

public static void main (String [] args)
{
    Test test1 = new Test ("paluszki");
    Test test2 = new Test ("gowno");
    Test test3 = new Test ("mienso");

}
}
