package xadrez;

import java.util.ArrayList;
import java.util.List;

import jogodetabuleiro.Pe�a;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	
	private List<Pe�a> pe�asNoTabuleiro = new ArrayList<>();
	private List<Pe�a> pe�asCapturadas = new ArrayList<>();
	
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
		proximoTurno();
		return (Pe�aXadrez) pe�aCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPe�a(posicao)) {
			throw new ChessException("There is no such piece at origin position.");
		}
		if (jogadorAtual != ((Pe�aXadrez)tabuleiro.pe�a(posicao)).getCor()) {
			throw new ChessException("Chosen piece is not yours.");
		}
		if (!tabuleiro.pe�a(posicao).existeMovimentoPossivel()) {
			throw new ChessException("There is no possible movement for chosen piece.");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.pe�a(origem).movimentoPossivel(destino)) {
			throw new ChessException("The selected movement is impossible.");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Pe�a fazerMover(Posicao origem, Posicao destino) {
		Pe�a p = tabuleiro.removerPe�a(origem);
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		if (pe�aCapturada != null) {
			pe�asCapturadas.add(pe�aCapturada);
		}
		tabuleiro.colocarPe�a(p, destino);
		return pe�aCapturada;
	}
	
	private void colocarNovaPe�a(char coluna, int linha, Pe�aXadrez pe�a) {
		tabuleiro.colocarPe�a(pe�a, new PosicaoXadrez(coluna, linha).paraPosicao());
		pe�asNoTabuleiro.add(pe�a);
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
