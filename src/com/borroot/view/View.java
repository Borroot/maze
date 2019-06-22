package com.borroot.view;

import com.borroot.Main;
import com.borroot.maze.Maze;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {

	private final int WIDTH = 1200, HEIGHT = 800;
	private GameView gameView;

	public View(){
		init();
	}

	private void init(){
		gameView = new GameView();

		BorderPane root = new BorderPane();
		root.setCenter(gameView);

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Stage window = Main.getWindow();
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();
	}

	public void draw(Maze maze){
		gameView.draw(maze);
	}
}
