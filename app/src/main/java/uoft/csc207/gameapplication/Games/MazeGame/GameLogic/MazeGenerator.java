package uoft.csc207.gameapplication.Games.MazeGame.GameLogic;

import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
    /**
     * the maze game data points
     */
    private Character[][] maze;
    private int[] startingPoint;
    private int[] endPoint;

    /**
     * the height and width of the maze
     */
    private int width;
    private int height;

    /**
     * creates the mazeGenerator
     *
     * @param width how many columns the user wants
     * @param height how many rows the the user wants
     */
    MazeGenerator(int width, int height) {
        this.width = 2 * width + 1;
        this.height = 2 * height + 1;
        this.startingPoint =  new int[2];
        this.endPoint = new int[2];
        this.startingPoint[0] = 1;
        this.startingPoint[1] = 1;
        this.endPoint[0] = -1;
        this.endPoint[1] = -1;
        newMaze();
    }

    /**
     *
     * @return the starting point of the character
     */
    int[] getStartingPoint() {
        return startingPoint;
    }

    /**
     *
     * @return the end point of the maze
     */
    int[] getEndPoint() {
        return endPoint;
    }

    /**
     *
     * @return returns the maze board for drawing
     */
    Character[][] getMaze() {
        return maze;
    }

    /**
     * creates a new maze and creates a new endpoint and starting point
     */
    void newMaze() {
        maze = generateMaze(width, height);
    }

    /**
     *
     * @param unitWidth how many pixels wide the maze is
     * @param unitHeight how many pixels tall the maze is
     * @return the 2d array representation of the maze
     */
    private Character[][] generateMaze(int unitWidth, int unitHeight) {
        Character[][] blocks = new Character[unitWidth][unitHeight];
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                blocks[x][y] = 'W';
            }
        }
        recursiveMazeGeneration(blocks, 1, 1);
        setEnd(blocks);
        blocks[1][1] = 'S';
        return blocks;
    }

    /**
     * a recursive back tracking maze algorithm to mutate the maze
     * @param maze a 2d maze still being generated
     * @param startX x coordinate to branch from
     * @param startY y coordinate to branch from
     */
    private void recursiveMazeGeneration(Character[][] maze, int startX, int startY) {
        maze[startX][startY] = 'P';
        boolean flag = true;
        while (flag) {
            ArrayList<int[]> moveBranch = new ArrayList<>();
            if (possiblePath(maze, startX, startY + 2)) {
                moveBranch.add(new int[]{0, 2});
            }
            if (possiblePath(maze, startX + 2, startY)) {
                moveBranch.add(new int[]{2, 0});
            }
            if (possiblePath(maze, startX - 2, startY)) {
                moveBranch.add(new int[]{-2, 0});
            }
            if (possiblePath(maze, startX, startY - 2)) {
                moveBranch.add(new int[]{0, -2});
            }
            if (moveBranch.size() == 0) {
                flag = false;
            }
            else {
                int randomNum = new Random().nextInt(moveBranch.size());
                int dx = moveBranch.get(randomNum)[0];
                int dy = moveBranch.get(randomNum)[1];
                maze[startX + dx/2][startY + dy/2] = 'P';
                recursiveMazeGeneration(maze, startX + dx, startY + dy);
            }
        }
    }

    /**
     * determines whether if we can create a path to this startX and startY coordinate
     * @param maze a 2d maze still being generated
     * @param startX x coordinate to check
     * @param startY y coordinate to check
     * @return true if we can create a path to this node
     */
    private boolean possiblePath(Character[][] maze, int startX, int startY) {
        if (0 < startX && startX < maze.length && 0 < startY && startY < maze[startX].length) {
            if (maze[startX][startY].equals('W')) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a spot in the bottom corner of the screen that is tucked into a corner to set
     * as the end point
     * @param maze a maze with the path finished generating
     */
    private void setEnd(Character[][] maze) {
        for (int x = maze.length - 2; x > 0; x -= 2) {
            int yCheck = maze[0].length - 2;
            int xCheck = x;
            boolean flag = false;
            while (xCheck < maze.length - 1) {
                if (checkSurrounding(maze, xCheck, yCheck)) {
                    flag = true;
                    maze[xCheck][yCheck] = 'E';
                    endPoint[0] = xCheck;
                    endPoint[1] = yCheck;
                    break;
                }
                xCheck += 2;
                yCheck -= 2;
            }
            if (flag) {
                break;
            }
        }
    }

    /**
     * checks to see if there only exists one path to reach this node by its adjacent nodes
     * @param maze a maze with the paths finished generating
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return whether if there only exists one path to this node form the adjacent nodes
     */
    private boolean checkSurrounding(Character[][] maze, int x, int y) {
        int count = 0;
        if (maze[x + 1][y].equals('W')) {
            count += 1;
        }
        if (maze[x][y + 1].equals('W')) {
            count += 1;
        }
        if (maze[x - 1][y].equals('W')) {
            count += 1;
        }
        if (maze[x][y - 1].equals('W')) {
            count += 1;
        }
        return count > 2;
    }
}
