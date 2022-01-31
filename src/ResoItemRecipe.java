import java.util.*;

public class ResoItemRecipe {
    private final Items item;
    private final Resources res;
    private final Set<Techs> reqTechs = new HashSet<>();
    private final Set<Building> reqBuildings = new HashSet<>();
    private final Set<RecipeReq> reqRecipeSet = new HashSet<>();

    public Items getItem() {
        return item;
    }

    public Resources getRes() {
        return res;
    }

    public boolean reqFulfilled(Kobold kob, Resources re){
        boolean tech = reqTechs.isEmpty(), res = false, build = reqBuildings.isEmpty();
        Map<Resources, Integer> availableResources = new HashMap<>();
        if (kob.getLocation().hasTown() && kob.isInTown()){
            Set<Techs> tTech = kob.getLocation().getTown().getTechs();
            Set<Techs> totTech = new HashSet<>();
            totTech.addAll(tTech);
            //totTech.addAll(kob.getFam()); Uncomment this line to add familiarity to crafting
            if (totTech.containsAll(reqTechs)){
                tech = true;
            }
            if (kob.getLocation().getTown().getBuildings().containsAll(reqBuildings)){
                build = true;
            }
            availableResources.putAll(kob.getLocation().getTown().getTownResources());
        }
        for (Map.Entry<Resources, Integer> e : kob.getLocation().getGroundResources().entrySet()) {
            if (availableResources.containsKey(e.getKey())){
                availableResources.put(e.getKey(), e.getValue() + availableResources.get(e.getKey()));
            }
        }
        for (Map.Entry<Resources, Integer> e : kob.getInventory().entrySet()) {
            if (availableResources.containsKey(e.getKey())){
                availableResources.put(e.getKey(), e.getValue() + availableResources.get(e.getKey()));
            }
        }
        for (RecipeReq rr : reqRecipeSet) {
            if (!rr.canAfford(availableResources)) {
                res = false;
                break;
            }
            else res = true;
        }


        return tech && res && build;
    }

    public ResoItemRecipe(Items i, Resources r, Techs[] t){
        item = i;
        res = r;
        reqTechs.addAll(Arrays.asList(t));

        //insert all recipes here in a FAT switch case
    }


}
