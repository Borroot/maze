package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;

import java.util.ArrayList;
import java.util.List;

public class BacktrackGenerator implements Generator {

	private void generate_recursive(Maze maze, List<Cell> unvisited, List<Cell> visited){

	}

	@Override
	public void generate(Maze maze){
		List<Cell> unvisited = maze.getCells();
		List<Cell> visited = new ArrayList<>();

		generate_recursive(maze, unvisited, visited);
	}
}
