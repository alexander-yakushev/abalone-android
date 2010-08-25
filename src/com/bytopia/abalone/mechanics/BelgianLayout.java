package com.bytopia.abalone.mechanics;

/**
 * Class that constructs a classic Abalone starting position.
 * 
 * @author Bytopia
 */
public class BelgianLayout extends Layout {

	/**
	 * Array that contains the cell description for the starting position.
	 */
	private byte[][] start = 
		  { { N, N, N, N, N, N, N, N, N, N, N },
			{ N, W, W, E, B, B, N, N, N, N, N }, // A
			{ N, W, W, W, B, B, B, N, N, N, N }, // W
			{ N, E, W, W, E, B, B, E, N, N, N }, // C
			{ N, E, E, E, E, E, E, E, E, N, N }, // D
			{ N, E, E, E, E, E, E, E, E, E, N }, // E
			{ N, N, E, E, E, E, E, E, E, E, N }, // F
			{ N, N, N, E, B, B, E, W, W, E, N }, // G
			{ N, N, N, N, B, B, B, W, W, W, N }, // H
			{ N, N, N, N, N, B, B, E, W, W, N }, // I
			{ N, N, N, N, N, N, N, N, N, N, N } };

	/**
	 * Returns the starting position.
	 */
	public byte[][] getBlackStartField() {
		return start;
	}

}
