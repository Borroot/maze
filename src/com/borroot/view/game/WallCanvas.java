package com.borroot.view.game;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import javafx.scene.paint.Color;

import static com.borroot.maze.Tile.WALL;

public class WallCanvas extends GameCanvas {

	protected WallCanvas(){
		super();
	}

	/**
	 * @param maze
	 * @param x
	 * @param y
	 * @return if the given position is a wall within the boundaries of the maze.
	 */
	private boolean validWall(Maze maze, int x, int y){
		return maze.validIndex(x, y) && maze.cellVal(new Cell(x, y)) == WALL;
	}

	/**
	 * @param maze
	 * @param x
	 * @param y
	 * @param hor if checking horizontally (if not then vertically).
	 * @return if there are three consecutive walls starting from the given position.
	 */
	private boolean threeWalls(Maze maze, int x, int y, boolean hor){
		for(int i = 0; i < 3; i++){
			int newx = (hor)? x+i : x;
			int newy = (hor)? y : y+i;

			if(!validWall(maze, newx, newy)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Draw a line either horizontally or vertically starting from the given position.
	 * @param x
	 * @param y
	 * @param hor if drawing horizontally or else vertically.
	 */
	private void drawWallLine(int x, int y, boolean hor){
		int endx = (hor)? x*LL + 2*LL : x*LL;
		int endy = (hor)? y*LL : y*LL + 2*LL;
		gc.strokeLine(x*LL, y*LL, endx, endy);
	}

	/**
	 * If there are three consecutive walls, draw a line.
	 * @param maze
	 * @param x
	 * @param y
	 * @param hor if creating horizontally or else vertically.
	 */
	private void lineWall(Maze maze, int x, int y, boolean hor){
		if(threeWalls(maze, x, y, hor)){
			drawWallLine(x, y, hor);
		}
	}

	/**
	 * Draw the walls of the maze.
	 * @param maze
	 */
	private void drawWalls(Maze maze){
		gc.setFill(Color.BLACK);

		for(int y = 0; y < maze.getHeight(); y += 2){
			for(int x = 0; x < maze.getWidth(); x += 2){
				lineWall(maze, x, y, true);
				lineWall(maze, x, y, false);
			}
		}
	}

	@Override
	protected void actualDraw(Maze maze){
		drawWalls(maze);
	}
}
