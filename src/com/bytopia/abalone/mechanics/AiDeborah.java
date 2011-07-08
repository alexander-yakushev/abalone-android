package com.bytopia.abalone.mechanics;

/**
 * Strong configuration of AI. Uses two-step depth and neighbouring marbles
 * analysis. Due to 07/08/11 is in beta-testing stage.
 * 
 * @author Bytopia
 */
public class AiDeborah extends ArtificialIntilligence {

	public Move requestMove(Game g) {
		return findNextMove(g.getBoard(), g.getSide(), 2, CLEVER, true);
	}
}