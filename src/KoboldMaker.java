import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KoboldMaker
{
    Random rng = new Random();
    NameList nameList = new NameList();

    public Kobold spawnKobold(Kobold dad, Kobold mom){
	int colour;
	int str = (dad.getStat('s') + mom.getStat('s')) / 2 + (rng.nextInt(7) - 3);
	int dex = (dad.getStat('d') + mom.getStat('d')) / 2 + (rng.nextInt(7) - 3);
	int con = (dad.getStat('c') + mom.getStat('c')) / 2 + (rng.nextInt(7) - 3);
	int inte = (dad.getStat('i') + mom.getStat('i')) / 2 + (rng.nextInt(7) - 3);
	int fai = (dad.getStat('f') + mom.getStat('f')) / 2 + (rng.nextInt(7) - 3);
	int cha = (dad.getStat('h') + mom.getStat('h')) / 2 + (rng.nextInt(7) - 3);
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
		break;
	    case 2:
		aspects = new Aspects[]{mom.getMajorAspect(), Aspects.BROWN};
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

	Map.Entry<String, Integer> maxStat = null;
	for (Map.Entry<String, Integer> stat: stats.entrySet()) {
	    if( maxStat == null || stat.getValue() > maxStat.getValue()){
		maxStat = stat;
	    }
	}
	

	Kobold baby = new Kobold(nameList.getMaleName(rng.nextInt(nameList.getLength('m'))), str, dex, con, inte, fai, cha, rng.nextInt(2)==1, aspects[0], aspects[1]);
	baby.setParents(mom, dad);
	return baby;
    }
}
