package com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public class ElixirOfUpgrade extends Elixir{

    {
        image = ItemSpriteSheet.ELIXIR_ICY;
    }

    @Override
    public void apply(Hero hero) {
        curUser = hero;
        GameScene.selectItem(itemSelector);
    }



    @Override
    protected int splashColor() {
        return 0xFF18C3E6;
    }

    @Override
    public int value() {
        //prices of ingredients
        return quantity * 400;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{PotionOfExperience.class, AlchemicalCatalyst.class};
            inQuantity = new int[]{1, 1};

            cost = 10;

            output = ElixirOfUpgrade.class;
            outQuantity = 1;
        }

    }
    private final WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {
        @Override
        public String textPrompt() {
            return null;
        }

        @Override
        public boolean itemSelectable(Item item) {
            return (item.isUpgradable() &&item instanceof Weapon||item instanceof Armor|| item instanceof Ring);
        }

        @Override
        public void onSelect(Item item) {
            if (item != null) {
                if (item instanceof Armor || item instanceof Weapon || item instanceof Ring) {
                    if (item.isUpgradable()) {
                        item.upgrade();
                        item.upgrade();
                        GLog.p(Messages.get(ScrollOfUpgrade.class, "upgraded", item.name()));
                    }
                    Sample.INSTANCE.play(Assets.Sounds.EVOKE);
                } else {
                    GLog.w(Messages.get(ElixirOfUpgrade.class, "not_equipment"));
                }
            }
        }
    };

}



