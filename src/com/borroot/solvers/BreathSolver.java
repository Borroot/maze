package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Path;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.borroot.maze.Tile.*;

/**
 * Solve the maze using a breath first search algorithm.
 * @author Bram Pulles
 */
public class BreathSolver implements Solver {

	@Override
	public Path solve(Maze maze){
		Queue<Cell> queue = new LinkedList<>();
		List<Cell> discovered = new LinkedList<>();

		discovered.add(maze.getStart());
		queue.add(maze.getStart());

		while(!queue.isEmpty()){
			Cell current = queue.remove();

			if(maze.validIndex(current) && current.equals(maze.getFinish())){
				return new Path(current);
			}

			for(Direction dir : Direction.values()){
				Cell next = new Cell(current.x + dir.getX(), current.y + dir.getY());
				if(!discovered.contains(next) && maze.validIndex(next) && maze.get(next) == EMPTY){
					discovered.add(next);
					next.setParent(current);
					queue.add(next);
				}
			}
		}

		return new Path();
	}

	@Override
	public String toString(){
		return "Breath First Search";
	}
}
