import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class Commands extends ListenerAdapter {
    public String prefix = "!";
    private int guilds = 0;
    EmbedBuilder embed = new EmbedBuilder();
    Map<Guild, World> gwm = new HashMap<>();
    EquipmentCrafter crafter = new EquipmentCrafter();

    //TESTING

    /*
    Kobold raxis = new Kobold("Raxis", 10, 10, 10, 10, 10, 10, true, Aspects.RED, Aspects.BROWN);
    */

    private Items toItem(String str){
        for (Items i : Items.values()) {
            if (i.toString().equalsIgnoreCase(str)){
                return i;
            }
        }
        return null;
    }

    private Resources toResource(String str){
        for (Resources r : Resources.values()) {
            if (r.toString().equalsIgnoreCase(str)){
                return r;
            }
        }
        return null;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");
        MessageChannel c = event.getChannel();
        if (args[0].startsWith("!")) {

            if (!gwm.containsKey(event.getGuild()) && args[0].equalsIgnoreCase(prefix + "start")) {
                gwm.put(event.getGuild(), new World());
                guilds++;
                c.sendMessage("Siul has blessed your server, let there be Kobolds!").queue();

            } else if (gwm.containsKey(event.getGuild())) {
                World wrld = gwm.get(event.getGuild());
                if (gwm.get(event.getGuild()).memHasKob(event.getMember())) {
                    Kobold kob = gwm.get(event.getGuild()).getKob(event.getMember());

                    if (args[0].equalsIgnoreCase(prefix + "test")) {
                        c.sendMessage("*kobold noises*").queue();
                    } else if (args[0].equalsIgnoreCase(prefix + "look")) {
                        if (args.length == 1) {
                            Tile local = gwm.get(event.getGuild()).getKob(event.getMember()).getLocation();
                            c.sendMessageEmbeds(local.toEmbed().build()).queue();
                        } else if (kob.getLocation().isKobHere(args[1])) {
                            Kobold victim = kob.getLocation().getKobHere(args[1]);
                            c.sendMessageEmbeds(victim.toEmbed().build()).queue();
                        }
                    } else if (args[0].equalsIgnoreCase(prefix + "craft")) {
                        if (args.length != 3){
                            c.sendMessage("You gotta pick something to craft!").queue();
                            embed.setTitle("!craft (command)");
                            embed.appendDescription("!Craft <resource> <item>");
                            embed.clear();
                        } else{
                            Resources res = toResource(args[1]);
                            Items item = toItem(args[2]);
                            if (crafter.itemIndex.canCraft(item, res, kob)){
                                Equipment eq = crafter.craftEquipment(item, res, kob);
                                c.sendMessage(kob.getName() + " has crafted an " + eq.toString()).queue();
                                if (!kob.pickUpItem(eq)){
                                    c.sendMessage(kob.getName() + "s inventory was full, so " + eq.toString() +
                                            " was dropped on the ground!").queue();
                                    kob.inventoryOverflowE(eq);
                                }
                            }

                        }
                        crafter.craftEquipment(Items.AXE, Resources.BONE, kob);

                    } else if (args[0].equalsIgnoreCase(prefix + "backflip")){
                        c.sendMessage(kob.getName() + " does a sick backflip!!!!!").queue();
                    }



                } else if (args[0].equalsIgnoreCase(prefix + "join")) {
                    if (args.length >= 2) {
                        wrld.memJoin(event.getMember(), args[1]);
                        event.getMember().modifyNickname(args[1]).queue();
                        c.sendMessage("@" + args[1] + " welcome to the world of kobolds!").queue();
                    }else c.sendMessage("You have to pick a name! add it after !join").queue();

                }else{
                    c.sendMessage("You gotta join before you can do that!").queue();
                }
            }
        }
    }
}

