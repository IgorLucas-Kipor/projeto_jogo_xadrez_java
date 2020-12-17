package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.Pe�aXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		
		while(true) {
			try {
			IU.limparTela();
			IU.imprimirTabuleiro(partidaXadrez.pegarPe�as());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
			
			boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
			IU.limparTela();
			IU.imprimirTabuleiro(partidaXadrez.pegarPe�as(), possiveisMovimentos);
					
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
			
			Pe�aXadrez pe�aCapturada = partidaXadrez.realizarMovimentoXadrez(origem, destino);
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}
