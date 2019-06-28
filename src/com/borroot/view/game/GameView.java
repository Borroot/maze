package com.borroot.view.game;

import com.borroot.maze.Maze;
import javafx.scene.layout.StackPane;

/**
 * This class draws the maze.
 * @author Bram Pulles
 */
public class GameView extends StackPane {

	private WallCanvas wallCanvas = new WallCanvas();
	private PathCanvas pathCanvas = new PathCanvas();

	public GameView(){
		this.getChildren().addAll(wallCanvas, pathCanvas);
	}

	/**
	 * Draw the given maze by first drawing the walls and then the path.
	 * @param maze
	 */
	public void draw(Maze maze){
		wallCanvas.draw(maze, this.getHeight(), this.getWidth());
		pathCanvas.draw(maze, this.getHeight(), this.getWidth());
	}
}
