/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 4 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.general.Stone;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.triggers.Coin;
import ch.epfl.cs107.play.game.actor.triggers.Finish;
import ch.epfl.cs107.play.game.addons.RGBColor;
import ch.epfl.cs107.play.game.decorations.SwissFlag;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class MoonLevel extends Level{
	private ActorGame game;
	private Coin coin1, coin2, coin3, coin4, coin5, coin6;
	private Finish finish;
	private Terrain terrain;
	private Bike bike;
	private Stone stone1,stone2;
	private SwissFlag swissFlag;
	//Caractéristiques terrain
	private final float MAP_MAX_HEIGHT = 50f;
	private final float MAP_MIN_HEIGHT = 0f;
	private final float MAP_MIN_LENGHT = -50;
	private final float MAP_MAX_LENGHT = 575f;
	private Polyline polyline = new Polyline(
			MAP_MIN_LENGHT-50, MAP_MIN_HEIGHT-100,
			MAP_MIN_LENGHT-50, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT+50, MAP_MAX_HEIGHT,
			MAP_MIN_LENGHT+50, MAP_MIN_HEIGHT,
			50f,MAP_MIN_HEIGHT,
			100f,20f,
			100f,MAP_MIN_HEIGHT,
			105f,MAP_MIN_HEIGHT,
			105f,20f,
			150f,MAP_MIN_HEIGHT,
			200f,MAP_MIN_HEIGHT,
			225f,20f,
			225,MAP_MIN_HEIGHT,
			230f,MAP_MIN_HEIGHT,
			230,20f,
			250f,MAP_MIN_HEIGHT,
			275f,MAP_MIN_HEIGHT,
			325f,25f,
			350f,25f,
			375f,MAP_MAX_HEIGHT,
			375f,MAP_MIN_HEIGHT,
			450f,MAP_MIN_HEIGHT,
			475,20f,
			475,MAP_MIN_HEIGHT,
			480,MAP_MIN_HEIGHT,
			480,20f,
			500,MAP_MIN_HEIGHT,
			MAP_MAX_LENGHT-50,MAP_MIN_HEIGHT,
			MAP_MAX_LENGHT, MAP_MIN_HEIGHT,
			MAP_MAX_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MIN_HEIGHT-100
			);
	
	
	

	public MoonLevel(ActorGame game) {
		super(game);
			this.game = game;
	}
	
	@Override
	public void begin() {
		//gravité lunaire
		this.game.getWorld().setGravity(new Vector(0f,-1.622f));
	}
	public void draw(Canvas canvas){
		if(Bike.mode!=Bike.WIN && Bike.mode != Bike.RESET && game.getLevelNumber()==game.MOON_LEVEL) {
			System.out.println(game.getLevelNumber());
			bike.draw(canvas);
			terrain.draw(canvas);
			finish.draw(canvas);
			coin1.draw(canvas);
			coin2.draw(canvas);
			coin3.draw(canvas);
			coin4.draw(canvas);
			coin5.draw(canvas);
			coin6.draw(canvas);
			stone1.draw(canvas);
			stone2.draw(canvas);
			swissFlag.draw(canvas);
		}
	}

	@Override
	public void createAllActors() {	
		try {
			bike = new Bike(game, false, new Vector(5.0f, 5.0f));
			game.createActor(bike);
			game.setViewCandidate(bike);
			terrain = new Terrain(game, true, new Vector(0f, 0f), polyline ,RGBColor.getRGB(75,75,75), RGBColor.getRGB(125,125,125),50f);
			game.createActor(terrain);
			finish = new Finish(game, true, new Vector(MAP_MAX_LENGHT-50,MAP_MIN_HEIGHT));
			game.createActor(finish);
			coin1 = new Coin(game, true, new Vector(105f,20f+1f));
			game.createActor(coin1);
			coin2 = new Coin(game, true, new Vector(225f,20f+1f));
			game.createActor(coin2);
			coin3 = new Coin(game, true, new Vector(230f,20f+1f));
			game.createActor(coin3);
			coin4 = new Coin(game, true, new Vector(350f,25f+1f));
			game.createActor(coin4);
			coin5 = new Coin(game, true, new Vector(375f+3f,MAP_MAX_HEIGHT));
			game.createActor(coin5);
			coin6 = new Coin(game, true, new Vector(475f,20f+1f));
			game.createActor(coin6);
			stone1 = new Stone(game, false, new Vector(175f,1f));
			game.createActor(stone1);
			stone2 = new Stone(game, false, new Vector(260f,21f));
			game.createActor(stone2);
			swissFlag = new SwissFlag(game, true, new Vector(-5f,0f));
			game.createActor(swissFlag);
		}catch (NullPointerException e) {}
		
	}

	@Override
	public void update(float deltaTime) {
		//Permet de detruire tous les acteurs du niveau et débloquer le niveau suivant si nécessaire
		if(Bike.mode==Bike.WIN && game.getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			destroy();
			if (Menu.numberOfUnlockedLevels >=3) {
				Menu.numberOfUnlockedLevels = 4;
			}
		//En cas de reset detruit les acteurs et met le bike en mode reset
		}else if(game.getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			destroy();
			Bike.mode=Bike.RESET;
		}else {
			terrain.update(deltaTime);
			bike.update(deltaTime);
			coin1.update(deltaTime);
			coin2.update(deltaTime);
			coin3.update(deltaTime);
			coin4.update(deltaTime);
			coin5.update(deltaTime);
			coin6.update(deltaTime);
			finish.update(deltaTime);
		}
	}

	@Override
	public void destroy() {
		terrain.getEntity().destroy();
		bike.getEntity().destroy();
		coin1.getEntity().destroy();
		coin2.getEntity().destroy();
		coin3.getEntity().destroy();
		coin4.getEntity().destroy();
		coin5.getEntity().destroy();
		coin6.getEntity().destroy();
		finish.getEntity().destroy();
		stone1.destroy();
		stone2.destroy();
		swissFlag.getEntity().destroy();
		game.removeActor(terrain);
		game.removeActor(bike);
		game.removeActor(coin1);
		game.removeActor(coin2);
		game.removeActor(coin3);
		game.removeActor(coin4);
		game.removeActor(coin5);
		game.removeActor(coin6);
		game.removeActor(stone1);
		game.removeActor(stone2);
		game.removeActor(finish);
		game.removeActor(swissFlag);
	}


}
