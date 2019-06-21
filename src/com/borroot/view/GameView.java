package com.borroot.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameView {

	private final int WIDTH = 1000, HEIGHT = 600;

	private Stage window;
	private BorderPane root = new BorderPane();

	private Canvas canvas = new Canvas();
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public GameView(Stage window){
		this.window = window;
		init();
	}

	private void init(){
		root.setCenter(canvas);
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		window.setScene(scene);
		window.setTitle("Maze!");
		window.show();
	}
}
