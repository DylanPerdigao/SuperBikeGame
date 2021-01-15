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
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Stone extends GameEntity implements Actor{
	
	private ImageGraphics graphicsStone;
	
	public Stone(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		 //Ball
		PartBuilder partBuilder = getEntity().createPartBuilder();
	    Circle circle = new Circle(4f);
	    partBuilder.setFriction(30f);
	    partBuilder.setShape(circle);
	    partBuilder.build();
	      
	  	graphicsStone = new ImageGraphics("stone.11.png", 8, 8, new Vector(0.5f, 0.5f));
	  	graphicsStone.setParent(getEntity());
	}

	@Override
	public void draw(Canvas canvas) {
		graphicsStone.draw(canvas);
	}

}
