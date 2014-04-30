package Eligorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.Deque;


public class MonteCarloSearchTree implements MonteCarloAI
{
    // Get a concurrent set
    private final Set<MonteCarloSearchTreeNode> nodes = Collections.newSetFromMap(new ConcurrentHashMap<MonteCarloSearchTreeNode, Boolean>());
    private final HashMap<Integer, Double> probabilities = new HashMap<Integer, Double>();
    private final Random rGen = new Random();
    private final MonteCarloSearchTreeNode root;
    private MonteCarloSearchTreeNode current;
    
    // Search time in nano seconds allowed (tenth of a second)
    private static final long MAX_SEARCH_TIME = 1000000000l;

    public MonteCarloSearchTree()
    {
        root = MonteCarloSearchTreeNode.getRoot(ConnectFourPlayer.getHuman());
        assert(root != null);
        current = root;
        nodes.add(root);
    }
    
    // Assume we're passed in a game where the player made the last move
    private void updateGameState(ConnectFourGame game)
    {
        ConnectFourMove move = game.getLastMove();
        assert(move != null);
        assert(current != null);
        
        for(MonteCarloSearchTreeNode node : current.children())
        {
            assert(node.move().player() == move.player());
            if(node.move().getColumn() == move.getColumn())
            {
                current = node;
                break;
            }
        }
    }

    @Override
    public ConnectFourMove determineMove(ConnectFourGame game)
    {
        updateGameState(game);
        final MonteCarloSearchTree tree = this;
        if(current.needToGenerateChildren())
            current.generateChildren();
        for(final MonteCarloSearchTreeNode node : current.children())
        {
            // Spawn some threads and stuff
            Thread t = new Thread(new Runnable()
            {
                public void run()
                {
                    MonteCarloSearchTree.doWork(tree, node);
                }
            });
            t.start();
        }
        
        final long startTime = System.nanoTime();
        while(System.nanoTime() - startTime <= MAX_SEARCH_TIME)
            ;   //Spin
        
        int bestScore = -1;
        ConnectFourMove bestMove = null;
        for(MonteCarloSearchTreeNode node : current.children())
        {
            probabilities.put(node.move().getColumn(), node.score());
            if(node.wins() > bestScore)
            {
                current = node;
                bestScore = node.wins();
                bestMove = node.move();
            }            
        }
        
        if(bestMove == null)
        {
            final int maxSize = current.children().size();
            final int index = rGen.nextInt(maxSize);
            bestMove = current.children().get(index).move();        
        }
        
        assert(bestMove != null);
        return bestMove;
    }

    private static void doWork(MonteCarloSearchTree tree, MonteCarloSearchTreeNode parent)
    {
        final long startTime = System.nanoTime();
        // Depth first search to try and solve games
        final Deque<MonteCarloSearchTreeNode> stack = new LinkedList<MonteCarloSearchTreeNode>();
        stack.push(parent);
        while(!stack.isEmpty() && (System.nanoTime() - startTime <= MAX_SEARCH_TIME))
        {
            MonteCarloSearchTreeNode node = stack.pop();
            if(node == null)
                continue;
            if(node.needToGenerateChildren())
                node.generateChildren();
            for(MonteCarloSearchTreeNode child : node.children())
            {
                stack.push(child);
                tree.nodes.add(child);
            }
        }
    }

    @Override
    public HashMap<Integer, Double> getProbabilities()
    {
        return probabilities;
    }

}
