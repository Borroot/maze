package com.borroot.view;

import com.borroot.Main;
import com.borroot.maze.Maze;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class GameView {

	private final int WIDTH = 1000, HEIGHT = 600;

	private Canvas canvas = new Canvas(WIDTH-150, HEIGHT-150);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public GameView(){
		init();
	}

	public void draw(Maze maze){
		gc.setFill(Color.BLUE);
		gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
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
}
