package edu.miami.cse.reversi.strategy.Knotttv_Strategic_Tests;

import edu.miami.cse.reversi.Board;
import edu.miami.cse.reversi.Square;
import edu.miami.cse.reversi.Strategy;

import java.util.Random;

public class AlphaBeta implements Strategy {

    private final long MAX_TIME = 1000;
    private long startTime;
    private int nodeCutoff = 0;
    private int epthCutoff = 0;


    @Override
    public Square chooseSquare(Board board) {

        /* The first thing we do is setting the start time. */
        startTime = System.currentTimeMillis();

        Board initialBoard;
        int allowedDepth = 3;
//        int allowedNodes = 20;
        int alpha = -999;
        int beta = 999;
        boolean wantToMaximize = false;
        Square optimalMove;


        //Generate a state that we are able to work with locally
        //Check to see if there are available moves for the current player
        // Otherwise, pass and generate new local board
        if (!board.getCurrentPossibleSquares().isEmpty()) {
            initialBoard = board;
        } else {
            initialBoard = board.pass();
        }


        //Send to brendan
//        Object[] possibileMoves = Heuristics.orderMoves(board, initialBoard.getCurrentPossibleSquares().toArray());

        Object[] possibileMoves = board.getCurrentPossibleSquares().toArray();

        //Start with an arbitrary move, in the future we will do an intelligent move selection
        //send wantToMaximize as false to start because the immediate proceeding recursive call will be a minimization

        optimalMove = (Square)possibileMoves[new Random().nextInt(possibileMoves.length)];

//        optimalMove = (Square)possibileMoves[0];

        int currentUtilityScore = a_b_Pruning(initialBoard.play(optimalMove),
                alpha,
                beta,
                allowedDepth,
                allowedNodes--,
                !wantToMaximize);

        for( int i = 0; i < possibileMoves.length; i++){
            if (alpha >= beta || initialBoard.isComplete()) break;

            if(MAX_TIME - (System.currentTimeMillis() - startTime) < MAX_TIME - 100){
                System.out.println("Ran Out of Time");
                return optimalMove;
                }


            //update aplha/beta based on the currentUtilityScore
            if (wantToMaximize){
                alpha = alpha > currentUtilityScore ? alpha : currentUtilityScore;
            }else{
                beta = beta > currentUtilityScore ? beta : currentUtilityScore;
            }

            //For each of the possible moves we want to call a Minimization or Maximization of the subtree
            int proceedingUtilityScore = a_b_Pruning(initialBoard.play((Square)possibileMoves[i]),
                    alpha,
                    beta,
                    allowedDepth,
                    allowedNodes--,
                    !wantToMaximize);


            //Is the utility of a testMove higher than our currentUtility, if so, this is our better move
            //we would want to update the aplha accordingly
            //Is the utility of testMove lower than the currentUtility, if we are testing for the opponent minimization
            //we would want to update the beta accordingly and use this move to minimize opponent opportunity
            if (wantToMaximize) {
                if (proceedingUtilityScore > currentUtilityScore) {
                    currentUtilityScore = proceedingUtilityScore;
                    optimalMove = (Square)possibileMoves[i];
                }
                alpha = alpha > currentUtilityScore ? alpha : currentUtilityScore;
            } else {
                if (proceedingUtilityScore < currentUtilityScore) {
                    currentUtilityScore = proceedingUtilityScore;
                    optimalMove = (Square)possibileMoves[i];
                }
                beta = beta > currentUtilityScore ? beta : currentUtilityScore;
            }

        }

//        System.out.println("Node Cutoff: " + nodeCutoff + " Depth Cutoff: " + depthCutoff);

        return optimalMove;
    }

    private int a_b_Pruning(Board instanceBoard, int alpha, int beta, int allowedDepth, int allowedNodes, boolean wantToMaximize) {

        //returns the score at the end of this recursive branch, to be compared to the other scores, higher is better for us
        // we want to maximize the score
//        if (allowedDepth <= 0) {
//            depthCutoff++;
//            return Math.abs(instanceBoard.getPlayerSquareCounts().get(instanceBoard.getCurrentPlayer()) -
//                    instanceBoard.getPlayerSquareCounts().get(instanceBoard.getCurrentPlayer().opponent()));
//        }

        if(instanceBoard.isComplete()){
            return Math.abs(instanceBoard.getPlayerSquareCounts().get(instanceBoard.getCurrentPlayer()) -
                    instanceBoard.getPlayerSquareCounts().get(instanceBoard.getCurrentPlayer().opponent()));
        }

        if(allowedNodes <= 0) {
            nodeCutoff++;
            return Math.abs(instanceBoard.getPlayerSquareCounts().get(instanceBoard.getCurrentPlayer()) -
                    instanceBoard.getPlayerSquareCounts().get(instanceBoard.getCurrentPlayer().opponent()));
        }

        allowedDepth--;

        //Send to brendan
        Object[] instancePossibileMoves = instanceBoard.getCurrentPossibleSquares().toArray();

        //No Possible Moves, Pass
        if(instancePossibileMoves.length == 0){
            return a_b_Pruning(instanceBoard.pass(),
                    alpha,
                    beta,
                    allowedDepth,
                    allowedNodes--,
                    !wantToMaximize);
        }

        int optimalUtility = wantToMaximize ? -999 : 999;

        for(int i = 0; i < instancePossibileMoves.length ; i++){
            if (alpha >= beta) break;

            int proceedingUtilityInstanceScore = a_b_Pruning(instanceBoard.play((Square)instancePossibileMoves[i]),
                    alpha,
                    beta,
                    allowedDepth,
                    allowedNodes--,
                    !wantToMaximize);
            if (wantToMaximize) {
                optimalUtility = optimalUtility > proceedingUtilityInstanceScore ? optimalUtility : proceedingUtilityInstanceScore;
                alpha = alpha > optimalUtility ? alpha : optimalUtility;
            } else {
                optimalUtility = optimalUtility < proceedingUtilityInstanceScore ? optimalUtility : proceedingUtilityInstanceScore;
                beta = beta > optimalUtility ? beta : optimalUtility;
            }
        }
        return optimalUtility;
    }

}



