package uoft.csc207.gameapplication.Games.MazeGame.GameLogic;


public class MazeGame {
    /**
     *  if this mazeGame needs to be reinitialized with the new game screen
     */
    private boolean initializationStatus = false;

    /**
     * the maze generator used to create a new maze
     */
    private MazeGenerator mazeGenerator;

    /**
     * the 2d array representing the mapping of the maze
     */
    public Character[][] maze;

    /**
     * user position
     */
    private int xCharacter;
    private int yCharacter;

    /**
     * end point position
     */
    private int xEndPos = 0;
    private int yEndPos = 0;

    /**
     * current game status
     */

    private int currentLevel;

    private boolean gameIsOver;

    /**
     * game stats to keep track of
     */
    private long startTime;
    private int points = 0;
    private int currentLevelPoints = 2000;

    /**
     * Constructs the maze game
     */
    public MazeGame() {
        mazeGenerator = new MazeGenerator(7, 13);

        xCharacter = mazeGenerator.getStartingPoint()[0];
        yCharacter = mazeGenerator.getStartingPoint()[1];

        xEndPos = mazeGenerator.getEndPoint()[0];
        yEndPos = mazeGenerator.getEndPoint()[1];

        maze = mazeGenerator.getMaze();

        currentLevel = 1;
        gameIsOver = false;
    }

    /**
     * Checks to see if the user can move down if movement is possible complete it
     */
    public void moveDown() {

        if (!maze[xCharacter][yCharacter + 1].equals('W')) {
            maze[xCharacter][yCharacter + 1] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            yCharacter += 1;
            checkEndpointReached();
        }
    }

    /**
     * Checks to see if the user can move up if movement is possible complete it
     */
    public void moveUp() {
        if (!maze[xCharacter][yCharacter - 1].equals('W')) {
            maze[xCharacter][yCharacter - 1] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            yCharacter -= 1;
            checkEndpointReached();
        }
    }

    /**
     * Checks to see if the user can move left if movement is possible complete it
     */
    public void moveLeft() {
        if (!maze[xCharacter - 1][yCharacter].equals('W')) {
            maze[xCharacter - 1][yCharacter] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            xCharacter -= 1;
            checkEndpointReached();
        }
    }

    /**
     * Checks to see if the user can move right if movement is possible complete it
     */
    public void moveRight() {
        if (!maze[xCharacter + 1][yCharacter].equals('W')) {
            maze[xCharacter + 1][yCharacter] = maze[xCharacter][yCharacter];
            maze[xCharacter][yCharacter] = 'P';
            xCharacter += 1;
            checkEndpointReached();
        }
    }

    /**
     * @return the status of this game if it is over or not
     */
    public boolean getGameIsOver() {
        return gameIsOver;
    }

    /**
     * @return the current sessions points and the points accumulated over each level
     */
    public int getPoints() {
        if (currentLevelPoints > 0) {
            return points + currentLevelPoints;
        }
        else {
            return points;
        }
    }

    /**
     * checks to see if the user reached the end point if reached either the game ends
     * or we play again
     */
    private void checkEndpointReached() {
        if (xCharacter == xEndPos && yCharacter == yEndPos) {
            xEndPos = -1;
            yEndPos = -1;
            calculatePoints();
            if (currentLevel == 3) {
                gameIsOver = true;
            }
            else {
                currentLevel += 1;
                mazeGenerator.newMaze();
                maze = mazeGenerator.getMaze();
                xCharacter = mazeGenerator.getStartingPoint()[0];
                yCharacter = mazeGenerator.getStartingPoint()[1];
                xEndPos = mazeGenerator.getEndPoint()[0];
                yEndPos = mazeGenerator.getEndPoint()[1];
                initializationStatus = true;
            }
        }
    }

    /**
     * updates the time taken for this user and lowers the score for this level
     */
    public void update() {
        long timeTaken = System.currentTimeMillis() - startTime;
        currentLevelPoints = (int)(2000 - (timeTaken / 1000 * 60));
    }

    /**
     * adds the current level points to the total accumulated points and resets level points
     */
    private void calculatePoints() {
        long timeTaken = System.currentTimeMillis() - startTime;
        currentLevelPoints = (int)(2000 - (timeTaken / 1000 * 60));
        if (currentLevelPoints > 0) {
            points += currentLevelPoints;
        }
        currentLevelPoints = 2000;
        startTime = System.currentTimeMillis();
    }

    /**
     * gets this games 2-D character array maze representation
     * @return 2-D character array
     */
    public Character[][] getMaze() {
        return maze;
    }

    /**
     * gets this games (x,y) character position
     * @return Array with two values [x,y]
     */
    public int[] getCharacterPos() {
        return new int[]{xCharacter, yCharacter};
    }

    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    public boolean getInitializationStatus() {
        boolean status = initializationStatus;
        initializationStatus = false;
        return status;
    }
}
