import java.util.HashSet;
import java.util.Set;

public class Tribe {
    private String name;
    private Set<Techs> unlockedResearches = new HashSet<>();
    private Kobold chief;
    private Kobold overseer;
    private final Kobold founder;

    public Tribe(String name, Kobold founder){
        this.founder = founder;
        chief = founder;
        this.name = name;
    }
}
