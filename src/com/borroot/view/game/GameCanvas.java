package com.borroot.view.game;

import com.borroot.maze.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class represents a canvas used to display the game.
 * @author Bram Pulles
 */
public abstract class GameCanvas extends Canvas {

	protected GraphicsContext gc = this.getGraphicsContext2D();
	protected int LL; // Line length

	private double horizontalPadding = 0;
	private double verticalPadding = 0;

	/**
	 * Clear the canvas, set the size of the canvas, the line length and the line width.
	 * @param maze
	 * @param h
	 * @param w
	 */
	private void prepare(Maze maze, double h, double w){
		clearCanvas();
		setSize(h, w);
		setLineLength(maze, h, w);
		setLineWidth(maze, h, w);
	}

	/**
	 * Set the length of a line according to size of  the canvas and maze.
	 * @param maze
	 */
	private void setLineLength(Maze maze, double h, double w){
		int width = (int)w / maze.getWidth();
		int height = (int)h / maze.getHeight();

		LL = (width < height)? width : height;
		LL = (LL % 2 == 0)? LL : LL - 1; // If the LL is uneven the lines get blurry.
	}

	/**
	 * Set the width of a line according to the length of a line.
	 * Also set the starting position for drawing so the maze will be drawn in the middle.
	 * @param maze
	 */
	private void setLineWidth(Maze maze, double h, double w){
		int lineWidth = LL / 5;

		horizontalPadding = (w - LL * maze.getWidth()) / 2 + 2.5 * lineWidth;
		verticalPadding = (h - LL * maze.getHeight()) / 2 + 2.5 * lineWidth;

		gc.setLineWidth(lineWidth);
		gc.translate(horizontalPadding, verticalPadding);
	}

	/**
	 * Clear the canvas.
	 */
	private void clearCanvas(){
		gc.translate(-horizontalPadding, -verticalPadding);
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * Set the size of the canvas.
	 * @param h
	 * @param w
	 */
	private void setSize(double h, double w){
		this.setHeight(h);
		this.setWidth(w);
	}

	/**
	 * Prepare the canvas and then draw the maze.
	 * @param maze
	 * @param h
	 * @param w
	 */
	protected void draw(Maze maze, double h, double w){
		prepare(maze, h, w);
		actualDraw(maze);
	}

	/**
	 * Actually draw the part of the maze.
	 * @param maze
	 */
	protected abstract void actualDraw(Maze maze);
}
