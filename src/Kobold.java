import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Kobold
{
    private Kobold mother, father;
    private String name;
    private int ageMonth=0, ageYear=0, strength, dexterity, constitution, intelligence, faith, charisma, health, morale, mana=0, inventorySize, total, luck=1, breedingCooldown = 7;
    private double joy=0, power=0, peace=0, hunger=0, oldAgeRisk;
    private Random rng = new Random();
    private String color;
    private boolean male, wounded=false, poisoned=false, dead=false, fertile=false;
    private Aspects majorAspect, minorAspect;
    private Map<Skills, Integer> skillMap = new HashMap<>();
    private Map<Skills, Integer> skillExpMap = new HashMap<>();
    private Map<String, Integer> statExpMap = new HashMap<>();
    private Map<Resources, Integer> inventory = new HashMap<>();
    private Map<String, Equipment> equipedItems = new HashMap<>();
    {
	equipedItems.put("RHand", null);
	equipedItems.put("LHand", null);
	equipedItems.put("Back", null);
	equipedItems.put("Body", null);
	equipedItems.put("Head", null);
    }


    public void increaseAge(){
	if(!dead){
	    ageMonth += 1;
	    if(ageMonth == 7){
		ageMonth = 0;
		ageYear += 1;
	    }
	    if(breedingCooldown > 0){
		breedingCooldown--;
		if (breedingCooldown == 0){fertile=true;}
	    }
	    if(ageYear >= 2 && ageMonth == 0){
		switch(ageYear){
		    case 2:
			oldAgeRisk += 2.2;
			break;
		    case 6:
		    case 3:
			oldAgeRisk += 13.6;
			break;
		    case 4:
		    case 5:
			oldAgeRisk += 34.1;
			break;
		}
	    }
	    if((wounded || poisoned) && (ageYear > 4 || health <= 3) && !(ageMonth%2==0)){
		switch (ageYear){
		    case 5: if(rng.nextInt(21) + 5 > constitution + health){
			if(wounded) {
			    death("a terrible wound");
			} else death("poisoning");
		    }
		    else{
			incStat(Stats.CONSTITUTION);
			wounded = false;
		    }
			break;
		    case 6: if(rng.nextInt(21) + 10 > constitution + health){
			if(wounded) {
			    death("a terrible wound");
			} else death("poisoning");
		    }else{
			incStat(Stats.CONSTITUTION);
			wounded = false;
		    }
			break;
		    case 7:
			if(wounded) {
			    death("a terrible wound");
			} else death("poisoning");
			break;
		    default: if(rng.nextInt(21) > constitution + health){
			if(wounded) {
			    death("a terrible wound");
			} else death("poisoning");
		    }
		    else if(rng.nextInt(100) > 50){ 			//CHANGE TO BE BASED ON MOOD!!!!
			incStat(Stats.CONSTITUTION);
			wounded = false;
		    }
			break;
		}
	    }
	    else if((wounded || poisoned) && ageMonth%2==0 && rng.nextInt(21) > constitution - ((constitution/2) - health)){
		if(wounded){
		    takeDamage(1,"wounded");
		}
		else{
		    takeDamage(1,"poison");
		}
	    }
	    if(rng.nextInt(101) < oldAgeRisk){
		death("old age");
	    }
	}
    }

    public boolean pickUp(Resources res, int amount){
	int usedSpace=0;
	for (Map.Entry<Resources, Integer> resource : inventory.entrySet()) {
	    usedSpace += resource.getValue();
	}
	for (Map.Entry<String, Equipment> equipment : equipedItems.entrySet()) {
	    if(equipment.getValue() != null){
		usedSpace++;
	    }
	}
	if (usedSpace + amount > inventorySize){
	    return false;
	}else{
	    if(inventory.containsKey(res)){
		int newVal = inventory.get(res);
		newVal += amount;
		inventory.replace(res, newVal);
	    }else{
		inventory.put(res, amount);
	    }
	    return true;
	}
    }

    private void death(String reason){
	dead = true;
	//System.out.print("\n" + name + " has tragically passed away from " + reason + " :<");
	//print death :<
    }

    private void takeDamage(int amount, String source){
	health -= amount;
	// Print damage source
	if(health <= 0)
	    switch (source){
		case "wound":
		    death("a terrible wound");
		    break;
		case "poison":
		    death("a bad poisoning");
		    break;
		case "hunger":
		    death("starvation");
		    break;
		default:
		    death("an attack by " + source);
		    break;
	    }
    }

    private void incStat(Stats stat){
	switch (stat){
	    case STRENGTH:
		strength += 1;
		break;
	    case DEXTERITY:
		dexterity += 1;
		break;
	    case CONSTITUTION:
		constitution += 1;
		break;
	    case INTELLIGENCE:
		intelligence += 1;
		break;
	    case FAITH:
		faith += 1;
		break;
	    case CHARISMA:
		charisma += 1;
		break;
	}
	updateTotal();
	//Post stat increase in chat!
    }

    private void updateTotal(){
	total = strength + dexterity + constitution + intelligence + faith + charisma;
    }

    public int getTotal() {
	updateTotal();
	return total;
    }
    public int getStat(char s){
	switch (s){
	    case 's': return strength;
	    case 'd': return dexterity;
	    case 'c': return constitution;
	    case 'i': return intelligence;
	    case 'f': return faith;
	    case 'h': return charisma;
	    case 'l': return luck;
	}
	return 0;
    }

	public String getColor(){
		return color;
	}

    public Equipment getEquips(String slot){
	return equipedItems.get(slot);
    }

    public boolean equip(Equipment equip){
	switch (equip.getType()){
	    case HELMET:
		if(getEquips("Head") == null){
		    equipedItems.put("Head", equip);
		    return true;
		}
		break;
	    case ARMOUR:
		if(getEquips("Body") == null){
		    equipedItems.put("Body", equip);
		    return true;
		}
		break;
	    case BACKPACK:
		if(getEquips("Back") == null){
		    equipedItems.put("Back", equip);
		    return true;
		}
		break;
	    default:
		if(getEquips("RHand") == null){
		    equipedItems.put("RHand", equip);
		    return true;
		}
		else if(getEquips("LHand") == null){
		    equipedItems.put("LHand", equip);
		    return true;
		}
	}
	if(isInventoryFull()){
	    return false;
	}
	else{
	    //pickUp(equip, 1);
	    return  true;
	}
    }

    public int getSkillLVL(Skills skill){
	if(skillMap != null && skillMap.containsKey(skill)){return skillMap.get(skill);}
	else return 0;
    }

    private boolean isInventoryFull(){		//IMPLEMENT WITH INVBNTORY REWORK
	return true;
    }

    public  HashMap getSkillMap(){
	return (HashMap) skillMap;
    }

    public String getName(){
	return name;
    }
    public boolean getSex(){return male;}
    public boolean getDead(){return dead;}
    public boolean getFertile(){return fertile;}
    public Aspects getMajorAspect(){return majorAspect;}
    public Aspects getMinorAspect(){return minorAspect;}
    public Aspects[] getAspects(){return new Aspects[] {majorAspect, minorAspect};}
    public void setFertile(){
	fertile = false;
	breedingCooldown = 5;
    }

    @Override public String toString() {
	updateTotal();
	return "\n ---------------" + name + "---------------\n color: " + color + "\n Aspecs: "+ majorAspect.toString() + " " + minorAspect + "\n age: " + ageYear + " years and " + ageMonth + " months \n str: " + strength + "\n dex: " + dexterity + "\n con: " +
	       constitution + "\n int: " + intelligence + "\n fai: " + faith + "\n cha: " + charisma + "\n Luck: " + luck + "\n health=" +
	       health + ", morale=" + morale + ", mana=" + mana + "\n inventorySize=" + inventorySize +
	        "\n joy=" + joy + ", power=" + power + ", peace=" + peace + ", hunger=" + hunger +
	       "\n male=" + male + ", wounded=" + wounded + ", poisoned=" + poisoned + ", dead=" + dead +
	       ", skillMap=" + skillMap + "\n -----------------------------------";
    }

    public Kobold(String name, int str, int dex, int con, int intel, int fai, int cha, boolean sex, Aspects major, Aspects minor){
	this.name = name;
	this.male = sex;
	this.strength = str;
	this.dexterity = dex;
	this.constitution = con;
	this.intelligence = intel;
	this.faith = fai;
	this.charisma = cha;
	majorAspect = major;
	minorAspect = minor;
	if(minorAspect != Aspects.BROWN){
	    if(sex){
		color = ":BLUE_SQUARE:";
	    }
	    else{
		color = ":BLUE_CIRCLE:";
	    }
	}
	else if(sex){
	    color = ":" + majorAspect.toString() + "_SQUARE:";
	}
	else{
	    color = ":" + majorAspect.toString() + "_CIRCLE:";
	}
	luck = 70 - (strength + dexterity + constitution + intelligence + faith + charisma);
	if(luck <= 0) luck = 1;
	health = constitution/2;
	mana = intelligence/2;
	morale = faith/2;
	inventorySize = constitution*3/4;

	statExpMap.put("str", 0);
	statExpMap.put("dex", 0);
	statExpMap.put("con", 0);
	statExpMap.put("int", 0);
	statExpMap.put("fai", 0);
	statExpMap.put("cha", 0);

	skillExpMap.put(Skills.SWORD, 0);
	skillExpMap.put(Skills.AXE, 0);
	skillExpMap.put(Skills.BOW, 0);
	skillExpMap.put(Skills.MAGIC, 0);
	skillExpMap.put(Skills.HEALING, 0);
	skillExpMap.put(Skills.INSPIRE, 0);
	skillExpMap.put(Skills.SHIELD, 0);
	skillExpMap.put(Skills.MINING, 0);
	skillExpMap.put(Skills.FARMING, 0);
	skillExpMap.put(Skills.SMITHING, 0);
	skillExpMap.put(Skills.SNEAKING, 0);
	skillExpMap.put(Skills.FORAGING, 0);
	skillExpMap.put(Skills.COOKING, 0);
	skillExpMap.put(Skills.BUILDING, 0);
	skillExpMap.put(Skills.ALCHEMY, 0);
	skillExpMap.put(Skills.CRAFTING, 0);
	skillExpMap.put(Skills.PRAYING, 0);
	skillExpMap.put(Skills.SPEECH, 0);
	skillExpMap.put(Skills.PLANNING, 0);
}
public void setParents(Kobold mom, Kobold dad){
	mother = mom;
	father = dad;
}
//              Exp to reach lvl:   1    2    3    4    5    6    7    8    9   10
private int[] expPerSkillLevel = {100, 125, 155, 195, 245, 305, 385, 475, 595, 745};

public void awardSkillExp(Skills skill){
    int lvl = skillMap.getOrDefault(skill, 0);
    int exp = skillExpMap.get(skill);
    exp += 15 + rng.nextInt(11);
    if(rng.nextInt(expPerSkillLevel[lvl]) <= exp){
	skillMap.put(skill, lvl + 1);
	skillExpMap.put(skill, 0);
	//				PRINT SKILL LEVEL UP!
    }
    else{
	skillExpMap.put(skill, exp);
    }
}

public  void awardStatExp(String stat){

}

}
