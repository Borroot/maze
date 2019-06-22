package com.borroot;

import com.borroot.controller.GameController;
import com.borroot.generators.BacktrackGenerator;
import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.solvers.DepthSolver;
import com.borroot.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage window;

    public static void main(String[] args) {
    	Application.launch();
    }

	@Override
	public void start(Stage stage) throws Exception {
    	this.window = stage;
		new GameController();
	}

	public static Stage getWindow(){
    	return window;
	}
}
