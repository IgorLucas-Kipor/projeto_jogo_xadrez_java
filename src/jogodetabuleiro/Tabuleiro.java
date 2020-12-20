package jogodetabuleiro;


public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peça[][] peças;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Error creating board: at least one line and one column are needed.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		peças = new Peça[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peça peça(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return peças[linha][coluna];
	}
	
	public Peça peça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return peças[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeça(Peça peça, Posicao posicao) {
		if (haUmaPeça(posicao)) {
			throw new BoardException("Error creating board: there is already a piece in this position.");
		}
		peças[posicao.getLinha()][posicao.getColuna()] = peça;
		peça.posicao = posicao;
	}
	
	public Peça removerPeça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Position does not exist.");
		}
		if (peça(posicao) == null) {
			return null;
		}
		Peça aux = peça(posicao);
		aux.posicao = null;
		peças[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return this.posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean haUmaPeça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Error creating board: position does not exist.");
		}
		return peça(posicao) != null;
	}
	

}
