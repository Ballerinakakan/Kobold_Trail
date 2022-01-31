import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Town
{
    public String name;
    private Tribe tribe;
    private Set<Building> buildings = new HashSet<>();
    private Set<Kobold> koboldsHere = new HashSet<>();
    private Map<Resources, Integer> townResources = new HashMap<>();
    private Set<Equipment> localEquipment = new HashSet<>();
    private Set<Techs> tech;


    public String getSum(){
        return "there's a small town here very cool :D";
    }

    public Town(String name, Tribe tribe){
        this.tribe = tribe;
        this.name = name;
    }

    public Map<Resources, Integer> getTownResources(){
        return townResources;
    }

    public Set<Building> getBuildings(){
        return buildings;
    }

    public Set<Techs> getTechs(){
        return tech;
    }

    public void moveKoboldHere(Kobold kob){
        koboldsHere.add(kob);
    }

    public void unlockTech(Techs re){
        tech.add(re);
    }

    public void dropE(Equipment eq){
        localEquipment.add(eq);
    }

}
