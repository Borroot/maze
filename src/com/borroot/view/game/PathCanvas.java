package com.borroot.view.game;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Iterator;
import java.util.LinkedList;

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
	 * Draw all the lines starting from the start and then selecting the next tile until the finish is reached.
	 * @param maze
	 */
	private void drawSolution(Maze maze){
		gc.beginPath();

		Iterator<Cell> it = maze.getPath().iterator();
		Cell cur = it.next();

		while(it.hasNext()) {
			gc.moveTo(cur.x * LL, cur.y * LL);
			cur = it.next();
			gc.lineTo(cur.x * LL, cur.y * LL);
		}
		gc.moveTo(cur.x * LL, cur.y * LL);

		gc.closePath();
		gc.stroke();
	}

	@Override
	protected void actualDraw(Maze maze){
		if(!maze.getPath().isEmpty()){
			initLineSettings(maze);
			drawSolution(maze);
		}
	}

}
