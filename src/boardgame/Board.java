package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns, Piece[][] pieces) {
		this.rows = rows;
		this.columns = columns;
		this.pieces = pieces;
	}
	
	
	
}
