package com.borroot.maze;

/**
 * Since this class will be used a lot to represent the different cells in the maze
 * the attributes will be public to improve the user experience while using this class.
 * @author Bram Pulles
 */
public class Cell {

	public final int x;
	public final int y;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}
}