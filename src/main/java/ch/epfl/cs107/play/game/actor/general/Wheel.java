/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 25 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;

import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor{
	
private ShapeGraphics wheelGraphics;
private WheelConstraint constraint;
private ShapeGraphics wheelRimsGraphics;
private Entity vehicle;
	public Wheel(ActorGame game, boolean fixed, Vector position, float rayon) {
		super(game, false, position);
		
		PartBuilder partBuilder = getEntity().createPartBuilder(); 
	    Circle circle = new Circle(rayon);
	    
	    partBuilder.setShape(circle);
	    partBuilder.build();
	    
	    //creer des jantes pour les roues
	    Polygon polygon = new Polygon( 
	    			new Vector(0.35f, 0.35f),
	    			new Vector(0.04f, 0.04f),
				new Vector(0.0f, 0.35f),
				new Vector(0.0f, 0.04f),
				new Vector(-0.35f, 0.35f),
				new Vector(-0.04f, 0.04f),
				new Vector(0.35f, 0.0f),
				new Vector(0.04f, 0.0f),
				new Vector(0.35f, -0.35f),
				new Vector(0.04f, -0.04f),
				new Vector(0.0f, -0.35f),
				new Vector(0.0f, -0.04f),
				new Vector(-0.35f, -0.35f),
				new Vector(-0.04f, -0.04f),
				new Vector(-0.35f, 0.0f),
				new Vector(-0.04f, 0.0f)
		);
	    partBuilder.setShape(polygon);
	    partBuilder.setGhost(true);
		partBuilder.build();
		
		wheelGraphics = new ShapeGraphics(circle, null, Color.GRAY, 0.1f, 1, 1);
		wheelGraphics.setDepth(0.2f);
		wheelGraphics.setParent(getEntity());
		wheelRimsGraphics = new ShapeGraphics(polygon, null, Color.LIGHT_GRAY, 0.01f);
		wheelRimsGraphics.setDepth(0.1f);
		wheelRimsGraphics.setParent(getEntity());
	}

	public void attach(Entity vehicle, Vector anchor, Vector axis) {
				//CONSTRAIN
				WheelConstraintBuilder constraintBuilder = super.getOwner().createWheelConstraintBuilder();
				constraintBuilder.setFirstEntity(vehicle);
				// point d'ancrage du véhicule : 
				constraintBuilder.setFirstAnchor(anchor);
				// Entity associée à la roue :
				constraintBuilder.setSecondEntity(getEntity());
				// point d'ancrage de la roue (son centre):
				constraintBuilder.setSecondAnchor(Vector.ZERO);
				// axe le long duquel la roue peut se déplacer :
				constraintBuilder.setAxis(axis);
				// fréquence du ressort associé
				constraintBuilder.setFrequency(3.0f); 
				constraintBuilder.setDamping(0.5f);
				// force angulaire maximale pouvant être appliquée 
				//à la roue pour la faire tourner :
				constraintBuilder.setMotorMaxTorque(10.0f); 
				constraint = constraintBuilder.build();
				this.vehicle = vehicle;
		
	}
	//gere le moteur (inutilisée voir CONCEPTION)
	public void power(float speed) {
		if(speed>0) {
			constraint.setMotorEnabled(true);
		}
		constraint.setMotorSpeed(speed);
	}
	//arrête le moteur(inutilisée voir CONCEPTION)
	public void relax() {
		this.power(0.0f);
		constraint.setMotorEnabled(false);
	}
	
	public void destroy() {
		detach();
		super.destroy();
	}
	
	//enlève la contrainte
	public void detach() {
		constraint.destroy();
	}
	
	
	@Override
	public void draw(Canvas canvas){
		wheelGraphics.draw(canvas);
		wheelRimsGraphics.draw(canvas);
	} 
	/*
	 * @return relative rotation speed, in radians per second 
	 */
	public float getSpeed() {
		return  getEntity().getAngularVelocity() - vehicle.getAngularVelocity();
	}

}
