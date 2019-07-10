package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This generator is very similar to the backtracking algorithm, it is implemented as follows:
 *
	 Choose a starting location.
	 Perform a random walk, carving passages to unvisited neighbors, until the current cell has no unvisited neighbors.
	 Enter “hunt” mode, where you scan the grid looking for an unvisited cell that is adjacent to a visited cell. If found, carve a passage between the two and let the formerly unvisited cell be the new starting location.
	 Repeat steps 2 and 3 until the hunt mode scans the entire grid and finds no unvisited cells.

 * @author Bram Pulles
 */
public class HuntAndKillGenerator implements Generator {

	/**
	 * Remove the wall between cell c1 and cell c2.
	 * @param c1
	 * @param c2
	 */
	public void removeWall(Maze maze, Cell c1, Cell c2){
		Cell wall = new Cell((c1.x+c2.x)/2, (c1.y+c2.y)/2);
		maze.set(wall, Tile.EMPTY);
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
	 * @param visited
	 * @return one random unvisited neighbor from the cell.
	 */
	private Cell randomUnvisitedNeighbor(Maze maze, Cell cell, LinkedList<Cell> visited){
		assert(hasUnvisitedNeighbors(maze, cell, visited));

		LinkedList<Cell> neighbors = neighbors(maze, cell);
		LinkedList<Cell> unvisitedNeighbors = new LinkedList<>();

		for(Cell neighbor : neighbors){
			if(!visited.contains(neighbor)){
				unvisitedNeighbors.push(neighbor);
			}
		}

		int index = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size());
		return unvisitedNeighbors.get(index);
	}

	/**
	 * @param maze
	 * @param cell
	 * @param visited
	 * @return if the cell has unvisited neighbors.
	 */
	private boolean hasUnvisitedNeighbors(Maze maze, Cell cell, LinkedList<Cell> visited){
		LinkedList<Cell> neighbors = neighbors(maze, cell);

		for(Cell neighbor : neighbors){
			if(!visited.contains(neighbor)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param maze
	 * @param visited
	 * @return the next cell which has not been visited yet.
	 */
	private Cell hunt(Maze maze, LinkedList<Cell> visited){
		for(int y = 1; y < maze.getHeight(); y += 2){
			for(int x = 1; x < maze.getWidth(); x += 2){
				Cell cell = new Cell(x, y);
				if(visited.contains(cell) && hasUnvisitedNeighbors(maze, cell, visited)){
					Cell neighbor = randomUnvisitedNeighbor(maze, cell, visited);
					removeWall(maze, cell, neighbor);
					return neighbor;
				}
			}
		}
		return null;
	}

	@Override
	public Maze generate(final int WIDTH, final int HEIGHT){
		Maze maze = new Maze(WIDTH, HEIGHT);
		maze.initGrid();

		LinkedList<Cell> visited = new LinkedList<>();

		Random random = new Random();
		int startX = random.nextInt(maze.getWidth() / 2) * 2 + 1;
		int startY = random.nextInt(maze.getHeight() / 2) * 2 + 1;
		Cell current = new Cell(startX, startY);
		visited.add(current);

		do{
			while(hasUnvisitedNeighbors(maze, current, visited)){
				Cell neighbor = randomUnvisitedNeighbor(maze, current, visited);
				removeWall(maze, current, neighbor);

				current = neighbor;
				visited.add(current);
			}

			if(!visited.contains(current)){
				visited.add(current);
			}
		} while((current = hunt(maze, visited)) != null);

		return maze;
	}

	@Override
	public String toString(){
		return "Hunt and Kill";
	}
}
