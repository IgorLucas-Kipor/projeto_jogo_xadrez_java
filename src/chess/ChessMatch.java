package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkmate;
	private ChessPiece vulnerableEnPassant;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		iniciarPartida();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckmate() {
		return checkmate;
	}
	
	public ChessPiece getVulnerableEnPassant() {
		return vulnerableEnPassant;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getLines()][board.getColumns()];
		for (int i=0; i<board.getLines(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public ChessPiece makeChessMove(ChessPosition originPosition, ChessPosition targetPosition) {
		Position origin = originPosition.toPosition();
		Position destination = targetPosition.toPosition();
		validateOriginPosition(origin);
		validateTargetPosition(origin, destination);
		Piece capturedPiece = makeMove(origin, destination);
		
		if (tryCheck(currentPlayer)) {
			desfazerMovimento(origin, destination, capturedPiece);
			
			throw new ChessException("You can't put yourself in check.");
		}
		
		ChessPiece movedPiece = (ChessPiece) board.piece(destination);
		
		// #movimento especial Promoção
		
		promoted = null;
		
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && destination.getLine() == 0) || (movedPiece.getColor() == Color.BLACK && destination.getLine() == 7)) {
				promoted = (ChessPiece) board.piece(destination);
				promoted = changePromotedPiece("Q");
			}
		}
		
		
		check = (tryCheck(opponent(currentPlayer))) ? true : false;
		
		if (tryCheckmate(opponent(currentPlayer))) {
			checkmate = true;
		} else {
			nextTurn();
		}
		
		// #movimento especial En Passant
		
		if (movedPiece instanceof Pawn && (destination.getLine() == origin.getLine()+2 || destination.getLine() == origin.getLine()-2)) {
			vulnerableEnPassant = movedPiece;
		} else {
			vulnerableEnPassant = null;
		}
		
		
		return (ChessPiece) capturedPiece;
	}
	
	private void validateOriginPosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no such piece at origin position.");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("Chosen piece is not yours.");
		}
		if (!board.piece(position).thereIsPossibleMove()) {
			throw new ChessException("There is no possible movement for chosen piece.");
		}
	}
	
	private void validateTargetPosition(Position origin, Position target) {
		if (!board.piece(origin).possibleMove(target)) {
			throw new ChessException("The selected movement is impossible.");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public ChessPiece changePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted.");
		}
		
		if (!type.equals("B") && !type.equals("Q") && !type.equals("N") && !type.equals("R")) {
			return promoted;
		}
		
		Position promotedPiece = promoted.getChessPosition().toPosition();
		
		Piece p = board.removePiece(promotedPiece);
		piecesOnBoard.remove(p);
		
		ChessPiece newPiece = newPiece (type, promoted.getColor());
		
		board.placePiece(newPiece, promotedPiece);
		piecesOnBoard.add(newPiece);
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) {
			return new Bishop(board, color);
		} else if (type.equals("N")) {
			return new Knight(board, color);
		} else if (type.equals("Q")) {
			return new Queen(board, color);
		} else {
			return new Rook(board, color);
		}
	}
	
	private Piece makeMove(Position origin, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(origin);
		p.increaseMovementCounter();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece != null) {
			piecesOnBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// #movimento especial ROQUE lado do rei
		
		if (p instanceof King && target.getColumn() == origin.getColumn()+2) {
			Position originT = new Position(origin.getLine(), origin.getColumn()+3);
			Position targetT = new Position(origin.getLine(), origin.getColumn()+1);
			ChessPiece rook = (ChessPiece) board.removePiece(originT);
			board.placePiece(rook, targetT);
			rook.increaseMovementCounter();
		}
		
		// #movimento especial ROQUE lado da rainha
		
		if (p instanceof King && target.getColumn() == origin.getColumn()-2) {
			Position originT = new Position(origin.getLine(), origin.getColumn()-4);
			Position targetT = new Position(origin.getLine(), origin.getColumn()-1);
			ChessPiece rook = (ChessPiece) board.removePiece(originT);
			board.placePiece(rook, targetT);
			rook.increaseMovementCounter();
		}
		
		// #movimento especial En Passant
		
		if (p instanceof Pawn) {
			if (origin.getColumn() != target.getColumn() && capturedPiece == null) {
				Position posicaoPeao;
				if (p.getColor() == Color.WHITE) {
					posicaoPeao = new Position (target.getLine()+1, target.getColumn());
					capturedPiece = board.removePiece(posicaoPeao);
					capturedPieces.add(capturedPiece);
					piecesOnBoard.remove(capturedPiece);
				} else {
					posicaoPeao = new Position (target.getLine()-1, target.getColumn());
					capturedPiece = board.removePiece(posicaoPeao);
					capturedPieces.add(capturedPiece);
					piecesOnBoard.remove(capturedPiece);
				}
			}
		}
		
		
		return capturedPiece;
	}
	
	private void desfazerMovimento(Position origin, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMovementCounter();
		board.placePiece(p, origin);
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnBoard.add(capturedPiece);
		}
		
		// #movimento especial ROQUE lado do rei
		
		if (p instanceof King && target.getColumn() == origin.getColumn()+2) {
			Position originRook = new Position(origin.getLine(), origin.getColumn()+3);
			Position targetRook = new Position(origin.getLine(), origin.getColumn()+1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, originRook);
			rook.decreaseMovementCounter();
		}
		
		// #movimento especial ROQUE lado da rainha
		
		if (p instanceof King && target.getColumn() == origin.getColumn()-2) {
			Position originRook = new Position(origin.getLine(), origin.getColumn()-4);
			Position targetRook = new Position(origin.getLine(), origin.getColumn()-1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, originRook);
			rook.decreaseMovementCounter();
		}
		
		// #movimento especial En Passant
		
		if (p instanceof Pawn) {
			if (origin.getColumn() != target.getColumn() && capturedPiece == vulnerableEnPassant) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position (3, target.getColumn());
				} else {
					pawnPosition = new Position (4, target.getColumn());
				}
				
				board.placePiece(pawn, pawnPosition);
			}
		}
		
		
	}
	
	private Color opponent(Color color) {
		if (color == Color.WHITE) {
			return Color.BLACK;
		} else {
			return Color.WHITE;
		}
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no king of the color "+color+" on the board.");
	}
	
	private boolean tryCheck(Color color) {
		Position positionKing = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] matrix = p.possibleMoves();
			if (matrix[positionKing.getLine()][positionKing.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean tryCheckmate(Color color) {
		if (!tryCheck(color)) {
			return false;
		}
		
		List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getLines(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) {
						Position origin = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(origin, target);
						boolean tryCheck = tryCheck(color);
						desfazerMovimento(origin, target, capturedPiece);
						if (!tryCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char column, int line, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, line).toPosition());
		piecesOnBoard.add(piece);
	}
	
	public boolean[][] possibleMovements(ChessPosition originalPosition) {
		Position position = originalPosition.toPosition();
		validateOriginPosition(position);
		return board.piece(position).possibleMoves();
	}
	
	private void iniciarPartida() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
	


}
