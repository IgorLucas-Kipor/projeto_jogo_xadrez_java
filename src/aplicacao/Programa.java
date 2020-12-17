package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		
		while(true) {
			try {
			IU.limparTela();
			IU.imprimirTabuleiro(partidaXadrez.pegarPeças());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
			
			boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
			IU.limparTela();
			IU.imprimirTabuleiro(partidaXadrez.pegarPeças(), possiveisMovimentos);
					
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
			
			PeçaXadrez peçaCapturada = partidaXadrez.realizarMovimentoXadrez(origem, destino);
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
