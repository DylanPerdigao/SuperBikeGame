/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 24 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.special;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SpecialTerrain extends GameEntity implements Actor {
	
private ShapeGraphics graphicsTerrain;

	public SpecialTerrain(ActorGame game, boolean fixed, Vector position, Polyline polyline, Color groundColor, Color undergroundColor, float friction) {
		super(game, true, position);
		//ce type de terrain n'a pas de hitbox
		graphicsTerrain = new ShapeGraphics(polyline, undergroundColor, groundColor, 0.1f);
		graphicsTerrain.setAlpha(1.0f);
		graphicsTerrain.setDepth(1.0f);
		graphicsTerrain.setParent(getEntity());
		
	}
	
	public void draw(Canvas canvas){
		graphicsTerrain.draw(canvas);
	}

	//GETTERS-----------
	public ShapeGraphics getGraphics() {
		return graphicsTerrain;
	}
}
