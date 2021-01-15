/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 24 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;

import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.game.addons.RGBColor;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor{
//Hitbox
	PartBuilder partBuilder;
//GRaphique
private ShapeGraphics graphicsEye;
private ShapeGraphics graphicsHair;
private ShapeGraphics graphicsHead;
private ShapeGraphics graphicsArm;
private ShapeGraphics graphicsBody;
private ShapeGraphics graphicsLeftThigh;
private ShapeGraphics graphicsRightThigh;
private ShapeGraphics graphicsRightLeg;
private ShapeGraphics graphicsLeftLeg;
private ShapeGraphics graphicsCadre;
private ShapeGraphics graphicsSaddle;
private ShapeGraphics graphicsLeftPedal;
private ShapeGraphics graphicsRightPedal;
//roues
private Wheel leftWheel;
private Wheel rightWheel;
//formes
private Circle head;
private Circle eye;
private Polygon polygon;//hitBox
private Polyline hair;
private Polyline arm;
private Polyline body;
private Polyline leftThigh;
private Polyline rightThigh;
private Polyline leftLeg;
private Polyline rightLeg;
private Polyline cadre;
private Polyline saddle;
private Polyline leftPedal;
private Polyline rightPedal;

//Caractéristiques vélo
private final float MAX_WHEEL_SPEED = 20.0f;
private final float RADIUS_WHEELS = 0.5f;
private final float RADIUS_PEDALS = 0.25f;
private final float ANGULAR_FORCE = 10.0f;

//DIrection
private final int RIGHT = 1;
private final int LEFT = -1;
private int orientation=RIGHT;

//COntact
private ContactListener listener;
protected boolean hit;
//Saut
private boolean canJump;
private int force;
//Messages
private TextGraphics messageGameOver;
private TextGraphics messageWin;
private TextGraphics messageContinue;
private TextGraphics messageForce;
private TextGraphics messageRetry;



//VERROUS
public final static int RESET = -1;
public final static int PLAY = 0; 
public final static int WIN = 1; 
public final static int LOSE = 2; 
public static int mode = PLAY; // defini si le jeu est en cours, perdu, ou gagné , par defaut il est en cours

	/**
	 * Initialise le vélo avec les roues et sa hitBox
	 * @param game renvoie le game
	 * @param fixed renvoie si il est fixé
	 * @param position renvoie la position
	 */
	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, false, position);
		mode = PLAY;
		polygon = new Polygon( 
				new Vector(0.0f, 0.5f), 
				new Vector(0.5f, 1.0f), 
				new Vector(0.0f, 2.0f),
				new Vector(-0.5f, 1.0f) 
		);
	
		partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polygon);
		partBuilder.build();
		//Attach wheels
		leftWheel = new Wheel(game, true, new Vector(3.0f, 5.0f), RADIUS_WHEELS);
		leftWheel.attach(getEntity(), getLeftWheelLocation(orientation), new Vector(-0.5f, -1.0f));
		rightWheel = new Wheel(game, true, new Vector(5.0f, 5.0f), RADIUS_WHEELS);
		rightWheel.attach(getEntity(), getRightWheelLocation(orientation), new Vector(0.5f, -1.0f));
		
		//Dessine le personnage et le velo tourné a droite par defaut
		mouvements(RIGHT);
		//Detection de contact
		contact();
		
	}
	
	
	
	
	/**
	 * Verifie s'il y a contact
	 */
	private void contact() {
		listener = new ContactListener(){
			@Override
			public void beginContact(Contact contact) { 
				if (contact.getOther().isGhost()) {
					hit=false;
					return;
				}else if (	(contact.getOther().getEntity() == leftWheel.getEntity()) || 	
							(contact.getOther().getEntity() == rightWheel.getEntity())) {
					hit =false;
					return;
				}else {
					hit = true;
					return;
				}
			}
			@Override
			public void endContact(Contact contact) {
				if (hit) {
					System.out.println(contact.getOther().getEntity().getClass());
					gameOver();
					
				}
			}
		}; 
		getEntity().addContactListener(listener);
	}





	//UPDATE---------------------------------------------
	/**
	 * Actualise le velo
	 * Permet d'afficher les message de victoire/defaite selon l'état (le mode) du jeu
	 * Gere les touches pour se déplacer
	 * @param deltaTime temps d'intervalle entre chaque actualisation
	 */
	@Override
	public void update(float deltaTime) {
		mouvements(orientation);
		if (getOwner().getKeyboard().get(KeyEvent.VK_TAB).isPressed()) {//POUR TRICHER ET REUSSIR LE NIVEAU
			mode=WIN;
		}
		super.update(deltaTime);
		switch (mode) {
		case WIN:
			messageWin = new TextGraphics("SUPER!", 0.3f, Color.GREEN, RGBColor.getRGB(155,205,155), 0.03f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
			messageWin.setParent(getOwner().getCanvas()); 
			messageWin.setRelativeTransform(Transform.I.translated(0.0f, -1.2f));	
			messageWin.draw(getOwner().getCanvas());
			messageContinue = new TextGraphics("PRESS_SPACE_TO_CONTINUE", 0.05f, Color.GREEN, RGBColor.getRGB(155,205,155), 0.03f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
			messageContinue.setParent(getOwner().getCanvas()); 
			messageContinue.setRelativeTransform(Transform.I.translated(0.0f, -1.4f));	
			messageContinue.draw(getOwner().getCanvas());
			break;
		case LOSE:
			messageGameOver = new TextGraphics("WASTED", 0.3f, Color.RED, RGBColor.getRGB(238,162,173), 0.03f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
			messageGameOver.setParent(getOwner().getCanvas()); 
			messageGameOver.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));	
			messageGameOver.draw(getOwner().getCanvas());
			messageRetry = new TextGraphics("PRESS_R_TO_RETRY", 0.05f, Color.RED, RGBColor.getRGB(238,162,173), 0.03f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
			messageRetry.setParent(getOwner().getCanvas()); 
			messageRetry.setRelativeTransform(Transform.I.translated(0.0f, -1.4f));	
			messageRetry.draw(getOwner().getCanvas());
			break;
		default://mode=PLAY
			/*
			 * J'ai choisi de tourner le personnage avec les deux flèches directionelles
			 * pour freiner il suffit de changer de direction
			 */
			if (getOwner().getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
				if (leftWheel.getSpeed() < MAX_WHEEL_SPEED) {
					if(orientation==RIGHT) {
					leftWheel.getEntity().applyAngularForce(ANGULAR_FORCE );
					}else {
					rightWheel.getEntity().applyAngularForce(ANGULAR_FORCE );	
					}
					//this.getEntity().applyForce(new Vector(0f, 1f), getLeftWheelLocation(orientation));
				}
				if (rightWheel.getSpeed() >= 0.0f) {
					orientation = LEFT;
				}
			} else if (getOwner().getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
				if (leftWheel.getSpeed() > -MAX_WHEEL_SPEED) {
					if(orientation==RIGHT) {
					leftWheel.getEntity().applyAngularForce(-ANGULAR_FORCE );
					}else {
					rightWheel.getEntity().applyAngularForce(-ANGULAR_FORCE );	
					}
					//this.getEntity().applyForce(new Vector(0f, -1f), getLeftWheelLocation(orientation));
				}
				if (rightWheel.getSpeed() <= 0.0f) {
					orientation = RIGHT;
				}
	    		}
			if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
				if (canJump) {
					this.getEntity().setVelocity(new Vector(0.0f, 30.0f));
					force = 0;
					canJump = false;
				}
			}else {
				//il faut avoir toute sa force pour pouvoir sauter
				force++;
				if (force > 500) {
					messageForce = new TextGraphics("FORCE - 100/100", 0.015f, Color.WHITE, null, 0.03f, true, false, new Vector(0.0f, 0.0f), 1.0f, 100.0f);
					canJump=true;
				}else {
					messageForce = new TextGraphics("FORCE - " + force/5 +"/100", 0.015f, Color.WHITE, null, 0.03f, true, false, new Vector(0.0f, 0.0f), 1.0f, 100.0f);
				}
		    		messageForce.setParent(getOwner().getCanvas()); 
				messageForce.setRelativeTransform(Transform.I.translated(0.8f, -0.5f));	
				messageForce.draw(getOwner().getCanvas());
			}
		}
	}
	/**
	 * detruit tout et fait changer le mode de jeu en défaite
	 */
	private void gameOver() {
		if (mode != WIN) {
		mode=LOSE;
		mouvements(orientation);
		leftWheel.detach();
		rightWheel.detach();
		destroy();
		getOwner().end();
		}
	}
	/**
	 * détruit tout et change le mode de jeu en victoire
	 */
	public void win() {
		mode=WIN;
		mouvements(orientation);
		destroy();
		getOwner().end();
	}
	//DRAW---------------------------------------------
	
	/**
	 * dessine le cycliste
	 * @param canvas le canvevas ou l'on dessine le cycliste
	 */
	public void draw(Canvas canvas){
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);

		graphicsCadre.draw(canvas);
		graphicsSaddle.draw(canvas);
		graphicsLeftPedal.draw(canvas);
		graphicsRightPedal.draw(canvas);
		
		graphicsHair.draw(canvas);
		graphicsArm.draw(canvas);
		graphicsBody.draw(canvas);
		graphicsHead.draw(canvas);
		graphicsEye.draw(canvas);
		graphicsLeftThigh.draw(canvas);
		graphicsRightThigh.draw(canvas);
		graphicsLeftLeg.draw(canvas);
		graphicsRightLeg.draw(canvas);	
	}
	/**
	 * detruit les composantes externes et internes du velo
	 */
	public void destroy() {
		super.destroy();
		rightWheel.destroy();
		leftWheel.destroy();
	}
	//OTHER METHODS---------------------------------------------
	/**
	 * Reactualise les parties du coprs
	 * @param orientation un entier (positif ou negatif) permettant avec sa valeur d'inverser l'axe des "x" et donc inverser le cycliste
	 */
	private void mouvements(int orientation) {
		createGraphics();
		createShape(orientation);
	}
	/**
	 * actualise les membres
	 * @param orientation un entier (positif ou negatif) permettant avec sa valeur d'inverser l'axe des "x" et donc inverser le cycliste
	 */
	private void createShape(int orientation) {
		//Draw head 
				head = new Circle(0.2f, getHeadLocation(orientation));
				eye = new Circle(0.05f, getEyeLocation(orientation));
				hair = new Polyline(
						getHeadLocation(orientation), 
						new Vector(orientation * 0.0f, 1.7f),
						new Vector(orientation *-0.2f, 1.6f),
						new Vector(orientation *-0.2f, 2.0f),
						new Vector(orientation *0.3f, 2.0f),
						getHeadLocation(orientation)
					);
			// Draw arm
				arm = new Polyline(
					getShoulderLocation(orientation), 
					getHandLocation(orientation)
				);	
			//Draw Body
				body = new Polyline(
					getHeadLocation(orientation), 
					getBodyLocation(orientation)
				);
			//Draw Legs
				leftThigh = new Polyline(
					getBodyLocation(orientation), 
					getLeftKneeLocation(orientation)
				);
				rightThigh = new Polyline(
						getBodyLocation(orientation), 
						getRightKneeLocation(orientation)
				);
				leftLeg = new Polyline(
					getLeftKneeLocation(orientation),
					getLeftFootLocation(orientation)
				);
				
				rightLeg = new Polyline( 
					getRightKneeLocation(orientation),
					getRightFootLocation(orientation)
				);
				//DRAW BIKE
				saddle = new Polyline(
						getBodyLocation(orientation),
						getSaddleLocation(orientation),
						new Vector(orientation * -0.2f, 0.8f)
						);
				cadre = new Polyline( 
						getHandleBarLocation(orientation),
						getCenterBikeLocation(orientation),//milieu cadre - main - milieu du cadre
						getRightWheelLocation(orientation),
						getCenterBikeLocation(orientation),//milieu cadre -roue av. - milieu du cadre
						getPedalLocation(orientation),
						getLeftWheelLocation(orientation),
						getSaddleLocation(orientation),
						getLeftWheelLocation(orientation),
						getPedalLocation(orientation),
						getCenterBikeLocation(orientation),//milieu cadre - pedales - roue ar. - Selle - roue ar. - pedales - milieu du cadre
						getSaddleLocation(orientation),
						getCenterBikeLocation(orientation)// millieu cadre - Selle - milieu du cadre
					);
				leftPedal = new Polyline( 
						getLeftFootLocation(orientation),
						getPedalLocation(orientation)
					);
				rightPedal = new Polyline( 
						getPedalLocation(orientation),
						getRightFootLocation(orientation)
					);
		
	}

	/**
	 * creer la forme des membres
	 */
	private void createGraphics() {
		graphicsHair = new ShapeGraphics(hair, RGBColor.getRGB(205, 155, 29),Color.BLACK, 0.01f);
		graphicsHair.setDepth(0.9f);
		graphicsHair.setParent(getEntity());
		graphicsEye = new ShapeGraphics(eye, Color.BLACK,Color.WHITE, 0.02f);
		graphicsEye.setDepth(0.9f);
		graphicsEye.setParent(getEntity());
		graphicsHead = new ShapeGraphics(head, RGBColor.getRGB(254,195,172), Color.BLACK, 0.01f);
		graphicsHead.setDepth(0.8f);
		graphicsHead.setParent(getEntity());
		graphicsArm = new ShapeGraphics(arm,RGBColor.getRGB(254,195,172),RGBColor.getRGB(254,195,172), 0.1f);
		graphicsArm.setDepth(0.9f);
		graphicsArm.setParent(getEntity());
		graphicsBody = new ShapeGraphics(body, Color.GREEN, Color.GREEN, 0.2f);
		graphicsBody.setDepth(0.7f);
		graphicsBody.setParent(getEntity());
		graphicsLeftThigh = new ShapeGraphics(leftThigh, Color.BLUE, Color.BLUE, 0.2f);
		graphicsLeftThigh.setDepth(0.25f);
		graphicsLeftThigh.setParent(getEntity());
		graphicsRightThigh = new ShapeGraphics(rightThigh, Color.BLUE, Color.BLUE, 0.2f);
		graphicsRightThigh.setDepth(0.6f);
		graphicsRightThigh.setParent(getEntity());
		graphicsLeftLeg = new ShapeGraphics(leftLeg, RGBColor.getRGB(254,195,172), RGBColor.getRGB(254,195,172), 0.1f);
		graphicsLeftLeg.setDepth(0.2f);
		graphicsLeftLeg.setParent(getEntity());
		graphicsRightLeg = new ShapeGraphics(rightLeg, RGBColor.getRGB(254,195,172), RGBColor.getRGB(254,195,172), 0.1f);
		graphicsRightLeg.setDepth(0.5f);
		graphicsRightLeg.setParent(getEntity());
		graphicsCadre = new ShapeGraphics(cadre, Color.RED, Color.RED, 0.1f);
		graphicsCadre.setDepth(0.3f);
		graphicsCadre.setParent(getEntity());
		graphicsSaddle = new ShapeGraphics(saddle, RGBColor.getRGB(126, 88, 53), RGBColor.getRGB(126, 88, 53), 0.1f);
		graphicsSaddle.setDepth(0.4f);
		graphicsSaddle.setParent(getEntity());
		graphicsLeftPedal = new ShapeGraphics(leftPedal, Color.GRAY, Color.GRAY, 0.1f);
		graphicsLeftPedal.setDepth(0.275f);
		graphicsLeftPedal.setParent(getEntity());
		graphicsRightPedal = new ShapeGraphics(rightPedal, Color.GRAY, Color.GRAY, 0.1f);
		graphicsRightPedal.setDepth(0.4f);
		graphicsRightPedal.setParent(getEntity());
		
	}
	//SETTERS---------------------------------------------
	public static void setMode(int type) {
		if(mode!=LOSE)
			mode=type;
	}
	//GETTERS---------------------------------------------
	private Vector getRightWheelLocation(int orientation) {
		return new Vector(orientation*1.0f, 0.0f);
	}
	private Vector getLeftWheelLocation(int orientation) {
		return new Vector(orientation*-1.0f, 0.0f);
	}
	private Vector getSaddleLocation(int orientation) {
		return  new Vector(orientation*-0.8f, 0.8f);
	}
	private Vector getPedalLocation(int orientation) {
		return new Vector(orientation * 0.0f, 0.0f);
	}
	private Vector getCenterBikeLocation(int orientation) {
		return new Vector(orientation * 0.3f, 0.5f);
	}
	private Vector getRightFootLocation(int orientation) {
		return new Vector((float)Math.cos(leftWheel.getEntity().getAngularPosition() - Math.PI) * RADIUS_PEDALS, (float)Math.sin(leftWheel.getEntity().getAngularPosition() - Math.PI) * RADIUS_PEDALS);
	}
	private Vector getLeftFootLocation(int orientation) {
		return new Vector((float)Math.cos(leftWheel.getEntity().getAngularPosition()) * RADIUS_PEDALS, (float)Math.sin(leftWheel.getEntity().getAngularPosition()) * RADIUS_PEDALS);
	}
	private Vector getLeftKneeLocation(int orientation) {
		return new Vector(orientation * 0.1f, (float)Math.sin(leftWheel.getEntity().getAngularPosition()) * 0.1f +0.5f);
	}
	private Vector getRightKneeLocation(int orientation) {
		return new Vector(orientation * 0.1f, (float)Math.sin(leftWheel.getEntity().getAngularPosition()- Math.PI) * 0.1f +0.5f);
	}
	private Vector getBodyLocation(int orientation) {
		return new Vector(orientation * -0.5f, 1.0f);
	}
	private Vector getHandLocation(int orientation) {
		if(mode==WIN) {
			return new Vector(orientation * 0.2f, 2.0f);
		}else {
			return getHandleBarLocation(orientation);
		}
	}
	private Vector getHandleBarLocation(int orientation) {
			return new Vector(orientation * 0.5f, 1.0f);
	}
	private Vector getShoulderLocation(int orientation) {
		return new Vector(orientation * -0.2f, 1.5f);
	}
	private Vector getHeadLocation(int orientation) {
		return new Vector(orientation*0.0f, 1.75f);
	}
	private Vector getEyeLocation(int orientation) {
		return new Vector(orientation *0.15f, 1.75f);
	}
}
