/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 23 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor;


import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity implements Actor {
	private Entity entity;
	private ActorGame game;
	
	public GameEntity(ActorGame game, boolean fixed, Vector position){
		this.game = game;
		entity = game.newEntity(position, fixed);
	}
	public GameEntity(ActorGame game, boolean fixed){
		this.game = game;
		entity = game.newEntity(fixed);
	}
	
	public void destroy() {
		entity.destroy();
	}
	
	//GETTERS
	public Entity getEntity(){
		return entity;
	}
	protected ActorGame getOwner(){
		return game;	
	}
	
	@Override
	public Transform getTransform() {
		return entity.getTransform();
	}
	@Override
	public Vector getVelocity() {
		return entity.getVelocity();
	}
}
