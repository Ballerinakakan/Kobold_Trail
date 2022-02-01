import net.dv8tion.jda.api.entities.Member;

import java.util.*;

public class World {
    private Random rng = new Random();
    private Set<Kobold> namelessKobolds = new HashSet<>();
    private Map<Member, Kobold> memKoboldMap = new HashMap();
    private Map<Integer, Kobold> koboldIDMap = new HashMap<>();
    private Map<String, Kobold> nameKoboldMap = new HashMap();
    private Map<Coordinate, Tile> worldMap = new HashMap<>();
    private TileGen tileGen = new TileGen();
    private Coordinate cradle = new Coordinate(0,0);



    public Kobold getKob(Member mem){
        return memKoboldMap.get(mem);
    }

    public World(){
        worldMap.put(cradle, tileGen.gen(cradle));
        namelessKobolds.add( new Kobold("Dothraxis", 12, 8, 12, 8, 12, 8, true, Aspects.YELLOW, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Ringo", 12, 8, 12, 8, 12, 8, true, Aspects.RED, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Lulin", 12, 8, 12, 8, 12, 8, true, Aspects.GREEN, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Joyli", 12, 8, 12, 8, 12, 8, true, Aspects.BLACK, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Dronald", 10, 10, 10, 10, 10, 10, true, Aspects.WHITE, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Zap", 10, 10, 10, 10, 10, 10, false, Aspects.YELLOW, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Jill", 10, 10, 10, 10, 10, 10, false, Aspects.RED, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Breen", 10, 10, 10, 10, 10, 10, false, Aspects.WHITE, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Taco", 10, 10, 10, 10, 10, 10, false, Aspects.BLACK, Aspects.BROWN));
        namelessKobolds.add( new Kobold("Amogus", 10, 10, 10, 10, 10, 10, false, Aspects.GREEN, Aspects.BROWN));

        for (Kobold kob : namelessKobolds) {
            kob.setLocation(worldMap.get(cradle).getTown());
            worldMap.get(cradle).getTown().moveKoboldHere(kob);
            //worldMap.get(cradle).moveKoboldHere(kob);
        }

        //THIS IS FOR TESTING PURPOSES REMOVE ONCE DONE WITH TESTING!!!!!
        for (Resources r : Resources.values()) {
            worldMap.get(cradle).getTown().dropR(r, 20);
        }
        worldMap.get(cradle).getTown().unlockTech(Techs.BASIC_TOOLS);
        worldMap.get(cradle).getTown().unlockTech(Techs.ADVANCED_TOOLS);

        //END OF TESTING-----------------------------------------------------

    }

    public void memJoin(Member mem, String pName){
        ArrayList<Kobold> tempNamelessList = new ArrayList<>(namelessKobolds);
        Kobold selected = tempNamelessList.get(rng.nextInt(tempNamelessList.size()));
        namelessKobolds.remove(selected);
        memKoboldMap.put(mem, selected);
        selected.setPlayerName(pName);
    }

    public boolean memHasKob(Member mem){
        return memKoboldMap.containsKey(mem);
    }

    public void addNamelessKob(Kobold kob){
        namelessKobolds.add(kob);
        nameKoboldMap.put(kob.getName(), kob);
    }


}
