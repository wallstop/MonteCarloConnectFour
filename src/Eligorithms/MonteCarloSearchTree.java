package Eligorithms;

import java.util.HashMap;
import java.util.HashSet;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators.ChildrenIterator;

public class MonteCarloSearchTree implements MonteCarloAI
{
    private HashSet<MonteCarloSearchTreeNode> nodes;
    private final MonteCarloSearchTreeNode root;
    private MonteCarloSearchTreeNode current;
    
    // Search time in nano seconds allowed
    private static final int MAX_SEARCH_TIME = 100000;

    public MonteCarloSearchTree()
    {
        nodes = new HashSet<MonteCarloSearchTreeNode>();
        root = null;
        current = root;
        nodes.add(root);
    }

    @Override
    public ConnectFourMove determineMove(ConnectFourGame game)
    {
        final long startTime = System.nanoTime();
        while((System.nanoTime() - startTime) > MAX_SEARCH_TIME)
            doWork();
        
        // ALL BELOW IS TODO

        
//        double bestScore = 0.;
//        for(ConnectFourMove move : current.children())
//        {
//            if(
//        }

        return null;
    }

    private void doWork()
    {
        // TODO lol
    }

    @Override
    public boolean runSimulation(ConnectFourGame game)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public HashMap<Integer, Double> getProbabilities()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
