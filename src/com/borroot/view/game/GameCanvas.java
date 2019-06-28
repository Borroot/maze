package com.borroot.view.game;

import com.borroot.maze.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameCanvas extends Canvas {

	protected GraphicsContext gc = this.getGraphicsContext2D();
	protected int LL; // Line length

	private double horizontalPadding = 0;
	private double verticalPadding = 0;

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
		System.out.println(horizontalPadding + ":" + verticalPadding);

		gc.setLineWidth(lineWidth);
		gc.translate(horizontalPadding, verticalPadding);
	}

	private void clearCanvas(){
		gc.translate(-horizontalPadding, -verticalPadding);
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	private void setSize(double h, double w){
		this.setHeight(h);
		this.setWidth(w);
	}

	protected void draw(Maze maze, double h, double w){
		prepare(maze, h, w);
		actualDraw(maze);
	}

	protected abstract void actualDraw(Maze maze);
}
