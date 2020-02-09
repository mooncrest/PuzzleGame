package uoft.csc207.gameapplication.Games.MazeGame.Controller;

public class TapController extends MazeController{

    /**
     * The accepted error in case you meant to swipe instead of tapping the screen.
     */
    private double acceptedError;

    /**
     * the angle formed by connecting the two top corners of the screen to the center of the
     * screen ie. "V" shaped angle used to split quadrants.
     */
    private double quadrantOneAngle;

    /**
     * The vector from the top left corner to the center of the screen and the negative version
     *
     */
    private double[] vectorToCenterOfScreen;
    private double[] negativeCenterToScreenVector;

    /**
     * Creates the tap controller and initializes some variables
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public TapController(double screenWidth, double screenHeight) {
        acceptedError = 40;
        double normLength = normLength(new double[] {screenWidth, screenHeight});
        vectorToCenterOfScreen = new double[]{screenWidth / 2, screenHeight / 2};
        negativeCenterToScreenVector = new double[] {-screenWidth / normLength, -screenHeight / normLength};
        initializeQuadrants(screenWidth, screenHeight);
    }

    /**
     * determines and sets up the angle formed by connecting the two top corners of the screen
     * to the center of the screen
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    private void initializeQuadrants(double screenWidth, double screenHeight) {
        quadrantOneAngle = Math.PI - 2 * Math.atan(screenHeight/screenWidth);
    }

    /**
     * calculates the dot product of these two vectors
     * @param vector1 a double array representing a vector in R^n
     * @param vector2 a double array representing a vector in R^n
     * @return the dot product of these 2 vectors
     */
    private double dotProduct(double[] vector1, double[] vector2) {
        double dotSum = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotSum += vector1[i] * vector2[i];
        }
        return dotSum;
    }

    /**
     * calculates the length of the vector
     * @param vector a double array representing a vector in R^n
     * @return the length of this vector
     */
    private double normLength(double[] vector) {
        double nonSquaredLength = 0;
        for (int i = 0; i < vector.length; i++) {
            nonSquaredLength += Math.pow(vector[i], 2);
        }
        return Math.sqrt(nonSquaredLength);
    }

    /**
     * A helper class to find which quadrant is clicked on the screen. the screen is split into 4
     * portions like and "X" shape similar like the regular 4 quadrants in R^2 rotated 45 degrees.
     * @return integer that represents game movement 1 is move up,
     * 2 is move right, 3 is move down, 4 is move left
     */
    private int findQuadrant() {
        double horizontalLength = startX - vectorToCenterOfScreen[0];
        double verticalLength = startY - vectorToCenterOfScreen[1];
        double[] tapVectorFromCenter = new double[]{horizontalLength, verticalLength};


        double dotValue = dotProduct(negativeCenterToScreenVector, tapVectorFromCenter);
        double normSum = normLength(negativeCenterToScreenVector) * normLength(tapVectorFromCenter);
        double theta = Math.acos(dotValue / normSum);

        double slope = negativeCenterToScreenVector[1] / negativeCenterToScreenVector[0];

        if (horizontalLength * slope > verticalLength) {
            return theta < quadrantOneAngle ? 1 : 2;
        }
        else {
            return theta < Math.PI - quadrantOneAngle ? 4 : 3;
        }
    }

    /**
     * Called when the user lets go of the screen press
     * @return integer that represents game movement -1 is do nothing, 1 is move up,
     * 2 is move right, 3 is move down, 4 is move left
     */
    @Override
    public int touchUp() {
        Double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        if (distance < acceptedError) {
            int quadrant = findQuadrant();
            return quadrant;
        }
        return -1;
    }
}
