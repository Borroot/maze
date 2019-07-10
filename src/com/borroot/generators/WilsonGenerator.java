package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;

import java.util.LinkedList;
import java.util.Random;

/**
 * This generator uses Wilson's algorithm to generate mazes.
 *
 * This is implemented as follows: (UST the existing maze or a uniform spanning tree)
	 Choose any vertex at random and add it to the UST.
	 Select any vertex that is not already in the UST and perform a random walk until you encounter a vertex that is in the UST.
	 Add the vertices and edges touched in the random walk to the UST.
	 Repeat 2 and 3 until all vertices have been added to the UST.

 * Note that every cell has a direction to point to the next cell in order to prevent loops.
 * @author Bram Pulles
 */
public class WilsonGenerator implements Generator {

	/**
	 * Cell with direction to the next cell.
	 * @author Bram Pulles
	 */
	private class DirCell extends Cell {
		Direction dir;

		private DirCell(int x, int y){
			super(x, y);
		}

		private DirCell(int x, int y, Direction dir){
			this(x, y);
			this.dir = dir;
		}

		private void setDir(Direction dir){
			this.dir = dir;
		}

		private Direction getDir(){
			return dir;
		}

		@Override
		public boolean equals(Object o){
			if(o instanceof DirCell){
				return ((DirCell)o).x == this.x && ((DirCell)o).y == this.y;
			}
			return false;
		}

		@Override
		public String toString(){
			return new Cell(x, y).toString();
		}
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

	private DirCell randomUnvisitedCell(Maze maze, LinkedList<DirCell> visited){
		Random random = new Random();
		DirCell cell;
		do{
			int x = random.nextInt(maze.getWidth() / 2) * 2 + 1;
			int y = random.nextInt(maze.getHeight() / 2) * 2 + 1;
			cell = new DirCell(x, y);
		}while(visited.contains(cell));

		return cell;
	}

	/**
	 * @param maze
	 * @param cell
	 * @return a random neighbor cell.
	 */
	private DirCell nextCell(Maze maze, DirCell cell){
		DirCell next;
		do {
			int index = (int) (Math.random() * Direction.values().length);
			Direction dir = Direction.values()[index];

			cell.setDir(dir);
			next = new DirCell(cell.x + dir.getX()*2, cell.y + dir.getY()*2);
		}while(!maze.validIndex(next));

		return next;
	}

	/**
	 * Do a random walk until a part of the maze is touched.
	 * @param maze
	 * @param current
	 * @param visited
	 * @return a path.
	 */
	private LinkedList<DirCell> randomWalk(Maze maze, DirCell current, LinkedList<DirCell> visited){
		LinkedList<DirCell> path = new LinkedList<>();
		path.add(current);

		DirCell next = current;
		do{
			next = nextCell(maze, next);
			if(path.contains(next)){
				path.remove(next);
			}
			path.add(next);
		}while(!visited.contains(next));

		return path;
	}

	/**
	 * Carve a path in the maze starting from the start cell until the end of the path.
	 * @param maze
	 * @param start cell.
	 * @param path
	 * @param visited
	 */
	private void carvePath(Maze maze, DirCell start, LinkedList<DirCell> path, LinkedList<DirCell> visited){
		DirCell current = start;
		visited.add(current);
		do{
			Direction dir = current.getDir();
			DirCell next = new DirCell(current.x + dir.getX()*2, current.y + dir.getY()*2);
			removeWall(maze, current, next);

			if(!visited.contains(next)){
				visited.add(next);
			}

			for(DirCell cell : path){
				if(cell.equals(next)){
					current = cell;
				}
			}
		}while(current.getDir() != null);
	}

	@Override
	public Maze generate(int WIDTH, int HEIGHT) {
		Maze maze = new Maze(WIDTH, HEIGHT);
		maze.initGrid();

		LinkedList<DirCell> visited = new LinkedList<>();

		DirCell start = randomUnvisitedCell(maze, visited);
		visited.add(start);

		int size = ((maze.getWidth() - 1) / 2) * ((maze.getHeight() - 1) / 2);
		do{
			DirCell current = randomUnvisitedCell(maze, visited);
			LinkedList<DirCell> path = randomWalk(maze, current, visited);
			carvePath(maze, current, path, visited);
		}while(visited.size() < size);

		return maze;
	}

	@Override
	public String toString(){
		return "Wilson";
	}
}
