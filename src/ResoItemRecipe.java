import java.util.*;

public class ResoItemRecipe {
    private final Items item;
    private final Set<Techs> reqTechs = new HashSet<>();
    private final Set<Building> reqBuildings = new HashSet<>();
    private final Set<RecipeReq> reqRecipeSet = new HashSet<>();

    public Items getItem() {
        return item;
    }

    public boolean reqFulfilled(Resources r, Kobold kob){
        Set<RecipeReq> incRes = reqRecipeSet;
        Set<Techs> reqTechRes = reqTechs;
        incRes.addAll(getSpecRes(r));
        reqTechRes.addAll(getSpecTech(r));

        boolean tech = reqTechs.isEmpty(), res = false, build = reqBuildings.isEmpty();
        Map<Resources, Integer> availableResources = new HashMap<>();
        if (kob.getLocation().getClass() == Town.class){
            Set<Techs> tTech = kob.getLocation().getTechs();
            Set<Techs> totTech = new HashSet<>();
            totTech.addAll(tTech);
            //totTech.addAll(kob.getFam()); //Uncomment this line to add familiarity to crafting
            if (totTech.containsAll(reqTechs)){
                tech = true;
            }
            if (kob.getLocation().getBuildings().containsAll(reqBuildings)){
                build = true;
            }
            availableResources.putAll(kob.getLocation().getLocalResources());
        }
        for (Map.Entry<Resources, Integer> e : kob.getLocation().getLocalResources().entrySet()) {
            if (availableResources.containsKey(e.getKey())){
                availableResources.put(e.getKey(), e.getValue() + availableResources.get(e.getKey()));
            }
        }
        for (Map.Entry<Resources, Integer> e : kob.getInventoryRes().entrySet()) {
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

        if (tech && res && build){
            for (RecipeReq rr : reqRecipeSet) {
                rr.removeRes(kob);
            }
        }

        return tech && res && build;
    }

    private Set<RecipeReq> getSpecRes(Resources r){
        Set<RecipeReq> res = new HashSet<>();
        switch (item){
            case KNIFE:
            case BOW:
            case HOE:
            case BANNER:
            case TOME:
                res.add(new RecipeReq(r, 1));
                break;
            case AXE:
            case HELMET:
            case STAFF:
            case CLUB:
            case SWORD:
            case HAMMER:
                res.add(new RecipeReq(r, 2));
                break;
            case PICKAXE:
            case SHIELD:
                res.add(new RecipeReq(r, 3));
                break;
            case ARMOUR:
                res.add(new RecipeReq(r, 5));
                break;
        }
        return res;
    }

    private Set<Techs> getSpecTech(Resources r){
        Set<Techs> res = new HashSet<>();
        switch (item){
            case KNIFE:
            case BOW:
            case HOE:
            case HELMET:
            case CLUB:
            case HAMMER:
            case PICKAXE:
                res.add(Techs.BASIC_TOOLS);
                break;
            case SWORD:
            case BANNER:
            case TOME:
            case AXE:
            case STAFF:
            case SHIELD:
            case ARMOUR:
            case BACKPACK:
        }
        return res;
    }

    public ResoItemRecipe(Items i){ //ADD REQTECH TO EACH OF THE EQUIPMENTS!!!!
        item = i;

        switch (i){
            case AXE:
            case KNIFE:
            case HAMMER:
            case SWORD:
            case CLUB:
            case PICKAXE:
                reqTechs.add(Techs.BASIC_TOOLS);
                reqRecipeSet.add(new RecipeReq(Resources.STICK, 1));
                break;
            case SHIELD:
                reqRecipeSet.add(new RecipeReq(Resources.STICK, 1));
                break;
            case BOW:
                reqRecipeSet.add(new RecipeReq(Resources.FIBER, 10));
            case HOE:
                reqRecipeSet.add(new RecipeReq(Resources.FIBER, 3));
            case STAFF:
                reqRecipeSet.add(new RecipeReq(Resources.STICK, 3));
                break;
            case BANNER:
                reqRecipeSet.add(new RecipeReq(Resources.STICK, 3));
            case BACKPACK:
                reqRecipeSet.add(new RecipeReq(Resources.LEATHER, 4));
            case TOME:
                reqRecipeSet.add(new RecipeReq(Resources.FIBER, 4));
                break;
        }
    }


}
