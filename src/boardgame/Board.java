package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) throw new BoardException("Error creating board: there must be one line and one column");
		this.rows = rows;
		this.columns = columns;
		this.pieces = new Piece[this.rows][this.columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if(!positionExists(row,column)) throw new BoardException("Position not exists on the board");
		return this.pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) throw new BoardException("Position not exists on the board");
		return this.pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) throw new BoardException("There is a piece in this position " + position);
		this.pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(),position.getColumn());
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
	}
	
	public boolean thereIsAPiece(Position position){
		if(!positionExists(position)) throw new BoardException("Position not exists on the board");
		return piece(position) != null;
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) throw new BoardException("Position not exists on the board");
		if(piece(position) == null) return null;
		Piece aux = piece(position);
		aux.position = null;
		this.pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
}
