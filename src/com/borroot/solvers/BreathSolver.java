package com.borroot.solvers;

import com.borroot.maze.Cell;
import com.borroot.maze.Direction;
import com.borroot.maze.Maze;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.borroot.maze.Tile.*;

/**
 * Solve the maze using a breath first search algorithm.
 * @author Bram Pulles
 */
public class BreathSolver implements Solver {

	/**
	 * A cell with a parent cell.
	 * @author Bram Pulles
	 */
	private class CellP {

		private Cell cell;
		private CellP parent;

		public CellP(Cell cell){
			this.cell = cell;
		}

		public Cell getCell(){
			return cell;
		}

		public CellP getParent(){
			return parent;
		}

		public void setParent(CellP parent){
			this.parent = parent;
		}

		@Override
		public boolean equals(Object o){
			if(o instanceof CellP){
				return cell.equals(((CellP)o).getCell());
			}else{
				return false;
			}
		}

		@Override
		public String toString(){
			return cell.toString();
		}
	}

	/**
	 * Set all the cells which are in the solution to a path.
	 * This is checked by getting all the parents of the cells, starting from the finish.
	 * @param maze
	 * @param cell
	 */
	private void setPath(Maze maze, CellP cell){
		do{
			cell = cell.getParent();
			maze.set(cell.getCell(), PATH);
		}while(cell.getParent() != null);
	}

	@Override
	public void solve(Maze maze){
		Queue<CellP> queue = new LinkedList<>();
		List<CellP> discovered = new LinkedList<>();

		discovered.add(new CellP(maze.getStart()));
		queue.add(new CellP(maze.getStart()));

		while(!queue.isEmpty()){
			CellP cellP = queue.remove();

			if(maze.validIndex(cellP.getCell()) && cellP.getCell().equals(maze.getFinish())){
				setPath(maze, cellP);
				maze.setSolved(true);
				return;
			}

			for(Direction dir : Direction.values()){
				CellP nextP = new CellP(new Cell(cellP.getCell().x + dir.getX(), cellP.getCell().y + dir.getY()));
				if(maze.validIndex(nextP.getCell())){
					if(!discovered.contains(nextP) && maze.get(nextP.getCell()) == EMPTY){
						discovered.add(nextP);
						nextP.setParent(cellP);
						queue.add(nextP);
					}
				}
			}
		}
	}

	@Override
	public String toString(){
		return "Breath First Search";
	}
}
