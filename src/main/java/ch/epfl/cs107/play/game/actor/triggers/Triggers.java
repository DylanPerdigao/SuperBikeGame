/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.triggers;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Vector;

public abstract class Triggers extends GameEntity{
	
	protected BasicContactListener contactListener;
	
	public Triggers(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		//Contact
		contactListener = new BasicContactListener(); 
		getEntity().addContactListener(contactListener);
	}
	
	abstract void create();
	
	abstract void comeback(float time);
}
