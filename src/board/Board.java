package board;

import com.qualitascorpus.testsupport.IO;
import player.Player;

public class Board {
    private final IO io;
    private final Player[] players;
    private final boolean vertical;

    public Board(IO io, Player[] players, boolean vertical) {
        this.io = io;
        this.players = players;
        this.vertical = vertical;
    }

    public void printBoard() {
        if (vertical)
            printBoardVertically();

        if (!vertical)
            printBoardHorizontally();
    }

    public void printBoardVertically(){
        String[] player1Field = playerField(1);
        String player1Store = playerStore( 1);
        String[] player2Field = playerField(2);
        String player2Store = playerStore(2);
        io.println("+---------------+");
        io.println("|       | P2 " +player2Store+" |");
        io.println("+-------+-------+");
        io.println("| 1["+player1Field[0]+"] | 6["+player2Field[5]+"] |");
        io.println("| 2["+player1Field[1]+"] | 5["+player2Field[4]+"] |");
        io.println("| 3["+player1Field[2]+"] | 4["+player2Field[3]+"] |");
        io.println("| 4["+player1Field[3]+"] | 3["+player2Field[2]+"] |");
        io.println("| 5["+player1Field[4]+"] | 2["+player2Field[1]+"] |");
        io.println("| 6["+player1Field[5]+"] | 1["+player2Field[0]+"] |");
        io.println("+-------+-------+");
        io.println("| P1 "+player1Store+" |       |");
        io.println("+---------------+");
    }

    public void printBoardHorizontally(){
        String[] player1Field = playerField(1);
        String player1Store = playerStore( 1);
        String[] player2Field = playerField(2);
        String player2Store = playerStore(2);
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + player2Field[5] + "] | " +
                "5[" + player2Field[4] + "] | " +
                "4[" + player2Field[3] + "] | " +
                "3[" + player2Field[2] + "] | " +
                "2[" + player2Field[1] + "] | " +
                "1[" + player2Field[0] + "] | " +
                player1Store + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + player2Store + " | " +
                "1[" + player1Field[0] + "] | " +
                "2[" + player1Field[1] + "] | " +
                "3[" + player1Field[2] + "] | " +
                "4[" + player1Field[3] + "] | " +
                "5[" + player1Field[4] + "] | " +
                "6[" + player1Field[5] + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    public String[] playerField (int playerNumber) {
        int[] playerField = new int[players[playerNumber-1].getPlayersHouses().length];
        String[] playerFieldString = new String[players[playerNumber-1].getPlayersHouses().length];
        for (int i = 0; i < players[playerNumber-1].getPlayersHouses().length; i++) {
            playerField[i] = players[playerNumber-1].getPlayersHouses()[i].getSeedsCount();
            if (playerField[i] <= 9){
                playerFieldString[i] = " "+ playerField[i];
            }else{
                playerFieldString[i] = ""+ playerField[i];
            }
        }
        return playerFieldString;
    }

    public String playerStore (int playerNumber) {
        int playerStore ;
        String playerStoreString;
        playerStore = players[playerNumber-1].getPlayerStore().getSeedsCount();
        if(playerStore <= 9){
            playerStoreString = " "+playerStore;
        }else{
            playerStoreString = ""+playerStore;
        }
        return playerStoreString;
    }

    public void printScores() {
        int playerScore1 = players[0].getScore();
        int playerScore2 = players[1].getScore();
        io.println("\tplayer 1:" + playerScore1);
        io.println("\tplayer 2:" + playerScore2);
        if (playerScore1 > playerScore2)
            io.println("Player 1 wins!");
        if (playerScore2 > playerScore1)
            io.println("Player 2 wins!");
        if (playerScore1 == playerScore2)
            io.println("A tie!");
    }
}