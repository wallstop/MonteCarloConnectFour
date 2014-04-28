package Eligorithms;

import java.util.HashMap;

public interface MonteCarloAI
{
    abstract ConnectFourMove determineMove(ConnectFourGame game);
    abstract HashMap<Integer, Double> getProbabilities();
    
    // Does this need to be here?
    abstract boolean runSimulation(ConnectFourGame game);
}
