package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez{
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	
	public String toString() {
		return "K";
	}
	
	private boolean podeMover(Posicao posicao) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRoque(Posicao posicao) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
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
		
		// #movimento especial ROQUE
		
		if (getContadorMovimentos() == 0 && !partidaXadrez.getXeque()) {
			// movimento especial ROQUE do lado do rei
			Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna()+3);
			if (testeTorreRoque(posicaoTorre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna()+1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna()+2);
				if (getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna()+2] = true;
				}
			}
			// movimento especial ROQUE do lado da rainha
			Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna()-4);
			if (testeTorreRoque(posicaoTorre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna()-1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna()-2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna()-3);
				if (getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null && getTabuleiro().peça(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna()-2] = true;
				}
			}
		}
		
		return mat;
	}
	

}
