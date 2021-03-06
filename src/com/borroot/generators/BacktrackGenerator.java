package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static com.borroot.maze.Tile.EMPTY;

/**
 * Generate a maze using depth-first search recursive backtracking.
 *
 * The algorithm is implemented as follows:
 *     1. Make the initial cell the current cell and mark it as visited
 *     2. While there are unvisited cells
 *         1. If the current cell has any neighbours which have not been visited
 *             1. Choose randomly one of the unvisited neighbours
 *             2. Push the current cell to the stack
 *             3. Remove the wall between the current cell and the chosen cell
 *             4. Make the chosen cell the current cell and mark it as visited
 *         2. Else if stack is not empty
 *             1. Pop a cell from the stack
 *             2. Make it the current cell
 *
 * @author Bram Pulles
 */
public class BacktrackGenerator implements Generator {

	/**
	 * Remove the wall between cell c1 and cell c2.
	 * @param c1
	 * @param c2
	 */
	public void removeWall(Maze maze, Cell c1, Cell c2){
		Cell wall = new Cell((c1.x+c2.x)/2, (c1.y+c2.y)/2);
		maze.set(wall, EMPTY);
	}

	/**
	 * @param maze
	 * @param cell
	 * @return all the neighbors of this cell.
	 */
	private LinkedList<Cell> neighbors(Maze maze, Cell cell){
		LinkedList<Cell> neighborList = new LinkedList<>();

		for(Direction dir : Direction.values()){
			int newX = cell.x + dir.getX()*2;
			int newY = cell.y + dir.getY()*2;
			Cell neighbor = new Cell(newX, newY);

			if(maze.validIndex(neighbor)){
				neighborList.push(neighbor);
			}
		}
		return neighborList;
	}

	/**
	 * @param maze
	 * @param cell
	 * @param unvisited
	 * @return one random unvisited neighbor from the cell.
	 */
	private Cell randomUnvisitedNeighbor(Maze maze, Cell cell, LinkedList<Cell> unvisited){
		assert(hasUnvisitedNeighbors(maze, cell, unvisited));

		LinkedList<Cell> neighbors = neighbors(maze, cell);
		LinkedList<Cell> unvisitedNeighbors = new LinkedList<>();

		for(Cell neighbor : neighbors){
			if(unvisited.contains(neighbor)){
				unvisitedNeighbors.push(neighbor);
			}
		}

		int index = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size());
		return unvisitedNeighbors.get(index);
	}

	/**
	 * @param maze
	 * @param cell
	 * @param unvisited
	 * @return if the cell has unvisited neighbors.
	 */
	private boolean hasUnvisitedNeighbors(Maze maze, Cell cell, LinkedList<Cell> unvisited){
		LinkedList<Cell> neighbors = neighbors(maze, cell);

		for(Cell neighbor : neighbors){
			if(unvisited.contains(neighbor)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Generate the maze.
	 * @param maze
	 * @param unvisited
	 * @param visited
	 */
	private void generateMaze(Maze maze, LinkedList<Cell> unvisited, LinkedList<Cell> visited){
		Cell current = unvisited.pop();
		visited.push(current);

		while(unvisited.size() > 0){
			if(hasUnvisitedNeighbors(maze, current, unvisited)){
				Cell neighbor = randomUnvisitedNeighbor(maze, current, unvisited);
				removeWall(maze, current, neighbor);

				current = neighbor;
				unvisited.remove(current);
				visited.push(current);
			}else if(visited.size() > 0){
				current = visited.pop();
			}
		}
	}

	@Override
	public Maze generate(final int WIDTH, final int HEIGHT){
		Maze maze = new Maze(WIDTH, HEIGHT);
		maze.initGrid();

		LinkedList<Cell> unvisited = maze.getEmptyCells();
		LinkedList<Cell> visited = new LinkedList<>();

		generateMaze(maze, unvisited, visited);
		return maze;
	}

	@Override
	public String toString(){
		return "Backtracking";
	}
}
