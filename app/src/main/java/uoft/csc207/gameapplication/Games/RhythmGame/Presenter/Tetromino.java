package uoft.csc207.gameapplication.Games.RhythmGame.Presenter;

/**
 * A shape defined by four adjacent blocks. The block coordinates are defined as (0, 0) is the
 * upper left corner of the shape and the components of coordinates are all 0 or positive.
 */
class Tetromino {
    private Integer[][] coords;
    private int height;
    private int width;

    Tetromino(Integer[][] coords) {
        this.coords = new Integer[4][2];
        if (validCoordinates(coords)) {
            setCoords(coords);
        } else {
            for (int i = 0; i < 4; i++) {
                this.coords[i][0] = i;
                this.coords[i][1] = 0;
            }
            height = 1;
            width = 4;
        }
    }

    private void setCoords(Integer[][] coords) {
        if (validCoordinates(coords)) {
            for (int i = 0; i < 4; i++) {
                int x = coords[i][0];
                int y = coords[i][1];
                this.coords[i][0] = x;
                this.coords[i][1] = y;

                if (width < x + 1) width = x + 1;
                if (height < y + 1) height = y + 1;
            }
        }
    }

    private boolean validCoordinates(Integer[][] coords) {
        if (coords.length != 4) return false;

        for (int i = 0; i < 4; i++) {
            if (coords[i].length != 2) return false;

            int x = coords[i][0];
            int y = coords[i][1];
            if (x < 0 || y < 0) return false;

            // Checks if there is another block before it that is one distance away
            for (int j = i; j > 0; j--) {
                int x1 = coords[j - 1][0];
                int y1 = coords[j - 1][1];
                if (Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2)) == 1.0) break;
                if (j == 1) return false;
            }
        }

        return true;
    }

    Integer[][] getCoords() {
        return coords;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

}
