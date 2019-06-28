package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;

import static com.borroot.maze.Tile.*;

/**
 * Solve the maze using a depth-first search algorithm.
 * @author Bram Pulles
 */
public class DepthSolver implements Solver {

	private boolean solved;

	/**
	 * @param maze
	 * @param from
	 * @param dir
	 * @return if the cell in the given direction is free (non-wall and non-path).
	 */
	private boolean freeAtDir(Maze maze, Cell from, Direction dir){
		Cell to = new Cell(from.x + dir.getX(), from.y + dir.getY());
		try{
			return !(maze.cellVal(to) == WALL || maze.cellVal(to) == PATH);
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	/**
	 * Solve the maze.
	 * @param maze
	 * @param current
	 */
	private void search(Maze maze, Cell current){
		if(maze.cellVal(current) == FINISH){
			solved = true;
			return;
		}

		if(maze.cellVal(current) != START)
			maze.set(current, PATH);

		for(Direction dir : Direction.values()){
			if(freeAtDir(maze, current, dir)){
				Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
				search(maze, next);
			}
		}

		if(!solved && maze.cellVal(current) != START)
			maze.set(current, EMPTY);
	}

	@Override
	public void solve(Maze maze) {
		solved = false;
		search(maze, maze.getStart());
	}

	@Override
	public String toString(){
		return "Depth First Search";
	}
}
