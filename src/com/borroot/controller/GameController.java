package com.borroot.controller;

import com.borroot.generators.BacktrackGenerator;
import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.view.View;

public class GameController {

	private View view;

	public GameController(){
		 view = new View();
		 view.draw(generateMaze());
	}

	private Maze generateMaze(){
		Maze maze = new Maze(10, 20);

		new BacktrackGenerator().generate(maze);

		maze.setStart(new Cell(1, maze.getHeight()-1));
		maze.setFinish(new Cell(maze.getWidth()-1, 1));

		return maze;
	}
}
