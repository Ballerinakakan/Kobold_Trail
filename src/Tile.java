import net.dv8tion.jda.api.EmbedBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tile {
    private Biome biome;
    private Landmarks landmark;
    private Town localTown = null;
    private Set<Kobold> koboldsHere;
    private Set<Creature> creaturesHere;
    private Set<CreatureType> availableCreatures;
    private Set<Resources> availableResources;
    private Map<Resources, Integer> groundResources = new HashMap<>();
    private Set<Equipment> localEquipment = new HashSet<>();

    public EmbedBuilder toEmbed(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(biome.toString());
        if(localTown != null){
            embed.addField("Local town: " + localTown.name, localTown.getSum(), true);
        }
        embed.appendDescription("\nKobolds here: \n");
        for (Kobold kob : koboldsHere ) {
            embed.appendDescription(kob.atLocation() + "\n");
        }
        if (creaturesHere != null) {
            embed.appendDescription("\nCreatures here: \n");
            for (Creature cre : creaturesHere) {
                embed.appendDescription(cre.atLocation() + "\n");
            }
        }
        if (availableResources != null) {
            embed.appendDescription("\nResources available: \n");
            for (Resources res : availableResources) {
                embed.appendDescription(res.toString().toLowerCase() + "\n");
            }
        }
        if(landmark != Landmarks.EMPTY && landmark != Landmarks.TOWN && landmark != Landmarks.CRADLE_TOWN){
            embed.appendDescription("\nLandmarks here: " + landmark.toString().toLowerCase());
        }
        return embed;
    }

    public Tile(Biome bio, Set<Kobold> kobs, Set<CreatureType> crets,
                Landmarks lm, Set<Resources> res){
        biome = bio;
        koboldsHere = kobs;
        availableCreatures = crets;
        availableResources = res;
        landmark = lm;
        switch (landmark){
            case TOWN:
                //AAAAHHHHHH MAKE NEW TOWN RANDOMLY!!!!
                break;
            case CRADLE_TOWN:
                localTown = new Town("Siul's Cradle", new Tribe("Siul's gift", Bot.siul));
        }

    }

    public Boolean isKobHere(String name){
        for (Kobold kob : koboldsHere) {
            if (kob.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public Town getTown(){
        return localTown;
    }
    public boolean hasTown(){
        return localTown != null;
    }

    public Kobold getKobHere(String name){
        for (Kobold kob : koboldsHere) {
            if (kob.getName().equalsIgnoreCase(name)){
                return kob;
            }
        }
        return null;
    }

    public Map<Resources, Integer> getGroundResources() {
        return groundResources;
    }

    public void dropE(Equipment eq){
        localEquipment.add(eq);
    }

    public void moveKoboldHere(Kobold kob){
        koboldsHere.add(kob);
    }

}
