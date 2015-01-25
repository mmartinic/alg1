import java.util.NoSuchElementException;

public class ArrayStack<Item> {

    private Item[] array;
    private int n;

    public ArrayStack() {
        array = (Item[]) new Object[1];
    }

    private void copyArray(int newSize) {

        Item[] newArray = (Item[]) new Object[newSize];

        for (int i = 0; i < n; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
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
    public void push(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        if (n == array.length) {
            copyArray(2 * array.length);
        }

        array[n++] = item;
    }

    // delete and return a random item
    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = array[--n];
        array[n] = null;

        if (n > 0 && n == array.length / 4) {
            copyArray(array.length / 2);
        }

        return item;
    }
}
