import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GeneralTesting
{

    private static void buryKobold(){
	for (Map.Entry<Integer, Kobold> dead : koboldGraveyard.entrySet()) {
	    koboldMap.remove(dead.getKey(), dead.getValue());
	}
    }
    private static void hatchKobold(){
	koboldMap.putAll(koboldEggs);
	koboldEggs.clear();
    }
    private static Map<Integer, Kobold> koboldMap = new HashMap<>();
    private static Map<Integer, Kobold> koboldGraveyard = new HashMap<>();
    private static Map<Integer, Kobold> koboldEggs = new HashMap<>();

    public static void main(String[] args) {
	KoboldMaker koboldMaker = new KoboldMaker();
	Random rng = new Random();
	EquipmentCrafter crafter = new EquipmentCrafter();
	Kobold raxis = new Kobold("Raxis", 10, 8, 10, 10, 10, 10, true, Aspects.RED, Aspects.BROWN);
	Kobold defaultKobold = new Kobold("Default", 10, 10, 10, 10, 10, 10, true, Aspects.BROWN, Aspects.BROWN);
	int month = 1, year = 1;

	koboldMap.put(0, raxis);
	koboldMap.put(1, new Kobold("Ringo", 12, 8, 12, 8, 12, 8, true, Aspects.YELLOW, Aspects.BROWN));
	koboldMap.put(2, new Kobold("Neon", 12, 8, 12, 8, 12, 8, true, Aspects.GREEN, Aspects.BROWN));
	koboldMap.put(3, new Kobold("Breen", 12, 8, 12, 8, 12, 8, false, Aspects.BLACK, Aspects.BROWN));
	koboldMap.put(4, new Kobold("Dronald", 10, 10, 10, 10, 10, 10, false, Aspects.WHITE, Aspects.BROWN));


	int kobID = 5;

	for (int i = 0; i < 10; i++) {
	    koboldMap.put(kobID, koboldMaker.spawnKobold(koboldMap.get(rng.nextInt(koboldMap.size())),koboldMap.get(rng.nextInt(koboldMap.size()))));
	    kobID++;
	}

	//Map<Resources, Integer> pileOfStuff = new HashMap<>(); ---------- Start working on making and testing swords etc ------------
	//pileOfStuff.put(Resources.STONE, )
	//raxis.pickUp(Resources.STONE, 3);
	//raxis.pickUp(Resources.WOOD, 3);
	//raxis.pickUp(Resources.STONE, 2);
	//raxis.pickUp(Resources.STONE, 1);
	//raxis.pickUp(Resources.IRON_INGOT, 1);

	Equipment testAxe1 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	Equipment testHammer = crafter.craftEquipment(Items.HAMMER, Resources.WOOD, raxis, null);
	raxis.equip(testHammer);
	Equipment testAxe2 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	Equipment testAxe3 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	Equipment testAxe4 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	//System.out.print("\naxe1: " + testAxe1.toString() + "\naxe2: " + testAxe2.toString() + "\naxe3: " + testAxe3.toString() + "\naxe4: " + testAxe4);


	for (int i = 0; i < 30; i++) {
	    month++;
	    if (month == 8) {
		year++;
		month = 1;
		System.out.print("\n ---------It's the start of a new year!---------");
	    } else System.out.print("\n ---------A month has passed!--------- \n" + "It is currently: " + month + " / " + year );

	    for (Map.Entry<Integer, Kobold> kob : koboldMap.entrySet()) {
		kob.getValue().increaseAge();
		if(kob.getValue().getDead()){
		    koboldGraveyard.put(kob.getKey(), kob.getValue());
		    continue;
		}
		if(rng.nextInt(100) <= 10){
		    Kobold partner1 = kob.getValue();
		    Kobold partner2;
		    while(true) {
			partner2 = koboldMap.getOrDefault(rng.nextInt(koboldMap.size()), defaultKobold); //TERRIBLE HORRIBLE PROGRAMING!!!! do this https://stackoverflow.com/a/12386664
			if ((partner1.getSex() != partner2.getSex()) || partner2.equals(defaultKobold)) {break;}
		    }
		    if(partner1.getFertile() && partner2.getFertile()) {
			koboldEggs.put(kobID, koboldMaker.spawnKobold(partner1, partner2));
			kobID++;
			System.out.print(
				"\nA new kobold has been born! It's name shall be " + koboldEggs.get(kobID - 1).getName() + "!");
		    }
		}
	    }
	    buryKobold();
	    hatchKobold();
	}

	System.out.print (koboldMap.toString());

    }
}
