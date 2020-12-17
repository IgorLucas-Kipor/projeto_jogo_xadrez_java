package xadrez;

import jogodetabuleiro.Pe�a;
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
	
	public Pe�aXadrez realizarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Pe�a pe�aCapturada = fazerMover(origem, destino);
		return (Pe�aXadrez) pe�aCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPe�a(posicao)) {
			throw new ChessException("N�o existe pe�a na posi��o de origem.");
		}
		if (!tabuleiro.pe�a(posicao).existeMovimentoPossivel()) {
			throw new ChessException("N�o existe movimentos poss�veis para a pe�a escolhida.");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.pe�a(origem).movimentoPossivel(destino)) {
			throw new ChessException("O movimento intencionado � imposs�vel.");
		}
	}
	
	private Pe�a fazerMover(Posicao origem, Posicao destino) {
		Pe�a p = tabuleiro.removerPe�a(origem);
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		tabuleiro.colocarPe�a(p, destino);
		return pe�aCapturada;
	}
	
	private void colocarNovaPe�a(char coluna, int linha, Pe�aXadrez pe�a) {
		tabuleiro.colocarPe�a(pe�a, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	public boolean[][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.pe�a(posicao).possiveisMovimentos();
	}
	
	private void iniciarPartida() {
		colocarNovaPe�a('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocarNovaPe�a('c', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPe�a('c', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPe�a('d', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPe�a('e', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPe�a('e', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPe�a('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
	


}
