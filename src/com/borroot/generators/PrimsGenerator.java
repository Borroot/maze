package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import static com.borroot.maze.Direction.*;

/**
 * This class generates mazes using Prim's algorithm, it is implemented as follows:
 *
 *    Start with a grid full of walls.
 *     Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
 *     While there are walls in the list:
 *         Pick a random wall from the list. If only one of the two cells that the wall divides is visited, then:
 *             Make the wall a passage and mark the unvisited cell as part of the maze.
 *             Add the neighboring walls of the cell to the wall list.
 *         Remove the wall from the list.
 *
 * @author Bram Pulles
 */
public class PrimsGenerator implements Generator {

	private Random random = new Random();

	private void addWalls(Maze maze, Cell cell, LinkedList<Cell> walls){
		for(Direction dir : Direction.values()){
			Cell wall = new Cell(cell.x + dir.getX(), cell.y + dir.getY());
			if(wall.x > 0 && wall.y > 0 && wall.x < maze.getWidth()-1 && wall.y < maze.getHeight()-1){
				walls.add(wall);
			}
		}
	}

	private Cell unvisitedCell(Cell wall, LinkedList<Cell> visited){
		Cell north = new Cell(wall.x + NORTH.getX(), wall.y + NORTH.getY());
		Cell south = new Cell(wall.x + SOUTH.getX(), wall.y + SOUTH.getY());
		if(visited.contains(north) && !visited.contains(south)){
			return south;
		}else if(!visited.contains(north) && visited.contains(south)){
			return north;
		}

		Cell east = new Cell(wall.x + EAST.getX(), wall.y + EAST.getY());
		Cell west = new Cell(wall.x + WEST.getX(), wall.y + WEST.getY());
		if(visited.contains(east) && !visited.contains(west)){
			return west;
		}else{
			return east;
		}
	}

	private boolean wallDivides(Cell wall, LinkedList<Cell> visited){
		Cell north = new Cell(wall.x + NORTH.getX(), wall.y + NORTH.getY());
		Cell south = new Cell(wall.x + SOUTH.getX(), wall.y + SOUTH.getY());
		if((visited.contains(north) && !visited.contains(south)) || (!visited.contains(north) && visited.contains(south))){
			return true;
		}

		Cell east = new Cell(wall.x + EAST.getX(), wall.y + EAST.getY());
		Cell west = new Cell(wall.x + WEST.getX(), wall.y + WEST.getY());
		if((visited.contains(east) && !visited.contains(west) || (!visited.contains(east) && visited.contains(west)))){
			return true;
		}

		return false;
	}

	@Override
	public Maze generate(int WIDTH, int HEIGHT) {
		Maze maze = new Maze(WIDTH, HEIGHT);
		maze.initGrid();

		LinkedList<Cell> visited = new LinkedList<>();
		LinkedList<Cell> walls = new LinkedList<>();

		int startX = random.nextInt(maze.getWidth() / 2) * 2 + 1;
		int startY = random.nextInt(maze.getHeight() / 2) * 2 + 1;
		Cell start = new Cell(startX, startY);

		addWalls(maze, start, walls);
		visited.add(start);

		while(!walls.isEmpty()){
			Collections.shuffle(walls);
			Cell wall = walls.pop();

			if(wallDivides(wall, visited)){
				Cell unvisited = unvisitedCell(wall, visited);
				visited.add(unvisited);

				maze.set(wall, Tile.EMPTY);
				addWalls(maze, unvisited, walls);
			}
		}

		return maze;
	}

	@Override
	public String toString(){
		return "Prim";
	}
}
