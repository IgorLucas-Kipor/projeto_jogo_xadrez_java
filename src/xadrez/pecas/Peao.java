package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Peao extends PeçaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean matriz[][] = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// movimentos branco

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());

			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeça(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeça(p) && getContadorMovimentos() == 0
					&& getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPeça(p2)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPeçaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPeçaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			// #movimento especial En Passant BRANCO
			
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao (posicao.getLinha(), posicao.getColuna()-1);
				if (getTabuleiro().posicaoExiste(esquerda) && haUmaPeçaOponente(esquerda) && getTabuleiro().peça(esquerda) == partidaXadrez.getVulneravelEnPassant()) {
					matriz[esquerda.getLinha()-1][esquerda.getColuna()] = true;
				}
				
				Posicao direita = new Posicao (posicao.getLinha(), posicao.getColuna()+1);
				if (getTabuleiro().posicaoExiste(direita) && haUmaPeçaOponente(direita) && getTabuleiro().peça(direita) == partidaXadrez.getVulneravelEnPassant()) {
					matriz[direita.getLinha()-1][direita.getColuna()] = true;
				}
			}
			
			
		} else { // movimentos da peça afro-descendente
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());

			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeça(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeça(p) && getContadorMovimentos() == 0
					&& getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPeça(p2)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPeçaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPeçaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			// #movimento especial En Passant PRETO
			
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao (posicao.getLinha(), posicao.getColuna()-1);
				if (getTabuleiro().posicaoExiste(esquerda) && haUmaPeçaOponente(esquerda) && getTabuleiro().peça(esquerda) == partidaXadrez.getVulneravelEnPassant()) {
					matriz[esquerda.getLinha()+1][esquerda.getColuna()] = true;
				}
				
				Posicao direita = new Posicao (posicao.getLinha(), posicao.getColuna()+1);
				if (getTabuleiro().posicaoExiste(direita) && haUmaPeçaOponente(direita) && getTabuleiro().peça(direita) == partidaXadrez.getVulneravelEnPassant()) {
					matriz[direita.getLinha()+1][direita.getColuna()] = true;
				}
			}
			
		}

		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}

}
