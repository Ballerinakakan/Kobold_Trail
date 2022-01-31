import java.io.*;
import java.util.*;

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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	KoboldMaker koboldMaker = new KoboldMaker();
	Random rng = new Random();
	EquipmentCrafter crafter = new EquipmentCrafter();
	Kobold raxis = new Kobold("Raxis", 10, 10, 10, 10, 10, 10, true, Aspects.RED, Aspects.BROWN);
	Kobold defaultKobold = new Kobold("Default", 10, 10, 10, 10, 10, 10, true, Aspects.BROWN, Aspects.BROWN);
	int month = 1, year = 1;


	//Attempt to save Raxis and load Raxis
	serializeDataOut(raxis);
	System.out.print("Raxis has been saved!");
	System.out.print(serializeDataIn().toString());



	//Map<Resources, Integer> pileOfStuff = new HashMap<>(); ---------- Start working on making and testing swords etc ------------
	//pileOfStuff.put(Resources.STONE, )
	//raxis.pickUp(Resources.STONE, 3);
	//raxis.pickUp(Resources.WOOD, 3);
	//raxis.pickUp(Resources.STONE, 2);
	//raxis.pickUp(Resources.STONE, 1);
	//raxis.pickUp(Resources.IRON_INGOT, 1);


		/*
	Equipment testAxe1 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	Equipment testHammer = crafter.craftEquipment(Items.HAMMER, Resources.WOOD, raxis, null);
	raxis.equip(testHammer);
	Equipment testAxe2 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	Equipment testAxe3 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	Equipment testAxe4 = crafter.craftEquipment(Items.AXE, Resources.BONE, raxis, null);
	//System.out.print("\naxe1: " + testAxe1.toString() + "\naxe2: " + testAxe2.toString() + "\naxe3: " + testAxe3.toString() + "\naxe4: " + testAxe4);
	*/



	for (int k = 0; k < 10; k++) {
		koboldMap.put(0, raxis);
		koboldMap.put(1, new Kobold("Ringo", 12, 8, 12, 8, 12, 8, true, Aspects.RED, Aspects.BROWN));
		koboldMap.put(2, new Kobold("Lulin", 12, 8, 12, 8, 12, 8, true, Aspects.YELLOW, Aspects.BROWN));
		koboldMap.put(3, new Kobold("Joyli", 12, 8, 12, 8, 12, 8, true, Aspects.BLACK, Aspects.BROWN));
		koboldMap.put(4, new Kobold("Dronald", 10, 10, 10, 10, 10, 10, true, Aspects.WHITE, Aspects.BROWN));
		koboldMap.put(5, new Kobold("Zap", 10, 10, 10, 10, 10, 10, false, Aspects.YELLOW, Aspects.BROWN));
		koboldMap.put(6, new Kobold("Jill", 10, 10, 10, 10, 10, 10, false, Aspects.RED, Aspects.BROWN));
		koboldMap.put(7, new Kobold("Breen", 10, 10, 10, 10, 10, 10, false, Aspects.WHITE, Aspects.BROWN));
		koboldMap.put(8, new Kobold("Taco", 10, 10, 10, 10, 10, 10, false, Aspects.BLACK, Aspects.BROWN));
		koboldMap.put(9, new Kobold("Amogus", 10, 10, 10, 10, 10, 10, false, Aspects.GREEN, Aspects.BROWN));

		int kobID = 10;
		//for (int i = 0; i < 10; i++) {
			//koboldMap.put(kobID, koboldMaker.spawnKobold(koboldMap.get(rng.nextInt(koboldMap.size())),koboldMap.get(rng.nextInt(koboldMap.size()))));
			//kobID++;
		//}

		for (int i = 0; i < 90; i++) {
			month++;
			if (month == 8) {
				year++;
				month = 1;
				//System.out.print("\n ---------It's the start of a new year!---------");
			} else
				//System.out.print("\n ---------A month has passed!--------- \n" + "It is currently: " + month + " / " + year);

			for (Map.Entry<Integer, Kobold> kob : koboldMap.entrySet()) {
				kob.getValue().increaseAge();
				if (kob.getValue().getDead()) {
					koboldGraveyard.put(kob.getKey(), kob.getValue());
					continue;
				}
				if (rng.nextInt(100) <= 15) {
					Kobold partner1 = kob.getValue();
					Kobold partner2;
					while (true) {
						partner2 = koboldMap.getOrDefault(rng.nextInt(koboldMap.size()), defaultKobold); //TERRIBLE HORRIBLE PROGRAMING!!!! do this https://stackoverflow.com/a/12386664
						if ((partner1.getSex() != partner2.getSex()) || partner2.equals(defaultKobold)) {
							break;
						}
					}

					if (partner1.getFertile() && partner2.getFertile()) {
						int numberOfEggs = (rng.nextInt(9) + 1)/3;
						for (int j = 0; j < numberOfEggs; j++) {
							koboldEggs.put(kobID, koboldMaker.spawnKobold(partner1, partner2));
							kobID++;
							//System.out.print(
							//"\nA new kobold has been born! It's name shall be " + koboldEggs.get(kobID - 1).getName() + "!");
						}
					}
				}
			}
			buryKobold();
			hatchKobold();
		}

		Map<String, Integer> colorCounter = new HashMap<>() {{
			put("brown", 0);
			put("red", 0);
			put("blue", 0);
			put("yellow", 0);
			put("white", 0);
			put("black", 0);
			put("green", 0);
		}};

		//System.out.print (koboldMap.toString());

		int tot = 0;
		for (Map.Entry<Integer, Kobold> kob : koboldMap.entrySet()) {
			Kobold cur = kob.getValue();
			switch (cur.getColor()) {
				case ":GREEN_SQUARE:":
				case ":GREEN_CIRCLE:":
					colorCounter.put("green", colorCounter.get("green") + 1);
					break;
				case ":RED_SQUARE:":
				case ":RED_CIRCLE:":
					colorCounter.put("red", colorCounter.get("red") + 1);
					break;
				case ":BLUE_SQUARE:":
				case ":BLUE_CIRCLE:":
					colorCounter.put("blue", colorCounter.get("blue") + 1);
					break;
				case ":YELLOW_SQUARE:":
				case ":YELLOW_CIRCLE:":
					colorCounter.put("yellow", colorCounter.get("yellow") + 1);
					break;
				case ":WHITE_SQUARE:":
				case ":WHITE_CIRCLE:":
					colorCounter.put("white", colorCounter.get("white") + 1);
					break;
				case ":BLACK_SQUARE:":
				case ":BLACK_CIRCLE:":
					colorCounter.put("black", colorCounter.get("black") + 1);
					break;
				case ":BROWN_SQUARE:":
				case ":BROWN_CIRCLE:":
					colorCounter.put("brown", colorCounter.get("brown") + 1);
					break;
			}
			tot += 1;
		}
		System.out.print("\n" + colorCounter.toString() + " tot: " + tot);
		koboldMap.clear();
	}

    }

	static private void serializeDataOut(Object o) throws IOException {
		String fileName = "test.txt";
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(o);
		oos.close();
	}

	public static Kobold serializeDataIn() throws IOException, ClassNotFoundException {
		String fileName = "test.txt";
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Kobold kob = (Kobold) ois.readObject();
		ois.close();
		return kob;
	}
}
