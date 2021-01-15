/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 4 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.special.Bascule;
import ch.epfl.cs107.play.game.actor.triggers.Coin;
import ch.epfl.cs107.play.game.actor.triggers.Finish;
import ch.epfl.cs107.play.game.actor.triggers.GravityInversor;
import ch.epfl.cs107.play.game.addons.RGBColor;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GrassLevel extends Level{
	private ActorGame game;
	//Actors
		private Finish finish;
		private Terrain terrain;
		private Bike bike;
		private Bascule bascule;
		private Coin coin1, coin2, coin3, coin4;
		private Crate crate1,crate2,crate3,crate4;
		private GravityInversor gravityInversor;
	//Caractéristiques terrain
		private final float MAP_MAX_HEIGHT = 40f;
		private final float MAP_MIN_HEIGHT = 0f;
		private final float MAP_MIN_LENGHT = -50;
		private final float MAP_MAX_LENGHT = 550f;
		private Polyline polyline = new Polyline(
				MAP_MIN_LENGHT-50, MAP_MIN_HEIGHT-100,
				MAP_MIN_LENGHT-50, MAP_MAX_HEIGHT+100,
				MAP_MIN_LENGHT, MAP_MAX_HEIGHT+100,
				MAP_MIN_LENGHT, MAP_MIN_HEIGHT,
				MAP_MIN_LENGHT+50, MAP_MIN_HEIGHT,
				25f,MAP_MIN_HEIGHT,
				25f,-1f,
				29f,-1f,
				29f,MAP_MIN_HEIGHT,
				100f,MAP_MIN_HEIGHT,
				150f,MAP_MAX_HEIGHT,
				200f,MAP_MIN_HEIGHT,
				300f,MAP_MIN_HEIGHT,
				300f,-2f,
				350f,-2f,
				350f,MAP_MIN_HEIGHT,
				400f,MAP_MIN_HEIGHT,
				400f,-5f,
				450f,-5f,
				450f,MAP_MIN_HEIGHT,
				MAP_MAX_LENGHT-50,MAP_MIN_HEIGHT,
				MAP_MAX_LENGHT, MAP_MIN_HEIGHT,
				MAP_MAX_LENGHT, MAP_MAX_HEIGHT+100,
				MAP_MAX_LENGHT+50, MAP_MAX_HEIGHT+100,
				MAP_MAX_LENGHT+50, MAP_MIN_HEIGHT-100
				);
		
	public GrassLevel(ActorGame game) {
		super(game);
		this.game = game;
	}
	@Override
	public void begin() {
		//gravité terrestre
		this.game.getWorld().setGravity(new Vector(0f,-9.81f));
	}
	
	public void draw(Canvas canvas){
		if(Bike.mode!=Bike.WIN && Bike.mode != Bike.RESET && game.getLevelNumber()==game.GRASS_LEVEL) {
			bike.draw(canvas);
			terrain.draw(canvas);
			finish.draw(canvas);
			gravityInversor.draw(canvas);
			bascule.draw(canvas);
			crate1.draw(canvas); 
			crate2.draw(canvas); 
			crate3.draw(canvas); 
			crate4.draw(canvas);
			coin1.draw(canvas);
			coin2.draw(canvas);
			coin3.draw(canvas);
			coin4.draw(canvas);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		//Permet de detruire tous les acteurs du niveau et débloquer le niveau suivant si nécessaire
		if(Bike.mode==Bike.WIN && game.getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			destroy();
			if (Menu.numberOfUnlockedLevels >=1) {
				Menu.numberOfUnlockedLevels = 2;
			}
		//En cas de reset detruit les acteurs et met le bike en mode reset
		}else if(game.getKeyboard().get(KeyEvent.VK_R).isPressed()) {
		destroy();
		Bike.mode=Bike.RESET;
		}else {
		bike.update(deltaTime);
		finish.update(deltaTime);
		gravityInversor.update(deltaTime);
		coin1.update(deltaTime);
		coin2.update(deltaTime);
		coin3.update(deltaTime);
		coin4.update(deltaTime);
		}
	}
	
	@Override
	public void createAllActors() {	
		try {
			bike = new Bike(game, false, new Vector(5.0f, 4.0f));
			game.createActor(bike);
			game.setViewCandidate(bike);
			terrain = new Terrain(game, true, new Vector(0f, 0f),polyline ,Color.GREEN, RGBColor.getRGB(126,88,53), 20.0f);
			game.createActor(terrain);
			finish = new Finish(game, true, new Vector(MAP_MAX_LENGHT-50,MAP_MIN_HEIGHT));
			game.createActor(finish);
			//Puits de gravité 
			gravityInversor = new GravityInversor(game, true, new Vector(400f,-5f), 50f,5f);
			game.createActor(gravityInversor);
			//bascule
			bascule = new Bascule(game, true, new Vector(75f,0f));
			game.createActor(bascule);
			//caisses
			crate1 = new Crate(game, false, new Vector(23f,1f));
			game.createActor(crate1);
			crate2 = new Crate(game, false, new Vector(23f,3f));
			game.createActor(crate2);
			crate3 = new Crate(game, false, new Vector(220f,1f));
			game.createActor(crate3);
			crate4 = new Crate(game, false, new Vector(230f,1f));
			game.createActor(crate4);
			//pieces
			coin1 = new Coin(game, true, new Vector(75.0f,2.0f));
			game.createActor(coin1);
			coin2 = new Coin(game, true, new Vector(250.0f,1.0f));
			game.createActor(coin2);
			coin3 = new Coin(game, true, new Vector(325.0f,-1.0f));
			game.createActor(coin3);
			coin4 = new Coin(game, true, new Vector(425.0f,3.0f));
			game.createActor(coin4);
		}catch (NullPointerException e) {}
	}

	@Override
	public void destroy() {
		terrain.getEntity().destroy();
		bike.getEntity().destroy();
		bike.destroy();
		gravityInversor.getEntity().destroy();
		bascule.getEntity().destroy();
		bascule.destroy();
		coin1.getEntity().destroy();
		coin2.getEntity().destroy();
		coin3.getEntity().destroy();
		coin4.getEntity().destroy();
		crate1.getEntity().destroy();
		crate2.getEntity().destroy();
		crate3.getEntity().destroy();
		crate4.getEntity().destroy();
		finish.getEntity().destroy();
		game.removeActor(terrain);
		game.removeActor(bike);
		game.removeActor(gravityInversor);
		game.removeActor(bascule);
		game.removeActor(coin1);
		game.removeActor(coin2);
		game.removeActor(coin3);
		game.removeActor(coin4);
		game.removeActor(crate1);
		game.removeActor(crate2);
		game.removeActor(crate3);
		game.removeActor(crate4);
		game.removeActor(finish);
	}
}
