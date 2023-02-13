/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeirdBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.Calendar;

public class Pasty extends Food {

	//TODO: implement fun stuff for other holidays
	//TODO: probably should externalize this if I want to add any more festive stuff.
	 enum Holiday{
		NONE,
		EASTER, //TBD
		HWEEN,//2nd week of october though first day of november
		XMAS, //3rd week of december through first week of january
		SPECIAL // secret
	}

	private static Holiday holiday;

	static{

		holiday = Holiday.NONE;

		final Calendar calendar = Calendar.getInstance();
		switch(calendar.get(Calendar.MONTH)){
			case Calendar.JANUARY:
				if (calendar.get(Calendar.WEEK_OF_MONTH) == 1)
					holiday = Holiday.XMAS;
				break;
			case Calendar.OCTOBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.NOVEMBER:
				if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.DECEMBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 3)
					holiday = Holiday.XMAS;
				break;
			case Calendar.FEBRUARY:
				if (calendar.get(Calendar.DAY_OF_MONTH)==12)
					holiday = Holiday.SPECIAL;
				break;
		}
	}

	{
		reset();

		energy = Hunger.STARVING;

		bones = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		switch(holiday){
			case NONE:
				image = ItemSpriteSheet.PASTY;
				break;
			case HWEEN:
				image = ItemSpriteSheet.PUMPKIN_PIE;
				break;
			case XMAS:
				image = ItemSpriteSheet.CANDY_CANE;
				break;
			case SPECIAL:
				image = ItemSpriteSheet.CAKE;
		}
	}

	@Override
	public int quantity() {
		return super.quantity();
	}

	@Override
	protected void satisfy(Hero hero) {
		super.satisfy(hero);
		
		switch(holiday) {
			case NONE:
				break; //do nothing extra
			case HWEEN:
				//heals for 10% max hp
				hero.HP = Math.min(hero.HP + hero.HT / 10, hero.HT);
				hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
				break;
			case XMAS:
				Buff.affect(hero, Recharging.class, 2f); //half of a charge
				ScrollOfRecharging.charge(hero);
				break;
			case SPECIAL:
				Buff.affect(hero, Bless.class, Bless.DURATION * 2);
				Buff.affect(hero, Recharging.class, Recharging.DURATION);
				Buff.affect(hero, ArtifactRecharge.class).set(30).ignoreHornOfPlenty = false;
				Buff.affect(hero, WeirdBuff.class, 200);
				hero.sprite.emitter().burst(Speck.factory(Speck.BLIZZARD),1);

		}
		}

	@Override
	public String name() {
		switch(holiday){
			case NONE: default:
				return Messages.get(this, "pasty");
			case HWEEN:
				return Messages.get(this, "pie");
			case XMAS:
				return Messages.get(this, "cane");
			case SPECIAL:
				return "Cake";
		}
	}

	@Override
	public String info() {
		switch(holiday){
			case NONE: default:
				return Messages.get(this, "pasty_desc");
			case HWEEN:
				return Messages.get(this, "pie_desc");
			case XMAS:
				return Messages.get(this, "cane_desc");
			case SPECIAL:
				return "A rare piece of cake you won't see for long";
		}
	}
	
	@Override
	public int value() {
		return 20 * quantity;
	}
}
