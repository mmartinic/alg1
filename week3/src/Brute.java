import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Point[] points = new Point[N];

        int i = 0;
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();

            Point p = new Point(x, y);
            points[i++] = p;
            p.draw();
        }

        Arrays.sort(points);

        for (i = 0; i < N - 3; i++) {
            for (int j = i + 1; j < N - 2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int l = k + 1; l < N; l++) {

                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];

                        if (p.SLOPE_ORDER.compare(q, r) == 0
                                && p.SLOPE_ORDER.compare(r, s) == 0) {
                            StdOut.printf("%s -> %s -> %s -> %s\n", p, q, r, s);
                            p.drawTo(s);
                        }

                    }
                }
            }
        }
    }
}
