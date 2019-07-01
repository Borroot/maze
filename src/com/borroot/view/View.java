package com.borroot.view;

import com.borroot.Main;
import com.borroot.controller.GameController;
import com.borroot.generators.BacktrackGenerator;
import com.borroot.generators.Generator;
import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.solvers.BreathSolver;
import com.borroot.solvers.DepthSolver;
import com.borroot.solvers.Solver;
import com.borroot.view.game.GameView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import static com.borroot.maze.Direction.*;

public class View {

	private final int WINDOW_WIDTH = 1200, WINDOW_HEIGHT = 900;

	private GameController controller;

	private BorderPane root = new BorderPane();
	private Scene scene;

	private GameView gameView = new GameView();
	private HBox hbox = new HBox(20);

	private ChoiceBox<Generator> cbGenerator = new ChoiceBox<>();
	private ChoiceBox<Solver> cbSolver = new ChoiceBox<>();

	private final int MAX_VALUE = 80; // TODO: If bigger then drawing issues.
	private final int INIT_VALUE = 10;
	private TextField tfHeight = new TextField(INIT_VALUE + "");
	private TextField tfWidth = new TextField(INIT_VALUE + "");

	private String btnStrSolve = "Show Solution!";
	private String btnStrUnsolve = "Hide Solution!";

	private Button btnGenerate = new Button("Generate Maze!");
	private Button btnSolve = new Button(btnStrSolve);

	public View(GameController controller){
		this.controller = controller;
		init();
	}

	/**
	 * Initialize the window.
	 */
	private void init(){
		initSettings();

		root.setCenter(gameView);
		root.setBottom(hbox);
		root.setPadding(new Insets(10, 10, 10, 10));

		scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		Stage window = Main.getWindow();
		window.setMinWidth(953);
		window.setScene(scene);
		window.setTitle("Generate Mazes!");
		window.show();

		initListeners();
	}

	/**
	 * Initialize the size listener and the keystroke listener.
	 */
	private void initListeners(){
		initListenerSize();
		initListenerKeyStroke();
	}

	/**
	 * Initialize the listeners for a change in the width/height of the gameview.
	 * Whenever the size changes the maze is redrawn.
	 */
	private void initListenerSize(){
		gameView.heightProperty().addListener(e -> redraw());
		gameView.widthProperty().addListener(e -> redraw());
	}

	/**
	 * Whenever a key is pressed sent the controller the direction.
	 */
	private void initListenerKeyStroke(){
		scene.setOnKeyPressed(e -> {
			Direction dir;

			switch(e.getCode()){
				case UP:
					dir = NORTH;
					break;
				case RIGHT:
					dir = EAST;
					break;
				case DOWN:
					dir = SOUTH;
					break;
				case LEFT:
					dir = WEST;
					break;
				default:
					dir = null;
			}

			if(dir != null){
				controller.movePlayer(dir);
			}
		});

	}

	/**
	 * Redraw the maze if it exists.
	 */
	private void redraw(){
		if(controller.mazeExists()){
			draw(controller.getMaze());
		}
	}

	/**
	 * Initialize the settings at the bottom of the window.
	 */
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

		initNoFocus();
	}

	/**
	 * Make sure that the settings are not affected by pressing the movement keys.
	 */
	private void initNoFocus(){
		for(Node node : hbox.getChildren()){
			node.setFocusTraversable(false);
		}
	}

	/**
	 * Initialize the choice boxes used to choose a generator and/or solver.
	 */
	private void initChoiceBoxes(){
		cbGenerator.setItems(FXCollections.observableArrayList(new BacktrackGenerator(), new KruskalGenerator()));
		cbGenerator.getSelectionModel().selectFirst();

		cbSolver.setItems(FXCollections.observableArrayList(new DepthSolver(), new BreathSolver()));
		cbSolver.getSelectionModel().selectFirst();
	}

	/**
	 * Initialize the 'generate maze' and 'solve maze' buttons.
	 */
	private void initButtons(){
		btnGenerate.setOnAction(e -> {
			btnSolve.setText(btnStrSolve);

			int height = Integer.parseInt(tfHeight.getText());
			int width = Integer.parseInt(tfWidth.getText());
			controller.generateAction(cbGenerator.getValue(), height, width);
		});

		btnSolve.setOnAction(e -> {
			if(controller.mazeExists()) {
				String text = (btnSolve.getText() == btnStrSolve) ? btnStrUnsolve : btnStrSolve;
				btnSolve.setText(text);
				controller.solveAction(cbSolver.getValue());
			}
		});
	}

	/**
	 * Initialize the listeners of textfields for the maze size.
	 */
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

	/**
	 * Whenever a value in a textfield is changed check if the new value is an integer
	 * and within the bounds of the sizes of the maze.
	 * @param tf
	 * @param newVal
	 */
	private void changedValue(TextField tf, String newVal){
		if (!newVal.matches("\\d*")) {
			tf.setText(newVal.replaceAll("[^\\d]", ""));
		}else if(!tf.getText().equals("")){
			int val = Integer.parseInt(newVal);
			if(val < 2){
				tf.setText("2");
			}else if(val > MAX_VALUE){
				tf.setText(MAX_VALUE + "");
			}
		}
	}

	/**
	 * Show the finish screen.
	 */
	public void finished(){
		Label lblSolved = new Label("YOU SOLVED IT!!");
		Label lblClick = new Label("Click to go back");
		lblSolved.setFont(new Font(40));
		lblClick.setFont(Font.font("Serif", FontPosture.ITALIC, 20));

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(lblSolved, lblClick);
		vbox.setAlignment(Pos.CENTER);

		Stage window = Main.getWindow();
		Scene fscene = new Scene(vbox, scene.getWidth(), scene.getHeight());
		window.setScene(fscene);


		fscene.setOnMouseClicked(e -> {
			double width = window.getWidth();
			double height = window.getHeight();

			window.setScene(this.scene);
			window.setWidth(width);
			window.setHeight(height);
		});

		controller.resetPlayer();
	}

	/**
	 * Draw the maze.
	 * @param maze
	 */
	public void draw(Maze maze){
		gameView.draw(maze);
	}
}
