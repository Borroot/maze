package com.borroot;

import com.borroot.generators.BacktrackGenerator;
import com.borroot.maze.Cell;
import com.borroot.maze.Maze;
import com.borroot.solvers.DepthSolver;

public class Main {

    public static void main(String[] args) {
    	Maze maze = new Maze(15, 25);

    	new BacktrackGenerator().generate(maze);
    	maze.setStart(new Cell(1, maze.getHeight()-1));
    	maze.setFinish(new Cell(maze.getWidth()-1, 1));

    	new DepthSolver().solve(maze);
    }
}
