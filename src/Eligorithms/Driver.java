package Eligorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Driver
{

//    public static void main(String[] args)
//    {
//        ConnectFourGame game = new ConnectFourGame();
//        MonteCarloSearchPure search = new MonteCarloSearchPure();
//        Random rGen = new Random();
//        while(!game.isSolved())
//        {
//            ArrayList<ConnectFourMove> moves = game
//                    .getAvailableMoves(ConnectFourPlayer.getHuman());
//            final int index = rGen.nextInt(moves.size());
//            if(game.addMove(moves.get(index)))
//                break;
//            ConnectFourMove nextMove = search.determineMove(game);
//            assert(nextMove != null);
//            game.addMove(nextMove);
//        }
//
//        if(game.winner() == ConnectFourPlayer.getHuman())
//            System.out.println("You won :)");
//        else if(game.winner() == ConnectFourPlayer.getAI())
//            System.out.println("You lost :(");
//        else
//            System.out.println("What???");
//
//        for(LinkedList<ConnectFourPlayer> list : game.board())
//        {
//            for(ConnectFourPlayer player : list)
//            {
//                switch(player.owner())
//                {
//                case PLAYER_AI:
//                    System.out.print("1 ");
//                    break;
//                case PLAYER_HUMAN:
//                    System.out.print("2 ");
//                    break;
//                case PLAYER_INVALID:
//                    System.out.print("0 ");
//                    break;
//                }
//            }
//            System.out.println();
//        }
//
//    }

}
