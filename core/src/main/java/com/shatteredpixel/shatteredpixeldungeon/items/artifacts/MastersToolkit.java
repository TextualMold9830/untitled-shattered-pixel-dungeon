package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class MastersToolkit extends AlchemistsToolkit{
    {
        image = ItemSpriteSheet.ARTIFACT_TOOLKIT;
        defaultAction = AC_BREW;

        levelCap = 10;

        charge = 0;
        partialCharge = 0;
        unique=true;
    }
    public int getCharges(){
        return this.charge;
    }
    public void setCharges(int amount){
        charge=amount;
    }
    public void reduceCharges(int amount){
        charge-=amount;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        return super.actions(hero);
    }
}
