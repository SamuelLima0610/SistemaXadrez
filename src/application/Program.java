package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<ChessPiece> captured = new ArrayList<>();
		while(!match.getCheckMate()) {
			try {
				UI.clearScreen();
				
				UI.printMatch(match,captured);
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
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				if(match.getPromoted() != null) {
					System.out.println("Enter piece for promotion(R/N/Q/B): ");
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("B") && !type.equals("R") && !type.equals("N") && !type.equals("Q")) {
						System.out.println("Invalid valeu of type for promotion");
						match.replacePromotedPiece(type);
					}
				}
			}catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(match,captured);
	}

}
