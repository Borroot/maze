package com.borroot;

import com.borroot.generators.BacktrackGenerator;
import com.borroot.maze.Maze;

public class Main {

    public static void main(String[] args) {
    	Maze maze = new Maze();
    	System.out.println(maze);

    	new BacktrackGenerator().generate(maze);
    	System.out.println(maze);
    }
}
