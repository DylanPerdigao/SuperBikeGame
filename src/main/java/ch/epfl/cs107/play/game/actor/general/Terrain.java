/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 24 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {
	
private ShapeGraphics graphicsTerrain;
private Polyline polyline;
private PartBuilder partBuilder;

	public Terrain(ActorGame game, boolean fixed, Vector position, Polyline polyline, Color groundColor, Color undergroundColor, float friction) {
		super(game, true, position);
		
		partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polyline);
		partBuilder.setFriction(friction);
		partBuilder.build();
		
		graphicsTerrain = new ShapeGraphics(polyline, undergroundColor, groundColor, 0.1f);
		graphicsTerrain.setAlpha(1.0f);
		graphicsTerrain.setDepth(1.0f);
		graphicsTerrain.setParent(getEntity());
		
	}
	
	public void draw(Canvas canvas){
		graphicsTerrain.draw(canvas);
	}

	//GETTERS-----------
	public Polyline getPolyline() {
		return polyline;
	}
	public PartBuilder getPartBuilder() {
		return partBuilder;
	}
	public ShapeGraphics getGraphics() {
		return graphicsTerrain;
	}
}
