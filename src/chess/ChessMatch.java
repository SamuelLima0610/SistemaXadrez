package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private List<ChessPiece> piecesOnTheBoard;
	private List<ChessPiece> capturedPieces;
	
	public ChessMatch() {
		this.board = new Board(8,8);
		this.turn = 0;
		this.currentPlayer = Color.WHITE;
		this.piecesOnTheBoard = new ArrayList();
		this.capturedPieces = new ArrayList();
		initialSetup();
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public Color getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public boolean getCheck() {
		return this.check;
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[this.board.getRows()][this.board.getColumns()];
		for(int i = 0; i < this.board.getRows(); i++) {
			for(int j = 0; j < this.board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) this.board.piece(i, j); 
			}
		}
		return mat;
	}
	
	private Color opponent(Color color) {
		return color == Color.WHITE ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter( x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if( p instanceof King) return (ChessPiece) p;
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		this.piecesOnTheBoard.add(piece);
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = this.board.removePiece(source);
		Piece capturedPiece = this.board.removePiece(target);
		this.board.placePiece(p, target);
		if(capturedPiece != null) {
			this.piecesOnTheBoard.remove((ChessPiece)capturedPiece);
			this.capturedPieces.add((ChessPiece)capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = this.board.removePiece(target);
		this.board.placePiece(p, source);
		if(capturedPiece != null) {
			this.board.placePiece(capturedPiece, target);
			this.capturedPieces.remove((ChessPiece)capturedPiece);
			this.piecesOnTheBoard.add((ChessPiece)capturedPiece);
		}
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position p = sourcePosition.toPosition();
		validateSourcePosition(p);
		return this.board.piece(p).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source,target);
		if(testCheck(this.currentPlayer)) {
			undoMove(source,target,capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position source) {
		if(!this.board.thereIsAPiece(source)) throw new ChessException("There is no piece in the source position");
		if(this.currentPlayer != ((ChessPiece) this.board.piece(source)).getColor()) throw new ChessException("This chosen piece is not yours");
		if(!board.piece(source).isThereAnyPossibleMove()) throw new ChessException("There is no possible moves for the chosen piece");
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!this.board.piece(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
	}
	
	public void nextTurn() {
		this.turn++;
		this.currentPlayer = this.currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE; 
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
