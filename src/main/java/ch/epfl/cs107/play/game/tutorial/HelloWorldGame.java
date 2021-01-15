package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    
    // graphical representation of the body
    private ImageGraphics graphics;
    private ImageGraphics graphicsBow;
    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));
        
        //Create an entity
        EntityBuilder entityBuilder = world.createEntityBuilder();
        // Make sure this does not move
        entityBuilder.setFixed(true);
        // This helps you define properties, like its initial location
        entityBuilder.setPosition(new Vector(1.f, 1.5f)); 
        // Once ready, the body can be built
        body = entityBuilder.build();
        
        graphics = new ImageGraphics("stone.broken.4.png", 1, 1);
        // Transparency can be chosen for each drawing (0.0 - transparent, 1.0 - opaque)
        graphics.setAlpha(1.0f);
        // Additionally, you can choose a depth when drawing
        // Therefore, you could draw behind what you have already done 
        graphics.setDepth(0.0f);
        graphics.setParent(body);
        
        graphicsBow = new ImageGraphics("bow.png", 1, 1);
        graphicsBow.setAlpha(1.0f);
        graphicsBow.setDepth(1.0f);
        graphicsBow.setParent(body);
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
	    // The actual rendering will be done now, by the program loop
    	
	    	// we must place the camera where we want
	    	// We will look at the origin (identity) and increase the view size a bit
    		window.setRelativeTransform(Transform.I.scaled(10.0f));
    	
	    	// Game logic comes here 
	    	// Nothing to do, yet
	    	
	    	// Simulate physics
	    	// Our body is fixed, though, nothing will move 
	    	world.update(deltaTime);
	    	// We can render our scene now,
	    	graphics.draw(window);
	    	graphicsBow.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
