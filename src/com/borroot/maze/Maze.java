package com.borroot.maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.borroot.maze.Tile.*;

/**
 * This class represents a maze.
 * @author Bram Pulles
 */
public class Maze {

	private static final int DEFAULT_SIZE = 5;
	private Tile maze[][];

	/**
	 * Make a maze with the default size.
	 */
	public Maze(){
		this(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	/**
	 * Make a maze with n*n size.
	 * @param SIZE
	 */
	public Maze(final int SIZE){
		this(SIZE, SIZE);
	}

	/**
	 * Make a maze with the specified height and width.
	 * @param HEIGHT
	 * @param WIDTH
	 */
	public Maze(final int HEIGHT, final int WIDTH){
		if(HEIGHT > 0 && WIDTH > 0){
			maze = new Tile[HEIGHT * 2 + 1][WIDTH * 2 + 1];
			init();
		}
	}

	/**
	 * Initialize the maze to a grid with cells al having four walls.
	 */
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
	public LinkedList<Cell> getEmptyCells(){
		LinkedList<Cell> cells = new LinkedList<>();

		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(maze[y][x] == EMPTY){
					cells.add(new Cell(x, y));
				}
			}
		}
		return cells;
	}

	/**
	 * @return all the walls except for the border walls.
	 */
	public LinkedList<Cell> getWallCells(){
		LinkedList<Cell> cells = new LinkedList<>();

		for(int y = 1; y < maze.length-1; y++){
			for(int x = 1; x < maze[y].length-1; x++){
				if(maze[y][x] == WALL){
					cells.add(new Cell(x, y));
				}
			}
		}
		return cells;
	}

	public int getHeight(){
		return maze.length;
	}

	public int getWidth(){
		return maze[0].length;
	}

	public void set(Cell cell, Tile tile){
		maze[cell.y][cell.x] = tile;
	}

	public Tile get(Cell cell){
		return maze[cell.y][cell.x];
	}

	/**
	 * Set the cell to a start tile.
	 * @param cell
	 */
	public void setStart(Cell cell){
		maze[cell.y][cell.x] = START;
	}

	/**
	 * @return the cell with the start tile in the maze.
	 */
	public Cell getStart(){
		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(maze[y][x] == START){
					return new Cell(x, y);
				}
			}
		}
		return new Cell(1, 1);
	}

	/**
	 * Set the cell to a finish tile.
	 * @param cell
	 */
	public void setFinish(Cell cell){
		maze[cell.y][cell.x] = FINISH;
	}

	/**
	 * Remove the wall between cell c1 and cell c2.
	 * @param c1
	 * @param c2
	 */
	public void removeWall(Cell c1, Cell c2){
		Cell wall = new Cell((c1.x+c2.x)/2, (c1.y+c2.y)/2);
		maze[wall.y][wall.x] = EMPTY;
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
				switch (maze[y][x]){
					case WALL:
						if(y % 2 == 0){
							builder.append("-");
						}else{
							builder.append("|");
						}
						break;
					case EMPTY:
						builder.append(" ");
						break;
					case START:
						builder.append("S");
						break;
					case FINISH:
						builder.append("F");
						break;
					case PATH:
						builder.append("X");
				}
				builder.append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
