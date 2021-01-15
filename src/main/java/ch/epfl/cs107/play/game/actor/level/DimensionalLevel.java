/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 12 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.special.SpecialTerrain;
import ch.epfl.cs107.play.game.actor.triggers.Coin;
import ch.epfl.cs107.play.game.actor.triggers.Finish;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class DimensionalLevel extends Level{
	
	private ActorGame game;
	private Coin coin1, coin2, coin3, coin4, coin5, coin6;
	private Finish finish;
	private Terrain terrain;
	private SpecialTerrain terrainFake1,terrainFake2;
	private Bike bike;
	//Caractéristiques terrain
	private final float MAP_MAX_HEIGHT = 100f;
	private final float MAP_MIN_HEIGHT = -100f;
	private final float MAP_MIN_LENGHT = -50;
	private final float MAP_MAX_LENGHT = 450f;
	private Polyline polyline1 = new Polyline(
			MAP_MIN_LENGHT-50, MAP_MIN_HEIGHT-100,
			MAP_MIN_LENGHT-50, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT,
			MAP_MIN_LENGHT, 0f,
			50f/2,0f,
			100f/2,25f,
			200f/2,-25f,
			250f/2,-25f,
			300f/2,0f,
			350f/2,-25f,
			355f/2,-25f,
			400f/2,0f,
			450f/2,0f,
			450f/2,-25f,
			550f/2,-25f,
			650f/2,0f,
			700f/2,25f,
			800f/2,-25f,
			850f/2,-25f,
			MAP_MAX_LENGHT,-25f,
			MAP_MAX_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MIN_HEIGHT-100
			);
	private Polyline polyline2 = new Polyline(
			MAP_MIN_LENGHT-50, MAP_MIN_HEIGHT-100,
			MAP_MIN_LENGHT-50, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT,
			MAP_MIN_LENGHT, MAP_MIN_HEIGHT,
			50f/2,0f,
			100f/2,25f,
			150f/2,50f,
			200f/2,-25f,
			250f/2,-50f,
			300f/2,0f,
			350f/2,25f,
			400f/2,0f,
			450f/2,-25f,
			550f/2,-25f,
			600f/2,0f,
			650f/2,-25f,
			700f/2,25f,
			750f/2,50f,
			750f/2,-25f,
			800f/2,-25f,
			MAP_MAX_LENGHT-50,-25f,
			MAP_MAX_LENGHT, -25f,
			MAP_MAX_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MIN_HEIGHT-100
			);
	private Polyline polyline3 = new Polyline(
			MAP_MIN_LENGHT-50, MAP_MIN_HEIGHT-100,
			MAP_MIN_LENGHT-50, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MIN_LENGHT, MAP_MAX_HEIGHT,
			MAP_MIN_LENGHT, MAP_MIN_HEIGHT,
			50f/2,0f,
			100f/2,25f,
			150f/2,25f,
			200f/2,-25f,
			250f/2,0f,
			350f/2,0f,
			375f/2,-25f/2,
			425f/2,25f/2,
			525f/2,75f/2,
			550f/2,-25f,
			575f/2,-25f,
			600f/2,0f,
			650f/2,25f,
			650f/2,0f,
			700f/2,0f,
			750f/2,25f,
			750f/2,0f,
			800f/2,0f,
			850f/2,-25f,
			MAP_MAX_LENGHT-50,-25f,
			MAP_MAX_LENGHT, -25f,
			MAP_MAX_LENGHT, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MAX_HEIGHT+100,
			MAP_MAX_LENGHT+50, MAP_MIN_HEIGHT-100
			);

	

	public DimensionalLevel(ActorGame game) {
		super(game);
			this.game = game;
	}
	
	@Override
	public void begin() {
		//gravité moins forte que la terre
		this.game.getWorld().setGravity(new Vector(0f,-7f));
	}
	public void draw(Canvas canvas){
		if(Bike.mode!=Bike.WIN && Bike.mode != Bike.RESET && game.getLevelNumber()==game.DIMENSIONAL_LEVEL) {
			bike.draw(canvas);
			terrain.draw(canvas);
			finish.draw(canvas);
			coin1.draw(canvas);
			coin2.draw(canvas);
			coin3.draw(canvas);
			coin4.draw(canvas);
		}
	}

	@Override
	public void createAllActors() {	
		try {
			bike = new Bike(game, false, new Vector(5.0f, 5.0f));
			game.createActor(bike);
			game.setViewCandidate(bike);
			terrain = new Terrain(game, true, new Vector(0f, 0f), polyline1 ,Color.WHITE, null,1000f);
			game.createActor(terrain);
			terrainFake1 = new SpecialTerrain(game, true, new Vector(0f, 0f), polyline2 ,Color.WHITE, null,1000f);
			game.createActor(terrainFake1);
			terrainFake2 = new SpecialTerrain(game, true, new Vector(0f, 0f), polyline3 ,Color.WHITE, null,1000f);
			game.createActor(terrainFake2);
			finish = new Finish(game, true, new Vector(800f/2,-25f));
			game.createActor(finish);
			coin1 = new Coin(game, true, new Vector(100f/2,25f+1f));
			game.createActor(coin1);
			coin2 = new Coin(game, true, new Vector(300f/2,0f+1f));
			game.createActor(coin2);
			coin3 = new Coin(game, true, new Vector(400f/2,0f+1f));
			game.createActor(coin3);
			coin4 = new Coin(game, true, new Vector(700f/2,25f+1f));
			game.createActor(coin4);
		}catch (NullPointerException e) {}
		
	}

	@Override
	public void update(float deltaTime) {
		//Permet de detruire tous les acteurs du niveau
		if(Bike.mode==Bike.WIN && game.getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			destroy();
		//En cas de reset detruit les acteurs et met le bike en mode reset
		}else if(game.getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			destroy();
			Bike.mode=Bike.RESET;
		}else {
			terrain.update(deltaTime);
			terrainFake1.update(deltaTime);
			terrainFake2.update(deltaTime);
			bike.update(deltaTime);
			coin1.update(deltaTime);
			coin2.update(deltaTime);
			coin3.update(deltaTime);
			coin4.update(deltaTime);
			finish.update(deltaTime);
		}
	}

	@Override
	public void destroy() {
		terrain.getEntity().destroy();
		terrainFake1.getEntity().destroy();
		terrainFake2.getEntity().destroy();
		bike.getEntity().destroy();
		coin1.getEntity().destroy();
		coin2.getEntity().destroy();
		coin3.getEntity().destroy();
		coin4.getEntity().destroy();
		finish.getEntity().destroy();
		game.removeActor(terrain);
		game.removeActor(terrainFake1);
		game.removeActor(terrainFake2);
		game.removeActor(bike);
		game.removeActor(coin1);
		game.removeActor(coin2);
		game.removeActor(coin3);
		game.removeActor(coin4);
		game.removeActor(coin5);
		game.removeActor(coin6);
		game.removeActor(finish);
	}


}
