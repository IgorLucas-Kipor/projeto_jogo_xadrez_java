package jogodetabuleiro;


public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peça[][] peças;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Erro criando tabuleiro: é necessário haver pelo menos uma linha e uma coluna.");
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
			throw new BoardException("Erro criando tabuleiro: posição não existe.");
		}
		return peças[linha][coluna];
	}
	
	public Peça peça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Erro criando tabuleiro: posição não existe.");
		}
		return peças[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeça(Peça peça, Posicao posicao) {
		if (haUmaPeça(posicao)) {
			throw new BoardException("Erro criando tabuleiro: já há uma peça nessa posição.");
		}
		peças[posicao.getLinha()][posicao.getColuna()] = peça;
		peça.posicao = posicao;
	}
	
	public Peça removerPeça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Posição não existe.");
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
			throw new BoardException("Erro criando tabuleiro: posição não existe.");
		}
		return peça(posicao) != null;
	}
	

}
