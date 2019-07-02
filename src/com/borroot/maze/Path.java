package com.borroot.maze;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to represent a path going through the maze.
 * @author Bram Pulles
 */
public class Path {

	List<Cell> path = new LinkedList<>();

	public Path(){
	}

	public Path(Cell cell){
		while(cell.getParent() != null){
			add(cell);
			cell = cell.getParent();
		}
	}

	public void add(Cell cell){
		path.add(cell);
	}

	public void remove(Cell cell){
		path.remove(cell);
	}

	public boolean isEmpty(){
		return path.isEmpty();
	}

	public Iterator<Cell> iterator(){
		return path.iterator();
	}
}
