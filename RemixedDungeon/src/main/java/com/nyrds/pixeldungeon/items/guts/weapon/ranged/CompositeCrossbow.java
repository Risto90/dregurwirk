package com.nyrds.pixeldungeon.items.guts.weapon.ranged;

import com.watabou.pixeldungeon.items.Item;


public class CompositeCrossbow extends Crossbow {

	public CompositeCrossbow() {
		super(4, 1.1f, 1.6f);
		image = 3;
	}

	@Override
	public Item burn(int cell) {
		return null;
	}

	@Override
	public double acuFactor() {
		return 1 + level() * 0.5;
	}

	@Override
	public double dmgFactor() {
		return 1 + level() * 0.75;
	}

	public double dlyFactor() {
		return 1.1;
	}
}