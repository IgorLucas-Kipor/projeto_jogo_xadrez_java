package jogodetabuleiro;

public class Position {
	
	private int column;
	private int line;
	
	public Position(int line, int column) {
		this.column = column;
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	public void setValues(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return line + ", " + column;
	}

}
