import java.util.ArrayList;
import java.util.List;

public class Engine {
	private int ROWS;
	private int COLUMNS;
	private MyType[][] grid;
	private int startPosition;
	private int endPosition;
	private MyType whatToAdd;
	private boolean remove;

	public Engine(int rows, int columns) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		
		grid = new MyType[ROWS][COLUMNS];
		initialize();
	}

	public void initialize() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				grid[i][j] = MyType.EMPTY;
			}
		}
		
		remove = false;
		startPosition = -1;
		endPosition = -1;
		whatToAdd = MyType.WALL;
	}

	public void printGrid() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	public boolean addComponent(int i, int j) {
		
		if(remove == true)
		{
			if(grid[i][j] == MyType.START) startPosition = -1;
			else if(grid[i][j] == MyType.END) endPosition = -1;
			
			grid[i][j] = MyType.EMPTY;
			
			return true;
		}
		
		switch(whatToAdd)
		{
		case START:
			return addStartPosition(i, j);
		case END:
			return addEndPosition(i, j);
		default:
			return addWall(i, j);
		}
	}
	
	public void switchComponentToAdd(MyType type) {
		whatToAdd = type;
	}
	
	public int[][] findExit() {
		
		if(startPosition == -1 || endPosition == -1)
			return null;
		
		int[][] parents = run_bfs();
		
		return parents;
	}
	
	private int[][] run_bfs() {
		
		int[][] colors = new int[ROWS][COLUMNS];
		int[][] parents = new int[ROWS][COLUMNS];
		
		List<Integer> Q = new ArrayList<Integer>();
		
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				colors[i][j] = 0;
				parents[i][j] = -1;
			}
		}
		
		colors[startPosition/COLUMNS][startPosition%COLUMNS] = 1;
		Q.add(startPosition);
		
		while(Q.size() > 0)
		{
			int c = Q.remove(0);
			
			int indexI = c / COLUMNS;
			int indexJ = c % COLUMNS;
			
			if(indexI - 1 >= 0)
			{
				if(grid[indexI-1][indexJ] != MyType.WALL && colors[indexI-1][indexJ] == 0)
				{
					colors[indexI-1][indexJ] = 1;
					parents[indexI-1][indexJ] = c;
					
					Q.add((indexI-1) * COLUMNS + indexJ);
				}
			}
			
			if(indexI + 1 < ROWS)
			{
				if(grid[indexI+1][indexJ] != MyType.WALL && colors[indexI+1][indexJ] == 0)
				{
					colors[indexI+1][indexJ] = 1;
					parents[indexI+1][indexJ] = c;
					
					Q.add((indexI+1) * COLUMNS + indexJ);
				}
			}
			
			if(indexJ - 1 >= 0)
			{
				if(grid[indexI][indexJ-1] != MyType.WALL && colors[indexI][indexJ-1] == 0)
				{
					colors[indexI][indexJ-1] = 1;
					parents[indexI][indexJ-1] = c;
					
					Q.add(indexI * COLUMNS + indexJ - 1);
				}
			}
			
			if(indexJ + 1 < COLUMNS)
			{
				if(grid[indexI][indexJ+1] != MyType.WALL && colors[indexI][indexJ+1] == 0)
				{
					colors[indexI][indexJ+1] = 1;
					parents[indexI][indexJ+1] = c;
					
					Q.add(indexI * COLUMNS + indexJ + 1);
				}
			}
			
			colors[indexI][indexJ] = 2;
		}
		
		return parents;
	}
	
	private boolean addStartPosition(int positionI, int positionJ) {
		if(grid[positionI][positionJ] == MyType.EMPTY && startPosition == -1) {
			startPosition = positionI * COLUMNS + positionJ;
			grid[positionI][positionJ] = MyType.START;
			return true;
		}
		
		return false;
	}
	
	private boolean addEndPosition(int positionI, int positionJ) {
		if(grid[positionI][positionJ] == MyType.EMPTY && endPosition == -1) {
			endPosition = positionI * COLUMNS + positionJ;
			grid[positionI][positionJ] = MyType.END;
			return true;
		}
		
		return false;
	}
	
	private boolean addWall(int positionI, int positionJ) {
		if(grid[positionI][positionJ] == MyType.EMPTY) {
			grid[positionI][positionJ] = MyType.WALL;
			return true;
		}
		
		return false;
	}
	
	public void removeElement() {
		
		if(remove == true) remove = false;
		else remove = true;
		
	}

	public int getStartPosition() {
		return startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public MyType[][] getGrid() {
		return grid;
	}

	public MyType getWhatToAdd() {
		return whatToAdd;
	}
	
	
}
