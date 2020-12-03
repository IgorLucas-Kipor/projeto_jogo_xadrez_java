package xadrez;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
	}
	
	public PeçaXadrez[][] pegarPeças() {
		PeçaXadrez[][] mat = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return mat;
	}
	
	private void iniciarPartida() {
		tabuleiro.colocarPeça(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2,1));
		tabuleiro.colocarPeça(new Rei(tabuleiro, Cor.PRETO), new Posicao(0,4));
		tabuleiro.colocarPeça(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7,4));
	}
	


}
