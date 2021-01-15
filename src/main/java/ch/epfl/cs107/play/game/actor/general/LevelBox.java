/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
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

public class LevelBox extends GameEntity implements Actor{
	
	private ImageGraphics graphicsLevelBox;
	private ImageGraphics graphicsNumber;
	
	public LevelBox(ActorGame game, boolean fixed, Vector position, boolean isEnabled ,String levelNumber) {
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
		//Permet de faire la différence entre la boite activée et desactivée
		if (isEnabled) {
			graphicsLevelBox = new ImageGraphics("box.1.enabled.png", 2, 2);
		}else {
			graphicsLevelBox = new ImageGraphics("box.1.disabled.png", 2, 2);
		}
		graphicsLevelBox.setAlpha(1.0f);
		graphicsLevelBox.setDepth(0.0f);
		graphicsLevelBox.setParent(getEntity());
		
		graphicsNumber = new ImageGraphics(levelNumber, 1, 1);
		graphicsNumber.setAnchor(new Vector(-0.5f,-0.5f));
		graphicsNumber.setDepth(1.0f);
        graphicsNumber.setParent(getEntity());
       
	}

	public void draw(Canvas canvas){
		graphicsLevelBox.draw(canvas);
		graphicsNumber.draw(canvas);
	}

}
