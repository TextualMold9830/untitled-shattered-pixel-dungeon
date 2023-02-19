package com.shatteredpixel.shatteredpixeldungeon.items.bags;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;

public class ArtifactHolder extends Bag{
    public static float MULTIPLIER = 0.50f;
    @Override
    public int capacity() {
        return 5;
    }

    @Override
    public boolean canHold(Item item) {
        return (item instanceof Artifact);
    }

}
