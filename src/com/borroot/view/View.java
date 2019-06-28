package com.borroot.view;

import com.borroot.Main;
import com.borroot.controller.GameController;
import com.borroot.generators.BacktrackGenerator;
import com.borroot.maze.Maze;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class View {

	private final int WIDTH = 1200, HEIGHT = 900;

	private GameController controller;

	private BorderPane root = new BorderPane();

	private GameView gameView = new GameView();
	private HBox hbox = new HBox(20);

	public View(GameController controller){
		this.controller = controller;
		init();
	}

	private void init(){
		initButtons();

		root.setCenter(gameView);
		root.setBottom(hbox);
		root.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Stage window = Main.getWindow();
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();
	}

	private void initButtons(){
		Button btnGenerate = new Button("Generate Maze!");
		Button btnSolve = new Button("Solve!");
		TextField tfHeight = new TextField("Height");
		TextField tfWidth = new TextField("Width");

		hbox.getChildren().addAll(btnGenerate, tfHeight, tfWidth, btnSolve);
		hbox.setAlignment(Pos.BASELINE_CENTER);
		hbox.setPadding(new Insets(10, 10, 10, 10));

		btnGenerate.setOnAction(e -> {
			controller.btnGenerateAction(new BacktrackGenerator(), 10, 10);
		});
	}

	public void draw(Maze maze){
		gameView.draw(maze);
	}
}
