/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 20 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.tutorial;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game{
		
	    private Window window;
	    private World world;
		private Entity block;
		private Entity ball;
		private Entity plank;
	    private ImageGraphics graphicsBlock;
	    private ImageGraphics graphicsPlank;
	    private ImageGraphics ballGraphics;
	    
	    @Override
	    public boolean begin(Window window, FileSystem fileSystem) {
	    	
	    		this.window = window;
	    		
	    		//Init
		    		EntityBuilder entityBuilder;
		    		float blockWidth = 10.0f;
				float blockHeight = 1.0f;
				float plankWidth = 5.0f;
				float plankHeight = 0.2f;
				PartBuilder partBuilder;
			    Polygon polygon;
			    Circle circle;
			    
	    		//World
			    world = new World();
			    world.setGravity(new Vector(0.0f, -9.81f));
			    entityBuilder = world.createEntityBuilder();
			    
			//Block
				entityBuilder.setFixed(true);
				entityBuilder.setPosition(new Vector(-5.0f, -1.0f)); 
			    block = entityBuilder.build();
				
			    polygon = new Polygon( 
			    		new Vector(0.0f, 0.0f), 
			    		new Vector(blockWidth, 0.0f), 
			    		new Vector(blockWidth, blockHeight),
			    		new Vector(0.0f, blockHeight) 
			    	);
			    partBuilder = block.createPartBuilder();
				partBuilder.setShape(polygon);
				partBuilder.setFriction(0.5f);
				partBuilder.build();
				
			    graphicsBlock = new ImageGraphics("stone.broken.4.png", blockWidth, blockHeight);
			    graphicsBlock.setAlpha(1.0f);
		        graphicsBlock.setDepth(0.0f);
			    graphicsBlock.setParent(block);
		    
		  //Plank
				entityBuilder.setFixed(false);
				entityBuilder.setPosition(new Vector(-2.5f, -0.8f)); 
			    plank = entityBuilder.build();
			    
			    polygon = new Polygon( 
			    		new Vector(0.0f, 0.0f), 
			    		new Vector(plankWidth, 0.0f), 
			    		new Vector(plankWidth, plankHeight),
			    		new Vector(0.0f, plankHeight) 
			    	);
			    partBuilder = plank.createPartBuilder();
			    partBuilder.setShape(polygon);
			    partBuilder.build();
			    
			    graphicsPlank = new ImageGraphics("wood.3.png", plankWidth, plankHeight);
			    graphicsPlank.setAlpha(1.0f);
		        graphicsPlank.setDepth(0.0f);
			    graphicsPlank.setParent(plank);
		    
		    //Ball
			    entityBuilder.setFixed(false);
			    entityBuilder.setPosition(new Vector(0.5f, 4.0f)); 
			    ball = entityBuilder.build();
			    
			    partBuilder = ball.createPartBuilder();
			    circle = new Circle(0.5f);
			    partBuilder.setShape(circle);
			    partBuilder.build();
			    
				ballGraphics = new ImageGraphics("explosive.11.png", 1, 1, new Vector(0.5f, 0.5f));
				ballGraphics.setParent(ball);
			
			//Constrain
				RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
				revoluteConstraintBuilder.setFirstEntity(block); 
				revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2,(blockHeight*7)/4)); 
				revoluteConstraintBuilder.setSecondEntity(plank); 
				revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2,plankHeight/2)); 
				revoluteConstraintBuilder.setInternalCollision(true);
				revoluteConstraintBuilder.build();
		    return true;
	    }
	    
	    @Override
	    public void update(float deltaTime) {
	    		window.setRelativeTransform(Transform.I.scaled(10.0f));
		    	world.update(deltaTime);
		    	graphicsBlock.draw(window);
		    	graphicsPlank.draw(window);
		    	ballGraphics.draw(window);
		    	//if (window.getMouse().getLeftButton().isPressed() {...}
		    	if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) { 
		    		ball.applyAngularForce(10.0f);
		    	} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
		    		ball.applyAngularForce(-10.0f);
		    	}
	    }
	    
	    @Override
	    public void end() {
	        // Empty on purpose, no cleanup required yet
	    }
	}


