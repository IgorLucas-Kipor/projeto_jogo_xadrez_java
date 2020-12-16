package jogodetabuleiro;


public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Pe�a[][] pe�as;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Erro criando tabuleiro: � necess�rio haver pelo menos uma linha e uma coluna.");
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
			throw new BoardException("Erro criando tabuleiro: posi��o n�o existe.");
		}
		return pe�as[linha][coluna];
	}
	
	public Pe�a pe�a(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Erro criando tabuleiro: posi��o n�o existe.");
		}
		return pe�as[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPe�a(Pe�a pe�a, Posicao posicao) {
		if (haUmaPe�a(posicao)) {
			throw new BoardException("Erro criando tabuleiro: j� h� uma pe�a nessa posi��o.");
		}
		pe�as[posicao.getLinha()][posicao.getColuna()] = pe�a;
		pe�a.posicao = posicao;
	}
	
	public Pe�a removerPe�a(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Posi��o n�o existe.");
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
			throw new BoardException("Erro criando tabuleiro: posi��o n�o existe.");
		}
		return pe�a(posicao) != null;
	}
	

}
