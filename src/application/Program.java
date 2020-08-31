package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessMatch match = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				UI.clearScreen();
				
				UI.printBoard(match.getPieces());
				
				System.out.println();
				System.out.println("Source:");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = match.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(match.getPieces(),possibleMoves);
				System.out.println();
				System.out.println("Target:");
				ChessPosition target = UI.readChessPosition(sc);
			
				ChessPiece capturedPiece = match.performChessMove(source, target);
			}catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
