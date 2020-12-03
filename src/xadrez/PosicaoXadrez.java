package xadrez;

import jogodetabuleiro.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ChessException("Erro instânciando o xadrez: valores válidos são de a1 até h8.");
		}
		this.linha = linha;
		this.coluna = coluna;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao paraPosicao() {
		return new Posicao(8-linha, coluna-'a');
	}
	
	protected static PosicaoXadrez daPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()), 8-posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	

}
