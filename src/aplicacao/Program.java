package aplicacao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ChessException;
import xadrez.ChessMatch;
import xadrez.ChessPiece;
import xadrez.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		ChessMatch chessMatch = new ChessMatch();

		List<ChessPiece> captured = new ArrayList<>();

		while (!chessMatch.getCheckmate()) {
			try {
				UI.cleanScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Origin: ");
				ChessPosition origin = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessMatch.possibleMovements(origin);
				UI.cleanScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.makeChessMove(origin, target);

				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.println("Enter piece for promotion. Q for Queen, B for Bishop, N for Knight, R for Rook: ");
					String type = sc.nextLine().toUpperCase();
					
					while (!type.equals("B") && !type.equals("Q") && !type.equals("N") && !type.equals("R")) {
						System.out.println("Invalid value. Try again: ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.changePromotedPiece(type);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		UI.cleanScreen();
		UI.printMatch(chessMatch, captured);

	}
	
}
