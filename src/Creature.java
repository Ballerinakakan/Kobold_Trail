import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Creature implements Serializable {
    protected Tile location;
    protected CreatureType type;
    protected boolean wounded, poisoned, dead;
    protected int strength, dexterity, constitution, intelligence, faith, charisma, health, morale, mana=0;
    protected String birthName;
    protected Aspects majorAspect, minorAspect;
    protected Map<String, Equipment> equippedItems = new HashMap<>();

    protected void death(){
        dead = true;
        //print name is dead!
    }

    protected Tile getLocation(){return location;}
    protected void setLocation(Tile tile){
        location = tile;
    }

    private void takeDamage(int amount, String source){
        health -= amount;
        // Print damage source
    }

    public String atLocation(){
        return birthName + " (" + type.toString().toLowerCase() + ")";
    }
}
