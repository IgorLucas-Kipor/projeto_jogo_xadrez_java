package xadrez.pecas;

import jogodetabuleiro.Posicao;
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
	
	private boolean podeMover(Posicao posicao) {
		Pe�aXadrez p = (Pe�aXadrez) getTabuleiro().pe�a(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean mat[][] = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//acima
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna());
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna());
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		
		p.setValores(posicao.getLinha(), posicao.getColuna()+1);
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		
		p.setValores(posicao.getLinha(), posicao.getColuna()-1);
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//noroeste
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna()-1);
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//nordeste
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna()+1);
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudoeste
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna()+1);
		
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
	

}
