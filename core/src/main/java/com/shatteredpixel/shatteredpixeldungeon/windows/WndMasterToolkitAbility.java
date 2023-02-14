package com.shatteredpixel.shatteredpixeldungeon.windows;

public class WndMasterToolkitAbility extends WndOptions{
    public int chosenAbility;
    public WndMasterToolkitAbility(){
        super("Chose Ability","Convert alchemical energy to what you desire.","Convert to wand recharge","Convert to artifact recharge","Convert to alchemical energy");

    }

    @Override
    protected void onSelect(int index) {
        super.onSelect(index);
        chosenAbility=index;
    }
}
