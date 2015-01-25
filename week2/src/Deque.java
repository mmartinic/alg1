import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int n;

    // construct an empty deque
    public Deque() {
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // insert the item at the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        Node node = new Node();
        node.item = item;
        node.next = head;
        n++;

        if (head != null) {
            head.previous = node;
        }
        head = node;

        if (tail == null) {
            tail = node;
        }
    }

    // insert the item at the end
    public void addLast(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        Node node = new Node();
        node.item = item;
        node.previous = tail;
        n++;

        if (tail != null) {
            tail.next = node;
        }
        tail = node;

        if (head == null) {
            head = node;
        }
    }

    // delete and return the item at the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = head.item;
        n--;

        head = head.next;
        if (head != null) {
            head.previous = null;
        } else {
            tail = null;
        }

        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = tail.item;
        n--;

        tail = tail.previous;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }

        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequqeIterator();
    }

    private class DequqeIterator implements Iterator<Item> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}