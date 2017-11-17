package edu.miami.cse.reversi.strategy.Knotttv_Strategic_Tests;

import java.util.Arrays;

import edu.miami.cse.reversi.Board;
import edu.miami.cse.reversi.Move;
import edu.miami.cse.reversi.Player;
import edu.miami.cse.reversi.Square;

public class Heuristics {

	//Returns best move from array of moves
	public Move getBestMove(Board board, SmartMove[] possibleMoves) {
		orderMoves(board, possibleMoves);
		
		return possibleMoves[0];
	}
	
	//Orders an array of "SmartMoves" from highest heuristic value to lowest
	public SmartMove[] orderMoves(Board board, SmartMove[] possibleMoves) {
		for (int i = 0; 0 < possibleMoves.length; i++)
			calculateHeuristics(board, possibleMoves[i]);
		Arrays.sort(possibleMoves);
		return possibleMoves;
	}

	//Calculates heuristic for given move
	public void calculateHeuristics(Board board, SmartMove move) {
		Player player = move.getPlayer();
		Board tempBoard = board;
		board.play(move.getSquare());

		if (obtainsCenter2x2(tempBoard, player))
			move.updateHeuristic(1);
		;
		if (obtainsCenter4x4(tempBoard, player))
			move.updateHeuristic(2);
		;
		if (obtainsEdge(tempBoard, player))
			move.updateHeuristic(5);
		;
		if (obtainsCorner(tempBoard, player))
			move.updateHeuristic(10);
		;

//		if (relinquishesCenter2x2(tempBoard, player))
//			heuristic -= 1;
//		if (relinquishesCenter4x4(tempBoard, player))
//			heuristic -= 2;
//		if (relinquishesEdge(tempBoard, player))
//			heuristic -= 5;
//		if (relinquishesCorner(tempBoard, player))
//			heuristic -= 10;

		return;

	}

	//Early in the game
	public boolean isEarly(Board board) {
		return board.getMoves().size() < 20;
	}

	//Late in the game
	public boolean isLate(Board board) {
		return board.getMoves().size() > 40;
	}

	//Returns if player currently owns a tile in the center 4 of the board
	public boolean obtainsCenter2x2(Board board, Player user) {
		if (board.getSquareOwners().get(new Square(3, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 4)).equals(user))
			return true;
		return false;
	}

	//Returns if player currently owns a tile in the center 8 of the board
	public boolean obtainsCenter4x4(Board board, Player user) {
		if (board.getSquareOwners().get(new Square(2, 2)).equals(user)
				|| board.getSquareOwners().get(new Square(2, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(2, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(2, 5)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 2)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 5)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 2)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 5)).equals(user)
				|| board.getSquareOwners().get(new Square(5, 2)).equals(user)
				|| board.getSquareOwners().get(new Square(5, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(5, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(5, 5)).equals(user))
			return true;
		return false;
	}

	//If player currently owns a tile on the edge
	public boolean obtainsEdge(Board board, Player user) {
		if (board.getSquareOwners().get(new Square(0, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 1)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 2)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 5)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 6)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(1, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(2, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(5, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(6, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 6)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 5)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 4)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 3)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 2)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 1)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(6, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(5, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(4, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(3, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(2, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(1, 0)).equals(user))
			return true;
		return false;
	}

	//Returns if player currently owns tile in the corner
	public boolean obtainsCorner(Board board, Player user) {
		if (board.getSquareOwners().get(new Square(0, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(0, 7)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 0)).equals(user)
				|| board.getSquareOwners().get(new Square(7, 7)).equals(user))
			return true;
		return false;
	}

	//Extention of class Move 
	private class SmartMove extends Move implements Comparable {
		int heuristic;

		public SmartMove(Square square, Player player) {
			super(square, player);
			heuristic = 0;
		}

		public void updateHeuristic(int value) {
			heuristic += value;
		}

		@Override
		public int compareTo(Object o) {
			SmartMove other = (SmartMove) o;
			if (this.heuristic > other.heuristic)
				return 1;
			if (this.heuristic < other.heuristic)
				return -1;
			return 0;
		}


	}

}

