package xadrez.pecas;

import jogodetabuleiro.Position;
import jogodetabuleiro.Board;
import xadrez.Color;
import xadrez.ChessPiece;

public class Knight extends ChessPiece{

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[getBoard().getLines()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		p.setValues(position.getLine()-1, position.getColumn()-2);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		p.setValues(position.getLine()-2, position.getColumn()-1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}

		
		p.setValues(position.getLine()-1, position.getColumn()+2);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getLine()-2, position.getColumn()+1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getLine()+1, position.getColumn()+2);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getLine()+2, position.getColumn()+1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getLine()+1, position.getColumn()-2);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getLine()+2, position.getColumn()-1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getLine()][p.getColumn()] = true;
		}
		
		return mat;
	}
	
	

}
