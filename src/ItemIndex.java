import java.util.HashMap;
import java.util.Set;

public class ItemIndex
{

    private static final HashMap<Items, Statblock> testItemIndex = new HashMap<Items, Statblock>();
    private static final HashMap<Items, Statblock> woodItemIndex = new HashMap<Items, Statblock>();
    private static final HashMap<Items, Statblock> boneItemIndex = new HashMap<Items, Statblock>();

    private static final HashMap<Items, ResoItemRecipe> recipeMap = new HashMap<>();

    private static final HashMap<Items, ResoItemRecipe> recipes = new HashMap<>();

    private static final HashMap<Resources, HashMap<Items, Statblock>> matItemIndex = new HashMap<Resources, HashMap<Items, Statblock>>();
    private static final HashMap<Resources, HashMap<Items, Set<RecipeReq>>> matItemRecipeIndex = new HashMap<Resources, HashMap<Items, Set<RecipeReq>>>();

    static {
        woodItemIndex.put(Items.HOE, new Statblock(10, 95, 5, null, new int[] {1, 6, 0}, DamageType.TOOL));
        woodItemIndex.put(Items.PICKAXE, new Statblock(10, 95, 5, null, new int[] {1, 6, 0}, DamageType.TOOL));
        woodItemIndex.put(Items.HAMMER, new Statblock(20, 95, 5, null, new int[] {1, 6, 0}, DamageType.TOOL));
        woodItemIndex.put(Items.STAFF, new Statblock(15, 50, 20, new int[] {1, 4, 0}, new int[] {1, 4, 0}, DamageType.MAGICAL));
        woodItemIndex.put(Items.CLUB, new Statblock(10, 95, 5, new int[] {1, 4, -1}, new int[] {1, 4, -1}, DamageType.BLUDGEONING));
        woodItemIndex.put(Items.BANNER, new Statblock(30, 100, 20, new int[] {1, 3, -1}, new int[] {1, 4, 0}, DamageType.BLUDGEONING));
        woodItemIndex.put(Items.BOW, new Statblock(20, 20, 20, new int[] {1, 2, 0}, null, DamageType.PIERCING));// NEEDS FIXING TO BE BASED ON ARROW INSTEAD OF BOW
        woodItemIndex.put(Items.SWORD, new Statblock(15, 95, 5, new int[] {1, 2, -1}, null, DamageType.BLUDGEONING));

        boneItemIndex.put(Items.SWORD, new Statblock(15, 95, 5, new int[] {1, 4, 0}, new int[] {1, 2, 0}, DamageType.SLASHING));
        boneItemIndex.put(Items.AXE, new Statblock(20, 85, 5, new int[] {2, 4, -2}, new int[] {1, 6, 0}, DamageType.SLASHING));
        boneItemIndex.put(Items.CLUB, new Statblock(10, 95, 5, new int[] {1, 4, 0}, new int[] {1, 4, -2}, DamageType.PIERCING));
        boneItemIndex.put(Items.KNIFE, new Statblock(15, 75, 3, new int[] {1, 3, -1}, new int[] {1, 8, 0}, DamageType.PIERCING));
        boneItemIndex.put(Items.PICKAXE, new Statblock(20, 95, 5, null, new int[] {1, 8, 0}, DamageType.TOOL));
        boneItemIndex.put(Items.HOE, new Statblock(10, 95, 5, null, new int[] {1, 8, 0}, DamageType.TOOL));

        testItemIndex.put(Items.SWORD, new Statblock(15, 95, 5, new int[] {1, 4, 0}, new int[] {1, 2, 0}, DamageType.SLASHING));
        testItemIndex.put(Items.AXE, new Statblock(20, 85, 5, new int[] {2, 4, -2}, new int[] {1, 6, 0}, DamageType.SLASHING));
        testItemIndex.put(Items.BOW, new Statblock(20, 85, 5, new int[] {2, 4, -2}, new int[] {1, 6, 0}, DamageType.PIERCING));
        testItemIndex.put(Items.STAFF, new Statblock(20, 85, 5, new int[] {2, 4, -2}, new int[] {1, 6, 0}, DamageType.MAGICAL));
        testItemIndex.put(Items.TOME, new Statblock(20, 85, 5, new int[] {2, 4, -2}, new int[] {1, 6, 0}, DamageType.MAGICAL));
        testItemIndex.put(Items.BANNER, new Statblock(20, 85, 5, new int[] {2, 4, -2}, new int[] {1, 6, 0}, DamageType.PIERCING));
        testItemIndex.put(Items.CLUB, new Statblock(10, 95, 5, new int[] {1, 4, 0}, new int[] {1, 4, -2}, DamageType.BLUDGEONING));
        testItemIndex.put(Items.KNIFE, new Statblock(15, 75, 3, new int[] {1, 3, -1}, new int[] {1, 8, 0}, DamageType.PIERCING));
        testItemIndex.put(Items.PICKAXE, new Statblock(20, 95, 5, null, new int[] {1, 8, 0}, DamageType.TOOL));
        testItemIndex.put(Items.HAMMER, new Statblock(20, 95, 5, null, new int[] {1, 8, 0}, DamageType.TOOL));
        testItemIndex.put(Items.HOE, new Statblock(10, 95, 5, null, new int[] {1, 8, 0}, DamageType.TOOL));

        matItemIndex.put(Resources.MUSHROOM, testItemIndex);
        matItemIndex.put(Resources.BONE, boneItemIndex);
        matItemIndex.put(Resources.WOOD, woodItemIndex);

        //CRAFTING RECIPES
        for (Items i : Items.values()) {        //THIS IS SOME SEXY FUCKING CODE!
            recipeMap.put(i, new ResoItemRecipe(i));
        }


    }

    public Statblock getStatblock(Resources mat, Items item){
        HashMap<Items, Statblock> itemFolder = (HashMap<Items, Statblock>) matItemIndex.get(mat);
        return itemFolder.get(item);
    }

    public boolean canCraft(Items i, Resources r, Kobold kob){
        ResoItemRecipe rip = recipeMap.get(i);
        return rip.reqFulfilled(r, kob);
    }

}
