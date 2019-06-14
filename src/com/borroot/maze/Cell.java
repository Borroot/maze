package com.borroot.maze;

public class Cell {

	private int x;
	private int y;
	private Tile tile;

	public Cell(int x, int y, Tile tile){
		this.x = x;
		this.y = y;
		this.tile = tile;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setTile(Tile tile){
		this.tile = tile;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Tile getTile(){
		return tile;
	}
}
