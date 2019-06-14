package com.borroot.maze;

import static com.borroot.maze.Tile.*;

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
