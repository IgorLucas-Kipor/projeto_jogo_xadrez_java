package xadrez;

import jogodetabuleiro.Peça;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça {

	private Cor cor;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	protected boolean haUmaPeçaOponente(Posicao posicao) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posicao);
		return p != null && p.getCor() != cor;
	}
	
	
}
