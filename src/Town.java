import net.dv8tion.jda.api.EmbedBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Town extends Location
{
    public String name;
    private int maxSpace=10, usedSpace=0, defence=0;
    private Tribe tribe;
    private Tile localEnviorment;
    private Set<Building> buildings = new HashSet<>();
    private Set<Kobold> koboldsGuarding = new HashSet<>();
    private Set<Techs> tech = new HashSet<>();


    public String getSum(){
        return "there's a small town here very cool :D";
    }

    @Override
    public EmbedBuilder toEmbed(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(name);
        embed.appendDescription("Ruling Tribe: " + tribe.getName() +
                "\nSpace: " + usedSpace + "/" + maxSpace +
                "\nBase Defense: " + defence +
                "\nGuards:\n");
        for (Kobold kob : koboldsGuarding) {
            embed.appendDescription(kob.getName() + " (Defence: " + kob.getDefence() + "), ");
        }
        embed.appendDescription("\n\nBuildings:\n");
        for (Building b : buildings) {
            embed.appendDescription(b.toString() + ", ");
        }
        embed.appendDescription("\n\nUnlocked researches:\n");
        for (Techs t : tech) {
            embed.appendDescription(t.toString().toLowerCase() + ", ");
        }

        return embed;
    }

    public Town(String name, Tribe tribe, Tile loc){
        this.tribe = tribe;
        this.name = name;
        localEnviorment = loc;
    }

    public Set<Building> getBuildings(){
        return buildings;
    }

    public Set<Techs> getTechs(){
        return tech;
    }

    @Override
    public void moveKoboldHere(Kobold kob){
        koboldsHere.add(kob);
        usedSpace++;
    }

    @Override
    public void koboldLeaving(Kobold kob){
        koboldsHere.remove(kob);
        usedSpace--;
    }

    @Override
    public Tile getTile(){
        return localEnviorment;
    }

    public void unlockTech(Techs t){
        tech.add(t);
    }

}
