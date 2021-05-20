package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean matrix[][] = new boolean[getBoard().getLines()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// movimentos branco

		if (getColor() == Color.WHITE) {
			p.setValues(position.getLine() - 1, position.getColumn());

			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			p.setValues(position.getLine() - 2, position.getColumn());
			Position p2 = new Position(position.getLine() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMovementCounter() == 0
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			p.setValues(position.getLine() - 1, position.getColumn() - 1);

			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			p.setValues(position.getLine() - 1, position.getColumn() + 1);

			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			
			// #movimento especial En Passant WHITE
			
			if (position.getLine() == 3) {
				Position esquerda = new Position (position.getLine(), position.getColumn()-1);
				if (getBoard().positionExists(esquerda) && thereIsOpponentPiece(esquerda) && getBoard().piece(esquerda) == chessMatch.getVulnerableEnPassant()) {
					matrix[esquerda.getLine()-1][esquerda.getColumn()] = true;
				}
				
				Position direita = new Position (position.getLine(), position.getColumn()+1);
				if (getBoard().positionExists(direita) && thereIsOpponentPiece(direita) && getBoard().piece(direita) == chessMatch.getVulnerableEnPassant()) {
					matrix[direita.getLine()-1][direita.getColumn()] = true;
				}
			}
			
			
		} else { // movimentos da peça afro-descendente
			p.setValues(position.getLine() + 1, position.getColumn());

			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			p.setValues(position.getLine() + 2, position.getColumn());
			Position p2 = new Position(position.getLine() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMovementCounter() == 0
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			p.setValues(position.getLine() + 1, position.getColumn() - 1);

			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			p.setValues(position.getLine() + 1, position.getColumn() + 1);

			if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
				matrix[p.getLine()][p.getColumn()] = true;
			}
			
			// #movimento especial En Passant BLACK
			
			if (position.getLine() == 4) {
				Position left = new Position (position.getLine(), position.getColumn()-1);
				if (getBoard().positionExists(left) && thereIsOpponentPiece(left) && getBoard().piece(left) == chessMatch.getVulnerableEnPassant()) {
					matrix[left.getLine()+1][left.getColumn()] = true;
				}
				
				Position right = new Position (position.getLine(), position.getColumn()+1);
				if (getBoard().positionExists(right) && thereIsOpponentPiece(right) && getBoard().piece(right) == chessMatch.getVulnerableEnPassant()) {
					matrix[right.getLine()+1][right.getColumn()] = true;
				}
			}
			
		}

		return matrix;
	}

	@Override
	public String toString() {
		return "P";
	}

}
