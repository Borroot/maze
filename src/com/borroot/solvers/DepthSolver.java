package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Path;

import static com.borroot.maze.Tile.*;

/**
 * Solve the maze using a depth-first search algorithm.
 * @author Bram Pulles
 */
public class DepthSolver implements Solver {

	private boolean solved;

	/**
	 * @param maze
	 * @param cell
	 * @return if the cell in the given direction is empty.
	 */
	private boolean freeAtDir(Maze maze, Cell cell){
		return maze.validIndex(cell) && maze.get(cell) == EMPTY;
	}

	/**
	 * Solve the maze.
	 * @param maze
	 * @param current
	 */
	private void search(Maze maze, Cell current, Path path){
		path.add(current);

		if(current.equals(maze.getFinish())){
			solved = true;
			return;
		}

		for(Direction dir : Direction.values()){
			Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
			if(!solved && freeAtDir(maze, next) && !path.contains(next)){
				search(maze, next, path);
			}
		}

		if(!solved){
			path.remove(current);
		}
	}

	@Override
	public Path solve(Maze maze) {
		solved = false;

		Path path = new Path();
		search(maze, maze.getStart(), path);

		return path;
	}

	@Override
	public String toString(){
		return "Depth First Search";
	}
}
