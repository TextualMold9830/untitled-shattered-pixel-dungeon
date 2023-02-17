package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.badlogic.gdx.Gdx;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.VolumeIncrement;

import java.io.IOException;

public class HealingTrap extends Trap {
    {
        color = Random.Int(0,8);
        shape = Random.Int(0,6);
        hide();
    }

    @Override
    public void activate() {
        Sample.INSTANCE.play(Assets.Sounds.NVR_GNA_LYD);
        for (int i : PathFinder.NEIGHBOURS9) {
            Char ch = Actor.findChar(pos + i);
            if (ch != null) {
                ch.HP = ch.HT;
                    if (ch instanceof Hero) {
                        if (Dungeon.level.distance(Dungeon.hero.pos, pos) <= 1){
                            Music.INSTANCE.stop();
                            Dungeon.rickroll = true;
                            SPDSettings.music(true);
                            SPDSettings.soundFx(true);
                            try {
                                Dungeon.saveAll();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            if (DeviceCompat.isAndroid()) {
                                ShatteredPixelDungeon.controller.setVolume(0.33f);
                            }else {
                                VolumeIncrement.incrementVolume(0.1f);
                            }
                            Gdx.net.openURI("https://youtu.be/mx86-rTclzA");

                        }

                    }
            }
        }


    }
}
