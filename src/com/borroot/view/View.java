package com.borroot.view;

import com.borroot.Main;
import com.borroot.controller.GameController;
import com.borroot.generators.BacktrackGenerator;
import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Maze;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class View {

	private final int WINDOW_WIDTH = 1200, WINDOW_HEIGHT = 900;

	private GameController controller;

	private BorderPane root = new BorderPane();

	private GameView gameView = new GameView();
	private HBox hbox = new HBox(20);

	private final int MAX_VALUE = 80;
	private TextField txtHeight = new TextField("10");
	private TextField txtWidth = new TextField("10");

	public View(GameController controller){
		this.controller = controller;
		init();
	}

	private void init(){
		initButtons();

		root.setCenter(gameView);
		root.setBottom(hbox);
		root.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		Stage window = Main.getWindow();
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();
	}

	private void initButtons(){
		Button btnGenerate = new Button("Generate Maze!");
		Button btnSolve = new Button("Show Solution!");

		Label lblHeight = new Label("Height:");
		Label lblWidth = new Label("Width:");
		initValueListeners();

		hbox.getChildren().addAll(btnGenerate, lblHeight, txtHeight, lblWidth, txtWidth, btnSolve);
		hbox.setAlignment(Pos.BASELINE_CENTER);
		hbox.setPadding(new Insets(10, 10, 10, 10));

		btnGenerate.setOnAction(e -> {
			int height = Integer.parseInt(txtHeight.getText());
			int width = Integer.parseInt(txtWidth.getText());
			controller.btnGenerateAction(new KruskalGenerator(), height, width);
		});
	}

	private void initValueListeners(){
		txtHeight.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				changedValue(txtHeight, newValue);
			}
		});

		txtWidth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				changedValue(txtWidth, newValue);
			}
		});
	}

	private void changedValue(TextField tf, String newVal){
		if (!newVal.matches("\\d*")) {
			tf.setText(newVal.replaceAll("[^\\d]", ""));
		}else if(!tf.getText().equals("")){
			int val = Integer.parseInt(newVal);
			if(val < 1){
				tf.setText("1");
			}else if(val > MAX_VALUE){
				tf.setText(MAX_VALUE + "");
			}
		}
	}

	public void draw(Maze maze){
		gameView.draw(maze);
	}
}
