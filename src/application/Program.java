package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessMatch match = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		while(true) {
			UI.printBoard(match.getPieces());
			
			System.out.println();
			System.out.println("Source:");
			ChessPosition source = UI.readChessPosition(sc);
			
			System.out.println();
			System.out.println("Source:");
			ChessPosition target = UI.readChessPosition(sc);
		
			ChessPiece capturedPiece = match.performChessMove(source, target);
		}
	}

}
