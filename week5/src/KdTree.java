import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size = 0;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {

        root = insert(root, p, true);

        if (root.rect == null) {
            root.rect = new RectHV(0, 0, 1, 1);
        }
    }

    private Node insert(Node node, Point2D p, boolean verticalOrientation) {
        if (node == null) {
            Node newNode = new Node();
            newNode.p = p;
            size++;
            return newNode;
        }

        if (node.p.equals(p)) {
            return node;
        }

        if (verticalOrientation) {
            if (p.x() < node.p.x()) {
                node.left = insert(node.left, p, !verticalOrientation);
                if (node.left.rect == null) {
                    node.left.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
                }
            } else {
                node.right = insert(node.right, p, !verticalOrientation);
                if (node.right.rect == null) {
                    node.right.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                }
            }
        } else {
            if (p.y() < node.p.y()) {
                node.left = insert(node.left, p, !verticalOrientation);
                if (node.left.rect == null) {
                    node.left.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
                }
            } else {
                node.right = insert(node.right, p, !verticalOrientation);
                if (node.right.rect == null) {
                    node.right.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
                }
            }
        }

        return node;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean verticalOrientation) {

        if (node == null) {
            return false;
        }

        if (node.p.equals(p)) {
            return true;
        }

        if (verticalOrientation) {
            if (p.x() < node.p.x()) {
                return contains(node.left, p, !verticalOrientation);
            } else {
                return contains(node.right, p, !verticalOrientation);
            }
        } else {
            if (p.y() < node.p.y()) {
                return contains(node.left, p, !verticalOrientation);
            } else {
                return contains(node.right, p, !verticalOrientation);
            }
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean verticalOrientation) {

        if (node == null) {
            return;
        }

        draw(node.left, !verticalOrientation);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.p.draw();

        if (verticalOrientation) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        draw(node.right, !verticalOrientation);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<Point2D>();
        range(root, rect, list);
        return list;
    }

    private void range(Node node, RectHV rect, List<Point2D> list) {

        if (node == null) {
            return;
        }

        if (!node.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(node.p)) {
            list.add(node.p);
        }

        range(node.left, rect, list);
        range(node.right, rect, list);
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {

        if (isEmpty()) {
            return null;
        }

        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node node, Point2D query, Point2D nearestPoint, boolean verticalOrientation) {

        if (node == null) {
            return nearestPoint;
        }

        Point2D currentNearestPoint = nearestPoint;
        double currentPointDistance = node.p.distanceSquaredTo(query);
        double nearestPointDistance = nearestPoint.distanceSquaredTo(query);

        if (currentPointDistance < nearestPointDistance) {
            currentNearestPoint = node.p;
        }

        boolean goLeft;
        if (verticalOrientation) {
            goLeft = query.x() < node.p.x();
        } else {
            goLeft = query.y() < node.p.y();
        }

        Point2D newNearestPoint;
        if (goLeft) {
            newNearestPoint = nearest(node.left, query, currentNearestPoint, !verticalOrientation);
        } else {
            newNearestPoint = nearest(node.right, query, currentNearestPoint, !verticalOrientation);
        }

        if (goLeft && node.right != null) {
            double newNearestPointDistance = newNearestPoint.distanceSquaredTo(query);
            double rectDistance = node.right.rect.distanceSquaredTo(query);
            if (newNearestPointDistance > rectDistance) {
                newNearestPoint = nearest(node.right, query, newNearestPoint, !verticalOrientation);
            }
        } else if (!goLeft && node.left != null) {
            double newNearestPointDistance = newNearestPoint.distanceSquaredTo(query);
            double rectDistance = node.left.rect.distanceSquaredTo(query);
            if (newNearestPointDistance > rectDistance) {
                newNearestPoint = nearest(node.left, query, newNearestPoint, !verticalOrientation);
            }
        }

        return newNearestPoint;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;      // the left/bottom subtree
        private Node right;     // the right/top subtree
    }
}
