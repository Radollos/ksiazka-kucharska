package com.example.klaudia.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Klaudia on 2017-05-29.
 */

public class PagerAdapter extends FragmentPagerAdapter
{
    private final int NUM_ITEM = 3;

    public PagerAdapter(FragmentManager manager)
    {
        super(manager);
    }


    @Override
    public int getCount()
    {
        return NUM_ITEM;
    }

    @Override
    public Fragment getItem(int positon)
    {
        switch (positon)
        {
            case 0:
                return new RecipeView1();
            case 1:
                return new RecipeView2();
            case 2:
                return new RecipeView3();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Ingredients";
            case 1:
                return "Instruction";
            case 2:
                return "Description";
            default:
                return "";
        }
    }

}
