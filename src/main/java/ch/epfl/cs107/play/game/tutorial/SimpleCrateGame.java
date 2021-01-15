/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 18 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class SimpleCrateGame implements Game{

	private Window window;
	private World world;
	private Entity block;
	private Entity crate;
	private ImageGraphics graphicsBlock;
	private ImageGraphics graphicsCrate;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		this.window = window;
		
		//Init
			PartBuilder partBuilder;
			EntityBuilder entityBuilder;
			Polygon polygon = new Polygon( 
					new Vector(0.0f, 0.0f), 
					new Vector(2.0f, 0.0f), 
					new Vector(2.0f, 2.0f),
					new Vector(0.0f, 2.0f) 
			);
		
		//World
			world = new World();
			world.setGravity(new Vector(0.0f, -9.81f));
		
		//Block
			entityBuilder= world.createEntityBuilder();
			entityBuilder.setFixed(true);
			entityBuilder.setPosition(new Vector(1.0f, 0.5f)); 
			block = entityBuilder.build();
			
			partBuilder = block.createPartBuilder();
			partBuilder.setShape(polygon);
			partBuilder.setFriction(0.5f);
			partBuilder.build();
			
			graphicsBlock = new ImageGraphics("stone.broken.4.png", 1, 1);
			graphicsBlock.setAlpha(1.0f);
			graphicsBlock.setDepth(0.0f);
			graphicsBlock.setParent(block);

		//Crate
			entityBuilder.setFixed(false);
			entityBuilder.setPosition(new Vector(0.2f, 4.0f)); 
			crate = entityBuilder.build();
			
			partBuilder = crate.createPartBuilder();
			partBuilder.setShape(polygon);
			partBuilder.build();
			
			graphicsCrate = new ImageGraphics("box.4.png", 1, 1);
			graphicsCrate.setAlpha(1.0f);
			graphicsCrate.setDepth(1.0f);
			graphicsCrate.setParent(crate);
		return true;
	}

	@Override
	public void update(float deltaTime) {
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		world.update(deltaTime);
		graphicsBlock.draw(window);
		graphicsCrate.draw(window);
	}

	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}
}
