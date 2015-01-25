public class Subset {

    public static void main(String[] args) {

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);

        int i = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();

            if (i < k) {
                randomizedQueue.enqueue(s);
            } else {
                int uniform = StdRandom.uniform(0, i + 1);

                if (uniform < k) {
                    randomizedQueue.dequeue();
                    randomizedQueue.enqueue(s);
                }
            }
            i++;
        }

        for (int j = 0; j < k; j++) {
            StdOut.println(randomizedQueue.dequeue());
        }

//        String[] array = new String[k];
//        int i = 0;
//        while (!StdIn.isEmpty()) {
//            String s = StdIn.readString();
//
//            if (i < k) {
//                array[i] = s;
//            } else {
//                int uniform = StdRandom.uniform(0, i + 1);
//
//                if (uniform < k) {
//                    array[uniform] = s;
//                }
//            }
//            i++;
//        }
//
//        for (int j = 0; j < array.length; j++) {
//            String s = array[j];
//            StdOut.println(s);
//        }

//        int k = 3;
//        randomizedQueue.enqueue("A");
//        randomizedQueue.enqueue("B");
//        randomizedQueue.enqueue("C");
//        randomizedQueue.enqueue("D");
//        randomizedQueue.enqueue("E");
//        randomizedQueue.enqueue("F");
//        randomizedQueue.enqueue("G");
//        randomizedQueue.enqueue("H");
//        randomizedQueue.enqueue("I");

//        while (!StdIn.isEmpty()) {
//            String s = StdIn.readString();
//            randomizedQueue.enqueue(s);
//        }
//
//        for (int i = 0; i < k; i++) {
//            StdOut.println(randomizedQueue.dequeue());
//        }
    }
}
