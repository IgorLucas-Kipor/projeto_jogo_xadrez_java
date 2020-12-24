package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Pe�aXadrez;

public class Peao extends Pe�aXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean matriz[][] = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// movimentos branco

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());

			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p) && getContadorMovimentos() == 0
					&& getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPe�a(p2)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		} else { // movimentos da pe�a afro-descendente
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());

			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPe�a(p) && getContadorMovimentos() == 0
					&& getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPe�a(p2)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);

			if (getTabuleiro().posicaoExiste(p) && haUmaPe�aOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}

		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}

}
