package Eligorithms;

public class ConnectFourMove
{

    // column index of move
    private int index;
    // Owning player
    private ConnectFourPlayer player;

    public ConnectFourMove(int _index)
    {
        index = _index;
        player = ConnectFourPlayer.getInvalid();;
    }

    public ConnectFourMove(int _index, ConnectFourPlayer _player)
    {
        index = _index;
        player = _player;
    }

    public int getColumn()
    {
        return index;
    }

    public ConnectFourPlayer player()
    {
        return player;
    }

    public void setPlayer(ConnectFourPlayer _player)
    {
        player = _player;
    }
}
