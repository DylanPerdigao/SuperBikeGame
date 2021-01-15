/*
 * Auteur: Dylan Gonçalves Perdigao
 * Date: 29 nov. 2017
 *
 */
package ch.epfl.cs107.play.game.addons;

import java.awt.Color;

public final class RGBColor{
	//c.f. premier mini-projet
	public static Color getRGB(int red, int green, int blue) {
		Color color = new Color ((red << 16) | (green << 8) | blue);
		return color;
	}
}
