package GUI;

import Eligorithms.*;

import java.util.HashMap;

public class DrawingHandyman {

    private final ConnectFourInterfacePanel panel = ConnectFourInterfaceMenu.interfacePanel;
    private final HashMap<Integer, Integer> tokens = new HashMap<Integer, Integer>();
    private final int maxTokens;

    // Algorithmic variables
    private ConnectFourGame game = new Eligorithms.ConnectFourGame();
    private MonteCarloAI ai_MCSP = /*new MonteCarloSearchPure(); */ new MonteCarloSearchTree();

    public DrawingHandyman(int maxTokens) {
        this.maxTokens = maxTokens;
        for (int i = 0; i < panel.columns; i++) {
            tokens.put(i, 0);
        }
    }

    // Wipe the board
    public void requestReset() {
        // Reset internal representation of game
        for (int i = 0; i < panel.columns; i++) {
            tokens.put(i, 0);
        }

        // Reset algorithmic variables
        game = new Eligorithms.ConnectFourGame();
        ai_MCSP = new MonteCarloSearchTree();

        // Wipe the board visually
        panel.resetBoard();
    }

    public void requestColumnHighlight(int x, int y) {
        // Clear any previously lit columns location
        panel.eraseOverlays();

        // Catch drag events that move too far
        if (x < panel.getWidth() && y < panel.getHeight() && x >= 0 && y >= 0) {

            // Convert pixels to coords
            int column = x / panel.columnWidth + 1;
            panel.highlightColumn(column);
        }
    }

    // Translate and request a move event to be drawn
    public void requestMove(int x, int y, boolean isPlayer) {
        // Clear any previously lit columns location
        panel.eraseOverlays();

        int column = x / panel.columnWidth;

        System.out.printf("Requesting move at %d (%d, %d). ", column, x, y);
        int i = tokens.get(column);
        System.out.printf("Currently %d.", i);
        System.out.println("");

        if (i < maxTokens) {
            i++;
            tokens.put(column, i);

            panel.paintMove(column, i, isPlayer);

            if (isPlayer) {
                // Player moves require responses from the algorithm
                generateResponse(column);
            }
        }
    }

    // Check to ensure board has moves available     
    private boolean areMovesLeft() {
        if (game.isSolved() || game.isFull()) {
            switch (game.winner().owner()) {
                case PLAYER_HUMAN:
                    System.out.println("Player wins!");
                    break;
                case PLAYER_AI:
                    System.out.println("AI wins!");
                    break;
                case PLAYER_INVALID:
                default:
                    System.out.println("No one won!");
                    break;
            }
            return false;
        } else {
            return true;
        }
    }

    private void generateResponse(int column) {
        game.addMove(new ConnectFourMove(column, ConnectFourPlayer.getHuman()));

        if (areMovesLeft()) {

            // Add the next move
            ConnectFourMove nextMove = ai_MCSP.determineMove(game);

            game.addMove(nextMove);

            // Update internal representation of game
            column = nextMove.getColumn();
            int i = tokens.get(column);
            i++;
            tokens.put(column, i);

            // Paint the AI move
            panel.paintMove(column, i, false);

            // Write the rationale for the decision
            ConnectFourInterfaceMenu.updatePercentages(ai_MCSP.getProbabilities());

            if (!areMovesLeft()) {
                ConnectFourInterfaceMenu.endGame();
            }
        } else {
            ConnectFourInterfaceMenu.endGame();
        }
    }
}
