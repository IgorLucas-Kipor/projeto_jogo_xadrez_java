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
	
	public Pe�aXadrez[][] pegarPe�as() {
		Pe�aXadrez[][] mat = new Pe�aXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (Pe�aXadrez) tabuleiro.pe�a(i, j);
			}
		}
		return mat;
	}
	
	private void iniciarPartida() {
		tabuleiro.colocarPe�a(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2,1));
		tabuleiro.colocarPe�a(new Rei(tabuleiro, Cor.PRETO), new Posicao(0,4));
		tabuleiro.colocarPe�a(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7,4));
	}
	


}
