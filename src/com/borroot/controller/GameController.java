package com.borroot.controller;

import com.borroot.generators.KruskalGenerator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.solvers.DepthSolver;
import com.borroot.view.GameView;

public class GameController {

	private GameView view;

	public GameController(){
		 view = new GameView();
		 view.draw(generateMaze());
	}

	private Maze generateMaze(){
		Maze maze = new Maze(3);

		new KruskalGenerator().generate(maze);

		maze.setStart(new Cell(1, maze.getHeight()-1));
		maze.setFinish(new Cell(maze.getWidth()-1, 1));

		return maze;
	}
}
