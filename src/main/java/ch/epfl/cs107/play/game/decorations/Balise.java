/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 10 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.decorations;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Balise extends GameEntity implements Actor{
	private ImageGraphics baliseGraphics;
	
	public Balise(ActorGame game, boolean fixed, Vector position) {
		super(game, false, position);
		baliseGraphics = new ImageGraphics("balise.png", 1, 2, position);
		baliseGraphics.setAlpha(1.0f);
		baliseGraphics.setDepth(1000.0f);	
	}

	public void draw(Canvas canvas){
		baliseGraphics.draw(canvas);
	}
}
