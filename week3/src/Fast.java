import java.util.Arrays;
import java.util.Comparator;

public class Fast {

    public static void main(String[] args) {

        In in = new In(args[0]);
        int N = in.readInt();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Point[] lexSortedPoints = new Point[N];

        int i = 0;
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();

            Point p = new Point(x, y);
            lexSortedPoints[i++] = p;
            p.draw();
        }

        Arrays.sort(lexSortedPoints);

        Point[] slopeSortedPoints = new Point[N];

        for (i = 0; i < N; i++) {

            Point startPoint = lexSortedPoints[i];
            Comparator<Point> slopeOrder = startPoint.SLOPE_ORDER;

            System.arraycopy(lexSortedPoints, 0, slopeSortedPoints, 0, N);
            Arrays.sort(slopeSortedPoints, slopeOrder);

            int start = -1;
            int end = -1;
            for (int j = 0; j < N - 1; j++) {
                Point p1 = slopeSortedPoints[j];
                Point p2 = slopeSortedPoints[j + 1];

                if (slopeOrder.compare(p1, p2) == 0) {
                    if (start == -1) {
                        start = j;
                    }
                    end = j + 1;
                } else if (start > -1 && end - start + 2 >= 4) {
                    printSegment(startPoint, slopeSortedPoints, start, end);
                    start = -1;
                    end = -1;
                } else {
                    start = -1;
                    end = -1;
                }
            }

            if (start > -1 && end - start + 2 >= 4) {
                printSegment(startPoint, slopeSortedPoints, start, end);
            }

        }
    }

    private static void printSegment(Point startPoint, Point[] points, int start, int end) {

        if (startPoint.compareTo(points[start]) > 0) {
            return;
        }

        StdOut.printf("%s -> ", startPoint);
        for (int j = start; j < end; j++) {
            Point p = points[j];
            StdOut.printf("%s -> ", p);
        }
        Point endPoint = points[end];
        StdOut.println(endPoint);
        startPoint.drawTo(endPoint);
    }
}
