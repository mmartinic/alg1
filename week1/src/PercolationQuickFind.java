public class PercolationQuickFind {

    private static final byte OPEN_STATE = 1;
    private static final byte BOTTOM_CONNECTED_STATE = 2;

    private final int N;
    private final int top;
    private final byte[] states;
    private QuickFindUF quickFindUF;

    // create N-by-N grid, with all sites blocked
    public PercolationQuickFind(int N) {
        this.N = N;
        top = 0;
        states = new byte[N * N + 1];
        quickFindUF = new QuickFindUF(N * N + 1);

        states[top] = OPEN_STATE;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        checkIndex(i);
        checkIndex(j);

        int p = getArrayIndex(i, j);
        if (isOpen(p)) {
            return;
        }

        states[p] = OPEN_STATE;

        if (i == N) {
            states[p] = (byte) (states[p] | BOTTOM_CONNECTED_STATE);
        }

        if (i == 1) {
            union(top, p);
        }

        int[] neighbours = getNeighbours(i, j);

        for (int q : neighbours) {
            if (q > -1 && isOpen(q)) {
                union(p, q);
            }
        }
    }

    private void union(int p, int q) {
        int rootP = quickFindUF.find(p);
        int rootQ = quickFindUF.find(q);

        boolean connectToBottom = isConnectedToBottom(rootP) || isConnectedToBottom(rootQ);

        quickFindUF.union(p, q);

        if (connectToBottom) {
            int newRoot = quickFindUF.find(p);
            states[newRoot] = (byte) (states[newRoot] | BOTTOM_CONNECTED_STATE);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkIndex(i);
        checkIndex(j);

        int p = getArrayIndex(i, j);
        return isOpen(p);
    }

    private boolean isOpen(int p) {
        return (states[p] & 1) != 0;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkIndex(i);
        checkIndex(j);

        return quickFindUF.connected(top, getArrayIndex(i, j));
    }

    private boolean isConnectedToBottom(int p) {
        return (states[p] & 2) != 0;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnectedToBottom(quickFindUF.find(top));
    }

    private void checkIndex(int i) {
        if (i < 1 || i > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int getArrayIndex(int i, int j) {
        if (i < 1 || i > N) {
            return -1;
        }
        if (j < 1 || j > N) {
            return -1;
        }

        return (i - 1) * N + (j - 1) + 1;
    }

    private int[] getNeighbours(int i, int j) {

        int[] neighbours = new int[4];

        int q = getArrayIndex(i - 1, j);
        if (q > -1) {
            neighbours[0] = q;
        } else {
            neighbours[0] = -1;
        }

        q = getArrayIndex(i + 1, j);
        if (q > -1) {
            neighbours[1] = q;
        } else {
            neighbours[1] = -1;
        }

        q = getArrayIndex(i, j - 1);
        if (q > -1) {
            neighbours[2] = q;
        } else {
            neighbours[2] = -1;
        }

        q = getArrayIndex(i, j + 1);
        if (q > -1) {
            neighbours[3] = q;
        } else {
            neighbours[3] = -1;
        }

        return neighbours;
    }
}
