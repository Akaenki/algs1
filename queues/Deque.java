import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Node prev, next;
        Item item;
        Node(Item i, Node p, Node n){
            item = i; prev = p; next = n;
        }
    }

    private Node sentinel;
    private int size;

    /** Empty constructors */
    public Deque(){
        size = 0;
        sentinel = new Node(null,null,null);
        /* make the sentinel circular */
        sentinel.prev = sentinel;
        sentinel.next = sentinel.prev;
    }

    public void addFirst(Item x){
        if(x == null) throw new IllegalArgumentException();
        size += 1;
        sentinel.next = new Node(x,sentinel,sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(Item x){
        if(x == null) throw new IllegalArgumentException();
        size += 1;
        sentinel.prev.next = new Node(x,sentinel.prev,sentinel);
        sentinel.prev = sentinel.prev.next;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    /** DEBUG print the deque from first item to last, returns null if empty */
    private void printDeque(){
        int s = size;
        if(size == 0){
            System.out.println("null");
            return;
        }
        Node cur = sentinel.next;
        while(s > 0){
            System.out.print(cur.item + " ");
            cur = cur.next;
            s -= 1;
        }
        System.out.println();
    }

    /** Removes and returns the first item in deque, returns null if empty */
    public Item removeFirst(){
        if(isEmpty()) throw new NoSuchElementException();
        /* this newly created object will be cleaned after return (heap) */
        Node remove = new Node(sentinel.next.item,sentinel,sentinel.next.next);
        /* Clean memory */
        sentinel.next.next = null;
        sentinel.next.prev = null;
        sentinel.next.item = null;
        /* Reconstruct links */
        sentinel.next = remove.next;
        remove.next.prev = sentinel;
        size -= 1;
        return remove.item;
    }

    /** Removes and returns the last item in deque, returns null if empty */
    public Item removeLast(){
        if(isEmpty()) throw new NoSuchElementException();
        Node remove = new Node(sentinel.prev.item,sentinel.prev.prev,sentinel);
        /* Clean memory */
        sentinel.prev.next = null;
        sentinel.prev.prev = null;
        sentinel.prev.item = null;
        /* Reconstruct links */
        sentinel.prev = remove.prev;
        remove.prev.next = sentinel;
        size -= 1;
        return remove.item;
    }

    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = sentinel;
        @Override
        public boolean hasNext() { return current.next != sentinel; }
        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            current = current.next;
            return current.item;
        }
        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args){
        Deque<Integer> L = new Deque<>();
        for(int i = 0; i<10; ++i){
            if(i % 2==0) L.addFirst(i);
            else L.addLast(i);
        }

        L.printDeque();
        //L.removeLast();
        for(int i : L) System.out.println(i);
    }

}
