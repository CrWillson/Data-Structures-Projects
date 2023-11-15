package mazesolver;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Maze Solving algorithm. Takes a valid maze input from Standard.in and prints
 * the shortest path to the target cell if it is possible.
 * @author Caleb Willson
 * @version 1.0
 */
public class MazeSolver {
    public static void main(String[] args) {
        String inFileName = "mazesolver/project5_maze.in";
        int col;
        int row;
        Location[][] maze;
        Scanner scan = null;
        Queue<Location> pathQueue = new LinkedList<>();
        Location target = null;

        //scan = new Scanner(System.in);

        try {
            scan = new Scanner(new File(inFileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }
        
        // load the maze into the 2d maze array
        row = scan.nextInt();
        col = scan.nextInt();
        maze = new Location[row][col];

        for (int i = 0; i < row; i++) {
            String currRow = scan.next();
            for (int j = 0; j < col; j++) {
                maze[i][j] = new Location(j, i, currRow.charAt(j));
                if (currRow.charAt(j) == 'S') {
                    pathQueue.add(maze[i][j]);
                }
            }
        }

        scan.close();

        // scan the maze until the queue is empty
        while (!pathQueue.isEmpty()) {
            Location currCell = pathQueue.remove();

            if (currCell.getType() == 'T') {
                target = currCell;
                break;
            }

            int x = currCell.getCoord().getX();
            int y = currCell.getCoord().getY();

            // check right neighbor
            if (x + 1 < col && 
                maze[y][x + 1].isScanned() == false &&
                maze[y][x + 1].getType() != 'X') 
            {
                maze[y][x + 1].setDist(currCell.getDist() + 1);
                maze[y][x + 1].setPrevCell(currCell);
                pathQueue.add(maze[y][x + 1]);
            }

            // check left neighbor
            if (x - 1 >= 0 && 
                maze[y][x - 1].isScanned() == false &&
                maze[y][x - 1].getType() != 'X') 
            {
                maze[y][x - 1].setDist(currCell.getDist() + 1);
                maze[y][x - 1].setPrevCell(currCell);
                pathQueue.add(maze[y][x - 1]);
            }

            // check lower neighbor
            if (y + 1 < row && 
                maze[y + 1][x].isScanned() == false &&
                maze[y + 1][x].getType() != 'X') 
            {
                maze[y + 1][x].setDist(currCell.getDist() + 1);
                maze[y + 1][x].setPrevCell(currCell);
                pathQueue.add(maze[y + 1][x]);
            }

            // check upper neighbor
            if (y - 1 >= 0 && 
                maze[y - 1][x].isScanned() == false &&
                maze[y - 1][x].getType() != 'X') 
            {
                maze[y - 1][x].setDist(currCell.getDist() + 1);
                maze[y - 1][x].setPrevCell(currCell);
                pathQueue.add(maze[y - 1][x]);
            }

            maze[y][x].setScanned(true);
        }

        // print the path if it was solved
        if (target != null) {
            target.printCoords();
        }
        else {
            System.out.println("Maze not solvable.");
        }
    }
}