/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 10 déc. 2017
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

public class Arrow extends GameEntity implements Actor{
	
	private ImageGraphics graphicsLevelBox;
	
	public Arrow(ActorGame game, boolean fixed, Vector position) {
		super(game, true, position);
		
		Polygon polygon = new Polygon( 
				new Vector(0.0f, 0.0f), 
				new Vector(2.0f, 0.0f), 
				new Vector(2.0f, 2.0f),
				new Vector(0.0f, 2.0f) 
		);
		 
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polygon);
		partBuilder.build();
		
		graphicsLevelBox = new ImageGraphics("arrow.png", -3, 1);
		graphicsLevelBox.setAlpha(1.0f);
		graphicsLevelBox.setDepth(0.0f);
		graphicsLevelBox.setParent(getEntity());    
	}

	public void draw(Canvas canvas){
		graphicsLevelBox.draw(canvas);
	}
}