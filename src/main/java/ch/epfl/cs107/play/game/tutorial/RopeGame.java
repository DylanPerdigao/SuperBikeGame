/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 20 nov. 2017
 *
 */
/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 18 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RopeGame implements Game{
	
    private Window window;
    private World world;
	private Entity block;
	private Entity ball;
    private ImageGraphics graphicsBlock;
    private ShapeGraphics ballGraphics;
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
    	
    		this.window = window;
    		
    		//Init
	    		float blockWidth = 1.0f;
	    		float blockHeight = 1.0f;
	    		EntityBuilder entityBuilder;
	    		PartBuilder partBuilder;
	    		Polygon polygon = new Polygon( 
	    			new Vector(0.0f, 0.0f), 
	    			new Vector(blockWidth, 0.0f), 
	    			new Vector(blockWidth, blockHeight),
	    			new Vector(0.0f, blockHeight) 
	    		);
	    		Circle circle = new Circle(0.6f);
    		//World
		    world = new World();
		    world.setGravity(new Vector(0.0f, -9.81f));
		    entityBuilder = world.createEntityBuilder();
		    
		//Block
			entityBuilder.setFixed(true);
			entityBuilder.setPosition(new Vector(1.0f, 0.5f)); 
		    block = entityBuilder.build();
		    
		    partBuilder = block.createPartBuilder();
			partBuilder.setShape(polygon);
			partBuilder.setFriction(1.5f);
			partBuilder.build();
			
		    graphicsBlock = new ImageGraphics("stone.broken.4.png", 1, 1);
		    graphicsBlock.setAlpha(0.5f);
	        graphicsBlock.setDepth(0.0f);
		    graphicsBlock.setParent(block);
	    
	    //Ball
		    entityBuilder.setFixed(false);
		    entityBuilder.setPosition(new Vector(0.5f, 4.0f)); 
		    ball = entityBuilder.build();
		    
		    partBuilder = ball.createPartBuilder();
		    partBuilder.setShape(circle);
		    partBuilder.build();
		    
			ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.RED, 0.1f, 1.0f, 0);
			ballGraphics.setParent(ball);
		//Constrain
			RopeConstraintBuilder ropeConstraintBuilder = world.createRopeConstraintBuilder();
			ropeConstraintBuilder.setFirstEntity(block); 
			ropeConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, blockHeight/2)); 
			ropeConstraintBuilder.setSecondEntity(ball); 
			ropeConstraintBuilder.setSecondAnchor(Vector.ZERO); 
			ropeConstraintBuilder.setMaxLength(3.0f); 
			ropeConstraintBuilder.setInternalCollision(true); 
			ropeConstraintBuilder.build();
	    return true;
    }
    
    @Override
    public void update(float deltaTime) {
    		window.setRelativeTransform(Transform.I.scaled(10.0f));
	    	world.update(deltaTime);
	    	graphicsBlock.draw(window);
	    	ballGraphics.draw(window);
    }
    
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}

