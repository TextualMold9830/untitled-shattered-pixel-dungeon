package com.shatteredpixel.shatteredpixeldungeon.items.artifacts.masterstoolkit;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ConfusionGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StenchGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StormCloud;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

import java.util.ArrayList;

public class CovidMask extends Item {
    public static final String AC_USE="USE";
    public static com.shatteredpixel.shatteredpixeldungeon.items.Recipe Recipe;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<>();
        actions.add( AC_DROP );
        actions.add( AC_THROW );
        actions.add(AC_USE);
        return actions;
    }
    public void execute( Hero hero, String action ) {

        GameScene.cancel();
        curUser = hero;
        curItem = this;

        if (action.equals( AC_DROP )) {

            if (hero.belongings.backpack.contains(this) || isEquipped(hero)) {
                doDrop(hero);
            }

        } else if (action.equals( AC_THROW )) {

            if (hero.belongings.backpack.contains(this) || isEquipped(hero)) {
                doThrow(hero);
            }

        } else if (action.equals(AC_USE)) {
            Buff.affect(hero,GasImunity.class);
        }
    }

    public static class GasImunity extends Buff {

        {
            type = buffType.POSITIVE;
        }

        public static final float DURATION	= 20f;





        {
            //all harmful gasses
            immunities.add( ConfusionGas.class );
            immunities.add( CorrosiveGas.class );
            immunities.add( ParalyticGas.class );
            immunities.add( StenchGas.class );
            immunities.add( StormCloud.class );
            immunities.add( ToxicGas.class );
        }

    }
    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe{

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean leather = false;
            boolean purity = false;
            boolean cleansing = false;
            if (UnlockedRecipes.foundRecipes.contains(this)&&!Dungeon.hero.buffs().contains(GasImunity.class)){

                for (Item ingredient : ingredients){
                    if (ingredient.quantity() > 0) {
                        if (ingredient instanceof LeatherArmor) {
                            leather = true;
                        } else if (ingredient.getClass() == PotionOfPurity.class) {
                            purity = true;
                        } else if (ingredient.getClass()==PotionOfCleansing.class) {
                            cleansing = true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 20;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;

            for (Item ingredient : ingredients){
                ingredient.quantity(ingredient.quantity() - 1);
            }

            return sampleOutput(null);
        }

        public Item sampleOutput(ArrayList<Item> ingredients) {
            return new CovidMask();
        }

    }
}
