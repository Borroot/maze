package com.borroot.controller;

import com.borroot.generators.Generator;
import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;
import com.borroot.maze.Path;
import com.borroot.solvers.Solver;
import com.borroot.view.View;

import static com.borroot.maze.Tile.WALL;

/**
 * This class handles the game logic.
 * @author Bram Pulles
 */
public class GameController {

	private View view;
	private Maze maze;

	public GameController(){
		 view = new View(this);
	}

	/**
	 * Move the player in the given direction if possible.
	 * If the new position is the finish, then show the finish screen.
	 * @param dir
	 */
	public void movePlayer(Direction dir){
		if(mazeExists()) {
			Cell curP = maze.getPlayer();
			Cell newP = new Cell(curP.x + dir.getX(), curP.y + dir.getY());
			if(maze.validIndex(newP) && maze.get(newP) != WALL) {
				maze.setPlayer(newP);
				if(newP.equals(maze.getFinish())){
					view.finished((maze.getWidth()-1)/2, (maze.getHeight()-1)/2);
				}
			}
			draw();
		}
	}

	/**
	 * Put the player back at the starting position.
	 */
	public void resetPlayer(){
		if(mazeExists()) {
			maze.setPlayer(maze.getStart());
		}
		draw();
	}

	/**
	 * This function is called when the 'generate maze' button is called.
	 * @param generator
	 */
	public void generateAction(Generator generator, final int WIDTH, final int HEIGHT){
		generateMaze(generator, WIDTH, HEIGHT);
		draw();
	}

	/**
	 * This function is called when the 'solve maze' button is called.
	 * @param solver
	 */
	public void solveAction(Solver solver){
		if(mazeExists()){
			if(maze.getPath().isEmpty()){
				maze.setPath(solver.solve(maze));
			}else{
				maze.setPath(new Path());
			}
			draw();
		}
	}

	/**
	 * Generate a maze with the specified generator and size.
	 * @param generator
	 * @param WIDTH
	 * @param HEIGHT
	 */
	public void generateMaze(Generator generator, final int WIDTH, final int HEIGHT){
		maze = generator.generate(WIDTH, HEIGHT);

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
