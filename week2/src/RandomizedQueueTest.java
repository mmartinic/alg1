import org.junit.Assert;

public class RandomizedQueueTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testIsEmpty() throws Exception {

    }

    @org.junit.Test
    public void testSize() throws Exception {

    }

    @org.junit.Test
    public void testEnqueue() throws Exception {

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        String[] strings = "A B C D E F G H I J".split("\\s");
        int k = 100;
        for (int j = 0; j < k; j++) {
            for (int i = 0; i < strings.length; i++) {
                String string = strings[i];
                randomizedQueue.enqueue(string);
            }
        }

        for (int j = 0; j < k; j++) {
            for (int i = 0; i < strings.length; i++) {
                Assert.assertFalse(randomizedQueue.isEmpty());
                randomizedQueue.dequeue();
            }
        }

        Assert.assertTrue(randomizedQueue.isEmpty());

    }

    @org.junit.Test
    public void testDequeue() throws Exception {

    }

    @org.junit.Test
    public void testSample() throws Exception {

    }

    @org.junit.Test
    public void testIterator() throws Exception {

    }
}
