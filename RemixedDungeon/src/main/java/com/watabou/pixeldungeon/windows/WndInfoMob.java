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
package com.watabou.pixeldungeon.windows;

import com.nyrds.android.util.GuiProperties;
import com.nyrds.pixeldungeon.windows.HBox;
import com.nyrds.pixeldungeon.windows.VHBox;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.StringsManager;
import com.watabou.noosa.Text;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.ui.BuffIndicator;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.utils.Utils;

public class WndInfoMob extends WndTitledMessage {

	private static final float BUTTON_WIDTH		= 36;

	public WndInfoMob( Mob mob ) {
		super( new MobTitle( mob ), desc( mob, true ) );

		VHBox actions = new VHBox(width - 2* GAP);
		actions.setAlign(HBox.Align.Width);
		actions.setGap(GAP);

		//if (Dungeon.hero.isAlive() && mob.getOwner() == Dungeon.hero) {
		if (Dungeon.hero.isAlive()) {

			for (final String action:mob.actions( Dungeon.hero )) {

				if(Dungeon.hero.getHeroClass().forbidden(action)){
					continue;
				}

				RedButton btn = new RedButton(StringsManager.maybeId(action) ) {
					@Override
					protected void onClick() {
						mob.execute( Dungeon.hero, action );
						hide();
					}
				};
				btn.setSize( Math.max( BUTTON_WIDTH, btn.reqWidth() ), BUTTON_HEIGHT );

				actions.add(btn);
			}
		}

		add(actions);
		actions.setPos(GAP, height+2*GAP);

		resize( width, (int) (actions.bottom() + GAP));
	}

	public WndInfoMob( Mob mob, int knowledge) {
		super( new MobTitle( mob ), desc( mob, false) );
	}

	private static String desc( Mob mob, boolean withStatus ) {
		if(withStatus) {
			return mob.description() + "\n\n" + Utils.capitalize(mob.getState().status(mob)) + ".";
		} else {
			return mob.description();
		}
	}

	private static class MobTitle extends Component {
		
		private static final int COLOR_BG	= 0xFFCC0000;
		private static final int COLOR_LVL	= 0xFF00EE00;
		
		private static final int BAR_HEIGHT	= 2;
		
		private CharSprite image;
		private Text name;
		private ColorBlock hpBg;
		private ColorBlock hpLvl;
		private BuffIndicator buffs;
		
		private float hp;
		
		public MobTitle( Mob mob ) {

			hp = (float)mob.hp() / mob.ht();
			
			name = PixelScene.createText( Utils.capitalize( mob.getName() ), GuiProperties.titleFontSize());
			name.hardlight( TITLE_COLOR );
			add( name );
			
			image = mob.newSprite();
			add( image );
			
			hpBg = new ColorBlock( 1, 1, COLOR_BG );
			add( hpBg );
			
			hpLvl = new ColorBlock( 1, 1, COLOR_LVL );
			add( hpLvl );
			
			buffs = new BuffIndicator( mob );
			add( buffs );
		}
		
		@Override
		protected void layout() {
			
			image.x = 0 - image.visualOffsetX();
			image.y = Math.max( 0, name.height() + GAP + BAR_HEIGHT - image.visualHeight()) - image.visualOffsetY();
			
			name.x = image.visualWidth() + GAP;
			name.y = image.visualHeight() - BAR_HEIGHT - GAP - name.baseLine();
			
			float w = width - image.visualWidth() - GAP;
			
			hpBg.size( w, BAR_HEIGHT );
			hpLvl.size( w * hp, BAR_HEIGHT );
			
			hpBg.x = hpLvl.x = image.visualWidth() + GAP;
			hpBg.y = hpLvl.y = image.visualHeight() - BAR_HEIGHT;
			
			buffs.setPos( 
				name.x + name.width() + GAP, 
				name.y + name.baseLine() - BuffIndicator.ICON_SIZE );
			
			height = hpBg.y + hpBg.height();
		}
	}
}
