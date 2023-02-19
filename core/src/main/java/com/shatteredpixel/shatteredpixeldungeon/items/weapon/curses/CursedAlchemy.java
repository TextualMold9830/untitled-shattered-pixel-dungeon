package com.shatteredpixel.shatteredpixeldungeon.items.weapon.curses;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfSnapFreeze;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class CursedAlchemy extends Weapon.Enchantment {
    private static final ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );
    private static final ArrayList<Class> Pots =new ArrayList<>();
    static {
        Pots.add(PotionOfToxicGas.class);
        Pots.add(PotionOfLiquidFlame.class);
        Pots.add(PotionOfParalyticGas.class);
        Pots.add(PotionOfFrost.class);

        //exotic
        Pots.add(PotionOfCorrosiveGas.class);
        Pots.add(PotionOfSnapFreeze.class);
        Pots.add(PotionOfShroudingFog.class);
        Pots.add(PotionOfStormClouds.class);
    }

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {

        float procChance = 1 / 12f * procChanceMultiplier(attacker);
        if (Random.Float() < procChance && !defender.properties().contains(Char.Property.IMMOVABLE)) {
            int chosen = Random.Int(0, Pots.size());
            Class<? extends Potion> p =Pots.get(chosen);


        }

            return damage;
        }


    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }
}
