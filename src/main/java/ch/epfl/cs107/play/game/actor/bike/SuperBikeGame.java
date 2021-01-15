/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.level.DimensionalLevel;
import ch.epfl.cs107.play.game.actor.level.GameWithLevels;
import ch.epfl.cs107.play.game.actor.level.GrassLevel;
import ch.epfl.cs107.play.game.actor.level.Level;
import ch.epfl.cs107.play.game.actor.level.Menu;
import ch.epfl.cs107.play.game.actor.level.MoonLevel;
import ch.epfl.cs107.play.game.actor.level.SnowLevel;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class SuperBikeGame extends ActorGame implements GameWithLevels{
	//Niveaux
		private List<Level> levels;
		protected List<Level> createLevelList() {
			return Arrays.asList(
				new Menu(this),
				new GrassLevel(this),
				new SnowLevel(this),
				new MoonLevel(this),
				new DimensionalLevel(this)
			);
		}			
	//score
		private TextGraphics messageCoins;
		public static int coins = 0;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		//créer les niveaux
		levels = createLevelList();
		nextLevel();
		return true;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		levels.get(levelNumber).update(deltaTime);
		//Lance le niveau choisi
		if(levelNumber==MENU && super.getKeyboard().get(KeyEvent.VK_ENTER).isPressed()) {
			levelNumber = Menu.levelSelected;
			nextLevel();
		}
		//En cas de victoire ou de reset lance le Menu
		if((Bike.mode==Bike.WIN && super.getKeyboard().get(KeyEvent.VK_SPACE).isPressed() && levelNumber!=MENU) ||
			(Bike.mode==Bike.RESET && super.getKeyboard().get(KeyEvent.VK_R).isPressed()) && levelNumber!=MENU) {
			levelNumber = MENU;
			nextLevel();
		}
		//Affiche l'argent collecté
    		messageCoins = new TextGraphics("MONEY - "+coins+"$", 0.02f, Color.WHITE, null, 0.03f, true, false, new Vector(0.0f, 0.0f), 1.0f, 100.0f);
    		messageCoins.setParent(super.getCanvas()); 
		messageCoins.setRelativeTransform(Transform.I.translated(0.78f, -0.45f));	
		messageCoins.draw(super.getCanvas());
	}
	//Créer le niveau selectionné
	@Override
	public void nextLevel() {
		super.createActor(levels.get(levelNumber));
		levels.get(levelNumber).createAllActors();
		levels.get(levelNumber).begin();
	}
}
