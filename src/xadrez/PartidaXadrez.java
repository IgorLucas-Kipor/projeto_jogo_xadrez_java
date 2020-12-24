package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogodetabuleiro.Pe�a;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean xeque;
	private boolean xequeMate;
	
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
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
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
		
		if (testarXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pe�aCapturada);
			
			throw new ChessException("You can't put yourself in check.");
		}
		
		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;
		
		if (testarXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
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
		Pe�aXadrez p = (Pe�aXadrez)tabuleiro.removerPe�a(origem);
		p.aumentarContadorMovimentos();
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		tabuleiro.colocarPe�a(p, destino);
		if (pe�aCapturada != null) {
			pe�asNoTabuleiro.remove(pe�aCapturada);
			pe�asCapturadas.add(pe�aCapturada);
		}
		return pe�aCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Pe�a pe�aCapturada) {
		Pe�aXadrez p = (Pe�aXadrez)tabuleiro.removerPe�a(destino);
		p.diminuirContadorMovimentos();
		tabuleiro.colocarPe�a(p, origem);
		if (pe�aCapturada != null) {
			tabuleiro.colocarPe�a(pe�aCapturada, destino);
			pe�asCapturadas.remove(pe�aCapturada);
			pe�asNoTabuleiro.add(pe�aCapturada);
		}
	}
	
	private Cor oponente(Cor cor) {
		if (cor == Cor.BRANCO) {
			return Cor.PRETO;
		} else {
			return Cor.BRANCO;
		}
	}
	
	private Pe�aXadrez rei(Cor cor) {
		List<Pe�a> lista = pe�asNoTabuleiro.stream().filter(x -> ((Pe�aXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Pe�a p : lista) {
			if (p instanceof Rei) {
				return (Pe�aXadrez) p;
			}
		}
		throw new IllegalStateException("There is no king of the color "+cor+" on the board.");
	}
	
	private boolean testarXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Pe�a> pe�asOponente = pe�asNoTabuleiro.stream().filter(x -> ((Pe�aXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Pe�a p : pe�asOponente) {
			boolean[][] matriz = p.possiveisMovimentos();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testarXequeMate(Cor cor) {
		if (!testarXeque(cor)) {
			return false;
		}
		
		List<Pe�a> lista = pe�asNoTabuleiro.stream().filter(x -> ((Pe�aXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for (Pe�a p : lista) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((Pe�aXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Pe�a pe�aCapturada = fazerMover(origem, destino);
						boolean testarXeque = testarXeque(cor);
						desfazerMovimento(origem, destino, pe�aCapturada);
						if (!testarXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
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
		colocarNovaPe�a('h', 7, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        colocarNovaPe�a('d', 1, new Torre(tabuleiro, Cor.BRANCO));

        colocarNovaPe�a('b', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPe�a('a', 8, new Rei(tabuleiro, Cor.PRETO));
	}
	


}
