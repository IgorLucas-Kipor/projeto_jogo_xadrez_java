package xadrez.pecas;

import jogodetabuleiro.Pe�a;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Pe�aXadrez;

public class Rei extends Pe�aXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean mat[][] = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}

}
