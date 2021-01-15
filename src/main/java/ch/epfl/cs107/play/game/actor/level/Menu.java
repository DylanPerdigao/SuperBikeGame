/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 5 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.bike.SuperBikeGame;
import ch.epfl.cs107.play.game.actor.general.Arrow;
import ch.epfl.cs107.play.game.actor.general.LevelBox;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.addons.RGBColor;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Menu extends Level{

	private ActorGame game;
	//Caractéristiques Terrain
	private final float MAP_MAX_HEIGHT = 15f;
	private final float MAP_MIN_HEIGHT = -15f;
	private Polyline polyline = new Polyline(
			-100.0f, MAP_MIN_HEIGHT-100,
			-100.0f, MAP_MAX_HEIGHT+100,
			-50.0f, MAP_MAX_HEIGHT+100,
			-50.0f, -10.0f,
			500.0f, -10.0f,
			500.0f, MAP_MAX_HEIGHT+100,
			550.0f, MAP_MAX_HEIGHT+100,
			550.0f, MAP_MIN_HEIGHT-100
			);
	//Acteurs
	private Terrain terrain;
	private LevelBox levelBox1;
	private LevelBox levelBox2;
	private LevelBox levelBox3;
	private LevelBox levelBox4;
	private Arrow arrow;
	//Selection du niveau
	public static int numberOfUnlockedLevels = 1;
	public static int levelSelected = 1;
	private Vector arrowPosition = new Vector(-9.0f, 6.5f);
	//Deblocage des niveaux
	private boolean isLevel1Unlocked=true;
	private boolean isLevel2Unlocked=false;
	private boolean isLevel3Unlocked=false;
	private boolean isLevel4Unlocked=false;
	//Message niveau
	private TextGraphics messageLevel;

	public Menu(SuperBikeGame game){
		super(game);
		this.game = game;
	}
	
	@Override
	public void begin() {
		this.game.getWorld().setGravity(new Vector(0f,-1000f));
		game.setViewCandidate(terrain);
		//Affiche le nom du premier niveau
		if(levelSelected == 1) {
		messageLevel = new TextGraphics("GRASS_LEVEL", 0.05f, Color.WHITE, null, 0.03f, true, false, new Vector(0,0), 1.0f, 100.0f);
		messageLevel.setParent(game.getCanvas());
		messageLevel.setRelativeTransform(Transform.I.translated(-0.3f, -0.625f));	
		}
	}
	
	@Override
	public void update(float deltaTime) {
		//Deplacer la fleche pour choisir le niveau
		if (game.getKeyboard().get(KeyEvent.VK_UP).isPressed()) {
			if (levelSelected>1) {
				levelSelected--;
			}else {
				levelSelected = numberOfUnlockedLevels;
			}
			selectLevel();
		//Deplacer la fleche pour choisir le niveau
		} else if (game.getKeyboard().get(KeyEvent.VK_DOWN).isPressed()) {
			if (levelSelected<numberOfUnlockedLevels) {
				levelSelected++;
			}else {
				levelSelected = 1;
			}
			selectLevel();
		//Selectionner le niveau
    		}else if (game.getKeyboard().get(KeyEvent.VK_ENTER).isPressed()) {
    			destroy();
    		}
	}
	
	@Override
	public void draw(Canvas canvas) {	
		if (game.getLevelNumber() == game.MENU) {
		messageLevel.draw(game.getCanvas());
		}
	}

	@Override
	public void createAllActors() {
		unlockedLevel();
		try {
			terrain = new Terrain(game, true, new Vector(0f, 0f), polyline, Color.GREEN, RGBColor.getRGB(126,88,53), 20.0f);
			game.createActor(terrain);
			levelBox1 = new LevelBox(game, true, new Vector(-15.0f, 6.0f), isLevel1Unlocked, "digit.1.png");
			game.createActor(levelBox1);
			levelBox2 = new LevelBox(game, true, new Vector(-15.0f, 2.0f), isLevel2Unlocked, "digit.2.png");
			game.createActor(levelBox2);
			levelBox3 = new LevelBox(game, true, new Vector(-15.0f, -2.0f), isLevel3Unlocked, "digit.3.png");
			game.createActor(levelBox3);
			levelBox4 = new LevelBox(game, true, new Vector(-15.0f, -6.0f), isLevel4Unlocked, "digit.4.png");
			game.createActor(levelBox4);
			arrow = new Arrow(game, true, arrowPosition);
			game.createActor(arrow);
		}catch (NullPointerException e) {}
		
	}

	@Override
	public void destroy() {
		terrain.getEntity().destroy();
		levelBox1.getEntity().destroy();
		levelBox2.getEntity().destroy();
		levelBox3.getEntity().destroy();
		levelBox4.getEntity().destroy();
		arrow.getEntity().destroy();
		game.removeActor(terrain);
		game.removeActor(levelBox1);
		game.removeActor(levelBox2);
		game.removeActor(levelBox3);
		game.removeActor(levelBox4);
		game.removeActor(arrow);
	}
	
	//permet de debloquer les niveaux en fonction du nombre de niveaux débloqués
	public void unlockedLevel() {
		switch (numberOfUnlockedLevels) {
		case 4:
			isLevel4Unlocked = true;
		case 3:
			isLevel3Unlocked = true;
		case 2:
			isLevel2Unlocked = true;
		case 1:
			isLevel1Unlocked = true;
		default:
		}
	}
	//selctionne le niveau
	public void selectLevel() {
		switch (levelSelected) {
		case 1:
			arrowPosition = new Vector(-9.0f, 6.5f);
			messageLevel = new TextGraphics("GRASS_LEVEL", 0.05f, Color.WHITE, null, 0.03f, true, false, new Vector(0,0), 1.0f, 100.0f);
			messageLevel.setRelativeTransform(Transform.I.translated(-0.3f, -0.625f));	
			destroy();
			createAllActors();
			break;
		case 2:
			arrowPosition = new Vector(-9.0f, 2.5f);
			messageLevel = new TextGraphics("SNOW_LEVEL", 0.05f, Color.WHITE, null, 0.03f, true, false, new Vector(0,0), 1.0f, 100.0f);
			messageLevel.setRelativeTransform(Transform.I.translated(-0.3f, -0.825f));	
			destroy();
			createAllActors();
			break;
		case 3:
			arrowPosition = new Vector(-9.0f, -1.5f);
			messageLevel = new TextGraphics("MOON_LEVEL", 0.05f, Color.WHITE, null, 0.03f, true, false, new Vector(0,0), 1.0f, 100.0f);
			messageLevel.setRelativeTransform(Transform.I.translated(-0.3f, -1.025f));	
			destroy();
			createAllActors();
			break;
		case 4:
			arrowPosition = new Vector(-9.0f, -5.5f);
			messageLevel = new TextGraphics("DIMENSIONAL_LEVEL", 0.05f, Color.WHITE, null, 0.03f, true, false, new Vector(0,0), 1.0f, 100.0f);
			messageLevel.setRelativeTransform(Transform.I.translated(-0.3f, -1.225f));	
			destroy();
			createAllActors();
			break;
		default:
		}
		messageLevel.setParent(game.getCanvas()); 
	}
}
