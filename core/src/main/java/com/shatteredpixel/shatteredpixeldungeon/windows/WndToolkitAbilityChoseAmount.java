package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class WndToolkitAbilityChoseAmount extends WndNumber{
    public WndToolkitAbilityChoseAmount(String... options) {
        super(options);
    }

    @Override
    protected void onSelect(int index) {
        super.onSelect(index);
        GameScene.show(new WndMasterToolkitAbility());
    }
}
