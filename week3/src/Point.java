/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {

        if (compareTo(that) == 0) {
            //degenerate line segment (between a point and itself)
            return Double.NEGATIVE_INFINITY;
        }

        if (y == that.y) {
            //horizontal line
            return 0;
        }

        if (x == that.x) {
            //vertical line
            return Double.POSITIVE_INFINITY;
        }

        return (double) (that.y - y) / (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {

        int result = compareIntegers(y, that.y);

        if (result == 0) {
            result = compareIntegers(x, that.x);
        }

        return result;
    }

    private int compareIntegers(int a, int b) {
        int result = 0;

        if (a < b) {
            result = -1;
        } else if (a > b) {
            result = 1;
        }
        return result;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeComparator implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {

            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);

            if (slope1 == slope2) {
                return 0;
            } else if (slope1 < slope2) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
