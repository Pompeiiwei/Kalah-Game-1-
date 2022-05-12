package player;

import containers.House;
import containers.Store;

public class Player {
    private final House [] playersHouses;
    private final Store playerStore;
    private final int numberOfHouses;

    public Player(int numberOfHouses,int seedsNumbersHouse,int seedsNumbersStore) {
        this.numberOfHouses = numberOfHouses;
        this.playersHouses = new House[this.numberOfHouses];
        for (int i = 0; i <this.numberOfHouses ; i++) {
            playersHouses[i] = new House(seedsNumbersHouse);
        }
        this.playerStore = new Store(seedsNumbersStore);
    }

    public House[] getPlayersHouses() {
        return playersHouses;
    }

    public Store getPlayerStore() {
        return playerStore;
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < numberOfHouses; i++) {
            score += playersHouses[i].getSeedsCount();
        }
        score += playerStore.getSeedsCount();
        return score;
    }

}

