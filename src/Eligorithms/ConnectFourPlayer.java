package Eligorithms;

public class ConnectFourPlayer
{
    public enum Owner
    {
        PLAYER_INVALID,
        PLAYER_AI,
        PLAYER_HUMAN
    }
    
    private Owner owner;
    private static final ConnectFourPlayer INVALID = new ConnectFourPlayer(Owner.PLAYER_INVALID);
    private static final ConnectFourPlayer AI = new ConnectFourPlayer(Owner.PLAYER_AI);
    private static final ConnectFourPlayer HUMAN = new ConnectFourPlayer(Owner.PLAYER_HUMAN);
    
    private ConnectFourPlayer(Owner _owner)
    {
        owner = _owner;
    }
    
    public static ConnectFourPlayer getInvalid()
    {
        return INVALID;
    }
    
    public static ConnectFourPlayer getAI()
    {
        return AI;
    }
    
    public static ConnectFourPlayer getHuman()
    {
        return HUMAN;
    }
    
    public Owner owner()
    {
        return owner;
    }
}
