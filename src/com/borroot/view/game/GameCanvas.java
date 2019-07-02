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
	protected int lineWidth;

	private int horizontalPadding = 0;
	private int verticalPadding = 0;

	/**
	 * Clear the canvas, set the size of the canvas, the line length and the line width.
	 * @param maze
	 */
	private void prepare(Maze maze, double width, double height){
		clearCanvas();
		setSize(width, height);
		setLineLength(maze, width, height);
		setLineWidth(maze, width, height);
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
	 */
	private void setSize(double width, double heigth){
		this.setWidth(width);
		this.setHeight(heigth);
	}

	/**
	 * Set the length of a line according to size of  the canvas and maze.
	 * @param maze
	 */
	private void setLineLength(Maze maze, double width, double heigth){
		int w = (int)width / maze.getWidth();
		int h = (int)heigth / maze.getHeight();
		LL = (w < h)? w : h;
	}

	/**
	 * Set the width of a line according to the length of a line.
	 * Also set the starting position for drawing so the maze will be drawn in the middle.
	 * @param maze
	 */
	private void setLineWidth(Maze maze, double width, double height){
		lineWidth = LL / 5;
		lineWidth = (lineWidth % 2 == 0)? lineWidth : lineWidth - 1;

		horizontalPadding = (int)((width - LL * maze.getWidth()) / 2 + 0.5 * LL);
		verticalPadding = (int)((height - LL * maze.getHeight()) / 2 + 0.5 * LL);

		gc.setLineWidth(lineWidth);
		gc.translate(horizontalPadding, verticalPadding);
	}

	/**
	 * Prepare the canvas and then draw the maze.
	 * @param maze
	 */
	protected void draw(Maze maze, double width, double height){
		prepare(maze, width, height);
		actualDraw(maze);
	}

	/**
	 * Actually draw the part of the maze.
	 * @param maze
	 */
	protected abstract void actualDraw(Maze maze);
}
