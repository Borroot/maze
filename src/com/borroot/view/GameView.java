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

	private final int WIDTH = 1800, HEIGHT = 1000;
	private final int LL = 20, LINE_WIDTH = LL/5; // LL is the line length

	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
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

	public void draw(Maze maze){
		System.out.println(maze);

		gc.setLineWidth(LINE_WIDTH);
		gc.translate(LINE_WIDTH, LINE_WIDTH);

		for(int y = 0; y < maze.getHeight(); y += 2){
			for(int x = 0; x < maze.getWidth(); x += 2){
				line(maze, x, y, true);
				line(maze, x, y, false);
			}
		}
	}
}
