/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 23 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Mouse;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game{
		
	 	//Window/World
		private Window window;
	    private World world;
	    
	    //Liste d'acteurs
	  	protected ArrayList<Actor> actors  = new ArrayList<Actor>();
	  		
		// Viewport properties
		private Vector viewCenter;
		private Vector viewTarget;
		private Positionable viewCandidate;
		private Actor actor;
		private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
		private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
		private static final float VIEW_SCALE = 20.0f;
		
		//levels
		public final int MENU = 0;
		public final int GRASS_LEVEL = 1;
		public final int SNOW_LEVEL = 2;
		public final int MOON_LEVEL = 3;
		public final int DIMENSIONAL_LEVEL = 4;
		protected int levelNumber=MENU;
		
		
	    @Override
	    public boolean begin(Window window, FileSystem fileSystem) {
	    		//Initialization
		    		this.window = window;
		    		viewCenter = Vector.ZERO;
		    		viewTarget = Vector.ZERO;
	    		//World
				world = new World();
				world.setGravity(new Vector(0.0f, -9.81f));
		    return true;
	    }

		@Override
	    public void update(float deltaTime) {
	    		//UPDATE
		    	world.update(deltaTime);
		    	
		    	//parcours tout les acteurs de la liste
			     for (int i=0; i<actors.size(); i++) {
			    	 	actors.get(i).draw(getCanvas());
			     }

		    	//CAMERA POSITION
		    	// Update expected viewport center
		    	if (viewCandidate != null) { 
		    		viewTarget = viewCandidate.getPosition().add(viewCandidate.getVelocity() .mul(VIEW_TARGET_VELOCITY_COMPENSATION));
		    	}
		    	// Interpolate with previous location
		    	float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		    	viewCenter = viewCenter.mixed( viewTarget , 1.0f - ratio);
		    	// Compute new viewport
		    	Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		    	window.setRelativeTransform(viewTransform);
	    }
	    
	    @Override
	    public void end() {
	        removeActor(actor);
	    }
	    
	    //Create Actor
	    public void createActor(Actor actor) {
	    		actors.add(actor);
		}
	    //Remove Actor
	    public void removeActor(Actor actor) {
			actors.remove(actor);
		}
	    
	    //Create an entity
	    protected Entity newEntity(Vector position, boolean fixed) {
    			EntityBuilder entityBuilder = world.createEntityBuilder();
    			entityBuilder.setFixed(fixed);
			entityBuilder.setPosition(position); 
		return entityBuilder.build(); 
	    }
	    protected Entity newEntity(boolean fixed) {
			EntityBuilder entityBuilder = world.createEntityBuilder();
			entityBuilder.setFixed(fixed);
			entityBuilder.setPosition(new Vector(0.0f,0.0f)); 
			return entityBuilder.build(); 
	    }
	    
	    public WheelConstraintBuilder createWheelConstraintBuilder() {
	    		return world.createWheelConstraintBuilder();	
	    }
	    
	    //GETTERS
	    public int getLevelNumber(){ 
	  		return levelNumber;
	  	}
	    public Mouse getMouse(){ 
	  		return window.getMouse();
	  	}
	  	public Keyboard getKeyboard(){ 
	  		return window.getKeyboard();
	  	}
	  	public Canvas getCanvas(){ 
	  		return window;
	  	}
	  	public World getWorld(){ 
	  		return world;
	  	}
	  	
	    //SETTERS
	    public void setViewCandidate(Positionable viewCandidate) {
	    	this.viewCandidate = viewCandidate;
	    }
	    
}
