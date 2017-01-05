import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int n;
    
    private static class Node<Item> {
        private Item item;
        private Node<Item> prior;
        private Node<Item> next;
    }
    
    public Deque() {
        first = new Node<Item>();
        last = new Node<Item>();
        first.next = last;
        last.prior = first;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> newitem = new Node<Item>();
        newitem.item = item;
        newitem.prior = first;
        newitem.next = first.next;
        first.next.prior = newitem;
        first.next = newitem;
        ++n;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> newitem = new Node<Item>();
        newitem.item = item;
        newitem.next = last;
        newitem.prior = last.prior;
        last.prior.next = newitem;
        last.prior = newitem;
        ++n;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.next.item;
        first.next = first.next.next;
        first.next.prior = first;
        --n;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.prior.item;
        last.prior = last.prior.prior;
        last.prior.next = last;
        --n;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator(first, last);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;
        
        public DequeIterator(Node<Item> first, Node<Item> last) {
            current = first;
        }

        public boolean hasNext() {
            return current.next != last;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.next.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<String>();
        while (!StdIn.isEmpty()) {
            dq.addLast(StdIn.readString());
        }
        // test iterator
        StdOut.println("Test iterator:");
        for (String str : dq) {
            StdOut.println(str);
        }
        // test remove 
        StdOut.println("Test remove method:");
        while (!dq.isEmpty()) {
            StdOut.println(dq.removeLast());
        }        
    }
}
