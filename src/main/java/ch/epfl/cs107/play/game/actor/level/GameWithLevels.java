/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 4 déc. 2017
 *
 */
package ch.epfl.cs107.play.game.actor.level;

public abstract interface GameWithLevels{
		// gère ce qui se passe lorsque la transition au niveau suivant doit se faire :
		abstract void nextLevel(); 
		// gère ce qui se passe lorsque l'on veut recommencer le niveau courant :
		//abstract void resetLevel(); 
}
