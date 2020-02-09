package uoft.csc207.gameapplication.Games.MazeGame.Presenter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to parse maze game data to draw the maze
 */
public class MazePresenter {
    /**
     * A list of static blocks used for drawing.
     */
    private List<DrawingBlock> fixedBlocks = new ArrayList<>();

    /**
     * A list of dynamic blocks that changes based off user positions.
     */
    private List<DrawingBlock> characterBlocks = new ArrayList<>();

    /**
     * These values are used for initialization of the static mazeBlocks
     */
    private int xCord = 0;
    private int yCord = 0;
    private boolean initialized = false;

    /**
     * The screen height and width
     */
    private int screenWidth;
    private int screenHeight;

    /**
     * A array of paints should have exactly 3 in each for use in DrawingBlock or else animation
     * or initialization may not work
     */
    private Paint[] wallBlockPaints = new Paint[]{new Paint(), new Paint(), new Paint()};
    private Paint[] userBlockPaints = new Paint[]{new Paint(), new Paint(), new Paint()};
    private Paint[] endBlockPaints = new Paint[]{new Paint(), new Paint(), new Paint()};

    /**
     * Constructs this maze game presenter and initializes the paints
     * @param screenWidth the screen width
     * @param screenHeight the screen height
     */
    public MazePresenter(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        initializePaints();
    }

    /**
     * A helper to simplify constructor and could be swapped out easily in the future.
     */
    private void initializePaints() {
        wallBlockPaints[0].setColor(Color.rgb(180, 180, 180));
        wallBlockPaints[1].setColor(Color.rgb(64, 64, 64));
        wallBlockPaints[2].setColor(Color.rgb(20, 20, 20));

        for (Paint paint : wallBlockPaints) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
        }

        userBlockPaints[0].setColor(Color.rgb(204, 255, 204));
        userBlockPaints[1].setColor(Color.rgb(102, 255, 102));
        userBlockPaints[2].setColor(Color.rgb(51, 255, 51));

        for (Paint paint : userBlockPaints) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
        }

        endBlockPaints[0].setColor(Color.rgb(255, 204, 204));
        endBlockPaints[1].setColor(Color.rgb(255, 102, 102));
        endBlockPaints[2].setColor(Color.rgb(240, 0, 0));

        for (Paint paint : endBlockPaints) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
        }
    }

    /**
     * Initializes 1 block per frame to create a fancy effect
     * @param maze a 2-D character array representation of the maze
     * @param blockWidth the width of a block
     */
    private void initialize(Character[][] maze, int blockWidth) {
        while (yCord < maze[0].length) {
            while (xCord < maze.length) {

                if (maze[xCord][yCord].equals('W')) {

                    DrawingBlock wallBlock = new DrawingBlock(xCord, yCord, blockWidth);
                    wallBlock.setPaints(wallBlockPaints);
                    fixedBlocks.add(wallBlock);

                    ++xCord;
                    return;

                } else if (maze[xCord][yCord].equals('E')) {

                    DrawingBlock endBlock = new DrawingBlock(xCord, yCord, blockWidth);
                    endBlock.setPaints(endBlockPaints);
                    fixedBlocks.add(endBlock);

                    ++xCord;
                    return;
                }
                ++xCord;
            }
            xCord = 0;
            ++yCord;
        }
        initialized = true;
    }

    /**
     * draws the maze game with the fancy effects
     *
     * @param canvas the graphics canvas where we draw on
     * @param maze a 2-D character array representation of the maze
     * @param characterPos 2 values in this array containing the users (x,y) position on screen
     */
    public void draw(Canvas canvas, Character[][] maze, int[] characterPos) {
        int blockWidth = screenWidth / maze.length;
        int blockHeight = screenHeight / maze[0].length;

        if (!initialized) {
            initialize(maze, blockWidth);
        }

        for (DrawingBlock block: fixedBlocks) {
            block.draw(canvas);
        }

        drawCharacter(canvas, characterPos, blockWidth);
    }

    /**
     * A helper to help create the character block and will not draw unless game is initialized.
     * @param canvas the graphics canvas to draw on
     * @param characterPos 2 values in this array containing the users (x,y) position on screen
     * @param blockWidth the width of the block
     */
    private void drawCharacter(Canvas canvas, int[] characterPos, int blockWidth) {
        if (initialized) {
            DrawingBlock lastCharacterBlock = getLastCharacterBlock();
            DrawingBlock newCharacterBlock = new DrawingBlock(characterPos[0], characterPos[1], blockWidth);
            if (lastCharacterBlock == null || !lastCharacterBlock.equals(newCharacterBlock)) {

                newCharacterBlock.setPaints(userBlockPaints);
                characterBlocks.add(newCharacterBlock);
            }

            for (DrawingBlock character: characterBlocks) {
                character.draw(canvas);
            }

            deleteOldCharacters();
        }
    }

    /**
     * A helper for drawCharacter to get the last block added to the list to make sure
     * that we don't draw multiple blocks on same tile and lose memory
     * @return the last drawing block in the character block array list
     */
    private DrawingBlock getLastCharacterBlock() {
        int size = characterBlocks.size();
        if (size > 0) {
            return characterBlocks.get(size - 1);
        }
        return null;
    }

    /**
     * A helper method to help delete old blocks so previous blocks will fade away once the
     * character moves away with a nice fade away animation.
     */
    private void deleteOldCharacters() {
        List<DrawingBlock> cleanedBlocks = new ArrayList<>();

        for (int i = 0; i < characterBlocks.size(); i++) {
            DrawingBlock block = characterBlocks.get(i);
            if (i == characterBlocks.size() - 1) {
                cleanedBlocks.add(block);
            }
            else if (!block.getDeleteStatus()) {
                block.deleteBlock();
                cleanedBlocks.add(block);
            }
        }
        characterBlocks = cleanedBlocks;
    }

    public boolean isGameInitialized() {
        return initialized;
    }

    public void beginInitialization() {
        initialized = false;
        fixedBlocks = new ArrayList<>();
        characterBlocks = new ArrayList<>();
        xCord = 0;
        yCord = 0;
    }
}
