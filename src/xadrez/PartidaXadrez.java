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
	private PeçaXadrez vulneravelEnPassant;
	private PeçaXadrez promovida;
	
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
	
	public PeçaXadrez getVulneravelEnPassant() {
		return vulneravelEnPassant;
	}
	
	public PeçaXadrez getPromovida() {
		return promovida;
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
		
		PeçaXadrez peçaMovida = (PeçaXadrez) tabuleiro.peça(destino);
		
		// #movimento especial Promoção
		
		promovida = null;
		
		if (peçaMovida instanceof Peao) {
			if ((peçaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (peçaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promovida = (PeçaXadrez) tabuleiro.peça(destino);
				promovida = substituirPeçaPromovida("Q");
			}
		}
		
		
		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;
		
		if (testarXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
		
		// #movimento especial En Passant
		
		if (peçaMovida instanceof Peao && (destino.getLinha() == origem.getLinha()+2 || destino.getLinha() == origem.getLinha()-2)) {
			vulneravelEnPassant = peçaMovida;
		} else {
			vulneravelEnPassant = null;
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
	
	public PeçaXadrez substituirPeçaPromovida(String tipo) {
		if (promovida == null) {
			throw new IllegalStateException("There is no piece to be promoted.");
		}
		
		if (!tipo.equals("B") && !tipo.equals("Q") && !tipo.equals("N") && !tipo.equals("R")) {
			return promovida;
		}
		
		Posicao peçaPromovida = promovida.getPosicaoXadrez().paraPosicao();
		
		Peça p = tabuleiro.removerPeça(peçaPromovida);
		peçasNoTabuleiro.remove(p);
		
		PeçaXadrez novaPeça = novaPeça (tipo, promovida.getCor());
		
		tabuleiro.colocarPeça(novaPeça, peçaPromovida);
		peçasNoTabuleiro.add(novaPeça);
		return novaPeça;
	}
	
	private PeçaXadrez novaPeça(String tipo, Cor cor) {
		if (tipo.equals("B")) {
			return new Bispo(tabuleiro, cor);
		} else if (tipo.equals("N")) {
			return new Cavalo(tabuleiro, cor);
		} else if (tipo.equals("Q")) {
			return new Rainha(tabuleiro, cor);
		} else {
			return new Torre(tabuleiro, cor);
		}
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
		
		// #movimento especial En Passant
		
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && peçaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao (destino.getLinha()+1, destino.getColuna());
					peçaCapturada = tabuleiro.removerPeça(posicaoPeao);
					peçasCapturadas.add(peçaCapturada);
					peçasNoTabuleiro.remove(peçaCapturada);
				} else {
					posicaoPeao = new Posicao (destino.getLinha()-1, destino.getColuna());
					peçaCapturada = tabuleiro.removerPeça(posicaoPeao);
					peçasCapturadas.add(peçaCapturada);
					peçasNoTabuleiro.remove(peçaCapturada);
				}
			}
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
		
		// #movimento especial En Passant
		
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && peçaCapturada == vulneravelEnPassant) {
				PeçaXadrez peao = (PeçaXadrez) tabuleiro.removerPeça(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao (3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao (4, destino.getColuna());
				}
				
				tabuleiro.colocarPeça(peao, posicaoPeao);
			}
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
        colocarNovaPeça('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}
	


}
