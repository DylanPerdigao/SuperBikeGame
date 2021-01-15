/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 24 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.triggers.Finish;
import ch.epfl.cs107.play.game.addons.RGBColor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame{
	private Bike bike;
	private Finish finish;
	private Terrain terrain;
	private final float MAP_MAX_HEIGHT = 15f;
	private final float MAP_MIN_HEIGHT = -15f;
	private final float MAP_DENIVELATION_MAX = Math.abs(MAP_MAX_HEIGHT)+Math.abs(MAP_MIN_HEIGHT);
	
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		//génère le terrain
		try {
			terrain = new Terrain(this, true, new Vector(0f, 0f), getPolyline(),Color.GREEN, RGBColor.getRGB(126,88,53), 20.0f);
			super.createActor(terrain);	
			finish = new Finish(this, true, new Vector(650.0f,0.0f));
			super.createActor(finish);
			
			//génère le velo
			bike = new Bike(this, false, new Vector(5.0f, 4.0f));
			super.createActor(bike);
			super.setViewCandidate(bike);
			
			//génère les obstacles
			Crate crate1 = new Crate(this, false, new Vector(0.0f, 5.0f));
			super.createActor(crate1);
			Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f));
			super.createActor(crate2);
			Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f));
			super.createActor(crate3);
		}catch (NullPointerException e) {}
		return true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		bike.update(deltaTime);
		finish.update(deltaTime);
	}
	
	@Override
	public void end() {
		super.end();
		super.removeActor(bike);
	}
	
	//GETTER
	private Polyline getPolyline() {
		return new Polyline(
				-100.0f, MAP_MIN_HEIGHT-100,
				-100.0f, MAP_MAX_HEIGHT+100,
				-50.0f, MAP_MAX_HEIGHT+100,
				-50.0f, 0.0f,
				0.0f, 0.0f,
				3.0f, 1.0f,
				8.0f, 1.0f, 
				15.0f, 3.0f, 
				16.0f, 3.0f, 
				25.0f, 0.0f, 
				35.0f, -5.0f, 
				50.0f, -5.0f, 
				55.0f, -4.0f, 
				65.0f, 0.0f,
				100.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				125.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				150.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				175.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				200.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				225.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				250.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				275.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				300.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				325.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				350.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				375.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				400.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				425.0f, (float) Math.random()*MAP_DENIVELATION_MAX-MAP_MAX_HEIGHT,
				450.0f,0.0f,
				500.0f, 0.0f,
				500.0f, MAP_MAX_HEIGHT+100,
				550.0f, MAP_MAX_HEIGHT+100,
				550.0f, MAP_MIN_HEIGHT-100
				);
	}
}
