package com.borroot.view.game;

import com.borroot.maze.Maze;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * This class draws the maze.
 * @author Bram Pulles
 */
public class GameView extends Pane {

	public GameView(){
		this.getChildren().addAll(new WallCanvas(), new PathCanvas(), new PlayerCanvas());
		this.setOnMouseClicked(e -> requestFocus());
	}

	/**
	 * Draw the given maze by first drawing the walls and then the path.
	 * @param maze
	 */
	public void draw(Maze maze){
		for(Node node : this.getChildren()){
			if(node instanceof GameCanvas) {
				((GameCanvas) node).draw(maze, this.getHeight(), this.getWidth());
			}
		}
	}
}
