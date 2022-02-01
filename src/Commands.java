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
                    }
                    //---------------------------------LOOK---------------------------------------
                    else if (args[0].equalsIgnoreCase(prefix + "look")) {
                        if (args.length == 1) {
                            if (kob.isInTown()){
                                c.sendMessageEmbeds(kob.getLocation().toEmbed().build()).queue();
                            }else{
                                Location local = gwm.get(event.getGuild()).getKob(event.getMember()).getLocation();
                                c.sendMessageEmbeds(local.toEmbed().build()).queue();
                            }
                        } else {
                            if (args[1].equalsIgnoreCase("self")){
                                c.sendMessageEmbeds(kob.toEmbed().build()).queue();
                            }
                            else if (args[1].equalsIgnoreCase("items")){
                                if (kob.isInTown()){
                                    c.sendMessageEmbeds(kob.getLocation().stockpileEmbed().build()).queue();
                                }
                                else{
                                    c.sendMessageEmbeds(kob.getLocation().stockpileEmbed().build()).queue();
                                }
                            }
                            else if (kob.getLocation().isKobHere(args[1])) {
                                Kobold victim = kob.getLocation().getKobHere(args[1]);
                                c.sendMessageEmbeds(victim.toEmbed().build()).queue();
                            }
                            else{
                                c.sendMessage("No kobold named " + args[1] + "was found here, check for typos!").queue();
                            }
                        }
                    }
                    //----------------------------------CRAFT------------------------------------
                    else if (args[0].equalsIgnoreCase(prefix + "craft")) {
                        if (args.length != 3){
                            c.sendMessage("You gotta pick something to craft!").queue();
                            embed.setTitle("!craft (command)");
                            embed.appendDescription("!Craft <resource> <item>");
                            embed.clear();
                        } else{
                            Resources res = toResource(args[1]);
                            Items item = toItem(args[2]);
                            if (crafter.itemIndex.canCraft(item, res, kob)) {
                                try {
                                    Equipment eq = crafter.craftEquipment(item, res, kob);
                                    c.sendMessage(kob.getName() + " has crafted an " + eq.toString()).queue();
                                    if (!kob.pickUpItem(eq)) {
                                        c.sendMessage(kob.getName() + "s inventory was full, so " + eq.toString() +
                                                " was dropped on the ground!").queue();
                                        kob.inventoryOverflowE(eq);
                                    }
                                } catch (NullPointerException e){
                                    c.sendMessage("That's not an item you can craft!").queue();
                                }
                            }
                            else{
                                c.sendMessage("You can't craft that right now").queue();
                            }

                        }
                        crafter.craftEquipment(Items.AXE, Resources.BONE, kob);

                    }
                    //----------------------------------ENTER-----------------------------------    ADD CHANEL MOVING
                    else if (args[0].equalsIgnoreCase(prefix + "enter")){
                        if (!kob.isInTown() && kob.getLocation().hasTown()){
                            kob.getLocation().koboldLeaving(kob);
                            kob.getLocation().getTown().moveKoboldHere(kob);
                            c.sendMessage(kob.getName() + " has entered the town " + kob.getLocation().getTown().name).queue();
                            kob.setLocation(kob.getLocation().getTown());
                            kob.enterTown();
                        }
                        else c.sendMessage("There is no town to enter here!").queue();
                    }
                    //----------------------------------LEAVE-----------------------------------    ADD CHANEL MOVING
                    else if (args[0].equalsIgnoreCase(prefix + "leave")){
                        if (kob.isInTown() && kob.getLocation().getClass() == Town.class){
                            kob.getLocation().koboldLeaving(kob);
                            kob.getLocation().getTile().moveKoboldHere(kob);
                            kob.setLocation(kob.getLocation().getTile());
                            c.sendMessage(kob.getName() + " has left the town " + kob.getLocation().getTown().name).queue();
                            kob.leaveTown();
                        }
                        else c.sendMessage("You're not in a town! Can't leave what you're not in you know?").queue();
                    }
                    //--------------------------------BACKFLIP----------------------------------
                    else if (args[0].equalsIgnoreCase(prefix + "backflip")){
                        c.sendMessage(kob.getName() + " does a sick backflip!!!!!").queue();
                    }



                }
                //----------------------------------JOIN------------------------------------
                else if (args[0].equalsIgnoreCase(prefix + "join")) {
                    if (args.length >= 2) {
                        wrld.memJoin(event.getMember(), args[1]);
                        event.getMember().modifyNickname(args[1]).queue();
                        c.sendMessage(event.getMessageId() + " welcome to the world of kobolds!").queue();
                    }else c.sendMessage("You have to pick a name! add it after !join").queue();

                }else{
                    c.sendMessage("You gotta join before you can do that!").queue();
                }
            }
        }
    }
}

