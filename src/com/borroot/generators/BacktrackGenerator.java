package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;

import java.util.LinkedList;
import java.util.List;

public class BacktrackGenerator implements Generator {

	private List<Cell> neighbors(Maze maze, Cell cell){

		return null;
	}

	private Cell random_unvisited_neighbor(Maze maze, Cell cell){

		return null;
	}

	private boolean has_unvisited_neighbors(Maze maze, Cell cell){

		return true;
	}

	private void generate_recursive(Maze maze, LinkedList<Cell> unvisited, LinkedList<Cell> visited){
		Cell current = unvisited.pop();
		visited.push(current);

		while(unvisited.size() >= 0){
			if(has_unvisited_neighbors(maze, current)){
				Cell neighbor = random_unvisited_neighbor(maze, current);
				maze.removeWall(current, neighbor);

				current = neighbor;
				unvisited.remove(current);
				visited.push(current);
			}else if(visited.size() >= 0){
				current = visited.pop();
				visited.remove(current);
			}
		}
	}

	@Override
	public void generate(Maze maze){
		LinkedList<Cell> unvisited = maze.getEmptyCells();
		LinkedList<Cell> visited = new LinkedList<>();

		generate_recursive(maze, unvisited, visited);
	}
}
