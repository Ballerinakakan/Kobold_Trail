public class NameList
{
    private final String[] maleNames =
	    new String[] { "Jep", "Kesk", "Nak", "Arkilk", "Vitrob", "Olnod", "Vetzi", "Brok", "Meank", "Qap", "Maax", "Dreab", "Zedhus", "Zikrik", "Votan", "Votan"};

    private final String[] femaleNames = //NOT DONE
	    new String[] { "Jep", "Kesk", "Nak", "Arkilk", "Vitrob", "Olnod", "Vetzi", "Brok", "Meank"};

    public String getMaleName(int x) {
	return maleNames[x];
    }

    public int getLength(char g){
	if (g == 'm') return maleNames.length;
	else return femaleNames.length;
    }
}
