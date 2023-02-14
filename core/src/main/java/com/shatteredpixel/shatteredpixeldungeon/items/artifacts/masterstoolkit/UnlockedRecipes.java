package com.shatteredpixel.shatteredpixeldungeon.items.artifacts.masterstoolkit;

import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;

import java.util.ArrayList;
import java.util.List;

public class UnlockedRecipes {
    public static List<Recipe> foundRecipes = new ArrayList<Recipe>();
    public static List<Recipe> toBeFoundRecipes = new ArrayList<Recipe>();
    public static List<Recipe> toBeGeneratedRecipes = new ArrayList<Recipe>();


    public static void init(){
        toBeFoundRecipes.add((CovidMask.Recipe));
        toBeGeneratedRecipes.add(CovidMask.Recipe);
    }

}
