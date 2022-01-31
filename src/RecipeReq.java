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
        return false;
    }
}
