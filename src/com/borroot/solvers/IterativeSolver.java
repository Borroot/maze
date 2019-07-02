package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Path;

import static com.borroot.maze.Tile.EMPTY;

public class IterativeSolver implements Solver {

	private boolean solved = false;

	/**
	 * @param maze
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
	private void search(Maze maze, Cell current, Path path, final int bound, int depth){
		if(depth >= bound){
			return;
		}
		if(current.equals(maze.getFinish())){
			solved = true;
			return;
		}

		path.add(current);

		for(Direction dir : Direction.values()){
			Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
			if(!solved && freeAtDir(maze, next)){
				search(maze, next, path, bound, depth++);
			}
		}

		if(!solved){
			path.remove(current);
		}
	}

	@Override
	public Path solve(Maze maze) {
		Path path = new Path();
		int bound = 1;

		while(!solved) {
			search(maze, maze.getStart(), path, bound, 0);
			bound++;
		}

		return path;
	}

	@Override
	public String toString(){
		return "Iterative Deepening";
	}

}
