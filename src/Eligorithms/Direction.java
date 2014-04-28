package Eligorithms;

public class Direction
{
    public enum State
    {
        DIRECTION_NORTH, 
        DIRECTION_NORTH_EAST, 
        DIRECTION_EAST, 
        DIRECTION_SOUTH_EAST, 
        DIRECTION_SOUTH, 
        DIRECTION_SOUTH_WEST,
        DIRECTION_WEST, 
        DIRECTION_NORTH_WEST
    }
    
    private State state;
    private static final Direction NORTH = new Direction(State.DIRECTION_NORTH);
    private static final Direction NORTH_EAST = new Direction(State.DIRECTION_NORTH_EAST);
    private static final Direction EAST = new Direction(State.DIRECTION_EAST);
    private static final Direction SOUTH_EAST = new Direction(State.DIRECTION_SOUTH_EAST);
    private static final Direction SOUTH = new Direction(State.DIRECTION_SOUTH);
    private static final Direction SOUTH_WEST = new Direction(State.DIRECTION_SOUTH_WEST);
    private static final Direction WEST = new Direction(State.DIRECTION_WEST);
    private static final Direction NORTH_WEST = new Direction(State.DIRECTION_NORTH_WEST);
    
    private Direction(State _state)
    {
        state = _state;
    }
    
    public Direction opposite()
    {
        switch(state)
        {
        case DIRECTION_NORTH:
            return SOUTH;
        case DIRECTION_NORTH_EAST:
            return SOUTH_WEST;
        case DIRECTION_EAST:
            return WEST;
        case DIRECTION_SOUTH_EAST:
            return NORTH_WEST;
        case DIRECTION_SOUTH:
            return NORTH;
        case DIRECTION_SOUTH_WEST:
            return NORTH_EAST;
        case DIRECTION_WEST:
            return EAST;
        case DIRECTION_NORTH_WEST:
            return SOUTH_EAST;   
        default:
            return null;
        }
    }
    
    public static Direction north()
    {
        return NORTH;
    }
    
    public static Direction south()
    {
        return SOUTH;
    }
    
    public static Direction east()
    {
        return EAST;
    }
    
    public static Direction west()
    {
        return WEST;
    }
    
    public static Direction northEast()
    {
        return NORTH_EAST;
    }
    
    public static Direction northWest()
    {
        return NORTH_WEST;
    }
    
    public static Direction southEast()
    {
        return SOUTH_EAST;
    }
    
    public static Direction southWest()
    {
        return SOUTH_WEST;
    }
    
    public State state()
    {
        return state;
    }

}
