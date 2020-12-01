package xadrez;

import jogodetabuleiro.Tabuleiro;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
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

}
