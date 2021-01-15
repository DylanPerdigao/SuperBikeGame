/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 11 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Plank extends GameEntity implements Actor{
	private ImageGraphics graphicsPlank;
	public float plankWidth = 10.0f;
	public float plankHeight = 0.2f;
	public Plank(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		Polygon polygon = new Polygon( 
	    		new Vector(0.0f, 0.0f), 
	    		new Vector(plankWidth, 0.0f), 
	    		new Vector(plankWidth, plankHeight),
	    		new Vector(0.0f, plankHeight) 
	    	);
		PartBuilder partBuilder = getEntity().createPartBuilder();
	    partBuilder.setShape(polygon);
	    partBuilder.setFriction(20f);
	    partBuilder.build();
	
		graphicsPlank = new ImageGraphics("wood.3.png", plankWidth, plankHeight);
	    graphicsPlank.setAlpha(1.0f);
	    graphicsPlank.setDepth(0.0f);
	    graphicsPlank.setParent(getEntity());
	}

	public void draw(Canvas canvas){
		graphicsPlank.draw(canvas);
	}
}
