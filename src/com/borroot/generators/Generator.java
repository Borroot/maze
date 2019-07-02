package com.borroot.generators;

import com.borroot.maze.Maze;

/**
 * This is an interface for every maze generator class.
 * @author Bram Pulles
 */
public interface Generator {

	/**
	 * Generate the maze.
	 */
	Maze generate(final int WIDTH, final int HEIGHT);
}
