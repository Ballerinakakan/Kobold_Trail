import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Creature implements Serializable {
    protected Location location;
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

    protected Location getLocation(){return location;}

    protected void setLocation(Location loc){
        location = loc;
    }


    private void takeDamage(int amount, String source){
        health -= amount;
        // Print damage source
    }

    public String atLocation(){
        return birthName + " (" + type.toString().toLowerCase() + ")";
    }
}
