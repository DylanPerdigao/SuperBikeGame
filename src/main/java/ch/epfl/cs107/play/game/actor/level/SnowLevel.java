/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 4 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.triggers.Coin;
import ch.epfl.cs107.play.game.actor.triggers.Finish;
import ch.epfl.cs107.play.game.addons.RGBColor;
import ch.epfl.cs107.play.game.decorations.Balise;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SnowLevel extends Level{
	private ActorGame game;
	private Coin coin1, coin2, coin3, coin4, coin5, coin6,coin7, coin8;
	private Finish finish;
	private Terrain terrain;
	private Bike bike;
	private Balise balise;
	//Caractéristiques terrain
	private final float MAP_MAX_HEIGHT = 0f;
	private final float MAP_MIN_HEIGHT = -500f;
	private final float MAP_MIN_LENGHT = -50;
	private final float MAP_MAX_LENGHT = 1150f;
	private Polyline polyline = new Polyline(
			MAP_MIN_LENGHT-50, MAP_MIN_HEIGHT-100,
			MAP_MIN_LENGHT-50, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT,
			MAP_MIN_LENGHT+50, MAP_MAX_HEIGHT,
			100f,-50f,
			125f,-50f,
			200f,-100f,
			225f,-100f,
			300f,-150f,
			325f,-150f,
			475f,-225f,
			610f,-300f,
			625f,-300f,
			725f,-350f,
			750f,-350f,
			875f,-400f,
			900f,-400f,
			1000f,-450f,
			MAP_MAX_LENGHT-50,MAP_MIN_HEIGHT,
			MAP_MAX_LENGHT, MAP_MIN_HEIGHT,
			MAP_MAX_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MIN_HEIGHT-100
			);
	
	public SnowLevel(ActorGame game) {
		super(game);
		this.game = game;
	}
	
	@Override
	public void begin() {
		//gravité terrestre
		game.getWorld().setGravity(new Vector(0f,-9.81f));
	}
	
	public void draw(Canvas canvas){
		if(Bike.mode!=Bike.WIN && Bike.mode != Bike.RESET && game.getLevelNumber()==game.SNOW_LEVEL) {
			bike.draw(canvas);
			terrain.draw(canvas);
			finish.draw(canvas);
			coin1.draw(canvas);
			coin2.draw(canvas);
			coin3.draw(canvas);
			coin4.draw(canvas);
			coin5.draw(canvas);
			coin6.draw(canvas);
			coin7.draw(canvas);
			coin8.draw(canvas);
			balise.draw(canvas);
		}
	}

	@Override
	public void createAllActors() {	
		try {
			bike = new Bike(game, false, new Vector(5.0f, 5.0f));
			game.createActor(bike);
			game.setViewCandidate(bike);
			terrain = new Terrain(game, true, new Vector(0f, 0f),polyline ,RGBColor.getRGB(248,248,255), RGBColor.getRGB(176,196,222),5f);
			game.createActor(terrain);
			finish = new Finish(game, true, new Vector(MAP_MAX_LENGHT-50,MAP_MIN_HEIGHT));
			game.createActor(finish);
			coin1 = new Coin(game, true, new Vector(120f,-50+1.0f));
			game.createActor(coin1);
			coin2 = new Coin(game, true, new Vector(220f,-100+1.0f));
			game.createActor(coin2);
			coin3 = new Coin(game, true, new Vector(320f,-150+1.0f));
			game.createActor(coin3);
			coin4 = new Coin(game, true, new Vector(475f,-225+1.0f));
			game.createActor(coin4);
			coin5 = new Coin(game, true, new Vector(610f,-300+1.0f));
			game.createActor(coin5);
			coin6 = new Coin(game, true, new Vector(725f,-350+1.0f));
			game.createActor(coin6);
			coin7 = new Coin(game, true, new Vector(875f,-400+1.0f));
			game.createActor(coin7);
			coin8 = new Coin(game, true, new Vector(1000f,-450+1.0f));
			game.createActor(coin8);
			balise = new Balise(game, true, new Vector(0f,0f));
			game.createActor(balise);
		}catch (NullPointerException e) {}
		
	}

	@Override
	public void update(float deltaTime) {
		//Permet de detruire tous les acteurs du niveau et débloquer le niveau suivant si nécessaire
		if(Bike.mode==Bike.WIN && game.getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			destroy();
			if (Menu.numberOfUnlockedLevels >=2) {
				Menu.numberOfUnlockedLevels = 3;
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
			coin7.update(deltaTime);
			coin8.update(deltaTime);
			finish.update(deltaTime);
			balise.update(deltaTime);
		}
	}

	@Override
	public void destroy() {
		terrain.getEntity().destroy();
		bike.getEntity().destroy();
		bike.destroy();
		coin1.getEntity().destroy();
		coin2.getEntity().destroy();
		coin3.getEntity().destroy();
		coin4.getEntity().destroy();
		coin5.getEntity().destroy();
		coin6.getEntity().destroy();
		coin7.getEntity().destroy();
		coin8.getEntity().destroy();
		finish.getEntity().destroy();
		balise.getEntity().destroy();
		game.removeActor(terrain);
		game.removeActor(bike);
		game.removeActor(coin1);
		game.removeActor(coin2);
		game.removeActor(coin3);
		game.removeActor(coin4);
		game.removeActor(coin5);
		game.removeActor(coin6);
		game.removeActor(coin7);
		game.removeActor(coin8);
		game.removeActor(finish);
		game.removeActor(balise);
	}
}
