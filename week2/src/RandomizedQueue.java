import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    private void changeSize(int newSize) {
        array = copyArray(newSize);
    }

    private Item[] copyArray(int newSize) {

        Item[] newArray = (Item[]) new Object[newSize];

        for (int i = 0; i < n; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        if (n == array.length) {
            changeSize(2 * array.length);
        }

        array[n++] = item;
    }

    // delete and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int k = getRandomIndex();

        Item item = array[k];
        n--;
        array[k] = array[n];
        array[n] = null;

        if (n > 0 && n == array.length / 4) {
            changeSize(array.length / 2);
        }
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int k = getRandomIndex();
        return array[k];
    }

    private int getRandomIndex() {
        int k = StdRandom.uniform(n);
        return k;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] items;
        private int i;

        public RandomizedQueueIterator() {
            items = copyArray(n);
            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            return i < items.length;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
