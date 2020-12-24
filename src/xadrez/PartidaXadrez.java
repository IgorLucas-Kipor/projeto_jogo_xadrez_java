package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogodetabuleiro.Peça;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean xeque;
	private boolean xequeMate;
	
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
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
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
		
		if (testarXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, peçaCapturada);
			
			throw new ChessException("You can't put yourself in check.");
		}
		
		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;
		
		if (testarXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
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
		PeçaXadrez p = (PeçaXadrez)tabuleiro.removerPeça(origem);
		p.aumentarContadorMovimentos();
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.colocarPeça(p, destino);
		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturadas.add(peçaCapturada);
		}
		
		// #movimento especial ROQUE lado do rei
		
		if (p instanceof Rei && destino.getColuna() == origem.getColuna()+2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna()+3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna()+1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(origemT);
			tabuleiro.colocarPeça(torre, destinoT);
			torre.aumentarContadorMovimentos();
		}
		
		// #movimento especial ROQUE lado da rainha
		
		if (p instanceof Rei && destino.getColuna() == origem.getColuna()-2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna()-4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna()-1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(origemT);
			tabuleiro.colocarPeça(torre, destinoT);
			torre.aumentarContadorMovimentos();
		}
		return peçaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peça peçaCapturada) {
		PeçaXadrez p = (PeçaXadrez)tabuleiro.removerPeça(destino);
		p.diminuirContadorMovimentos();
		tabuleiro.colocarPeça(p, origem);
		if (peçaCapturada != null) {
			tabuleiro.colocarPeça(peçaCapturada, destino);
			peçasCapturadas.remove(peçaCapturada);
			peçasNoTabuleiro.add(peçaCapturada);
		}
		
		// #movimento especial ROQUE lado do rei
		
		if (p instanceof Rei && destino.getColuna() == origem.getColuna()+2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna()+3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna()+1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(destinoT);
			tabuleiro.colocarPeça(torre, origemT);
			torre.diminuirContadorMovimentos();
		}
		
		// #movimento especial ROQUE lado da rainha
		
		if (p instanceof Rei && destino.getColuna() == origem.getColuna()-2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna()-4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna()-1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(destinoT);
			tabuleiro.colocarPeça(torre, origemT);
			torre.diminuirContadorMovimentos();
		}
	}
	
	private Cor oponente(Cor cor) {
		if (cor == Cor.BRANCO) {
			return Cor.PRETO;
		} else {
			return Cor.BRANCO;
		}
	}
	
	private PeçaXadrez rei(Cor cor) {
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peça p : lista) {
			if (p instanceof Rei) {
				return (PeçaXadrez) p;
			}
		}
		throw new IllegalStateException("There is no king of the color "+cor+" on the board.");
	}
	
	private boolean testarXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peça> peçasOponente = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peça p : peçasOponente) {
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
		
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for (Peça p : lista) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PeçaXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peça peçaCapturada = fazerMover(origem, destino);
						boolean testarXeque = testarXeque(cor);
						desfazerMovimento(origem, destino, peçaCapturada);
						if (!testarXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
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
		colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('a', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('b', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('c', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('d', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 7, new Peao(tabuleiro, Cor.PRETO));
	}
	


}
