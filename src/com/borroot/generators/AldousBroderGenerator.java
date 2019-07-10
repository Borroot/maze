package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;

import java.util.LinkedList;
import java.util.Random;

/**
 * This generator uses the Aldous Broder algorithm, which is as follows:
 *
	 Choose a vertex. Any vertex.
	 Choose a connected neighbor of the vertex and travel to it. If the neighbor has not yet been visited, add the traveled edge to the spanning tree.
	 Repeat step 2 until all vertexes have been visited.

 * @author Bram Pulles
 */
public class AldousBroderGenerator implements Generator {

	/**
	 * @param maze
	 * @param cell
	 * @return a random neighbor cell.
	 */
	private Cell nextCell(Maze maze, Cell cell){
		Cell next;
		do {
			int index = (int) (Math.random() * Direction.values().length);
			Direction dir = Direction.values()[index];
			next = new Cell(cell.x + dir.getX()*2, cell.y + dir.getY()*2);
		}while(!maze.validIndex(next));

		return next;
	}

	/**
	 * Remove the wall between cell c1 and cell c2.
	 * @param c1
	 * @param c2
	 */
	public void removeWall(Maze maze, Cell c1, Cell c2){
		Cell wall = new Cell((c1.x+c2.x)/2, (c1.y+c2.y)/2);
		maze.set(wall, Tile.EMPTY);
	}

	@Override
	public Maze generate(int WIDTH, int HEIGHT) {
		Maze maze = new Maze(WIDTH, HEIGHT);
		maze.initGrid();

		LinkedList<Cell> visited = new LinkedList<>();

		Random random = new Random();
		int startX = random.nextInt(maze.getWidth() / 2) * 2 + 1;
		int startY = random.nextInt(maze.getHeight() / 2) * 2 + 1;
		Cell start = new Cell(startX, startY);

		visited.add(start);

		Cell current = start;
		int size = ((maze.getWidth() - 1) / 2) * ((maze.getHeight() - 1) / 2);

		while(visited.size() < size){
			Cell next = nextCell(maze, current);

			if(!visited.contains(next)){
				removeWall(maze, current, next);
				visited.add(next);
			}
			current = next;
		}

		return maze;
	}

	@Override
	public String toString(){
		return "Aldous Broder";
	}
}
