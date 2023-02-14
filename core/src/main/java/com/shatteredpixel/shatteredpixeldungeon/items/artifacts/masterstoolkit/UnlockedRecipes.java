package com.shatteredpixel.shatteredpixeldungeon.items.artifacts.masterstoolkit;

import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;

import java.util.ArrayList;
import java.util.List;

public class UnlockedRecipes {
    public static List<Recipe> foundRecipes = new ArrayList<>();
    public static List<Recipe> toBeFoundRecipes = new ArrayList<>();
    public static List<Recipe> toBeGeneratedRecipes = new ArrayList<>();


    public static void init(){
        toBeFoundRecipes.add((CovidMask.Recipe));
        toBeGeneratedRecipes.add(CovidMask.Recipe);
    }
    public static void clear(){
        foundRecipes.clear();
        toBeFoundRecipes.clear();
        toBeGeneratedRecipes.clear();
    }

}
