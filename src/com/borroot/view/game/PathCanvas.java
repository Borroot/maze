package com.borroot.view.game;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Tile;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.LinkedList;

import static com.borroot.maze.Tile.*;

/**
 * This canvas draws a path for the solution of the maze.
 * @author Bram Pulles
 */
public class PathCanvas extends GameCanvas {

	protected PathCanvas(){
		super();
	}

	/**
	 * Set the gradient and line width settings.
	 * @param maze
	 */
	private void initLineSettings(Maze maze){
		Cell start = maze.getStart();
		Cell finish = maze.getFinish();

		LinearGradient lg = new LinearGradient(start.x*LL, start.y*LL, finish.x*LL, finish.y*LL,
				false, CycleMethod.REFLECT,
				new Stop(0.0, Color.DARKBLUE),
				new Stop(1.0, Color.DARKRED));

		gc.setLineWidth(lineWidth * 2);
		gc.setStroke(lg);
	}

	/**
	 * Calculate the next cell based on the current cell and the list with already drawn cells.
	 * @param maze
	 * @param cur current cell.
	 * @param drawnCells
	 * @return the next cell.
	 */
	private Cell nextCell(Maze maze, Cell cur, LinkedList<Cell> drawnCells){
		for(Direction dir : Direction.values()){
			Cell next = new Cell(cur.x + dir.getX(), cur.y + dir.getY());
			Tile t = maze.cellVal(next);
			if(!drawnCells.contains(next) && (t == PATH || t == START || t == FINISH)){
				return next;
			}
		}
		return null;
	}

	/**
	 * Draw all the lines starting from the start and then selecting the next tile until the finish is reached.
	 * @param maze
	 */
	private void drawSolution(Maze maze){
		gc.beginPath();

		Cell cur = maze.getStart();
		Cell finish = maze.getFinish();
		LinkedList<Cell> drawnCells = new LinkedList<>();

		do{
			gc.moveTo(cur.x * LL, cur.y * LL);
			drawnCells.add(cur);
			cur = nextCell(maze, cur, drawnCells);
			gc.lineTo(cur.x * LL, cur.y * LL);
		}while(!cur.equals(finish));

		gc.closePath();
		gc.stroke();
	}

	@Override
	protected void actualDraw(Maze maze){
		if(maze.isSolved()){
			initLineSettings(maze);
			drawSolution(maze);
		}
	}

}
