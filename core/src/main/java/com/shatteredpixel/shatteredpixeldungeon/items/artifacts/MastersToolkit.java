package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMasterToolkitAbility;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndNumber;

import java.util.ArrayList;

public class MastersToolkit extends AlchemistsToolkit {
    public static final String AC_CONVERT = "CONVERT";
    public static final String AC_DRINK="DRINK";

    {
        image = ItemSpriteSheet.ARTIFACT_TOOLKIT;
        defaultAction = AC_BREW;

        levelCap = 10;

        charge = 0;
        partialCharge = 0;
        unique = true;
    }

    public int getCharges() {
        return this.charge;
    }

    public void setCharges(int amount) {
        charge = amount;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped( hero ) && !cursed && hero.buff(MagicImmune.class) == null&&level()>=10) {
            actions.add(AC_CONVERT);
        if (isEquipped(hero)||hero.hasTalent(Talent.LIGHT_CLOAK)&&hero.subClass== HeroSubClass.ALCHEMIST&&!cursed && hero.buff(MagicImmune.class) == null){
            actions.add(AC_DRINK);
        }

        }

        return actions;

    }
    @Override
    public void execute(Hero hero, String action ) {
        super.execute(hero, action);
        if (action == AC_CONVERT) {
            if (!isEquipped(hero))
                GLog.i(Messages.get(AlchemistsToolkit.class, "need_to_equip"));
            else if (cursed) GLog.w(Messages.get(AlchemistsToolkit.class, "cursed"));
            else if (charge < 10) GLog.w(Messages.get(AlchemistsToolkit.class, "need_energy"));
            else {
                final int[] chosenAmount = new int[1];
                GameScene.show(new WndNumber(String.valueOf(Math.floor(charge / 4f)), String.valueOf(Math.floor(charge / 2f)), String.valueOf(Math.floor(charge))) {

                    @Override
                    protected void onSelect(int index) {
                        super.onSelect(index);
                        chosenAmount[0] = Math.round(Float.parseFloat(options[index]));
                        GameScene.show(new WndMasterToolkitAbility() {
                            @Override
                            protected void onSelect(int index) {
                                super.onSelect(index);
                                switch (index) {
                                    case 0:
                                        Buff.affect(hero, Recharging.class, chosenAmount[0] - 1);
                                        charge -= chosenAmount[0];
                                        break;
                                    case 1:
                                        Buff.affect(hero, ArtifactRecharge.class).set(chosenAmount[0]).ignoreMastersToolkit = true;
                                        charge -= chosenAmount[0];
                                        break;
                                    case 2:
                                        if (chosenAmount[0] % 2 == 1) {
                                            chosenAmount[0]--;
                                        }
                                        Dungeon.energy += chosenAmount[0] / 2;
                                        charge -= chosenAmount[0];
                                        break;
                                }
                            }
                        });
                        updateQuickslot();
                    }
                });


            }
        }

        updateQuickslot();
        }

    }

