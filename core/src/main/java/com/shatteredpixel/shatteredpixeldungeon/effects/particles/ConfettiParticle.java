package com.shatteredpixel.shatteredpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class ConfettiParticle extends PixelParticle {

    public static final Emitter.Factory FACTORY = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((ConfettiParticle)emitter.recycle( ConfettiParticle.class )).reset( x, y );
        }
    };

    public ConfettiParticle() {
        super();

        color( ColorMath.random( 0x0044ff, 0xff66ff ) );
        lifespan = 0.5f;
        speed.polar( Random.Float( PointF.PI2 ), Random.Float( 16, 32 ) );
    }

    public void reset( float x, float y ) {
        revive();

        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        super.update();
        size( 8 - lifespan * 5 );
    }
}

