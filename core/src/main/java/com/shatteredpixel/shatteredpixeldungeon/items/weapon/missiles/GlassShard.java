package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class GlassShard extends MissileWeapon{
    {
        image = ItemSpriteSheet.THROWING_CLUB;
        hitSound = Assets.Sounds.HIT_ARROW;
        hitSoundPitch = 1.1f;

        tier = 1;
        baseUses = 4;
        sticky = true;
    }

    public int max(int lvl) {
        return  3 * tier +                  //6 base, down from 10
                (tier) * lvl;               //scaling unchanged
    }
}
