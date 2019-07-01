package com.borroot.controller;

import com.borroot.generators.Generator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.solvers.Solver;
import com.borroot.view.View;

/**
 * This class handles the game logic.
 * @author Bram Pulles
 */
public class GameController {

	private final int TICK = 100;
	private boolean running = false;

	private View view;
	private Maze maze;

	public GameController(){
		 view = new View(this);
//		 runGameLoop();
	}

	private void runGameLoop(){
		running = true;
		new Thread(() -> {
			gameLoop();
		}).start();
	}

	private void gameLoop(){
		while(running){


			if(mazeExists()){
				draw();
			}

			try{
				Thread.sleep(TICK);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function is called when the 'generate maze' button is called.
	 * @param generator
	 * @param height
	 * @param width
	 */
	public void generateAction(Generator generator, int height, int width){
		generateMaze(generator, height, width);
		draw();
	}

	/**
	 * This function is called when the 'solve maze' button is called.
	 * @param solver
	 */
	public void solveAction(Solver solver){
		if(mazeExists()){
			if(!maze.isSolved()){
				solver.solve(maze);
			}else{
				solver.unsolve(maze);
			}
			draw();
		}
	}

	/**
	 * Generate a maze with the specified generator and size.
	 * @param generator
	 * @param height
	 * @param width
	 */
	public void generateMaze(Generator generator, int height, int width){
		maze = new Maze(height, width);
		generator.generate(maze);

		maze.setStart(new Cell(1, maze.getHeight()-1));
		maze.setFinish(new Cell(maze.getWidth()-1, 1));
	}

	/**
	 * @return if there is a maze.
	 */
	public boolean mazeExists(){
		return maze != null;
	}

	/**
	 * @return the maze.
	 */
	public Maze getMaze(){
		return maze;
	}

	/**
	 * Draw the maze.
	 */
	private void draw(){
		view.draw(maze);
	}
}
