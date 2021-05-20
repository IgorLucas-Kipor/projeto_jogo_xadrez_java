package boardgame;


public class Board {
	
	private int lines;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int lines, int columns) {
		if (lines < 1 || columns < 1) {
			throw new BoardException("Error creating board: at least one line and one column are needed.");
		}
		this.lines = lines;
		this.columns = columns;
		pieces = new Piece[lines][columns];
	}

	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int line, int column) {
		if (!positionExists(line, column)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return pieces[line][column];
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return pieces[position.getLine()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("Error creating board: there is already a piece in this position.");
		}
		pieces[position.getLine()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position does not exist.");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getLine()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int line, int column) {
		return line >= 0 && line < lines && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return this.positionExists(position.getLine(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return piece(position) != null;
	}
	

}
