import net.dv8tion.jda.api.EmbedBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Location {
    protected Set<Kobold> koboldsHere = new HashSet<>();
    protected Map<Resources, Integer> localResources = new HashMap<>();
    protected Set<Equipment> localEquipment = new HashSet<>();

    public EmbedBuilder toEmbed(){
        EmbedBuilder e = new EmbedBuilder();
        e.setTitle("BIG ERROR LMAO!");
        return e;
    }

    public void dropE(Equipment eq){
        localEquipment.add(eq);
    }

    public void dropR(Resources r, int a){
        if (localResources.containsKey(r)){
            localResources.put(r, localResources.get(r) + a);
        }
        else{
            localResources.put(r, a);
        }
    }

    public EmbedBuilder stockpileEmbed(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Items here");
        embed.appendDescription("Resources:\n");
        for (Map.Entry<Resources, Integer> e : localResources.entrySet()) {
            embed.appendDescription(e.getKey().toString().toLowerCase() + " : " + e.getValue() + "\n");
        }
        embed.appendDescription("\nEquipments:\n");
        for (Equipment e : localEquipment){
            embed.appendDescription(e.toString() + "\n");
        }
        return embed;
    }

    public void moveKoboldHere(Kobold kob){
        koboldsHere.add(kob);
    }

    public void koboldLeaving(Kobold kob){
        koboldsHere.remove(kob);
    }

    public Map<Resources, Integer> getLocalResources() {
        return localResources;
    }

    public void useLocalResources(Resources r, int a){
        localResources.put(r, localResources.get(r) - a);
        if (localResources.get(r) <= 0){
            localResources.remove(r);
        }
    }

    public Kobold getKobHere(String name){
        for (Kobold kob : koboldsHere) {
            if (kob.getName().equalsIgnoreCase(name)){
                return kob;
            }
        }
        return null;
    }

    public Boolean isKobHere(String name){
        for (Kobold kob : koboldsHere) {
            if (kob.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    //DUMMY FUNCTIONS!
    public Set<Building> getBuildings(){
        return new HashSet<>();
    }
    public Set<Techs> getTechs(){
        return new HashSet<>();
    }
    public boolean hasTown(){
        return false;
    }

    public Town getTown(){
        return null;
        //return new Town("ERROR", new Tribe("ERROR", Bot.siul));
    }
    public Tile getTile(){
        return null;
    }






}
