package containers;

public class House extends Container{

    public House(int initialNumberOfSeeds) {
        this.seedsCount = initialNumberOfSeeds;

    }

    public int takeSeeds(boolean simulation){
        if (simulation){
            return takeSeedsSimulation();
        }else{
            return realTakeSeeds();
        }
    }

    public int takeSeedsSimulation(){
        int withdrawSeeds = this.seedsCount;
        this.seedsCountSimulation = 0;
        return withdrawSeeds;
    }

    public int realTakeSeeds(){
        int withdrawSeeds = this.seedsCount;
        this.seedsCount = 0;
        return withdrawSeeds;
    }

    public int sowSeedsHouse(int houseNumber,boolean simulation,int seedsPerMove){
        if (simulation){
            sowSeedsSimulation(seedsPerMove);
        }else{
            sowSeeds(seedsPerMove);
        }
        houseNumber++;
        return houseNumber;
    }
}
