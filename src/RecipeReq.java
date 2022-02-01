import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RecipeReq {
    private final Resources res;
    private final int amount;

    public RecipeReq(Resources res, int num){
        this.res = res;
        amount = num;
    }

    public boolean canAfford(Map<Resources, Integer> aR){
        //Check if the area around kob has the amount of required resources to pass this requirement
        if (aR.containsKey(res)){
            return aR.get(res) - amount >= 0;
        }
        return false;
    }

    public void removeRes(Kobold kob){
        Map<Resources, Integer> kobInv = kob.getInventoryRes();
        int toUse = amount;
        if (kobInv.containsKey(res)){
            toUse -= kobInv.get(res);
            kobInv.put(res, kobInv.get(res) - amount);
            if (kobInv.get(res) <= 0){
                kob.remInventoryRes(res);
            }
            if(toUse > 0){
                kob.getLocation().useLocalResources(res, toUse);
            }
        }
        else{
            kob.getLocation().useLocalResources(res, toUse);
        }
    }
}
