/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 23 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;

import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame{
	private Window window;
		
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		this.window = window;
		Crate crate1 = new Crate(this, false, new Vector(0.0f, 5.0f));
		super.createActor(crate1);
		Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f));
		super.createActor(crate2);
		Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f));
		super.createActor(crate3);
		return true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
	}
	
	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
