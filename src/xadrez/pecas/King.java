package xadrez.pecas;

import jogodetabuleiro.Position;
import jogodetabuleiro.Board;
import xadrez.Color;
import xadrez.ChessMatch;
import xadrez.ChessPiece;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testTowerCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMovementCounter() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[getBoard().getLines()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//acima
		
		p.setValues(position.getLine()-1, position.getColumn());
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//abaixo
		
		p.setValues(position.getLine()+1, position.getColumn());
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//direita
		
		p.setValues(position.getLine(), position.getColumn()+1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//esquerda
		
		p.setValues(position.getLine(), position.getColumn()-1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//noroeste
		
		p.setValues(position.getLine()-1, position.getColumn()-1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//nordeste
		
		p.setValues(position.getLine()+1, position.getColumn()-1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//sudeste
		
		p.setValues(position.getLine()+1, position.getColumn()+1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		//sudoeste
		
		p.setValues(position.getLine()-1, position.getColumn()+1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		// #movimento especial ROQUE
		
		if (getMovementCounter() == 0 && !chessMatch.getCheck()) {
			// movimento especial ROQUE do lado do rei
			Position rookPosition1 = new Position(position.getLine(), position.getColumn()+3);
			if (testTowerCastling(rookPosition1)) {
				Position p1 = new Position(position.getLine(), position.getColumn()+1);
				Position p2 = new Position(position.getLine(), position.getColumn()+2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getLine()][position.getColumn()+2] = true;
				}
			}
			// movimento especial ROQUE do lado da rainha
			Position rookPosition2 = new Position(position.getLine(), position.getColumn()-4);
			if (testTowerCastling(rookPosition2)) {
				Position p1 = new Position(position.getLine(), position.getColumn()-1);
				Position p2 = new Position(position.getLine(), position.getColumn()-2);
				Position p3 = new Position(position.getLine(), position.getColumn()-3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getLine()][position.getColumn()-2] = true;
				}
			}
		}
		
		return mat;
	}
	

}
