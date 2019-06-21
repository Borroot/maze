package com.borroot;

import com.borroot.generators.BacktrackGenerator;
import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.solvers.DepthSolver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
    	Application.launch();
    }

	@Override
	public void start(Stage window) throws Exception {
		Canvas canvas = new Canvas();
		GraphicsContext gc = canvas.getGraphicsContext2D();

		BorderPane root = new BorderPane();
		root.setCenter(canvas);
		root.setBottom(new Text("hi"));

		Scene scene = new Scene(root, 500, 500);
    	window.setScene(scene);
    	window.setTitle("Maze!");
    	window.show();
	}

	private void generateMaze(){
		Maze maze = new Maze(15, 35);

		new KruskalGenerator().generate(maze);

		maze.setStart(new Cell(1, maze.getHeight()-1));
		maze.setFinish(new Cell(maze.getWidth()-1, 1));

		new DepthSolver().solve(maze);
	}
}
