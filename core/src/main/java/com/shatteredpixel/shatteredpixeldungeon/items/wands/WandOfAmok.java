package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DwarfKing;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WandOfAmok extends Wand {
    {
        image= ItemSpriteSheet.WAND_CORRUPTION;

    }
    public static int corruptionLevel = 0;
    @Override
    public void onZap(Ballistica bolt) {
        Char ch = Actor.findChar(bolt.collisionPos);

        if (ch != null) {

            if (!(ch instanceof Mob)) {
                return;
            }

            Mob enemy = (Mob) ch;

            if (enemy instanceof DwarfKing) {
                Statistics.qualifiedForBossChallengeBadge = false;
            }
            if (!(enemy.properties().contains(Char.Property.BOSS))){
                Buff.affect(enemy, Amok.class,3+level());
            }

        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        int level = Math.max( 0, buffedLvl() );

        // lvl 0 - 25%
        // lvl 1 - 40%
        // lvl 2 - 50%
        float procChance = (level+1f)/(level+4f) * procChanceMultiplier(attacker);
        if (Random.Float() < procChance) {

            float powerMulti = Math.max(1f, procChance);

            Buff.prolong( defender, Amok.class, Math.round((4+level*2) * powerMulti));
        }
    }
    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe{

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean corruption = false;
            boolean corpse = false;

            for (Item ingredient : ingredients){
                if (ingredient.quantity() > 0) {
                    if (ingredient instanceof WandOfCorruption) {
                        corruption = true;
                        corruptionLevel=ingredient.level();


                    } else if (ingredient instanceof CorpseDust) {
                        corpse = true;
                    }
                }
            }

            return corpse&&corruption;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 8;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;

            for (Item ingredient : ingredients){
                ingredient.quantity(ingredient.quantity() - 1);
            }

            return sampleOutput(null);
        }
        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            WandOfAmok output = new WandOfAmok();
            output.level(corruptionLevel);
            return output.identify();
        }
        }


    }

