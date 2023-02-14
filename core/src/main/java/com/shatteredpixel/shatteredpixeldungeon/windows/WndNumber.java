package com.shatteredpixel.shatteredpixeldungeon.windows;

public class WndNumber extends WndOptions{
    public float returnedAmount;
    public String[]options;
    public WndNumber(String... options){
        super("Chose amount","Chose how much you want to use",options);
        this.options=options;
    }

    @Override
    protected void onSelect(int index) {
        super.onSelect(index);
        returnedAmount =Float.parseFloat(options[index]);
    }
}

