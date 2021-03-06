package com.borroot.maze;

/**
 * Since this class will be used a lot to represent the different cells in the maze
 * the attributes will be public to improve the user experience while using this class.
 * @author Bram Pulles
 */
public class Cell {

	private Cell parent;
	private int cost;

	public final int x;
	public final int y;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void setCost(int cost){
		this.cost = cost;
	}

	public int getCost(){
		return cost;
	}

	public void setParent(Cell parent){
		this.parent = parent;
	}

	public Cell getParent(){
		return parent;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Cell){
			Cell cell = (Cell)o;
			return cell.x == this.x && cell.y == this.y;
		}else{
			return false;
		}
	}

	@Override
	public String toString(){
		return "{" + x + "," + y + "}";
	}
}
