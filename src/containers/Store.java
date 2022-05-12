package containers;

public class Store extends Container{

    public Store(int initialNumberOfSeeds) {
        this.seedsCount = initialNumberOfSeeds;
    }

    public void sowSeedsStore(boolean simulation,int seedsPerMove){
        if (simulation){
            sowSeedsSimulation(seedsPerMove);
        }else{
            sowSeeds(seedsPerMove);
        }
    }
}
