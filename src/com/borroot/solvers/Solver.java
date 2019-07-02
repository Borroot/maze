package com.borroot.solvers;

import com.borroot.maze.Maze;
import com.borroot.maze.Path;

/**
 * An interface for all the different solver classes.
 * @author Bram Pulles
 */
public interface Solver {

	/**
	 * Solve the given maze.
	 * @param maze
	 */
	Path solve(Maze maze);
}
