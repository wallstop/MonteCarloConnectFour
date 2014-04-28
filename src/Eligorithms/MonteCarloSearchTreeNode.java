package Eligorithms;

import java.util.ArrayList;

public class MonteCarloSearchTreeNode
{
    private ArrayList<MonteCarloSearchTreeNode> children;
    private final MonteCarloSearchTreeNode parent;    
    private final ConnectFourGame game;
    private final ConnectFourMove move;
    private int wins;
    private int possibilities;
    
    public MonteCarloSearchTreeNode(MonteCarloSearchTreeNode _parent)
    {
        parent = _parent;
        wins = 0;
        possibilities = 0;
        move = null;
        game = new ConnectFourGame();
        generateChildren();
    }
    
    public MonteCarloSearchTreeNode(MonteCarloSearchTreeNode _parent, ConnectFourMove _move)
    {
        wins = 0;
        possibilities = 0;
        parent = _parent;
        move = _move;
        game = new ConnectFourGame(this);
        if(game.isSolved() || game.isFull())
        {
            children = new ArrayList<MonteCarloSearchTreeNode>();    // better than null
            if(game.winner() == ConnectFourPlayer.getAI())
                updateWithWin();
            else
                updateWithLoss();
        }         
        else
        {
            generateChildren();
        }
    }
    
    private void generateChildren()
    {
        ArrayList<ConnectFourMove> moves = game.getAvailableMoves(ConnectFourPlayer.getAI());
        children = new ArrayList<MonteCarloSearchTreeNode>();
        for(ConnectFourMove move : moves)
            children.add(new MonteCarloSearchTreeNode(this, move));      
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
        return (double)wins / (double)possibilities;
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
