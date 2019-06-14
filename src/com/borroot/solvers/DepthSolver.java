package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;

import static com.borroot.maze.Tile.*;

public class DepthSolver implements Solver {

	private boolean freeAtDir(Maze maze, Cell from, Direction dir){
		Cell to = new Cell(from.x + dir.getX(), from.y + dir.getY());
		try{
			return !(maze.cellVal(to) == WALL || maze.cellVal(to) == PATH);
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	private void search(Maze maze, Cell current){
		if(maze.cellVal(current) == FINISH){
			System.out.println(maze);
			return;
		}

		maze.set(current, PATH);
		for(Direction dir : Direction.values()){
			if(freeAtDir(maze, current, dir)){
				Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
				search(maze, next);
			}
		}
		maze.set(current, EMPTY);
	}

	@Override
	public void solve(Maze maze) {
		search(maze, maze.getStart());
	}
}
