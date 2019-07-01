package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;

import static com.borroot.maze.Tile.EMPTY;
import static com.borroot.maze.Tile.PATH;

public class IterativeSolver implements Solver {

	/**
	 * @param maze
	 * @param from
	 * @param dir
	 * @return if the cell in the given direction is empty.
	 */
	private boolean freeAtDir(Maze maze, Cell from, Direction dir){
		Cell to = new Cell(from.x + dir.getX(), from.y + dir.getY());
		try{
			return maze.get(to) == EMPTY;
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	/**
	 * Solve the maze.
	 * @param maze
	 * @param current
	 */
	private void search(Maze maze, Cell current, final int bound, int depth){
		if(depth >= bound){
			return;
		}
		if(current.equals(maze.getFinish())){
			maze.setSolved(true);
			return;
		}

		maze.set(current, PATH);

		for(Direction dir : Direction.values()){
			if(!maze.isSolved() && freeAtDir(maze, current, dir)){
				Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
				search(maze, next, bound, depth++);
			}
		}

		if(!maze.isSolved()){
			maze.set(current, EMPTY);
		}
	}

	@Override
	public void solve(Maze maze) {
		int bound = 1;
		while(!maze.isSolved()) {
			search(maze, maze.getStart(), bound, 0);
			bound++;
		}
	}

	@Override
	public String toString(){
		return "Iterative Deepening";
	}

}
