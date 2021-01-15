/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 2 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.triggers;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends Triggers{

	private ImageGraphics graphicsFinish;
	private PartBuilder partBuilder;
	private final static int WIN = 1; 
	
	public Finish(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);	
		create();
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		//En cas de de collision met le mode Victoire
		int numberOfCollisions = super.contactListener.getEntities().size();
	    	if (numberOfCollisions > 0){ 
	    		Bike.setMode(WIN);
	    	}
	}
	
	@Override
	public void draw(Canvas canvas) {
		graphicsFinish.draw(canvas);
	}

	@Override
	void create() {
		Polygon hitBox = new Polygon(
				new Vector(0.0f, 0.0f), 
				new Vector(1.0f, 0.0f), 
				new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f) 
		);
	    partBuilder = getEntity().createPartBuilder();
	    partBuilder.setShape(hitBox);
	    partBuilder.setGhost(true);
		partBuilder.build();
		
		graphicsFinish = new ImageGraphics("flag.red.png",2,2);
		graphicsFinish.setParent(getEntity());
	}

	@Override
	void comeback(float time) {
	}
}
