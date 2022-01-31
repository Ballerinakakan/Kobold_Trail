public class Coordinate {
    private final int x;
    private final int y;

    public int getX(){return x;}
    public int getY(){return y;}
    public int distToCradle(){return (int)(Math.sqrt(x^2 + y^2) + 0.5);}

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
}
