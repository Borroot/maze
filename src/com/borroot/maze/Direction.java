package com.borroot.maze;

/**
 * This class represents the directions you can go to in the maze.
 * @author Bram Pulles
 */
public enum Direction {
	NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

	private int x, y;

	Direction(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
}
