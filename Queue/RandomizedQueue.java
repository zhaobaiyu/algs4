import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n = 0;
    
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; ++i) {
            copy[i] = a[i];
        }
        a = copy;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }

    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniform(n);
        Item tmp = a[rand];
        --n;
        a[rand] = a[n];
        a[n] = null;
        if (n > 0 && n == a.length/4) {
            resize(a.length/2);
        }
        return tmp;
    }

    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        return a[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator() {
        return new RQIterator<Item>(a, size());
    }

    private class RQIterator<Item> implements Iterator<Item> {
        private int[] toVisite;
        private int n;
        private Item[] a;
        public RQIterator(Item[] a, int n) {
            this.n = n;
            this.a = a;
            toVisite = new int[n];
            for (int i = 0; i < n; ++i) {
                toVisite[i] = i;
            }
        }

        public boolean hasNext() {
            return n != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int rand = StdRandom.uniform(n);
            int thisVisit = toVisite[rand];
            --n;
            toVisite[rand] = toVisite[n];
            return a[thisVisit];
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        // test iterator
        StdOut.println("Test iterator");
        for (String str : rq) {
            StdOut.println(str);
        }        
        // test enqueue and dequeue
        StdOut.println("Test enqueue and dequeu");
        while (!rq.isEmpty()) {
            StdOut.println(rq.dequeue());
        }
    }
}
