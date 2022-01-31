import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Equipment {
    private Resources matterial;
    private Items type;
    private int durability, quality, accuracy, range=1;
    private int[] baseDamage = null;
    private int[] baseEfficiency = null;
    private DamageType dType = null;
    private Random rng = new Random();
    public final HashMap<Skills, Items> SKILLTYPE = new HashMap<>()
    {
        {
            put(Skills.SWORD, Items.SWORD);
            put(Skills.BOW, Items.BOW);
            put(Skills.AXE, Items.AXE);
            put(Skills.MAGIC, Items.STAFF);
            put(Skills.HEALING, Items.TOME);
            put(Skills.INSPIRE, Items.FLAG);
            put(Skills.BUILDING, Items.HAMMER);
            put(Skills.SMITHING, Items.HAMMER);
            put(Skills.CRAFTING, Items.HAMMER);
            put(Skills.FORAGING, Items.AXE);
            put(Skills.MINING, Items.PICKAXE);
            put(Skills.SHIELD, Items.SHIELD);
            put(Skills.FARMING, Items.HOE);
            put(Skills.SNEAKING, Items.KNIFE);
            put(Skills.COOKING, Items.KNIFE);
            put(Skills.PRAYING, Items.TOME);
            put(Skills.ALCHEMY, Items.TOME);
            put(Skills.SPEECH, Items.FLAG);
            put(Skills.PLANNING, Items.TOME);
        }
    };

    Equipment(Resources mat, Items type, int dur, int quali, int acc, int ran, int[] bDam, int[] bEff, DamageType dTy){
        matterial = mat;
        this.type = type;
        durability = dur;
        quality = quali;
        accuracy = acc;
        range = ran;
        baseDamage = bDam;
        baseEfficiency = bEff;
        dType = dTy;
    }

    @Override public String toString() {
        return "Equipment{" + "matterial=" + matterial + ", type=" + type + ", durability=" + durability + ", quality=" +
               quality + ", accuracy=" + accuracy + ", range=" + range + ", baseDamage=" + Arrays.toString(baseDamage) +
               ", baseEfficiency=" + Arrays.toString(baseEfficiency) + ", dType=" + dType + ", rng=" + rng + ", SKILLTYPE=" +
               SKILLTYPE + '}';
    }

    public void setBaseDamage(final int[] bDam) {
        if(baseDamage != null) this.baseDamage = bDam;
    }
    public void setBaseEfficiency(final int[] bEff) {
        if(baseEfficiency != null) this.baseEfficiency = bEff;
    }

    public int rollDamage(HashMap<Skills, Integer> skillMap, int luck){
        int damage = 0;
        for (int i = 0; i <= baseDamage[0]; i++) {
            damage += rng.nextInt(baseDamage[1]) + 1;
        }
        damage += baseDamage[2];
        for (Map.Entry<Skills, Integer> skill : skillMap.entrySet()) {
            if(SKILLTYPE.get(skill.getKey()) == type){
                damage += rng.nextInt(skill.getValue()+1)/2;
                break;
            }
        }
        if(rng.nextInt(100) <= luck) damage += damage/2;
        damage += quality/(rng.nextInt(5)+1);
        return damage;
    }

    public Items getType(){
        return type;
    }

    public int rollEfficiency(HashMap<Skills, Integer> skillMap, int luck){
        int efficiency = 0;
        for (int i = 0; i <= baseEfficiency[0]; i++) {
            efficiency += rng.nextInt(baseEfficiency[1]) + 1;
        }
        efficiency += baseEfficiency[2];
        for (Map.Entry<Skills, Integer> skill : skillMap.entrySet()) {
            if(SKILLTYPE.get(skill.getKey()) == type){
                efficiency += rng.nextInt(skill.getValue()+1)/2;
                break;
            }
        }
        efficiency += quality/(rng.nextInt(5)+1);
        if(rng.nextInt(100) <= luck) efficiency += efficiency/2;
        return efficiency;
    }
}

