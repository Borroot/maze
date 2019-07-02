package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Path;

import java.util.Comparator;
import java.util.LinkedList;

import static com.borroot.maze.Tile.EMPTY;

public class AStarSolver implements Solver {

	/**
	 * @param maze
	 * @return if the cell in the given direction is empty.
	 */
	private boolean freeAtDir(Maze maze, Cell cell){
		return maze.validIndex(cell) && maze.get(cell) == EMPTY;
	}

	private int h(Maze maze, Cell cell){
		Cell finish = maze.getFinish();
		return cell.getCost() + Math.abs(cell.x - finish.x) + Math.abs(cell.y - finish.y);
	}

	@Override
	public Path solve(Maze maze) {
		LinkedList<Cell> stack = new LinkedList<>();
		LinkedList<Cell> discovered = new LinkedList<>();

		Cell start = maze.getStart();
		start.setCost(0);
		stack.add(start);

		while(!stack.isEmpty()){
			stack.sort(Comparator.comparingInt(c -> h(maze, c)));
			Cell current = stack.pop();

			if(current.equals(maze.getFinish())){
				return new Path(current);
			}

			if(!discovered.contains(current)) {
				discovered.add(current);

				for (Direction dir : Direction.values()) {
					Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
					if (freeAtDir(maze, next) && !discovered.contains(next)) {
						next.setParent(current);
						next.setCost(current.getCost()+1);
						stack.push(next);
					}
				}
			}
		}

		return new Path();
	}

	@Override
	public String toString(){
		return "A* Search";
	}
}
