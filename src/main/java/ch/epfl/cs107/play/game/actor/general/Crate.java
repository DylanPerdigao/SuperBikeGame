/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 23 nov. 2017
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


public class Crate  extends GameEntity implements Actor{
	
	private ImageGraphics graphicsCrate;
	
	public Crate(ActorGame game, boolean fixed, Vector position) {
		super(game, false, position);
		
		Polygon polygon = new Polygon( 
				new Vector(0.0f, 0.0f), 
				new Vector(1.0f, 0.0f), 
				new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f) 
		);
		 
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polygon);
		partBuilder.build();
		
		graphicsCrate = new ImageGraphics("box.4.png", 1, 1);
		graphicsCrate.setAlpha(1.0f);
		graphicsCrate.setDepth(1.0f);
		graphicsCrate.setParent(getEntity());
		
	}

	public void draw(Canvas canvas){
		graphicsCrate.draw(canvas);
	}
}
