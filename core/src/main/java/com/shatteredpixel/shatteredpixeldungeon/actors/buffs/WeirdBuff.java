package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class WeirdBuff extends FlavourBuff{

    public int icon() {
        return BuffIndicator.INVISIBLE;
    }

    @Override
    public String desc() {
        return "A rare buff you won't see for long";
    }
}
