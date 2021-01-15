/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 11 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.special;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Plank;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bascule extends GameEntity implements Actor{
	private ShapeGraphics graphicsBascule;
	private Plank plank;
	Polygon polygon;
	public Bascule(ActorGame game, boolean fixed, Vector position) {
		super(game, true, position);
		//creer la planche
		plank = new Plank(game, false, position);
		//creer un triangle
		polygon = new Polygon( 
	    		new Vector(0.0f,1f), 
	    		new Vector(-0.5f,0f), 
	    		new Vector(0.5f,0f)
	    	);
		PartBuilder partBuilder = getEntity().createPartBuilder();
	    partBuilder.setShape(polygon);
	    partBuilder.build();
	
	    graphicsBascule = new ShapeGraphics(polygon, Color.RED, Color.RED, 0.1f);
	    graphicsBascule.setAlpha(1.0f);
	    graphicsBascule.setDepth(1.0f);
	    graphicsBascule.setParent(getEntity());
	 
	  //Constrain - attache le triangle et la planche
		RevoluteConstraintBuilder revoluteConstraintBuilder = super.getOwner().getWorld().createRevoluteConstraintBuilder();
		revoluteConstraintBuilder.setFirstEntity(this.getEntity()); 
		revoluteConstraintBuilder.setFirstAnchor(new Vector(0.0f,1f)); 
		revoluteConstraintBuilder.setSecondEntity(plank.getEntity()); 
		revoluteConstraintBuilder.setSecondAnchor(new Vector((plank.plankWidth/2)+0.05f,plank.plankHeight/2)); 
		revoluteConstraintBuilder.setInternalCollision(true);
		revoluteConstraintBuilder.build();
	}

	public void draw(Canvas canvas){
		graphicsBascule.draw(canvas);
		plank.draw(canvas);
	}
	
	public void destroy() {
		plank.getEntity().destroy();
		this.getEntity().destroy();
		super.getOwner().removeActor(plank);
		super.getOwner().removeActor(this);
	}



}
