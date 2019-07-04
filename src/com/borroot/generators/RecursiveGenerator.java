package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;

import java.util.Random;

import static com.borroot.generators.Orientation.*;
import static com.borroot.maze.Tile.EMPTY;
import static com.borroot.maze.Tile.WALL;

public class RecursiveGenerator implements Generator {

	private Random random = new Random();

	private class Chamber {

		private int x0, y0, x1, y1;

		private Chamber(int x0, int y0, int x1, int y1){
			this.x0 = x0;
			this.y0 = y0;
			this.x1 = x1;
			this.y1 = y1;
		}

		private int getWidth(){
			return x1 - x0;
		}

		private int getHeight(){
			return y1 - y0;
		}

		@Override
		public String toString(){
			return "(" + x0 + "," + y0 + ") -> (" + x1 + "," + y1 + ")";
		}
	}

	private Orientation wallOrientation(Chamber chamber){
		if(chamber.getWidth() < chamber.getHeight()){
			return HORIZONTAL;
		}else if(chamber.getHeight() < chamber.getWidth()){
			return VERTICAL;
		}else{
			return (Math.random() < 0.5)? HORIZONTAL : VERTICAL;
		}
	}

	private int startPos(Chamber chamber, Orientation wallOrientation){
		int size = (wallOrientation == HORIZONTAL)? chamber.getHeight() : chamber.getWidth();
		int relative = random.nextInt(size / 2) * 2 + 1;
		return (wallOrientation == HORIZONTAL)? chamber.y0 + relative : chamber.x0 + relative;
	}

	private int openPos(Chamber chamber, Orientation wallOrientation){
		int size = (wallOrientation == HORIZONTAL)? chamber.getWidth() : chamber.getHeight();
		int relative = random.nextInt(size / 2 + 1) * 2;
		return (wallOrientation == HORIZONTAL)? chamber.x0 + relative : chamber.y0 + relative;
	}

	private void setWalls(Maze maze, Chamber chamber, Orientation wallOrientation, int startPos, int openPos){
		if(wallOrientation == HORIZONTAL){
			for(int x = chamber.x0; x < chamber.x1 + 1; x++){
				maze.set(new Cell(x, startPos), WALL);
			}
			maze.set(new Cell(openPos, startPos), EMPTY);
		}else{ // VERTICAL
			for(int y = chamber.y0; y < chamber.y1 + 1; y++){
				maze.set(new Cell(startPos, y), WALL);
			}
			maze.set(new Cell(startPos, openPos), EMPTY);
		}
	}

	private Chamber[] newChambers(Chamber chamber, Orientation wallOrientation, int startPos){
		Chamber chambers[] = new Chamber[2];

		if(wallOrientation == HORIZONTAL){
			chambers[0] = new Chamber(chamber.x0, chamber.y0, chamber.x1, startPos - 1);
			chambers[1] = new Chamber(chamber.x0, startPos + 1, chamber.x1, chamber.y1);
		}else{ // VERTICAL
			chambers[0] = new Chamber(chamber.x0, chamber.y0, startPos - 1, chamber.y1);
			chambers[1] = new Chamber(startPos + 1, chamber.y0, chamber.x1, chamber.y1);
		}

		return chambers;
	}

	private void generateRecursive(Maze maze, Chamber chamber){
		if(chamber.getWidth() <= 0 || chamber.getHeight() <= 0){
			return;
		}

		Orientation wallOrientation = wallOrientation(chamber);
		int startPos = startPos(chamber, wallOrientation);
		int openPos = openPos(chamber, wallOrientation);
		setWalls(maze, chamber, wallOrientation, startPos, openPos);
		Chamber chambers[] = newChambers(chamber, wallOrientation, startPos);

		generateRecursive(maze, chambers[0]);
		generateRecursive(maze, chambers[1]);
	}

	@Override
	public Maze generate(int WIDTH, int HEIGHT) {
		Maze maze = new Maze(WIDTH, HEIGHT);
		maze.initSurroundingWalls();

		generateRecursive(maze, new Chamber(1, 1, maze.getWidth()-2, maze.getHeight()-2));
		return maze;
	}

	@Override
	public String toString(){
		return "Recursive";
	}
}

enum Orientation {
	HORIZONTAL, VERTICAL
}
