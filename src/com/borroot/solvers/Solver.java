package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;

import static com.borroot.maze.Tile.EMPTY;
import static com.borroot.maze.Tile.PATH;

/**
 * An interface for all the different solver classes.
 * @author Bram Pulles
 */
public interface Solver {

	/**
	 * Solve the given maze.
	 * @param maze
	 */
	void solve(Maze maze);

	/**
	 * Remove the path from the maze.
	 * @param maze
	 */
	default void unsolve(Maze maze){
		for(int y = 0; y < maze.getHeight(); y++){
			for(int x = 0; x < maze.getWidth(); x++){
				Cell cell = new Cell(x, y);
				if(maze.cellVal(cell) == PATH){
					maze.set(cell, EMPTY);
				}
			}
		}
		maze.setSolved(false);
	}
}
