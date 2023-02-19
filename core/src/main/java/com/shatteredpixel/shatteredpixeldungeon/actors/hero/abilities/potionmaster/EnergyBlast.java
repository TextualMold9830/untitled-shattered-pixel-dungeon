package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.potionmaster;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.watabou.noosa.Visual;

public class EnergyBlast extends ArmorAbility {


    public static ConeAOE arrangeBlast(int pos, Visual sprite, int type, float range) {
        Ballistica aim;
        if (pos % Dungeon.level.width() > 10){
            aim = new Ballistica(pos, pos - 1, Ballistica.WONT_STOP);
        } else {
            aim = new Ballistica(pos, pos + 1, Ballistica.WONT_STOP);
        }
        ConeAOE aoe = new ConeAOE(aim, range, 360, Ballistica.WONT_STOP);
        if (sprite.visible) {
            for (Ballistica ray : aoe.rays) {
                ((MagicMissile) sprite.parent.recycle(MagicMissile.class)).reset(
                        type,
                        sprite,
                        ray.path.get(ray.dist),
                        null
                );
            }
        }
        return aoe;
    }
    @Override
    protected void activate(ClassArmor armor, Hero hero, Integer target) {
        int charsHit=0;
        Ballistica aim;
        if (hero.pos % Dungeon.level.width() > 10){
            aim = new Ballistica(hero.pos, hero.pos - 1, Ballistica.WONT_STOP);
        } else {
            aim = new Ballistica(hero.pos, hero.pos + 1, Ballistica.WONT_STOP);
        }
        //Basically the direction of the aim only matters if it goes outside the map
        //So we just ensure it won't do that.


        int aoeSize = 4 + hero.pointsInTalent(Talent.ENERGY_RADIUS);

        int projectileProps = Ballistica.STOP_SOLID | Ballistica.STOP_TARGET;



        ConeAOE aoe = new ConeAOE(aim, aoeSize, 360, projectileProps);

        for (int cell : aoe.cells) {
            Char mob = Actor.findChar(cell);
            int damage =Dungeon.energy*hero.pointsInTalent(Talent.ENERGY_POWER)/4;

            if (mob != null && damage > 0 && mob.alignment != Char.Alignment.ALLY){
                if (hero.subClass==HeroSubClass.ALCHEMIST) {
                    Buff.affect(mob, Burning.class);
                }
                charsHit++;
            }
        }
        if (hero.hasTalent(Talent.ENERGY_SIPHON)){
            Dungeon.energy+=hero.pointsInTalent(Talent.ENERGY_SIPHON)*4;
        }
        if (hero.subClass== HeroSubClass.MADSCIENTIST){
            Buff.affect(hero, Barrier.class).incShield(4);
        }
        }


    @Override
    public Talent[] talents() {
        return new Talent[]{Talent.ENERGY_RADIUS,Talent.ENERGY_POWER,Talent.ENERGY_SIPHON};
    }
}
