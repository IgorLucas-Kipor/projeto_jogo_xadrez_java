package aplicacao;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.Pe�aXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		
		while(true) {
			IU.imprimirTabuleiro(partidaXadrez.pegarPe�as());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
			
			Pe�aXadrez pe�aCapturada = partidaXadrez.realizarMovimentoXadrez(origem, destino);
		}

	}

}
