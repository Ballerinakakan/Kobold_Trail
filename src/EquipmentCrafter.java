import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EquipmentCrafter
{
    private Random rng = new Random();
    private ItemIndex itemIndex = new ItemIndex();

    private int calcQuality(Kobold crafter, int skillLVL, Equipment tool, Building craftingStation, boolean complex){
        int quality = skillLVL * 2;
        if(complex) {
             quality += crafter.getStat('i');
        }
        else{quality += crafter.getStat('d');}
        if(tool != null && tool.getType() == Items.HAMMER){
            quality += tool.rollEfficiency(crafter.getSkillMap(), crafter.getStat('l'));
        }
        if(craftingStation != null){
            //quality += craftingStation.rollEff()              IMPLEMENT AFTER MAKING BUILDINGS A THING :EYES:
        }
        //                  ADD BASED ON CRAFTERS MOOD IF "CONFIDENT"/"CALM"/"PROUD" quality += 0->20
        //quality += 10; //TEMP!!!! REMOVE THIS ONCE MOOD AFFECTS QUALITY

        quality = (quality/10) - 5;
        return quality;
    }

    private boolean isMetal(Resources mat){
        switch (mat){
            case GOLD:
            case IRON_INGOT:
            case SILVER_INGOT:
            case TIN_INGOT:
            case COPPER_INGOT:
            case BRONZE_INGOT:
                return true;
            default:
                return false;
        }
    }

    private Equipment checkEquip(Kobold crafter){
        if(crafter.getEquips("RHand") != null){
            if(crafter.getEquips("RHand").getType() == Items.HAMMER){
                return crafter.getEquips("RHand");
            }
        }
        if (crafter.getEquips("LHand") != null) {
            if(crafter.getEquips("LHand").getType() == Items.HAMMER){
                return crafter.getEquips("LHand");
            }
        }
        return null;
    }

    private int calcDurability(int qual, Statblock sBlock){
        int res = sBlock.getDurability();
        res += qual;
        return res;
    }

    public Equipment craftEquipment(Items type, Resources material, Kobold crafter, Building craftingStation){ //FIX THIS
	int quality = calcQuality(crafter, crafter.getSkillLVL(Skills.CRAFTING), checkEquip(crafter), craftingStation, false);
        Statblock block = itemIndex.getStatblock(material, type);
        int durability = calcDurability(quality, block);
        //                                                  Calc damage and eff based on quality?
        //                                                  Or maybe leave those static???
        if(isMetal(material)){
            crafter.awardSkillExp(Skills.SMITHING);
        }
        crafter.awardSkillExp(Skills.CRAFTING);
        return new Equipment(material, type, durability, quality, block.getAccuracy(), block.getRange(), block.getbDamage(),
                                      block.getbEfficiency(), block.getdType());
    }
}
