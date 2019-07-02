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
		do{
			add(cell);
			cell = cell.getParent();
		}while(cell != null);
	}

	public void add(Cell cell){
		path.add(cell);
	}

	public void remove(Cell cell){
		path.remove(cell);
	}

	public boolean contains(Cell cell){
		return path.contains(cell);
	}

	public boolean isEmpty(){
		return path.isEmpty();
	}

	public Iterator<Cell> iterator(){
		return path.iterator();
	}

	@Override
	public String toString(){
		return path.toString();
	}
}
