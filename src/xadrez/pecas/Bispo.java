package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Pe�aXadrez;

public class Bispo extends Pe�aXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	public boolean[][] possiveisMovimentos() {
		boolean mat[][] = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		// noroeste
		
		p.setValores(posicao.getLinha() - 1, posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()-1, p.getColuna()-1);
		}
		
		if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// nordeste
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna() +1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()-1, p.getColuna()+1);
		}
		
		if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// sudeste
		
		p.setValores(posicao.getLinha() +1, posicao.getColuna() + 1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()+1, p.getColuna()+1);
		}
		
		if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// sudoeste
		
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha()+1, p.getColuna()-1);
		}
		
		if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}

}