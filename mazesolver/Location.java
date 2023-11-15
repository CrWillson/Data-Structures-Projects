package mazesolver;

/**
 * Stores the information of an individual location in the maze.
 * @author Caleb Willson
 */
public class Location {
    private Location prevCell;
    private Coordinate coords;
    private char type;
    private int dist;
    private boolean scanned;

    /**
     * Constructor. Sets the x and y coord and the type of cell
     * @param x
     * @param y
     * @param Type
     */
    public Location(int x, int y, char Type) {
        coords = new Coordinate(x, y);
        type = Type;
        prevCell = null;
        scanned = false;
    }

    /**
     * Recursively print the entire path to the 
     * calling location from the start. If it is the target location,
     * also print the total distance.
     */
    public void printCoords() {
        if (prevCell != null) {
            prevCell.printCoords();
        }

        coords.print();

        if (type == 'T') {
            System.out.println("Total distance = " + dist);
        }
    }

    /**
     * Get the type of location
     * @return location type (char)
     */
    public char getType() { return type; }

    /**
     * Get the previous location
     * @return previous location (Location)
     */
    public Location getPrevLocation() { return prevCell; }

    /**
     * Get the distance to the starting location
     * @return distance (int)
     */
    public int getDist() { return dist; }

    /**
     * Get the coordinates of the location
     * @return coords (Coordinate)
     */
    public Coordinate getCoord() { return coords; }

    /**
     * Return true if the location has been scanned
     * @return scanned (boolean)
     */
    public boolean isScanned() { return scanned; }

    /**
     * Set the previous location
     * @param prev (Location)
     */
    public void setPrevCell(Location prev) { prevCell = prev; }

    /**
     * Set the distance from the starting location
     * @param newDist (int)
     */
    public void setDist(int newDist) { dist = newDist; }    

    /**
     * Set whether the location has been scanned
     * @param newVal (boolean)
     */
    public void setScanned(boolean newVal) { scanned = newVal; }
}
