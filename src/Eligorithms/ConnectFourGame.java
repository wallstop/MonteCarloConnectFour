package Eligorithms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayList;

public class ConnectFourGame
{

    // Board constraints
    private static final int BOARD_WIDTH = 7;
    private static final int BOARD_HEIGHT = 6;
    private static final int NUM_TO_WIN = 4;

    // Member variables
    private boolean finished = false;
    private ConnectFourPlayer winningPlayer = ConnectFourPlayer.getInvalid();
    private LinkedList<ConnectFourPlayer> board[];
    private LinkedList<ConnectFourMove> moveList;

    // Internal helpers
    private static final int[] X_DIR = { 0, 1, 1, 1, 0, -1, -1, -1 };
    private static final int[] Y_DIR = { 1, 1, 0, -1, -1, -1, 0, 1 };
    private static final Direction[] ITERABLE_DIRECTIONS = { Direction.north(),
            Direction.northEast(), Direction.east(), Direction.southEast() };

    public ConnectFourGame(LinkedList<ConnectFourMove> moves)
    {
        initFromMoves(moves);
    }

    public ConnectFourGame(MonteCarloSearchTreeNode leafNode)
    {
        // Walk up the tree
        LinkedList<ConnectFourMove> moves = new LinkedList<ConnectFourMove>();
        MonteCarloSearchTreeNode temp = leafNode;
        while(temp != null)
        {
            moves.add(temp.move());
            temp = temp.parent();
        }

        Collections.reverse(moves);

        initFromMoves(moves);
    }

    public ConnectFourGame(ConnectFourGame game, ConnectFourMove move)
    {
        deepCopyBoard(game.board);
        deepCopyMoves(game.moveList);
        addMove(move);
    }

    public ConnectFourGame(ConnectFourGame game)
    {
        deepCopyBoard(game.board);
        deepCopyMoves(game.moveList);
        winningPlayer = game.winningPlayer;
        finished = game.finished;
    }

    public ConnectFourGame()
    {
        init();
    }

    private void deepCopyMoves(LinkedList<ConnectFourMove> moves)
    {
        for(ConnectFourMove move : moves)
            moveList.add(move);
    }

    private void deepCopyBoard(LinkedList<ConnectFourPlayer>[] _board)
    {
        // Deep copy...
        init();
        if(_board.length == BOARD_WIDTH)
        {
            for(int i = 0; i < BOARD_WIDTH; ++i)
            {
                for(ConnectFourPlayer player : _board[i])
                    board[i].add(player);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void init()
    {
        board = new LinkedList[BOARD_WIDTH];
        moveList = new LinkedList<ConnectFourMove>();
        for(int i = 0; i < BOARD_WIDTH; ++i)
        {
            board[i] = new LinkedList<ConnectFourPlayer>();
        }
    }

    private void initFromMoves(LinkedList<ConnectFourMove> moves)
    {
        init();
        deepCopyMoves(moves);
        for(ConnectFourMove move : moves)
        {
            if(move == null)
                continue;
            board[move.getColumn()].add(move.player());
        }
    }

    /*
     * Returns: True if the move won False if it did not
     */
    public boolean addMove(ConnectFourMove move)
    {
        // Update board
        if(move == null)
            return finished;
        finished = finished || internalCheckMove(move);
        board[move.getColumn()].add(move.player());
        moveList.add(move);

        if(finished)
            winningPlayer = move.player();

        return finished;
    }

    public boolean testMove(ConnectFourMove move)
    {
        return internalCheckMove(move);
    }

    private boolean internalCheckMove(ConnectFourMove move)
    {
        if(move == null)
            return finished;
        // Grab (x,y) coordinates
        final int x = move.getColumn();
        final int y = board[x].size();

        boolean ret = false;
        for(Direction dir : ITERABLE_DIRECTIONS)
            ret = ret || checkNodeHelper(x, y, dir, move.player());

        return ret;
    }

    private boolean checkNodeHelper(int x, int y, Direction direction, ConnectFourPlayer player)
    {
        final int leftIndex = direction.state().ordinal();
        final int rightIndex = direction.opposite().state().ordinal();
        final int left = checkNode(x + X_DIR[leftIndex], y + Y_DIR[leftIndex], direction, player, 0);
        final int right = checkNode(x + X_DIR[rightIndex], y + Y_DIR[rightIndex],
                direction.opposite(), player, 0);
        return left + right + 1 >= NUM_TO_WIN;
    }

    private int checkNode(int x, int y, Direction direction, ConnectFourPlayer player, int count)
    {
        if(count == (NUM_TO_WIN - 1)) // bail early
            return count;

        final int index = direction.state().ordinal();
        // If we're in the board and found a matching player, recurse
        if(x >= 0 && x < board.length && board[x].size() > y && y >= 0 && board[x].get(y) == player)
            return checkNode(x + X_DIR[index], y + Y_DIR[index], direction, player, ++count);

        return count;
    }

    public ArrayList<ConnectFourMove> getAvailableMoves()
    {
        return getAvailableMoves(ConnectFourPlayer.getInvalid());
    }

    public ArrayList<ConnectFourMove> getAvailableMoves(ConnectFourPlayer player)
    {
        ArrayList<ConnectFourMove> ret = new ArrayList<ConnectFourMove>();
        if(finished)
        {
            return ret;
        }

        for(int i = 0; i < BOARD_WIDTH; ++i)
        {
            if(board[i].size() < BOARD_HEIGHT)
            {
                ret.add(new ConnectFourMove(i, player));
            }
        }

        return ret;
    }

    public ConnectFourMove getLastMove()
    {
        return (moveList != null && moveList.size() > 0) ? moveList.getLast() : null;
    }

    public boolean isSolved()
    {
        return finished;
    }

    public ConnectFourPlayer winner()
    {
        return winningPlayer;
    }

    public int height()
    {
        return BOARD_HEIGHT;
    }

    public int width()
    {
        return BOARD_WIDTH;
    }

    public LinkedList<ConnectFourPlayer>[] board()
    {
        return board;
    }

    public int maxHeightOfCurrentBoard()
    {
        int ret = 0;
        for(LinkedList<ConnectFourPlayer> list : board)
        {
            if(list.size() > ret)
            {
                ret = list.size();
            }
        }
        return ret;
    }

    public boolean isFull()
    {
        if(finished)
            return true;

        boolean ret = true;
        for(LinkedList<ConnectFourPlayer> list : board)
        {
            if(list == null)
                continue;
            ret = ret && (list.size() == BOARD_HEIGHT);
        }
        return ret;
    }
}
