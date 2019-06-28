package com.borroot.view.game;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;
import javafx.scene.paint.Color;

import static com.borroot.maze.Tile.*;

/**
 * This canvas draws a path for the solution of the maze.
 */
public class PathCanvas extends GameCanvas {

	protected PathCanvas(){
		super();
	}

	/**
	 * Draw a line from (x0, y0) to (x1, y1).
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	private void drawPathLine(int x0, int y0, int x1, int y1){
		gc.strokeLine(x0*LL, y0*LL, x1*LL, y1*LL);
	}

	/**
	 * Check for every direction from this point if we can draw a path to another cell
	 * for the solution.
	 * @param maze
	 * @param x
	 * @param y
	 */
	private void linePath(Maze maze, int x, int y){
		for(Direction dir : Direction.values()){
			Cell cell = new Cell(x + dir.getX(), y + dir.getY());
			Tile t = maze.cellVal(cell);
			if(t == PATH || t == START || t == FINISH){
				drawPathLine(x, y, cell.x, cell.y);
			}
		}
	}

	/**
	 * Draw all the lines from every point and set the correct color and width for lines.
	 * @param maze
	 */
	private void drawSolution(Maze maze){
		gc.setLineWidth(lineWidth * 2);
		gc.setStroke(Color.DARKRED);

		if(maze.isSolved()){
			for(int y = 1; y < maze.getHeight(); y += 2){
				for(int x = 1; x < maze.getWidth(); x += 2){
					linePath(maze, x, y);
				}
			}
		}
	}

	@Override
	protected void actualDraw(Maze maze){
		drawSolution(maze);
	}

}
