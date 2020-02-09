package uoft.csc207.gameapplication.Games.TetrisGame.GameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A random piece generator using the 7-Bag-Randomization algorithm.
 *
 * <p>The 7-Bag-Randomization algorithm generates a sequence of all 7 pieces permuted randomly, as
 * if they were drawn from a bag. All 7 pieces are then dealt before generating another bag. This
 * ensures that the player does not get an extremely long drought without a desired piece.
 */
public class PieceGenerator {

    /**
     * A permutation of the 7 pieces in the bag.
     */
    private List<Character> bag;

    /**
     * The number of pieces that have already been dealt from the current bag.
     */
    private int count;

    /**
     * A collection of 2D string array representations of each piece, for each possible rotation.
     */
    private Map<Character, String[][]> pieceToSprites;

    /**
     * Construct a new PieceGenerator object.
     */
    public PieceGenerator() {
        count = 0;
        bag = new ArrayList<>(Arrays.asList('I', 'J', 'L', 'O', 'S', 'Z', 'T'));
        Collections.shuffle(bag);
        pieceToSprites = new HashMap<>();
        pieceToSprites.put(
                'I',
                new String[][]{
                        {"....",
                                "....",
                                "IIII",
                                "...."},
                        {"..I.",
                                "..I.",
                                "..I.",
                                "..I."}
                });
        pieceToSprites.put(
                'J',
                new String[][]{
                        {"....",
                                ".J..",
                                ".JJJ",
                                "...."},
                        {"....",
                                "..JJ",
                                "..J.",
                                "..J."},
                        {"....",
                                "....",
                                ".JJJ",
                                "...J"},
                        {"....",
                                "..J.",
                                "..J.",
                                ".JJ."}
                });
        pieceToSprites.put(
                'L',
                new String[][]{
                        {"....",
                                "...L",
                                ".LLL",
                                "...."},
                        {"....",
                                "..L.",
                                "..L.",
                                "..LL"},
                        {"....",
                                "....",
                                ".LLL",
                                ".L.."},
                        {"....",
                                ".LL.",
                                "..L.",
                                "..L."}
                });
        pieceToSprites.put(
                'O',
                new String[][]{
                        {"....",
                                ".OO.",
                                ".OO.",
                                "...."}
                });
        pieceToSprites.put(
                'S',
                new String[][]{
                        {"....",
                                "..SS",
                                ".SS.",
                                "...."},
                        {"....",
                                ".S..",
                                ".SS.",
                                "..S."}
                });
        pieceToSprites.put(
                'Z',
                new String[][]{
                        {"....",
                                ".ZZ.",
                                "..ZZ",
                                "...."},
                        {"..Z.",
                                ".ZZ.",
                                ".Z..",
                                "...."}
                });
        pieceToSprites.put(
                'T',
                new String[][]{
                        {"....",
                                "..T.",
                                ".TTT",
                                "...."},
                        {"....",
                                "..T.",
                                "..TT",
                                "..T."},
                        {"....",
                                "....",
                                ".TTT",
                                "..T."},
                        {"....",
                                "..T.",
                                ".TT.",
                                "..T."}
                });
    }

    /**
     * Return a random piece and generate a new bag if all 7 pieces have been dealt.
     *
     * @return The piece to be dealt.
     */
    Piece getRandomPiece() {
        int x = 3;
        int y = -1;
        int rotation = 0;
        if (count == 7) {  // all 7 pieces have been dealt
            Collections.shuffle(bag);
            count = 0;
        }
        return new Piece(x, y, rotation, pieceToSprites.get(bag.get(count++)));
    }
}
