import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KoboldMaker
{
    Random rng = new Random();
    NameList nameList = new NameList();
	int str;
	int dex;
	int con;
	int inte;
	int fai;
	int cha;

	private void setStat(String stat, int amount){
		int res=0;
		if(amount > 20){
			res = 20;
		}
		else { res = amount; }
		switch (stat){
			case "str":
				str = res;
				break;
			case "dex":
				dex = res;
				break;
			case "con":
				con = res;
				break;
			case "int":
				inte = res;
				break;
			case "fai":
				fai = res;
				break;
			case "cha":
				cha = res;
				break;
		}
	}

	private void alignAspect(String aspStat, Map.Entry<String, Integer> maxStat){
		int tempStorage = 0;
		switch (aspStat){
			case "str":
				tempStorage = str;
				str = maxStat.getValue();
				break;
			case "dex":
				tempStorage = dex;
				dex = maxStat.getValue();
				break;
			case "con":
				tempStorage = con;
				con = maxStat.getValue();
				break;
			case "int":
				tempStorage = inte;
				inte = maxStat.getValue();
				break;
			case "fai":
				tempStorage = fai;
				fai = maxStat.getValue();
				break;
			case "cha":
				tempStorage = cha;
				cha = maxStat.getValue();
				break;
		}
		switch (maxStat.getKey()){
			case "str":
				str = tempStorage;
				break;
			case "dex":
				dex = tempStorage;
				break;
			case "con":
				con = tempStorage;
				break;
			case "int":
				inte = tempStorage;
				break;
			case "fai":
				fai = tempStorage;
				break;
			case "cha":
				cha = tempStorage;
				break;
		}
	}

	public Kobold spawnRandKobold(){
		return new Kobold("Default", 10, 10, 10, 10, 10, 10, true, Aspects.BROWN, Aspects.BROWN);
	}


    public Kobold spawnKobold(Kobold dad, Kobold mom){
	str = (dad.getStat('s') + mom.getStat('s')) / 2 + (rng.nextInt(7) - 3);
	dex = (dad.getStat('d') + mom.getStat('d')) / 2 + (rng.nextInt(7) - 3);
	con = (dad.getStat('c') + mom.getStat('c')) / 2 + (rng.nextInt(7) - 3);
	inte = (dad.getStat('i') + mom.getStat('i')) / 2 + (rng.nextInt(7) - 3);
	fai = (dad.getStat('f') + mom.getStat('f')) / 2 + (rng.nextInt(7) - 3);
	cha = (dad.getStat('h') + mom.getStat('h')) / 2 + (rng.nextInt(7) - 3);
	HashMap<String, Integer> stats = new HashMap<>()
	{{
	    put("str", str);
	    put("dex", dex);
	    put("con", con);
	    put("int", inte);
	    put("fai", fai);
	    put("cha", cha);
	}};

	Aspects[] aspects;

	switch (rng.nextInt(4)){
	    case 1:
		aspects = new Aspects[]{dad.getMajorAspect(), Aspects.BROWN};
			randomAspect(aspects);
			break;
	    case 2:
		aspects = new Aspects[]{mom.getMajorAspect(), Aspects.BROWN};
			randomAspect(aspects);
			break;
	    case 3:
		if(rng.nextBoolean()){
		    aspects = new Aspects[]{dad.getMajorAspect(), mom.getMajorAspect()};
		}
		else{
		    aspects = new Aspects[]{mom.getMajorAspect(), dad.getMajorAspect()};
		}
		break;
	    default:
		if(dad.getMinorAspect() == mom.getMinorAspect()){
		    aspects = new Aspects[]{dad.getMinorAspect(), Aspects.BROWN};
		}
		else{
		    aspects = new Aspects[]{Aspects.BROWN, Aspects.BROWN};
		}
		break;
	}
	if(aspects[0] == Aspects.BROWN && aspects[1] != Aspects.BROWN){
		aspects[0] = aspects[1];
		aspects[1] = Aspects.BROWN;
	}

	Map.Entry<String, Integer> maxStat = null;
	for (Map.Entry<String, Integer> stat: stats.entrySet()) {
	    if( maxStat == null || stat.getValue() > maxStat.getValue()){
		maxStat = stat;
	    }
	}
	if(aspects[1] != Aspects.BROWN){
		alignAspect("int", maxStat);
	}
	else{
		switch (aspects[0]){
			case RED:
				alignAspect("str", maxStat);
				break;
			case GREEN:
				alignAspect("dex", maxStat);
				break;
			case BLACK:
				alignAspect("con", maxStat);
				break;
			case WHITE:
				alignAspect("fai", maxStat);
				break;
			case YELLOW:
				alignAspect("cha", maxStat);
				break;
			case BROWN:
				setStat("str", str + rng.nextInt(3));
				setStat("dex", dex + rng.nextInt(3));
				setStat("con", con + rng.nextInt(3));
				setStat("int", inte + rng.nextInt(3));
				setStat("fai", fai + rng.nextInt(3));
				setStat("cha", cha + rng.nextInt(3));
		}
	}



	Kobold baby = new Kobold(nameList.getMaleName(rng.nextInt(nameList.getLength('m'))), str, dex, con, inte, fai, cha, rng.nextInt(2)==1, aspects[0], aspects[1]);
	baby.setParents(mom, dad);
	return baby;
    }

	private void randomAspect(Aspects[] aspects) {
		if(aspects[0] == Aspects.BROWN && aspects[1] == Aspects.BROWN){
			switch(rng.nextInt(6)){
				case 0:
					aspects[0] = Aspects.BROWN;
					break;
				case 1:
					aspects[0] = Aspects.RED;
					break;
				case 2:
					aspects[0] = Aspects.GREEN;
					break;
				case 3:
					aspects[0] = Aspects.BLACK;
					break;
				case 4:
					aspects[0] = Aspects.WHITE;
					break;
				case 5:
					aspects[0] = Aspects.YELLOW;
					break;
			}
		}
	}
}
