package Eligorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MonteCarloSearchPure implements MonteCarloAI
{
    private Random rGen;
    private HashMap<Integer, Double> winProbabilities;
    private static final int NUM_SIMULATIONS = 1000;

    public MonteCarloSearchPure()
    {
        rGen = new Random();
        winProbabilities = new HashMap<Integer, Double>();
    }

    // Assume that this is never passed a full board
    public ConnectFourMove determineMove(ConnectFourGame _game)
    {
        ArrayList<ConnectFourMove> moves = _game
                .getAvailableMoves(ConnectFourPlayer.getAI());

        HashMap<ConnectFourMove, Integer> probabilityMap = new HashMap<ConnectFourMove, Integer>();
        for(ConnectFourMove move : moves)
            probabilityMap.put(move, 0);

        for(ConnectFourMove move : probabilityMap.keySet())
        {
            for(int i = 0; i < NUM_SIMULATIONS; ++i)
            {
                // Deep copy in
                if(runSimulation(new ConnectFourGame(_game, move)))
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

    public boolean runSimulation(ConnectFourGame _game)
    {
        boolean canMove = true;
        // Deep copy game state in
        ConnectFourGame simulatedGame = new ConnectFourGame(_game);
        ConnectFourPlayer player = ConnectFourPlayer.getAI();;
        while(!simulatedGame.isSolved())
        {
            ArrayList<ConnectFourMove> moves = simulatedGame.getAvailableMoves(player);
            canMove = moves.size() != 0;
            if(!canMove)
                break;

            int index = rGen.nextInt(moves.size());
            simulatedGame.addMove(moves.get(index));
            // Switch the player back and forth
            player = (player == ConnectFourPlayer.getAI() ? ConnectFourPlayer.getHuman()
                    : ConnectFourPlayer.getAI());
        }

        return simulatedGame.winner() == ConnectFourPlayer.getAI();
    }
}
