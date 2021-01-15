/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.triggers;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.SuperBikeGame;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Coin extends Triggers{
	//reapparition
	private static final float reccurence = Float.POSITIVE_INFINITY;
	private boolean isDraw = true;
	private int time;
	//graphique
	private ImageGraphics graphicsCoin;
	private PartBuilder partBuilder;	
	
		public Coin(ActorGame game, boolean fixed, Vector position) {
			super(game, fixed, position);
			create();
		}
		
		@Override
		public void update(float deltaTime) {
			super.update(deltaTime);
			comeback(deltaTime);
			//en cas de collission on ajoue 10 au "score"
			int numberOfCollisions = super.contactListener.getEntities().size();
		    	if (numberOfCollisions > 0 && Bike.mode == Bike.PLAY){ 
			    	SuperBikeGame.coins+=10;
			    	this.destroy();
			    	isDraw = false;
			}
		    	
		}
		
		@Override
		public void draw(Canvas canvas) {
			if (isDraw) {
				graphicsCoin.draw(canvas);
			}
		}
		//reapparition (elle ne dois jamais reapparaitre)
		void comeback(float deltaTime) {
			time++;
			if (time>reccurence) {
				create();
				time=0;
			}
			
		}
		//creer une nouvelle piece apres qu'elle soit disparue (ce qui n'est pas le cas comme elle reapparaiteras apres un temps infini)
		void create() {
			Circle hitBox = new Circle(0.1f);
		    partBuilder = getEntity().createPartBuilder();
		    partBuilder.setShape(hitBox);
		    partBuilder.setGhost(true);
			partBuilder.build();
			isDraw=true;
			graphicsCoin = new ImageGraphics("coin.gold.png",1,1);
			graphicsCoin.setParent(getEntity());	
		}
}
