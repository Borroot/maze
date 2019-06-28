package com.borroot.view;

import com.borroot.Main;
import com.borroot.controller.GameController;
import com.borroot.generators.BacktrackGenerator;
import com.borroot.generators.Generator;
import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Maze;
import com.borroot.solvers.DepthSolver;
import com.borroot.solvers.Solver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

	private ChoiceBox<Generator> cbGenerator = new ChoiceBox<>();
	private ChoiceBox<Solver> cbSolver = new ChoiceBox<>();

	private final int MAX_VALUE = 80; // TODO: If bigger then drawing issues.
	private final int INIT_VALUE = 10;
	private TextField tfHeight = new TextField(INIT_VALUE + "");
	private TextField tfWidth = new TextField(INIT_VALUE + "");

	private Button btnGenerate = new Button("Generate Maze!");
	private Button btnSolve = new Button("Show Solution!");

	public View(GameController controller){
		this.controller = controller;
		init();
	}

	private void init(){
		initSettings();

		root.setCenter(gameView);
		root.setBottom(hbox);
		root.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		Stage window = Main.getWindow();
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();
	}

	private void initSettings(){
		initButtons();
		initChoiceBoxes();

		Label lblHeight = new Label("Height:");
		Label lblWidth = new Label("Width:");
		tfHeight.setPrefWidth(40);
		tfWidth.setPrefWidth(40);
		initValueListeners();

		hbox.getChildren().addAll(btnGenerate, cbGenerator, lblHeight, tfHeight, lblWidth, tfWidth, btnSolve, cbSolver);
		hbox.setAlignment(Pos.BASELINE_CENTER);
		hbox.setPadding(new Insets(10, 10, 10, 10));
	}

	private void initChoiceBoxes(){
		cbGenerator.setItems(FXCollections.observableArrayList(new BacktrackGenerator(), new KruskalGenerator()));
		cbGenerator.getSelectionModel().selectFirst();

		cbSolver.setItems(FXCollections.observableArrayList(new DepthSolver()));
		cbSolver.getSelectionModel().selectFirst();
	}

	private void initButtons(){
		btnGenerate.setOnAction(e -> {
			int height = Integer.parseInt(tfHeight.getText());
			int width = Integer.parseInt(tfWidth.getText());
			controller.btnGenerateAction(cbGenerator.getValue(), height, width);
		});

		btnSolve.setOnAction(e -> {
			controller.btnSolveAction(cbSolver.getValue());
		});
	}

	private void initValueListeners(){
		tfHeight.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				changedValue(tfHeight, newValue);
			}
		});

		tfWidth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				changedValue(tfWidth, newValue);
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
