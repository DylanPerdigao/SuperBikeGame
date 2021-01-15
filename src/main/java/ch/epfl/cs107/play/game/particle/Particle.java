/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.particle;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Particle extends GameEntity implements Graphics, Positionable{
	
	
	
	//N'EST PAS ABOUTI
	
	
	
	
	
	
	ActorGame game;
	private Vector position; // dans le repère absolu
	private Vector acceleration;
	private Vector velocity;
	private float angularPosition; 
	private float angularVelocity; 
	private float angularAcceleration;
	
	
	public Particle(ActorGame game, boolean fixed, Vector position) {
		super(game, false, position);
		this.position = position;
		this.game=game;
	}

	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		velocity = velocity.add(acceleration.mul(deltaTime)); 
		position = position.add(velocity.mul(deltaTime)); 
		angularVelocity += angularAcceleration * deltaTime; 
		angularPosition += angularVelocity * deltaTime;
	}
	
	public Particle copy(Vector position, Vector velocity, Vector acceleration) {
		return null;//new Particle(game, false,position);
	}
	
	@Override
	public Transform getTransform() {
		return Transform.I.rotated(angularPosition).translated(position);
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

}
