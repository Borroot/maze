package com.borroot.view;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static com.borroot.maze.Tile.WALL;

/**
 * This class draws the maze.
 * @author Bram Pulles
 */
public class GameView extends StackPane {

	private int LL; // Line length

	private Canvas canvas = new Canvas();
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public GameView(){
		this.getChildren().add(canvas);
	}

	/**
	 * @param maze
	 * @param x
	 * @param y
	 * @return if the given position is a wall within the boundaries of the maze.
	 */
	private boolean validWall(Maze maze, int x, int y){
		return maze.validIndex(x, y) && maze.cellVal(new Cell(x, y)) == WALL;
	}

	/**
	 * @param maze
	 * @param x
	 * @param y
	 * @param hor if checking horizontally (if not then vertically).
	 * @return if there are three consecutive walls starting from the given position.
	 */
	private boolean threeWalls(Maze maze, int x, int y, boolean hor){
		for(int i = 0; i < 3; i++){
			int newx = (hor)? x+i : x;
			int newy = (hor)? y : y+i;

			if(!validWall(maze, newx, newy)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Draw a line either horizontally or vertically starting from the given position.
	 * @param x
	 * @param y
	 * @param hor if drawing horizontally or else vertically.
	 */
	private void drawLine(int x, int y, boolean hor){
		int endx = (hor)? x*LL + 2*LL : x*LL;
		int endy = (hor)? y*LL : y*LL + 2*LL;
		gc.strokeLine(x*LL, y*LL, endx, endy);
	}

	/**
	 * If there are three consecutive walls, draw a line.
	 * @param maze
	 * @param x
	 * @param y
	 * @param hor if creating horizontally or else vertically.
	 */
	private void line(Maze maze, int x, int y, boolean hor){
		if(threeWalls(maze, x, y, hor)){
			drawLine(x, y, hor);
		}
	}

	/**
	 * Set the length of a line according to size of  the canvas and maze.
	 * @param maze
	 */
	private void setLineLength(Maze maze){
		int width = (int)canvas.getWidth() / maze.getWidth();
		int height = (int)canvas.getHeight() / maze.getHeight();

		LL = (width < height)? width : height;
		LL = (LL % 2 == 0)? LL : LL - 1; // If the LL is uneven the lines get blurry.
	}

	/**
	 * Set the width of a line according to the length of a line.
	 * Also set the starting position for drawing so the maze will be drawn in the middle.
	 * @param maze
	 */
	private void setLineWidth(Maze maze){
		int lineWidth = LL / 5;

		double horizontalPadding = (canvas.getWidth() - LL * maze.getWidth()) / 2 + 2.5 * lineWidth;
		double verticalPadding = (canvas.getHeight() - LL * maze.getHeight()) / 2 + 2.5 * lineWidth;

		gc.setLineWidth(lineWidth);
		gc.translate(horizontalPadding, verticalPadding);
	}

	/**
	 * Draw the given maze.
	 * @param maze
	 */
	public void draw(Maze maze){
		canvas.setWidth(this.getWidth());
		canvas.setHeight(this.getHeight());
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// TODO: Remove this to remove the background of the canvas.
		gc.setFill(Color.LIGHTGREY);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		setLineLength(maze);
		setLineWidth(maze);

		for(int y = 0; y < maze.getHeight(); y += 2){
			for(int x = 0; x < maze.getWidth(); x += 2){
				line(maze, x, y, true);
				line(maze, x, y, false);
			}
		}
	}
}
