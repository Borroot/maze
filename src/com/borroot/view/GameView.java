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
			int newx = x+i;
			if(!(maze.validIndex(newx, y) && maze.cellVal(new Cell(newx, y)) == WALL)){
				return false;
			}
		}
		return true;
	}

	private void drawHorizontalLine(int x, int y){
		gc.strokeLine(x*LINE_LENGTH, y*LINE_LENGTH, x*LINE_LENGTH + 2*LINE_LENGTH, y*LINE_LENGTH);
	}

	private boolean threeVerticalWalls(Maze maze, int x, int y){
		for(int i = 0; i < 3; i++){
			int newy = y+i;
			if(!(maze.validIndex(x, newy) && maze.cellVal(new Cell(x, newy)) == WALL)){
				return false;
			}
		}
		return true;
	}

	private void drawVerticalLine(int x, int y){
		gc.strokeLine(x*LINE_LENGTH, y*LINE_LENGTH, x*LINE_LENGTH, y*LINE_LENGTH + 2*LINE_LENGTH);
	}

	public void draw(Maze maze){
		System.out.println(maze);

		gc.setLineWidth(LINE_WIDTH);
		gc.translate(50, 50);

		for(int y = 0; y < maze.getHeight(); y += 2){
			for(int x = 0; x < maze.getWidth(); x += 2){
				if(threeHorizontalWalls(maze, x, y)){
					drawHorizontalLine(x, y);
				}
				if(threeVerticalWalls(maze, x, y)){
					drawVerticalLine(x, y);
				}
			}
		}
	}
}
