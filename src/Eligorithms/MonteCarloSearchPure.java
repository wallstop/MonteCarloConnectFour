package Eligorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MonteCarloSearchPure implements MonteCarloAI
{
    private Random rGen;
    private HashMap<Integer, Double> winProbabilities;
    private static final int NUM_SIMULATIONS = 1200;

    public MonteCarloSearchPure()
    {
        rGen = new Random();
        winProbabilities = new HashMap<Integer, Double>();
    }

    // Assume that this is never passed a full board
    public ConnectFourMove determineMove(ConnectFourGame game)
    {
        final ArrayList<ConnectFourMove> moves = game.getAvailableMoves(ConnectFourPlayer.getAI());

        final HashMap<ConnectFourMove, Integer> probabilityMap = new HashMap<ConnectFourMove, Integer>();
        for(ConnectFourMove move : moves)
            probabilityMap.put(move, 0);

        for(ConnectFourMove move : probabilityMap.keySet())
        {
            for(int i = 0; i < NUM_SIMULATIONS; ++i)
            {
                // Deep copy in
                if(runSimulation(new ConnectFourGame(game, move)))
                    probabilityMap.put(move, probabilityMap.get(move) + 1);
            }
        }

        ConnectFourMove ret = null;
        int bestNumWins = 0;
        for(ConnectFourMove move : probabilityMap.keySet())
        {
            if(bestNumWins < probabilityMap.get(move))
            {
                ret = move;
                bestNumWins = probabilityMap.get(move);
            }
            // Update internal probabilities
            winProbabilities.put(move.getColumn(), (double) probabilityMap.get(move)
                    / (double) NUM_SIMULATIONS);
        }

        return ret;
    }

    public HashMap<Integer, Double> getProbabilities()
    {
        return winProbabilities;
    }

    public boolean runSimulation(ConnectFourGame game)
    {
        // Deep copy game state in
        final ConnectFourGame simulatedGame = new ConnectFourGame(game);
        ConnectFourPlayer player = ConnectFourPlayer.getHuman();;
        while(!simulatedGame.isSolved() && !simulatedGame.isFull())
        {
            final ArrayList<ConnectFourMove> myMoves = simulatedGame.getAvailableMoves(player);
            if(myMoves.size() == 0)
                break;
            
            ConnectFourMove moveToTake = null;
            // Check if we can take winning move
            for(ConnectFourMove move : myMoves)
            {
                if(simulatedGame.testMove(move))
                {
                    moveToTake = move;
                    break;
                }
            }
            
            // If we don't have a winning move, then randoomly pick one
            if(moveToTake == null)
            {
                final int index = rGen.nextInt(myMoves.size());
                moveToTake = myMoves.get(index);
            }
            
            simulatedGame.addMove(moveToTake);
            // Switch the player back and forth
            player = getOppositePlayer(player);
        }

        return simulatedGame.winner() == ConnectFourPlayer.getAI();
    }
    
    private ConnectFourPlayer getOppositePlayer(ConnectFourPlayer player)
    {
        assert(player == ConnectFourPlayer.getAI() || player == ConnectFourPlayer.getHuman());
        return (player == ConnectFourPlayer.getAI() ? ConnectFourPlayer.getHuman() : ConnectFourPlayer.getAI());
    }
}
