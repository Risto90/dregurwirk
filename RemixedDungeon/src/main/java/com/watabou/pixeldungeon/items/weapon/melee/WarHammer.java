/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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
package com.watabou.pixeldungeon.items.weapon.melee;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.actors.hero.Belongings;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class WarHammer extends MeleeWeapon {
	{
		image = ItemSpriteSheet.WAR_HAMMER;
		animation_class = HEAVY_ATTACK;
	}
	
	public WarHammer() {
		super( 5, 1.2f, 1f );
	}
	
	@Override
	public String desc() {
		return Game.getVar(R.string.WarHammer_Info);
	}

	@Override
	public Belongings.Slot slot(Belongings belongings) {
		return Belongings.Slot.WEAPON;
	}

	@Override
	public Belongings.Slot blockSlot() {
		return Belongings.Slot.LEFT_HAND;
	}
}
