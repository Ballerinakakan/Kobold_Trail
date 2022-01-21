public class Magic
{

    public Magic(){
    }

    private void failiure(){
        //Inflict damage on the caster, should have learned the system SMH
    }

    private void error(String errorType){
        //Print the error message
    }

    public void cast(Creature caster, Aspects major, Aspects minor, Object target, String... context ){
        if(((minor == caster.minorAspect || minor == caster.majorAspect || minor == Aspects.BROWN) &&
                (major == caster.majorAspect || major == caster.minorAspect)) || caster.getClass() != Kobold.class) {
            switch (major) {
                case RED:
                    switch (minor) {
                        case GREEN:
                            if (target.getClass() == Kobold.class) {
                                castRG((Kobold) target);
                            } else error("target");
                            break;
                        case BLACK:
                            if (target.getClass() == Equipment.class) {
                                castRB((Equipment) target);
                            } else error("target");
                            break;
                        case WHITE:
                            if (target.getClass() == Kobold.class) {
                                castRW((Kobold) target);
                            } else error("target");
                            break;
                        case YELLOW:
                            if (target.getClass() == Resources.class) {
                                castRY((Resources) target);
                            } else error("target");
                            break;
                        default:
                            if (target.getClass() == Kobold.class) {
                                castRR((Kobold) target);
                            } else error("target");
                            break;
                    }
                    break;
                case GREEN:
                    switch (minor) {
                        case RED:
                            if (target.getClass() == Kobold.class) {
                                castRG((Kobold) target);
                            } else error("target");
                            break;
                        case BLACK:
                            if (target.getClass() == Kobold.class) {
                                castGB((Kobold) target, context[0]);
                            } else error("target");
                            break;
                        case WHITE:
                            if (target.getClass() == Kobold.class) {
                                castGW((Kobold) target);
                            } else error("target");
                            break;
                        case YELLOW:
                            if (target.getClass() == Kobold.class) {
                                castGY((Kobold) target);
                            } else error("target");
                            break;
                        default:
                            if (target.getClass() == Creature.class) {
                                castGG((Creature) target);
                            } else error("target");
                            break;
                    }
                    break;
                case BLACK:
                    switch (minor) {
                        case RED:
                            if (target.getClass() == Equipment.class) {
                                castRB((Equipment) target);
                            } else error("target");
                            break;
                        case GREEN:
                            if (target.getClass() == Kobold.class) {
                                castGB((Kobold) target, context[0]);
                            } else error("target");
                            break;
                        case WHITE:
                            if (target.getClass() == Kobold.class) {
                                castBW((Kobold) target);
                            } else error("target");
                            break;
                        case YELLOW:
                            if (target.getClass() == Kobold.class) {
                                castBY((Kobold) target);
                            } else error("target");
                            break;
                        default:
                            if (target.getClass() == Kobold.class) {
                                castBB((Kobold) target);
                            } else error("target");
                            break;
                    }
                    break;
                case WHITE:
                    switch (minor) {
                        case RED:
                            if (target.getClass() == Creature.class) {
                                castRW((Creature) target);
                            } else error("target");
                            break;
                        case GREEN:
                            if (target.getClass() == Creature.class) {
                                castGW((Creature) target);
                            } else error("target");
                            break;
                        case BLACK:
                            if (target.getClass() == Kobold.class) {
                                castBW((Kobold) target);
                            } else error("target");
                            break;
                        case YELLOW:
                            if (target.getClass() == Kobold.class) {
                                castWY((Kobold) target);
                            } else error("target");
                            break;
                        default:
                            if (target.getClass() == Kobold.class) {
                                castWW((Kobold) target);
                            } else error("target");
                            break;
                    }
                    break;
                case YELLOW:
                    switch (minor) {
                        case RED:
                            if (target.getClass() == Resources.class) {
                                castRY((Resources) target);
                            } else error("target");
                            break;
                        case GREEN:
                            if (target.getClass() == Creature.class) {
                                castGY((Creature) target);
                            } else error("target");
                            break;
                        case BLACK:
                            if (target.getClass() == Kobold.class) {
                                castBY((Kobold) target);
                            } else error("target");
                            break;
                        case WHITE:
                            if (target.getClass() == Kobold.class) {
                                castWY((Kobold) target);
                            } else error("target");
                            break;
                        default:
                            if (target.getClass() == Creature.class) {
                                castYY((Creature) target);
                            } else error("target");
                            break;
                    }
                    break;
            }
        }else failiure();
    }

    private void castRR(Kobold target){

    }
    private void castRG(Kobold target){

    }
    private void castRB(Equipment target){

    }
    private void castRW(Creature target){

    }
    private void castRY(Resources target){

    }
    private void castGG(Creature target){

    }
    private void castGB(Kobold target, String context){

    }
    private void castGW(Creature target){

    }
    private void castGY(Creature target){

    }
    private void castBB(Kobold target){

    }
    private void castBW(Kobold target){

    }
    private void castBY(Kobold target){

    }
    private void castWW(Kobold target){

    }
    private void castWY(Kobold target){

    }
    private void castYY(Creature target){

    }

}
