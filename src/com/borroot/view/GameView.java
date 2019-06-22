package com.borroot.view;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

import static com.borroot.maze.Tile.WALL;

public class GameView extends StackPane {

	private int LL; // Line length

	private Canvas canvas = new Canvas();
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public GameView(){
		this.getChildren().add(canvas);
	}

	private boolean validWall(Maze maze, int x, int y){
		return maze.validIndex(x, y) && maze.cellVal(new Cell(x, y)) == WALL;
	}

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

	private void drawLine(int x, int y, boolean hor){
		int endx = (hor)? x*LL + 2*LL : x*LL;
		int endy = (hor)? y*LL : y*LL + 2*LL;
		gc.strokeLine(x*LL, y*LL, endx, endy);
	}

	private void line(Maze maze, int x, int y, boolean hor){
		if(threeWalls(maze, x, y, hor)){
			drawLine(x, y, hor);
		}
	}

	private void setLineLengthAndWidth(Maze maze){
		int width = (int)canvas.getWidth() / maze.getWidth();
		int height = (int)canvas.getHeight() / maze.getHeight();

		LL = (width < height)? width : height;

		int lineWidth = LL / 5;
		gc.setLineWidth(lineWidth);
		gc.translate(lineWidth, lineWidth);
	}

	public void draw(Maze maze){
		canvas.setWidth(this.getWidth());
		canvas.setHeight(this.getHeight());
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		setLineLengthAndWidth(maze);

		for(int y = 0; y < maze.getHeight(); y += 2){
			for(int x = 0; x < maze.getWidth(); x += 2){
				line(maze, x, y, true);
				line(maze, x, y, false);
			}
		}
	}
}
