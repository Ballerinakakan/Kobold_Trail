import java.util.*;

public class TileGen {
    //Generates new tiles!
    private final Random rng = new Random();
    private final KoboldMaker kobMak = new KoboldMaker();
    private Map<Landmarks, Integer> remainingLandmarks = new HashMap<>(){{
        put(Landmarks.TOWN, -1);
        put(Landmarks.WIZARDS_TOWER, 2);
    }};

    public Tile gen(Coordinate coor){
        Biome bio = calcBio(coor);
        Set<Resources> availableResources = calcRes(bio);
        Set<CreatureType> localCreatures = calcCre(bio, coor);
        Set<Kobold> kobs = new HashSet<>();
        if(localCreatures.isEmpty()) kobs = calcKobs(coor);
        Landmarks landmark;
        if (coor.distToCradle() == 0) landmark = Landmarks.CRADLE_TOWN;
        else landmark = calcLandmark();

        Tile res = new Tile(bio, kobs, localCreatures, landmark, availableResources);
        return res;
    }

    private Biome calcBio(Coordinate coor) {
        int dist = coor.distToCradle();
        if (dist <= 3) {
            return Biome.CALMING_MEADOWS;
        } else if (rng.nextInt(10) == 0) return Biome.RICH_CAVE;
        else if (dist <= 10) {
            return Biome.GENTLE_FOREST;
        } else if (coor.getX() > 0 && coor.getY() > 0) {
            return Biome.CHILLY_TUNDRA;
        } else if (coor.getX() > 0 && coor.getY() < 0) {
            return Biome.MAGICAL_MUSHROOM_FOREST;
        } else if (coor.getX() < 0 && coor.getY() < 0) {
            return Biome.DUSTY_DESERT;
        } else if (coor.getX() < 0 && coor.getY() > 0) {
            return Biome.HAUNTING_PLAGUELANDS;
        } else return Biome.RICH_CAVE;
    }

    private Boolean rollRes(int chance){
        return (rng.nextInt(100) < chance - 1);
    }

    private Set<Resources> calcRes(Biome bio){
        Set<Resources> res = new HashSet<>();
        switch(bio){
            case CALMING_MEADOWS:
                res.add(Resources.FIBER);
                if(rollRes(10)) res.add(Resources.STONE);
                if(rollRes(30)) res.add(Resources.HERBS);
                if (rollRes(50)) res.add(Resources.WATER);
                break;
            case GENTLE_FOREST:
                res.add(Resources.WOOD);
                if (rollRes(40)) res.add(Resources.MUSHROOM);
                if (rollRes(10)) res.add(Resources.STONE);
                break;
            case RICH_CAVE:
                int chance = 50;
                res.add(Resources.STONE);
                if (rollRes(70)) res.add(Resources.MUSHROOM);
                if (rollRes(60)) res.add(Resources.COAL);
                if (rollRes(chance)){
                    res.add(Resources.COPPER_ORE);
                    chance -= 40;
                }else if(rollRes(chance)){
                    res.add(Resources.TIN_ORE);
                    chance -= 40;
                }else if(rollRes(chance)){
                    res.add(Resources.IRON_ORE);
                    chance -= 40;
                }
                if (rollRes(chance)){
                    res.add(Resources.SILVER_ORE);
                    chance = 2;
                }
                if (rollRes(chance/2)){
                    res.add(Resources.GOLD_ORE);
                }
                break;
            case DUSTY_DESERT:
                res.add(Resources.SAND);
                if (rollRes(15)) res.add(Resources.GOLD_ORE);
                else if (rollRes(30)) res.add(Resources.COPPER_ORE);
                if (rollRes(3)) res.add(Resources.WATER);
                if (rollRes(40)) res.add(Resources.STONE);
                break;
            case CHILLY_TUNDRA:
                res.add(Resources.SNOW);
                if(rollRes(30)) res.add(Resources.STONE);
                if (rollRes(70)) res.add(Resources.WOOD);
                if (rollRes(40)) res.add(Resources.MUSHROOM);
                if (rollRes(20)) res.add(Resources.IRON_ORE);
                break;
            case HAUNTING_PLAGUELANDS:
                res.add(Resources.MUSHROOM);
                if(rollRes(30)) res.add(Resources.STONE);
                if(rollRes(20)) res.add(Resources.FIBER);
                if(rollRes(15)) res.add(Resources.WOOD);
                if (rollRes(70)) res.add(Resources.BONE);
                if (rollRes(30)) res.add(Resources.TIN_ORE);
                else if (rollRes(20)) res.add(Resources.COAL);
                if (rollRes(20)) res.add(Resources.WATER);
                break;
            case MAGICAL_MUSHROOM_FOREST:
                res.add(Resources.MUSHROOM);
                if (rollRes(60)) res.add(Resources.WATER);
                else if (rollRes(5)) res.add(Resources.LIQUID_MANA);
                if (rollRes(25)) res.add(Resources.SILVER_ORE);
                else if (rollRes(5)) res.add(Resources.COPPER_ORE);
                else if (rollRes(5)) res.add(Resources.TIN_ORE);
                else if (rollRes(10)) res.add(Resources.IRON_ORE);
                if(rollRes(25)) res.add(Resources.STONE);
                if(rollRes(20)) res.add(Resources.FIBER);
                break;
            default:
                if (rollRes(45)) res.add(Resources.WATER);
                if(rollRes(25)) res.add(Resources.STONE);
                if(rollRes(30)) res.add(Resources.FIBER);
                if (rollRes(35)) res.add(Resources.WOOD);
                if (rollRes(20)) res.add(Resources.MUSHROOM);
                break;
        }
        return res;
    }

    private Set<CreatureType> calcCre(Biome bio, Coordinate coor){
        Set<CreatureType> res = new HashSet<>();
        switch(bio){
            case CALMING_MEADOWS:
                if (rollRes(25)) res.add(CreatureType.RAT);
                break;
            case GENTLE_FOREST:
                if (rollRes(20)) res.add(CreatureType.SPIDER);
                if (rollRes(20)) res.add(CreatureType.RAT);
                if (rollRes(10)) res.add(CreatureType.GOBLIN);
                break;
            case RICH_CAVE:
                if (rollRes(35)) res.add(CreatureType.SPIDER);
                if (rollRes(10)) res.add(CreatureType.RAT);
                if (coor.distToCradle() > 10){
                    if(coor.getX() > 0 && coor.getY() > 0){ //TUNDRA
                        if (rollRes(25)) res.add(CreatureType.TROLL);
                        else if (rollRes(25)) res.add(CreatureType.GOBLIN);
                    }
                    else if (coor.getX() > 0 && coor.getY() < 0){ //MUSHROOM FOREST
                        if (rollRes(40)) res.add(CreatureType.FROGMAN);
                        else if (rollRes(50)) res.add(CreatureType.GOBLIN);
                    }
                    else if (coor.getX() < 0 && coor.getY() < 0){ //DESERT
                        if (rollRes(30)) res.add(CreatureType.SNAKEMAN);
                        else if (rollRes(20)) res.add(CreatureType.GOBLIN);
                    }
                    else if (coor.getX() < 0 && coor.getY() > 0){ //PLAGUELANDS
                        if (rollRes(40)) res.add(CreatureType.ROTTLING);
                        else if (rollRes(10)) res.add(CreatureType.GOBLIN);
                    }
                    else if (rollRes(50)) res.add(CreatureType.GOBLIN);
                }
                else if (rollRes(50)) res.add(CreatureType.GOBLIN);
                break;
            case DUSTY_DESERT:
                if (rollRes(15)) res.add(CreatureType.SPIDER);
                if (rollRes(40)) res.add(CreatureType.LIZARD);
                if (rollRes(10)) res.add(CreatureType.SNAKEMAN);
                break;
            case CHILLY_TUNDRA:
                if (rollRes(30)) res.add(CreatureType.CRAB);
                if (rollRes(30)) res.add(CreatureType.FOX);
                if (rollRes(5)) res.add(CreatureType.TROLL);
                break;
            case HAUNTING_PLAGUELANDS:
                if (rollRes(40)) res.add(CreatureType.RAT);
                if (rollRes(15)) res.add(CreatureType.SPIDER);
                if (rollRes(20)) res.add(CreatureType.ROTTLING);
                break;
            case MAGICAL_MUSHROOM_FOREST:
                if (rollRes(20)) res.add(CreatureType.CRAB);
                if (rollRes(20)) res.add(CreatureType.LIZARD);
                if (rollRes(10)) res.add(CreatureType.GOBLIN);
                if (rollRes(15)) res.add(CreatureType.FROGMAN);
                else if (rollRes(1)) res.add(CreatureType.TROLL);
                break;
            default:
                if (rollRes(45)) res.add(CreatureType.GOBLIN);
                break;
        }
        return res;
    }

    private Set<Kobold> calcKobs(Coordinate co){
        Set<Kobold> res = new HashSet<>();
        for (int chance = 95 - co.distToCradle(); chance < rng.nextInt(100); chance -= 15) {
            res.add(kobMak.spawnRandKobold());
        }
        return res;
    }

    private Landmarks calcLandmark(){
        if (rollRes(5)){
            List<Landmarks> keysAsList = new ArrayList<>(remainingLandmarks.keySet());
            Landmarks res = keysAsList.get(rng.nextInt(keysAsList.size()));
            if (remainingLandmarks.get(res) - 1 == 0){
                remainingLandmarks.remove(res);
            }else remainingLandmarks.put(res, remainingLandmarks.get(res) - 1);
            return res;
        }
        else return Landmarks.EMPTY;
    }
}
