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

	private Tile maze[][];

	private Cell start;
	private Cell finish;
	private Cell player;

	private boolean solved = false;

	/**
	 * Make a maze with the specified height and width.
	 * @param HEIGHT
	 * @param WIDTH
	 */
	public Maze(final int HEIGHT, final int WIDTH){
		if(HEIGHT > 0 && WIDTH > 0){
			maze = new Tile[HEIGHT * 2 + 1][WIDTH * 2 + 1];
		}
	}

	public void initSurroundingWalls(){
		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(x == 0 || y == 0 || x == maze[y].length || y == maze.length){
					maze[y][x] = WALL;
				}
			}
		}
	}

	/**
	 * Initialize the maze to a grid with cells all having four walls.
	 */
	public void initGrid(){
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
	 * Use this function to get all the cells in the maze.
	 * @return a list containing all the non-wall cells.
	 */
	public LinkedList<Cell> getEmptyCells(){
		LinkedList<Cell> cells = new LinkedList<>();

		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				if(!(y % 2 == 0 || x % 2 == 0)){
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

	/**
	 * @return the height of the maze.
	 */
	public int getHeight(){
		return maze.length;
	}

	/**
	 * @return the width of the maze.
	 */
	public int getWidth(){
		return maze[0].length;
	}

	/**
	 * Set the value of solved.
	 * @param solved if the maze is solved.
	 */
	public void setSolved(boolean solved){
		this.solved = solved;
	}

	/**
	 * @return if the maze is solved.
	 */
	public boolean isSolved(){
		return solved;
	}

	public void set(Cell cell, Tile tile){
		maze[cell.y][cell.x] = tile;
	}

	public Tile get(Cell cell){
		return maze[cell.y][cell.x];
	}

	public void setPlayer(Cell player){
		this.player = player;
	}

	public Cell getPlayer(){
		return player;
	}

	/**
	 * Set the cell to a start tile.
	 * @param start
	 */
	public void setStart(Cell start){
		this.start = start;
		this.player = start;
		set(start, EMPTY);
	}

	/**
	 * @return the cell with the start tile in the maze.
	 */
	public Cell getStart(){
		return start;
	}

	/**
	 * Set the cell to a finish tile.
	 * @param finish
	 */
	public void setFinish(Cell finish){
		this.finish = finish;
		set(finish, EMPTY);
	}

	/**
	 * @return the cell with the finish tile in the maze.
	 */
	public Cell getFinish(){
		return finish;
	}

	/**
	 * @param cell
	 * @return if the given cell is in the maze-grid boundaries.
	 */
	public boolean validIndex(Cell cell){
		return cell.x >= 0 && cell.y >= 0 && cell.x < this.getWidth() && cell.y < this.getHeight();
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();

		for(int y = 0; y < maze.length; y++){
			for(int x = 0; x < maze[y].length; x++){
				Cell cell = new Cell(x, y);
				if(cell.equals(start)){
					builder.append("S");
				}else if(cell.equals(finish)){
					builder.append("F");
				}else {
					switch (maze[y][x]) {
						case WALL:
							if (y % 2 == 0) {
								builder.append("-");
							} else {
								builder.append("|");
							}
							break;
						case EMPTY:
							builder.append(" ");
							break;
						case PATH:
							builder.append("X");
					}
				}
				builder.append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
