/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.particle;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Emitter implements Actor{
	
	
	//N'EST PAS ABOUTI
	
	
	
	
	
	
	private ArrayList<Particle> imageParticleTab = new ArrayList<Particle>();

	private int numberOfParticle;
	
	public Emitter(ActorGame game, Vector position, Shape shape, int numberOfParticles) {
		for (int i=0; i<numberOfParticle; i++) {
			//imageParticleTab.add(new Particle(game, true, position));
		}
	}
	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		for (int i=0; i<imageParticleTab.size(); i++) {
			imageParticleTab.get(i).draw(canvas);
		}
	}

	
}
