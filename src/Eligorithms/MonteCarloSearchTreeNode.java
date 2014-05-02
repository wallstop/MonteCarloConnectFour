package Eligorithms;

import java.util.ArrayList;

public class MonteCarloSearchTreeNode
{
    private ArrayList<MonteCarloSearchTreeNode> children;
    private MonteCarloSearchTreeNode parent;
    private ConnectFourGame game;
    private ConnectFourMove move;
    private int wins;
    private int possibilities;

    public MonteCarloSearchTreeNode(MonteCarloSearchTreeNode _parent)
    {
        parent = _parent;
        wins = 0;
        possibilities = 0;
        move = null;
        game = new ConnectFourGame();
        children = null;
    }

    public MonteCarloSearchTreeNode(MonteCarloSearchTreeNode _parent, ConnectFourMove _move)
    {
        wins = 0;
        possibilities = 0;
        parent = _parent;
        move = _move;
        game = new ConnectFourGame(_parent);
        assert (move != null);
        game.addMove(_move);
        if(game.isSolved() || game.isFull())
        {
            children = new ArrayList<MonteCarloSearchTreeNode>();
            if(game.winner() == ConnectFourPlayer.getAI())
                updateWithWin();
            else
                updateWithLoss();
        }
    }

    public static MonteCarloSearchTreeNode getRoot(ConnectFourPlayer player)
    {
        MonteCarloSearchTreeNode ret = new MonteCarloSearchTreeNode(null);
        generateChildren(ret, player);
        return ret;
    }

    private static void generateChildren(MonteCarloSearchTreeNode node, ConnectFourPlayer player)
    {
        ArrayList<ConnectFourMove> moves = node.game.getAvailableMoves(player);
        node.children = new ArrayList<MonteCarloSearchTreeNode>();
        // Check for winning move (prevents stupid game states)
        boolean foundWinningMove = false;
        for(ConnectFourMove move : moves)
        {
            if(move == null)
                continue;
            if(node.game.testMove(move))
            {
                foundWinningMove = true;
                node.children.add(new MonteCarloSearchTreeNode(node, move));
                break;
            }
        }

        if(!foundWinningMove)
        {
            // If no winning move found, add all possible children
            for(ConnectFourMove move : moves)
            {
                if(move == null)
                    continue;
                node.children.add(new MonteCarloSearchTreeNode(node, move));
            }
        }
    }

    public void generateChildren()
    {
        if(needToGenerateChildren())
            generateChildren(this, move.player().opposite());
    }

    private boolean needToGenerateChildren()
    {
        return children == null;
    }

    public void updateWithWin()
    {
        ++wins;
        ++possibilities;
        if(parent != null)
            parent.updateWithWin();
    }

    public void updateWithLoss()
    {
        ++possibilities;
        if(parent != null)
            parent.updateWithLoss();
    }

    public double score()
    {
        return (double) wins / (double) possibilities;
    }

    public int wins()
    {
        return wins;
    }

    public int possibilities()
    {
        return possibilities;
    }

    public ArrayList<MonteCarloSearchTreeNode> children()
    {
        return children;
    }

    public MonteCarloSearchTreeNode parent()
    {
        return parent;
    }

    public ConnectFourMove move()
    {
        return move;
    }

}
