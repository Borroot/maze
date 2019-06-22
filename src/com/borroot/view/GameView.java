package com.borroot.view;

import com.borroot.Main;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameView {

	private final int WIDTH = 1000, HEIGHT = 600;

	private Stage window = Main.getWindow();
	private BorderPane root = new BorderPane();

	private Canvas canvas = new Canvas();
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public GameView(){
		init();
	}

	private void init(){
		//root.setCenter(canvas);
		root.setCenter(new Text("hi"));
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		window.setScene(scene);
		window.setTitle("Maze! And why does this not show?");
		window.show();
	}
}
