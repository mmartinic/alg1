public class PercolationStats {

    private final double[] thresholds;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        if (T <= 0) {
            throw new IllegalArgumentException();
        }

        thresholds = new double[T];

        for (int k = 0; k < T; k++) {
            int count = 0;
            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);

                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    count++;
                }
            }

            thresholds[k] = (double) count / (N * N);
        }

        mean = StdStats.mean(thresholds);

        if (T == 1) {
            stddev = Double.NaN;
            confidenceLo = Double.NaN;
            confidenceHi = Double.NaN;
        } else {
            stddev = StdStats.stddev(thresholds);

            double margin = 1.96 * stddev / Math.sqrt(T);
            confidenceLo = mean - margin;
            confidenceHi = mean + margin;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client, described below
    public static void main(String[] args) {

        int N = 200;
        int T = 100;

        if (args.length == 2) {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }

        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(N, T);

        StdOut.println(stopwatch.elapsedTime() + " ms");

        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo()
                + ", " + percolationStats.confidenceHi());
    }
}
