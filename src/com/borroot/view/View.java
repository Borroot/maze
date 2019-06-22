package com.borroot.view;

import com.borroot.Main;
import com.borroot.controller.GameController;
import com.borroot.maze.Maze;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {

	private final int WIDTH = 1200, HEIGHT = 900;

	private GameController controller;

	private BorderPane root = new BorderPane();
	private GameView gameView = new GameView();
	private Button btnGenerate = new Button("Generate Maze!");

	public View(GameController controller){
		this.controller = controller;
		init();
	}

	private void init(){
		initButton();

		root.setCenter(gameView);
		root.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Stage window = Main.getWindow();
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();
	}

	private void initButton(){
		root.setBottom(btnGenerate);

		btnGenerate.setOnAction(e -> {
			this.draw(controller.generateMaze());
		});
	}

	public void draw(Maze maze){
		gameView.draw(maze);
	}
}
