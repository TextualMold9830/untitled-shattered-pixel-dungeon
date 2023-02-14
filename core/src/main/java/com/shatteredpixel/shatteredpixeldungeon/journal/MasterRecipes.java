package com.shatteredpixel.shatteredpixeldungeon.journal;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.masterstoolkit.UnlockedRecipes;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class MasterRecipes extends Item {
    public static final String RECIPE="RECIPE";
    {
        image = ItemSpriteSheet.ALCH_PAGE;
    }
    public Recipe recipe;
   public MasterRecipes(Recipe recipe){
       this.recipe=recipe;
   }
    @Override
    public final boolean doPickUp(Hero hero, int pos) {
        UnlockedRecipes.foundRecipes.add(this.recipe);
        UnlockedRecipes.toBeFoundRecipes.remove(this.recipe);
        return true;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(RECIPE,this.recipe.getClass());
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        this.recipe = (Recipe) bundle.get(RECIPE);

    }
}
