package aplicacao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.Pe�aXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		PartidaXadrez partidaXadrez = new PartidaXadrez();

		List<Pe�aXadrez> capturadas = new ArrayList<>();

		while (!partidaXadrez.getXequeMate()) {
			try {
				IU.limparTela();
				IU.imprimirPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origin: ");
				PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);

				boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				IU.limparTela();
				IU.imprimirTabuleiro(partidaXadrez.pegarPe�as(), possiveisMovimentos);

				System.out.println();
				System.out.print("Target: ");
				PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);

				Pe�aXadrez pe�aCapturada = partidaXadrez.realizarMovimentoXadrez(origem, destino);

				if (pe�aCapturada != null) {
					capturadas.add(pe�aCapturada);
				}
				
				if (partidaXadrez.getPromovida() != null) {
					System.out.println("Enter piece for promotion. Q for Queen, B for Bishop, N for Knight, R for Rook: ");
					String tipo = sc.nextLine().toUpperCase();
					
					while (!tipo.equals("B") && !tipo.equals("Q") && !tipo.equals("N") && !tipo.equals("R")) {
						System.out.println("Invalid value. Try again: ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.substituirPe�aPromovida(tipo);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		IU.limparTela();
		IU.imprimirPartida(partidaXadrez, capturadas);

	}
	
}
