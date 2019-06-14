package com.borroot.maze;

import java.util.ArrayList;
import java.util.List;

import static com.borroot.maze.Tile.*;

public class Maze {

	private static final int DEFAULT_SIZE = 5;
	private Tile maze[][];
	private List<Cell> cells;

	public Maze(){
		this(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	public Maze(final int SIZE){
		this(SIZE, SIZE);
	}

	public Maze(final int HEIGHT, final int WIDTH){
		cells = new ArrayList<>(HEIGHT * WIDTH);
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
					cells.add(new Cell(x, y, EMPTY));
				}
			}
		}
	}

	public void setCell(Cell cell){
		maze[cell.getY()][cell.getX()] = cell.getTile();
	}

	public Tile getTile(int x, int y){
		return maze[y][x];
	}

	public List<Cell> getCells(){
		return cells;
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
