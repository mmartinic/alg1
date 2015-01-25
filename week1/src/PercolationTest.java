public class PercolationTest {

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            boolean full = perc.isFull(i, j);
            System.out.println(i + "," + j + " : " + full);
        }
    }
}
