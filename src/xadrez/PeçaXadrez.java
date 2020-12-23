package xadrez;

import jogodetabuleiro.Pe�a;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;

public abstract class Pe�aXadrez extends Pe�a {

	private Cor cor;

	public Pe�aXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	protected boolean haUmaPe�aOponente(Posicao posicao) {
		Pe�aXadrez p = (Pe�aXadrez) getTabuleiro().pe�a(posicao);
		return p != null && p.getCor() != cor;
	}
	
	
}
