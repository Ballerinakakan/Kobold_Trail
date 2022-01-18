public class Statblock
{
    private final int durability;
    private final int accuracy;
    private final int range;
    private final int[] bDamage;
    private final int[] bEfficiency;
    private final DamageType dType;

    public int getDurability() {
	return durability;
    }

    public int getAccuracy() {
	return accuracy;
    }

    public int getRange() {
	return range;
    }

    public int[] getbDamage() {
	return bDamage;
    }

    public int[] getbEfficiency() {
	return bEfficiency;
    }

    public DamageType getdType() {
	return dType;
    }

    public Statblock(final int durability, final int accuracy, final int range, final int[] bDamage,
		     final int[] bEfficiency, final DamageType dType) {
	this.durability = durability;
	this.accuracy = accuracy;
	this.range = range;
	this.bDamage = bDamage;
	this.bEfficiency = bEfficiency;
	this.dType = dType;
    }
}
