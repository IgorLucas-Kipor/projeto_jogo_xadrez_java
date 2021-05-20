package xadrez.pecas;

import jogodetabuleiro.Position;
import jogodetabuleiro.Board;
import xadrez.Color;
import xadrez.ChessPiece;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[getBoard().getLines()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		// noroeste
		
		p.setValues(position.getLine() - 1, position.getColumn()-1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
			p.setValues(p.getLine()-1, p.getColumn()-1);
		}
		
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		// nordeste
		
		p.setValues(position.getLine()-1, position.getColumn() +1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
			p.setValues(p.getLine()-1, p.getColumn()+1);
		}
		
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		// sudeste
		
		p.setValues(position.getLine() +1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
			p.setValues(p.getLine()+1, p.getColumn()+1);
		}
		
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		// sudoeste
		
		p.setValues(position.getLine() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
			p.setValues(p.getLine()+1, p.getColumn()-1);
		}
		
		if (getBoard().positionExists(p) && thereIsOpponentPiece(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		return mat;
	}

}