/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 20 nov. 2017
 *
 */
/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 20 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ContactGame implements Game{
		
	    private Window window;
	    private World world;
		private Entity block;
		private Entity ball;
	    private ImageGraphics graphicsBlock;
	    private ShapeGraphics ballGraphics;
	    private BasicContactListener contactListener;
	    
	    @Override
	    public boolean begin(Window window, FileSystem fileSystem) {
	    	
	    		this.window = window;
	    		
	    		//Init
		    		EntityBuilder entityBuilder;
		    		float blockWidth = 10.0f;
				float blockHeight = 1.0f;
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
		    
		    //Ball
			    entityBuilder.setFixed(false);
			    entityBuilder.setPosition(new Vector(0.5f, 4.0f)); 
			    ball = entityBuilder.build();
			    
			    partBuilder = ball.createPartBuilder();
			    circle = new Circle(0.5f);
			    partBuilder.setShape(circle);
			    partBuilder.build();
			    
				ballGraphics = new ShapeGraphics(circle,Color.BLUE, Color.BLUE, .1f, 1, 0);
				ballGraphics.setParent(ball);
			
			//Contact
				contactListener = new BasicContactListener(); 
				ball.addContactListener(contactListener);
		    return true;
	    }
	    
	    @Override
	    public void update(float deltaTime) {
	    		window.setRelativeTransform(Transform.I.scaled(10.0f));
		    	world.update(deltaTime);
		    	graphicsBlock.draw(window);
		    	//if (window.getMouse().getLeftButton().isPressed() {...}
		    	if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) { 
		    		ball.applyAngularForce(2.0f);
		    	} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
		    		ball.applyAngularForce(-2.0f);
		    	}
		    	// contactListener is associated to ball
		    	// contactListener.getEntities() returns the list of entities in collision with ball
		    	int numberOfCollisions = contactListener.getEntities().size();
		    	if (numberOfCollisions > 0){ 
		    		ballGraphics.setFillColor(Color.RED);
		    	} else {
		    		ballGraphics.setFillColor(Color.GREEN);
		    	}
		    	
		    	if (ball.getPosition().y < -10.0f) {
			    	ball.setPosition(new Vector(ball.getPosition().x, 10.0f));
			}
		    	if (ball.getPosition().x > 10.0f) {
			    	ball.setPosition(new Vector(-10.0f, ball.getPosition().y));
			}else if(ball.getPosition().x < -10.0f){
				ball.setPosition(new Vector(10.0f, ball.getPosition().y));
			}
		    	ballGraphics.draw(window);
	    }
	    
	    @Override
	    public void end() {
	        // Empty on purpose, no cleanup required yet
	    }
	}



