package com.borroot.view;

import com.borroot.Main;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.borroot.maze.Tile.WALL;

public class GameView {

	private final int WIDTH = 1000, HEIGHT = 800;
	private final int LINE_LENGTH = 50, LINE_WIDTH = 10;

	private Canvas canvas = new Canvas(WIDTH-150, HEIGHT-150);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public GameView(){
		init();
	}

	private void init(){
		BorderPane root = new BorderPane();
		root.setCenter(canvas);

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Stage window = Main.getWindow();
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();
	}

	private boolean threeHorizontalWalls(Maze maze, int x, int y){
		for(int i = 0; i < 3; i++){
			int newx = x+i, newy = y;
			if(!(maze.validIndex(newx, newy) && maze.cellVal(new Cell(newx, newy)) == WALL)){
				return false;
			}
		}
		return true;
	}

	private void drawHorizontalLine(int x, int y){
		gc.strokeLine(x*LINE_LENGTH, y*LINE_LENGTH, x*2*LINE_LENGTH, y*LINE_LENGTH);
	}

	private void drawHorizontalLines(Maze maze){
		for(int y = 0; y < maze.getHeight(); y += 2){
			for(int x = 0; x < maze.getWidth(); x += 2){
				if(threeHorizontalWalls(maze, x, y)){
					drawHorizontalLine(x, y);
				}
			}
		}
	}

	private void drawVerticalLines(Maze maze){

	}

	public void draw(Maze maze){
		System.out.println(maze);

		gc.setLineWidth(LINE_WIDTH);
		drawHorizontalLines(maze);
		drawVerticalLines(maze);
	}
}
