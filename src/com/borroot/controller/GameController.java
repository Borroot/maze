package com.borroot.controller;

import com.borroot.generators.Generator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.solvers.Solver;
import com.borroot.view.View;

public class GameController {

	private View view;
	private Maze maze;

	public GameController(){
		 view = new View(this);
	}

	public void btnGenerateAction(Generator generator, int height, int width){
		generateMaze(generator, height, width);
		draw();
	}

	public void btnSolveAction(Solver solver){
		if(!maze.isSolved()){
			solver.solve(maze);
		}else{
			solver.unsolve(maze);
		}
		draw();
	}

	public void generateMaze(Generator generator, int height, int width){
		maze = new Maze(height, width);
		generator.generate(maze);

		maze.setStart(new Cell(1, maze.getHeight()-1));
		maze.setFinish(new Cell(maze.getWidth()-1, 1));
	}

	private void draw(){
		view.draw(maze);
	}
}
