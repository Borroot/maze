package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class BacktrackGenerator implements Generator {

	private boolean validCell(Maze maze, Cell cell){
		return cell.x >= 0 && cell.y >= 0 && cell.y < maze.getHeight() && cell.x < maze.getWidth();
	}

	private LinkedList<Cell> neighbors(Maze maze, Cell cell){
		LinkedList<Cell> neighborList = new LinkedList<>();

		for(Direction dir : Direction.values()){
			int newX = cell.x + dir.getX()*2;
			int newY = cell.y + dir.getY()*2;
			Cell neighbor = new Cell(newX, newY);

			if(validCell(maze, neighbor)){
				neighborList.push(neighbor);
			}
		}
		return neighborList;
	}

	private Cell randomUnvisitedNeighbor(Maze maze, Cell cell, LinkedList<Cell> unvisited){
		assert(hasUnvisitedNeighbors(maze, cell, unvisited));

		LinkedList<Cell> neighbors = neighbors(maze, cell);
		LinkedList<Cell> unvisitedNeighbors = new LinkedList<>();

		for(Cell neighbor : neighbors){
			if(unvisited.contains(neighbor)){
				unvisitedNeighbors.push(neighbor);
			}
		}

		int index = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size());
		return unvisitedNeighbors.get(index);
	}

	private boolean hasUnvisitedNeighbors(Maze maze, Cell cell, LinkedList<Cell> unvisited){
		LinkedList<Cell> neighbors = neighbors(maze, cell);

		for(Cell neighbor : neighbors){
			if(unvisited.contains(neighbor)){
				return true;
			}
		}
		return false;
	}

	private void generateMaze(Maze maze, LinkedList<Cell> unvisited, LinkedList<Cell> visited){
		Cell current = unvisited.pop();
		visited.push(current);

		while(unvisited.size() > 0){
			if(hasUnvisitedNeighbors(maze, current, unvisited)){
				Cell neighbor = randomUnvisitedNeighbor(maze, current, unvisited);
				maze.removeWall(current, neighbor);

				current = neighbor;
				unvisited.remove(current);
				visited.push(current);
			}else if(visited.size() > 0){
				current = visited.pop();
			}
		}
	}

	@Override
	public void generate(Maze maze){
		LinkedList<Cell> unvisited = maze.getEmptyCells();
		LinkedList<Cell> visited = new LinkedList<>();

		generateMaze(maze, unvisited, visited);
	}
}
