package xadrez;

import java.util.ArrayList;
import java.util.List;

import jogodetabuleiro.Peça;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	
	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> peçasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		iniciarPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public PeçaXadrez realizarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peça peçaCapturada = fazerMover(origem, destino);
		proximoTurno();
		return (PeçaXadrez) peçaCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeça(posicao)) {
			throw new ChessException("There is no such piece at origin position.");
		}
		if (jogadorAtual != ((PeçaXadrez)tabuleiro.peça(posicao)).getCor()) {
			throw new ChessException("Chosen piece is not yours.");
		}
		if (!tabuleiro.peça(posicao).existeMovimentoPossivel()) {
			throw new ChessException("There is no possible movement for chosen piece.");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peça(origem).movimentoPossivel(destino)) {
			throw new ChessException("The selected movement is impossible.");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Peça fazerMover(Posicao origem, Posicao destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		if (peçaCapturada != null) {
			peçasCapturadas.add(peçaCapturada);
		}
		tabuleiro.colocarPeça(p, destino);
		return peçaCapturada;
	}
	
	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.colocarPeça(peça, new PosicaoXadrez(coluna, linha).paraPosicao());
		peçasNoTabuleiro.add(peça);
	}
	
	public boolean[][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peça(posicao).possiveisMovimentos();
	}
	
	private void iniciarPartida() {
		colocarNovaPeça('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocarNovaPeça('c', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('c', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('d', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 7, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
	


}
