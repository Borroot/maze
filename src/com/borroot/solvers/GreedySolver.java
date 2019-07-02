package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import sun.awt.image.ImageWatched;

import java.util.Comparator;
import java.util.LinkedList;

import static com.borroot.maze.Tile.EMPTY;
import static com.borroot.maze.Tile.PATH;

public class GreedySolver implements Solver {

	/**
	 * @param maze
	 * @return if the cell in the given direction is empty.
	 */
	private boolean freeAtDir(Maze maze, Cell to){
		try{
			return maze.get(to) == EMPTY;
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	private int h(Maze maze, Cell cell){
		Cell finish = maze.getFinish();
		return Math.abs(cell.x - finish.x) + Math.abs(cell.y - finish.y);
	}

	private void setPath(Maze maze, Cell cell){
		while(cell.getParent() != null){
			maze.set(cell, PATH);
			cell = cell.getParent();
		}
	}

	@Override
	public void solve(Maze maze) {
		LinkedList<Cell> stack = new LinkedList<>();
		LinkedList<Cell> discovered = new LinkedList<>();
		stack.add(maze.getStart());

		while(!stack.isEmpty()){
			stack.sort(Comparator.comparingInt(c -> h(maze, c)));
			Cell current = stack.pop();

			if(current.equals(maze.getFinish())){
				maze.setSolved(true);
				setPath(maze, current);
				return;
			}

			if(!discovered.contains(current)) {
				discovered.add(current);

				for (Direction dir : Direction.values()) {
					Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
					if (freeAtDir(maze, next) && !discovered.contains(next)) {
						next.setParent(current);
						stack.push(next);
					}
				}
			}
		}
	}

	@Override
	public String toString(){
		return "Greedy Search";
	}
}
