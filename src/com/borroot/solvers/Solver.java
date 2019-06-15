package com.borroot.solvers;

import com.borroot.maze.Maze;

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
}
