import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] blocks;
    private final int n;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] input) {
        n = input.length;

        blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = input[i][j];
            }
        }
    }

    // board dimension N
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int value = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int element = blocks[i][j];
                if (element == 0) {
                    continue;
                }
                if (i * n + j != element - 1) {
                    value++;
                }
            }
        }
        return value;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int value = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int element = blocks[i][j];
                if (element == 0) {
                    continue;
                }

                int zeroBasedElement = element - 1;
                if (i * n + j != zeroBasedElement) {
                    int correctI = zeroBasedElement / n;
                    int correctJ = zeroBasedElement % n;
                    value += Math.abs(correctI - i) + Math.abs(correctJ - j);
                }
            }
        }
        return value;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int element = blocks[i][j];
                if (element == 0) {
                    continue;
                }

                int zeroBasedElement = element - 1;

                if (i * n + j != zeroBasedElement) {
                    return false;
                }
            }
        }

        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    return copyAndSwap(i, j, i, j + 1);
                }
            }
        }

        throw new AssertionError();
    }

    private Board copyAndSwap(int i1, int j1, int i2, int j2) {
        int[][] newBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        }

        swap(newBlocks, i1, j1, i2, j2);

        return new Board(newBlocks);
    }

    private void swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        int t = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = t;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (!(y instanceof Board)) {
            return false;
        }

        return Arrays.deepEquals(blocks, ((Board) y).blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        int zeroI = -1;
        int zeroJ = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }

        List<Board> neighbors = new ArrayList<Board>();
        if (zeroI > 0) {
            neighbors.add(copyAndSwap(zeroI, zeroJ, zeroI - 1, zeroJ));
        }
        if (zeroI < n - 1) {
            neighbors.add(copyAndSwap(zeroI, zeroJ, zeroI + 1, zeroJ));
        }
        if (zeroJ > 0) {
            neighbors.add(copyAndSwap(zeroI, zeroJ, zeroI, zeroJ - 1));
        }
        if (zeroJ < n - 1) {
            neighbors.add(copyAndSwap(zeroI, zeroJ, zeroI, zeroJ + 1));
        }

        return neighbors;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(String.format("%2d ", blocks[i][j]));
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int[][] matrix = new int[3][3];
//        matrix[0][0] = 2;
//        matrix[0][1] = 8;
//        matrix[0][2] = 3;
//
//        matrix[1][0] = 6;
//        matrix[1][1] = 5;
//        matrix[1][2] = 0;
//
//        matrix[2][0] = 1;
//        matrix[2][1] = 4;
//        matrix[2][2] = 7;


        matrix[0][0] = 6;
        matrix[0][1] = 5;
        matrix[0][2] = 4;

        matrix[1][0] = 3;
        matrix[1][1] = 1;
        matrix[1][2] = 7;

        matrix[2][0] = 8;
        matrix[2][1] = 0;
        matrix[2][2] = 2;

        Board board1 = new Board(matrix);
        Board board2 = new Board(matrix);

        System.out.println(board1.equals(board2));

    }
}
