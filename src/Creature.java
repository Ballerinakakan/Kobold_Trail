import java.util.HashMap;
import java.util.Map;

public class Creature {
    protected boolean wounded, poisoned, dead;
    protected int strength, dexterity, constitution, intelligence, faith, charisma, health, morale, mana=0;
    protected String name;
    protected Aspects majorAspect, minorAspect;
    protected Map<String, Equipment> equippedItems = new HashMap<>();

    protected void death(){
        dead = true;
        //print name is dead!
    }

    private void takeDamage(int amount, String source){
        health -= amount;
        // Print damage source
    }
}
