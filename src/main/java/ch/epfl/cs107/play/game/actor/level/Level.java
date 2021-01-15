/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 30 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;

import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Level extends Node implements Actor{

	@SuppressWarnings("unused")
	private ActorGame game;

	public Level(ActorGame game) {
		this.game = game;
	}
	public abstract void begin();
	public abstract void update(float deltaTime);
	public abstract void draw(Canvas canvas);
	public abstract void destroy();
	public abstract void createAllActors();
	


}
