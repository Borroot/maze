package com.borroot;

import com.borroot.generators.BacktrackGenerator;
import com.borroot.maze.Maze;

public class Main {

    public static void main(String[] args) {
    	Maze maze = new Maze(20, 40);
    	new BacktrackGenerator().generate(maze);
    	System.out.println(maze);
    }
}
