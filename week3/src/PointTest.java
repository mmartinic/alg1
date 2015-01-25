import org.junit.Assert;

public class PointTest {

    @org.junit.Test
    public void testSlopeTo1() throws Exception {
        Point p = new Point(9, 8);
        Point q = new Point(6, 6);

        Assert.assertEquals(0.6666666666666666, p.slopeTo(q), 0);
    }

    @org.junit.Test
    public void testSlopeTo2() throws Exception {
        Point p = new Point(1, 1);
        Point q = new Point(1, 1);

        Assert.assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(q), 0);
    }
}
