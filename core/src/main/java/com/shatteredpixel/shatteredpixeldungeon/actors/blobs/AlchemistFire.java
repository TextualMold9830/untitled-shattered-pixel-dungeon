package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;

public class AlchemistFire extends Fire{
    public static void burn( int pos ) {
        Char ch = Actor.findChar( pos );
        if (ch != null && !ch.isImmune(Fire.class)) {
            Buff.affect( ch, Burning.class ).reignite(ch,8+ Dungeon.hero.pointsInTalent(Talent.STRONGER_REACTION));
        }

        Heap heap = Dungeon.level.heaps.get( pos );
        if (heap != null) {
            heap.burn();
        }

        Plant plant = Dungeon.level.plants.get( pos );
        if (plant != null){
            plant.wither();
        }
    }
}
