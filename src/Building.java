import java.util.HashSet;

public abstract class Building
{
    private BuildingType bType = BuildingType.WALLS; //lol change this!!!!
    private int strucInteg = 0;

    //UNSURE IF THESE SHOULD BE USED
    private int progress = 0;
    private int progressReq = 0;
    private int level = 0;
    private int storageSize = 0;
    private int capacity = 0;
    private HashSet<Kobold> occupants = new HashSet<Kobold>();

    public Building(BuildingType bt){
        bType = bt;
    }

    public String toString(){
        return bType.toString().toLowerCase();
    }

    public int getStrucInteg() {
	return strucInteg;
    }

    public void setStrucInteg(int strucInteg) {
	this.strucInteg = strucInteg;
    }



    //UNSURE IF ANYTHING BELOW SHOULD BE USED
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    public int getStorageSize() {
	return storageSize;
    }

    public void setStorageSize(int storageSize) {
	this.storageSize = storageSize;
    }

    public int getCapacity() {
	return capacity;
    }

    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }

    public HashSet<Kobold> getOccupants() {
	return occupants;
    }

    public void setOccupants(HashSet<Kobold> occupants) {
	this.occupants = occupants;
    }
};
