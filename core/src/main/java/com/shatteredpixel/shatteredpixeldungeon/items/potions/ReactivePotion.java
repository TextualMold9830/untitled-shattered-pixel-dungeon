package com.shatteredpixel.shatteredpixeldungeon.items.potions;

import static com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing.pharmacophobiaProc;

import com.badlogic.gdx.Gdx;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StrongerFire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.io.IOException;
import java.util.ArrayList;

public class ReactivePotion extends Potion {
    {
        image = ItemSpriteSheet.REACTIVE_POTION;
        defaultAction = AC_THROW;

    }

    @Override
    protected void drink(Hero hero) {
        super.drink(hero);
        if (Dungeon.isChallenged(Challenges.NO_HEALING)) {
            pharmacophobiaProc(Dungeon.hero);
        } else {
            Buff.affect(hero, Healing.class).setHeal(Math.round(5 + hero.HT / 10f), 0, 1);
            if (Random.Int(1, 100) == 1) {
                try {
                    Dungeon.saveAll();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Gdx.net.openURI("https://www.youtube.com/watch?v=mx86-rTclzA");
            }
        }
    }
    @Override
    public void shatter(int cell) {
        Char c = Actor.findChar(cell);
        if (c!=null&&c == Dungeon.hero && Dungeon.isChallenged(Challenges.NO_HEALING)){
            pharmacophobiaProc(Dungeon.hero);
        }else if (c != null && c.alignment == Char.Alignment.ALLY) {
            Buff.affect(c, Healing.class).setHeal(Math.round(5 + c.HT / 5f), 0, 1);

            return;
        }

        if (Dungeon.level.heroFOV[cell]) {
            identify();

            splash(cell);
            Sample.INSTANCE.play(Assets.Sounds.SHATTER);
            Sample.INSTANCE.play(Assets.Sounds.BURNING);
        }
        if (!Dungeon.level.solid[cell]) {
            if (!Dungeon.hero.hasTalent(Talent.STRONGER_REACTION)) {
                GameScene.add(Blob.seed(cell, 2, Fire.class));
            } else {
                GameScene.add(Blob.seed(cell,2, StrongerFire.class));

            }

                if (Random.Int(1, 100) < 3) {
                    try {
                        Dungeon.saveAll();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Gdx.net.openURI("https://www.youtube.com/watch?v=mx86-rTclzA");
                }

            }
        }


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
    }


    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
    }

    @Override
    public int value(){
        return quantity()*5;
    }
    @Override
    public int energyVal(){
        return quantity;
    }
    public boolean isKnown(){return true;}
    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {


        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean flame = false;
            boolean healing = false;
            if (Dungeon.hero.heroClass == HeroClass.POTIONMASTER) {
                for (Item ingredient : ingredients) {
                    if (ingredient.quantity() > 0) {
                        if (ingredient.getClass() == PotionOfLiquidFlame.class) {
                            flame = true;
                        } else if (ingredient.getClass() == PotionOfHealing.class) {
                            healing = true;
                        }
                    }
                }
            }
            return flame && healing;
        }
        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 4;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;

            for (Item ingredient : ingredients) {
                ingredient.quantity(ingredient.quantity() - 1);
            }

            return sampleOutput(null);
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
           return new ReactivePotion().quantity(15+Dungeon.hero.pointsInTalent(Talent.MULTIPLIED_POTIONS)).identify();
        }

    }
}


