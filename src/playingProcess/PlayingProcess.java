package playingProcess;

import board.Board;
import com.qualitascorpus.testsupport.IO;
import player.Player;

public class PlayingProcess {
    private final IO io;
    private final int numberOfPlayers = 2;
    private final int numberOfHouses = 6;
    private final int initialNumberOfSeedsHouse = 4;
    private final int initialNumberOfSeedsStore = 0;
    private final int numberPerMove = 1;
    private final Player[] players = new Player[numberOfPlayers];
    private Player playerInOperation;
    private Player player;
    private Player opposite;
    private boolean anotherMove;
    private boolean botCanMove;
    private Board board;

    public PlayingProcess(IO io, boolean vertical) {
        this.io = io;
        board= new Board(io, players, vertical);
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player(numberOfHouses,initialNumberOfSeedsHouse,initialNumberOfSeedsStore);
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean notAnotherMove() {
        return !anotherMove;
    }

    public void botMove(){
        botCanMove = false;
        int playerNumber = 2;
        playerInOperation = players[playerNumber - 1];
        // decide which move the bot should make
        if (!botAnotherMove(playerNumber)){

            if (!botCapture(playerNumber))
                lastChoiceOfBotMove(playerNumber);
        }
    }

    public boolean botAnotherMove(int playerNumber){
        for (int i = 0; i <numberOfHouses ; i++) {
            int houseIndex = i;
            int houseNumberAfterSowing = houseNumberAfterEachSimulation(playerNumber, houseIndex);
            // judge whether the bot can take "another move", if so, make the move
            if ( houseNumberAfterSowing == numberOfHouses + 1){
                if (player.equals(playerInOperation)) {
                    botCanMove = true;
                    int houseNumberToMove = i + 1;
                    io.println("Player P"+ playerNumber +" (Robot) chooses house #" + houseNumberToMove +
                            " because it leads to an extra move");
                    move(playerNumber,houseNumberToMove);
                    break;
                }
            }
        }
        return botCanMove;
    }

    public boolean botCapture(int playerNumber){
        for (int i = 0; i < numberOfHouses ; i++) {
            int houseIndex = i;
            int houseNumberAfterSowing = houseNumberAfterEachSimulation(playerNumber, houseIndex);
            // judge whether the bot can take "capture", if so, make the move
            if (player.equals(playerInOperation)
                    && houseNumberAfterSowing <= numberOfHouses
                    && opposite.getPlayersHouses()[numberOfHouses - houseNumberAfterSowing].getSeedsCountSimulation()!= 0
                    && player.getPlayersHouses()[houseNumberAfterSowing - 1].getSeedsCountSimulation() == 1 // Capture
            ) {
                int houseNumberToMove = i + 1;
                botCanMove = true;
                io.println("Player P"+ playerNumber +" (Robot) chooses house #"+houseNumberToMove+
                        " because it leads to a capture");
                move(playerNumber,houseNumberToMove);
                break;
            }
        }
        return botCanMove;
    }

    public void lastChoiceOfBotMove(int playerNumber){
        for (int i = 0; i < numberOfHouses; i++) {

            int seedsToMoveSimulation2 =  playerInOperation.getPlayersHouses()[i].getSeedsCount();
            if (seedsToMoveSimulation2 > 0){
                int houseNumber = i+1;
                io.println("Player P"+ playerNumber +" (Robot) chooses house #"+houseNumber+
                        " because it is the first legal move");
                move(playerNumber,houseNumber);
                break;
            }
        }
    }

    private int houseNumberAfterEachSimulation(int playerNumber, int houseIndex) {
        initialiseBotSimulation(playerNumber);
        return processOfSowSeeds(houseIndex,true);
    }

    public void initialiseBotSimulation(int playerNumber){
        player = players[playerNumber - 1];
        opposite = players[oppositePlayer(playerNumber)-1];

        for (int j = 0; j < numberOfHouses; j++) {
            player.getPlayersHouses()[j].setSeedsCountSimulation();
            opposite.getPlayersHouses()[j].setSeedsCountSimulation();
        }
    }

    public boolean canMove(int playerNumber){
        int allSeedCount = 0;

        for (int i = 0; i < numberOfHouses; i++)
            allSeedCount += players[playerNumber-1].getPlayersHouses()[i].getSeedsCount();

        return allSeedCount!=0;

    }

    public void move(int playerNumber,int houseNumber){
        anotherMove = false;
        playerInOperation = players[playerNumber - 1];
        int houseIndex = houseNumber - 1;
        player = players[playerNumber - 1];
        opposite = players[oppositePlayer(playerNumber)-1];
        int seedsToMove =  player.getPlayersHouses()[houseIndex].getSeedsCount();
        if (seedsToMove == 0){
            io.println("House is empty. Move again.");
            anotherMove = true;
        }
        // if seeds in the house is not 0 then we can start to sow
        houseNumber = processOfSowSeeds(houseIndex,false);
        anotherMove(houseNumber);
        capture(houseNumber);
        board.printBoard();
    }

    public void anotherMove(int houseNumber){
        if (houseNumber == numberOfHouses + 1){
            if (player.equals(playerInOperation))
                anotherMove = true; // player in operation gets another move
        }
    }

    public void capture(int houseNumber){
        if (player.equals(playerInOperation)
                && houseNumber <= numberOfHouses
                && opposite.getPlayersHouses()[numberOfHouses - houseNumber].getSeedsCount() != 0
                && player.getPlayersHouses()[houseNumber - 1].getSeedsCount() == 1 // Capture
        ) {
            int takeOppositeSeed = opposite.getPlayersHouses()[numberOfHouses - houseNumber].realTakeSeeds();
            int playerHousePoint = player.getPlayersHouses()[houseNumber-1].realTakeSeeds();
            player.getPlayerStore().sowSeeds(takeOppositeSeed + playerHousePoint);
        }
    }

    public int oppositePlayer(int playerNumber){
        if (playerNumber% numberOfPlayers == 0){
            return 1; // if player2 in operation then change to player1
        }else{
            return 2; // if player1 in operation then change to player2
        }
    }

    public int processOfSowSeeds(int houseIndex,boolean simulation){
        int houseNumber = houseIndex + 1;
        int seedsToMove =  player.getPlayersHouses()[houseIndex].takeSeeds(simulation);

        while (seedsToMove > 0){

            while (seedsToMove > 0 && houseNumber < numberOfHouses){
                houseNumber = player.getPlayersHouses()[houseNumber].sowSeedsHouse(houseNumber,simulation,numberPerMove);
                seedsToMove -= numberPerMove;
            }
            if (seedsToMove > 0 && houseNumber == numberOfHouses){// add seeds in player in operation 's Store
                if (player.equals(playerInOperation)){
//                    sowSeedsStore(simulation);
                    player.getPlayerStore().sowSeedsStore(simulation,numberPerMove);
                    seedsToMove -= numberPerMove;
                }
                houseNumber++;
            }
            if (seedsToMove > 0){// change the houses belong to opposite (add seeds to opposite)
                houseNumber = changeSowingToOpposite();
            }
        }
        return houseNumber;
    }

    public int changeSowingToOpposite(){
        Player originalPlayer = player;
        player = opposite;
        opposite = originalPlayer;
        return 0;
    }
}