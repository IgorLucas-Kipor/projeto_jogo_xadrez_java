package jogodetabuleiro;


public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Pe�a[][] pe�as;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Error creating board: at least one line and one column are needed.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pe�as = new Pe�a[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Pe�a pe�a(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return pe�as[linha][coluna];
	}
	
	public Pe�a pe�a(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return pe�as[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPe�a(Pe�a pe�a, Posicao posicao) {
		if (haUmaPe�a(posicao)) {
			throw new BoardException("Error creating board: there is already a piece in this position.");
		}
		pe�as[posicao.getLinha()][posicao.getColuna()] = pe�a;
		pe�a.posicao = posicao;
	}
	
	public Pe�a removerPe�a(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Position does not exist.");
		}
		if (pe�a(posicao) == null) {
			return null;
		}
		Pe�a aux = pe�a(posicao);
		aux.posicao = null;
		pe�as[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return this.posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean haUmaPe�a(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return pe�a(posicao) != null;
	}
	

}
