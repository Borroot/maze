package com.borroot.maze;

import java.util.ArrayList;
import java.util.List;

import static com.borroot.maze.Tile.*;

/**
 * This class represents a maze.
 * @author Bram Pulles
 */
public class Maze {

	private static final int DEFAULT_SIZE = 5;
	private Tile maze[][];

	public Maze(){
		this(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	public Maze(final int SIZE){
		this(SIZE, SIZE);
	}

	public Maze(final int HEIGHT, final int WIDTH){
		maze = new Tile[HEIGHT * 2 + 1][WIDTH * 2 + 1];
		init();
	}

	public void init(){
		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(y % 2 == 0 || x % 2 == 0){
					maze[y][x] = WALL;
				}else{
					maze[y][x] = EMPTY;
				}
			}
		}
	}

	/**
	 * Use this function to get all the empty cells currently in the maze.
	 * @return a list containing all the non-wall cells.
	 */
	public List<Cell> getEmptyCells(){
		List<Cell> cells = new ArrayList<>();

		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(maze[y][x] == EMPTY){
					cells.add(new Cell(x, y));
				}
			}
		}
		return cells;
	}

	public void set(int x, int y, Tile tile){
		maze[y][x] = tile;
	}

	public Tile get(int x, int y){
		return maze[y][x];
	}

	/**
	 * Use this function to get the tile value of a cell.
	 * @param cell with x and y coordinates in the maze.
	 * @return the corresponding tile value.
	 */
	public Tile cellVal(Cell cell){
		return maze[cell.y][cell.x];
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();

		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(maze[y][x] == WALL){
					if(y % 2 == 0){
						builder.append("-");
					}else{
						builder.append("|");
					}
				}else{
					builder.append(" ");
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
