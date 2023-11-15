package mazesolver;

/**
 * Stores an x,y pair
 * @author Caleb Willson
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Constructor
     * @param X
     * @param Y
     */
    public Coordinate(int X, int Y) {
        x = X;
        y = Y;
    }

    /**
     * Get the X value
     * @return X (int)
     */
    public int getX() { return x; }

    /**
     * Get the Y value
     * @return Y (int)
     */
    public int getY() { return y; }

    /**
     * Print the x,y pair
     */
    public void print() {
        System.out.println("<" + y + " " + x + ">");
    }
}
