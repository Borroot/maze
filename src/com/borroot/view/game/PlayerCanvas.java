package com.borroot.view.game;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import javafx.scene.paint.Color;

/**
 * This canvas draws the player.
 * @author Bram Pulles
 */
public class PlayerCanvas extends GameCanvas {

	@Override
	protected void actualDraw(Maze maze) {
		Cell player = maze.getPlayer();

		gc.setFill(Color.DARKGREEN);
		gc.fillRect(player.x*LL - LL/2, player.y*LL-LL/2, LL, LL);
	}
}
