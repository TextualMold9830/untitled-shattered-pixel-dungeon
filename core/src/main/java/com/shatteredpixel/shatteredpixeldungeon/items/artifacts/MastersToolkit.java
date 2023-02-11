package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

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


}
