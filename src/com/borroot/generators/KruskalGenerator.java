package com.borroot.generators;

import com.borroot.maze.Cell;
import com.borroot.maze.Maze;

import java.util.Collections;
import java.util.LinkedList;

import static com.borroot.maze.Tile.EMPTY;
import static com.borroot.maze.Tile.WALL;

public class KruskalGenerator implements Generator {

	private boolean isHorizontal(Maze maze, Cell wall){
		return maze.get(new Cell(wall.x+1, wall.y)) != WALL && maze.get(new Cell(wall.x-1, wall.y)) != WALL;
	}

	private Cell[] getDividedCells(Maze maze, Cell wall){
		if(isHorizontal(maze, wall)){
			Cell[] cells = {new Cell(wall.x+1, wall.y), new Cell(wall.x-1, wall.y)};
			return cells;
		}else{
			Cell[] cells = {new Cell(wall.x, wall.y+1), new Cell(wall.x, wall.y-1)};
			return cells;
		}
	}

	private LinkedList<Cell>[] getCellsets(Cell[] cells, LinkedList<LinkedList<Cell>> cellsets){
		LinkedList<Cell>[] sets = new LinkedList[2];

		for (LinkedList<Cell> cellset : cellsets) {
			for(Cell cell : cellset){
				if (cell.equals(cells[0])) {
					sets[0] = cellset;
				} else if (cell.equals(cells[1])) {
					sets[1] = cellset;
				}
			}
		}

		return sets;
	}

	private boolean distinctSets(Cell[] cells, LinkedList<LinkedList<Cell>> cellsets) {
		LinkedList<Cell>[] sets = getCellsets(cells, cellsets);
		if(sets[0] != null && sets[1] != null){
			return !(sets[0].contains(cells[0]) && sets[1].contains(cells[0]));
		}
		return false;
	}

	private void joinSets(Cell[] cells, LinkedList<LinkedList<Cell>> cellsets){
		LinkedList<Cell>[] sets = getCellsets(cells, cellsets);
		cellsets.remove(sets[0]);
		cellsets.remove(sets[1]);

		LinkedList<Cell> newSet = new LinkedList<>();
		newSet.addAll(sets[0]);
		newSet.addAll(sets[1]);

		cellsets.add(newSet);
	}

	@Override
	public void generate(Maze maze) {
		LinkedList<Cell> walls = maze.getWallCells();
		Collections.shuffle(walls);

		LinkedList<Cell> cells = maze.getEmptyCells();
		LinkedList<LinkedList<Cell>> cellsets = new LinkedList<>(new LinkedList<>());
		for(Cell cell : cells){
			LinkedList<Cell> set = new LinkedList<>();
			set.add(cell);
			cellsets.add(set);
		}

		for(Cell wall : walls){
			Cell[] dividedCells = getDividedCells(maze, wall);
			if(distinctSets(dividedCells, cellsets)){
				maze.set(wall, EMPTY);
				joinSets(dividedCells, cellsets);
			}
		}

	}
}
