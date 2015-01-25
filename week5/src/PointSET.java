import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> tree;

    // construct an empty set of points
    public PointSET() {
        tree = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return tree.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        tree.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return tree.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : tree) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<Point2D>();
        for (Point2D p : tree) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        double distance = Double.MAX_VALUE;

        for (Point2D treePoint : tree) {
            double newDistance = treePoint.distanceSquaredTo(p);
            if (newDistance < distance) {
                nearest = treePoint;
                distance = newDistance;
            }
        }

        return nearest;
    }
}
