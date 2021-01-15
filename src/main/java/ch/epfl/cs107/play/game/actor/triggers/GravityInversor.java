/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 11 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.triggers;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GravityInversor extends Triggers{
	
	private ImageGraphics graphicsCrate;
	private float width;
	private float height;
	private float time;
	public GravityInversor(ActorGame game, boolean fixed, Vector position, float width, float height) {
		super(game, true, position);
		this.width = width;
		this.height = height;
		create();
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
	    	if (super.contactListener.hasContacts()){ 
	    		//en cas de contact change la gravité
	    		getOwner().getWorld().setGravity(new Vector(0.1f, 1.0f));
	    	}else {
	    		//sinon fait avancer le timer 
	    		time++;
	    	}
	    	//si le timer depasse 100 alors la gravité redeviens normale
	    	if (time > 100) {
	    		getOwner().getWorld().setGravity(new Vector(0f, -9.81f));
	    		time=0;
	    	}
	}
	
	public void draw(Canvas canvas){
		graphicsCrate.draw(canvas);
	}
	
	@Override
	void create() {
		Polygon polygon = new Polygon( 
				new Vector(0.0f, 0.0f), 
				new Vector(width, 0.0f), 
				new Vector(width, height),
				new Vector(0.0f, height) 
		);
		 
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polygon);
		partBuilder.build();
		
		graphicsCrate = new ImageGraphics("gravity.inversor.png", width, height);
		graphicsCrate.setAlpha(1.0f);
		graphicsCrate.setDepth(1.0f);
		graphicsCrate.setParent(getEntity());
	}
	@Override
	void comeback(float time) {
	}
}
