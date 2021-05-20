package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int movementCounter;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMovementCounter() {
		return movementCounter;
	}
	
	public void increaseMovementCounter() {
		movementCounter++;
	}
	
	public void decreaseMovementCounter() {
		movementCounter--;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.ofPosition(position);
	}
	
	protected boolean thereIsOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
	
}
