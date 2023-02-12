package com.shatteredpixel.shatteredpixeldungeon.items.scrolls;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ScrollOfWipeOut extends Scroll {
    {
        image = ItemSpriteSheet.SCROLL_OF_WIPE_OUT;
        identify();
    }
    @Override
    public void doRead() {
        Dungeon.hero.die(this);
        if (Dungeon.depth > 4) {
            Dungeon.rickroll = false;
        }
    }
    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{ScrollOfPsionicBlast.class, MindVision.class, ArcaneCatalyst.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 8;

            output = ScrollOfWipeOut.class;
            outQuantity = 1;
        }
    }
}
