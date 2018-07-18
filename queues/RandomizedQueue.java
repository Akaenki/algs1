import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    /* Double the size when exceed capacity */
    private static int RFACTOR = 2;
    /* Shrink the size when usage is too low */
    private static double UFACTOR = 0.25;

    public RandomizedQueue(){
        size = 0;
        items = (Item[]) new Object[8];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    private void resize(int capacity){
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    private double usageFactor(){
        return 1.0*size/items.length;
    }

    public void enqueue(Item item){
        if(item == null) throw new IllegalArgumentException();
        if(size == items.length) resize(size * RFACTOR);
        items[size++] = item;
    }

    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        if(items.length>16 && usageFactor() < UFACTOR)
            resize(items.length / 2);
        int rand = StdRandom.uniform(size);
        Item item = items[rand];
        items[rand] = items[--size];
        return item;
    }

    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        return items[StdRandom.uniform(size)];
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] shuffled = (Item[]) new Object[size];

        RandomizedQueueIterator(){
            System.arraycopy(items, 0, shuffled, 0, size);
            StdRandom.shuffle(shuffled);
        }

        @Override
        public void remove() { throw new UnsupportedOperationException();}

        @Override
        public boolean hasNext() { return i < size; }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            return shuffled[i++];
        }
    }

    /* DEBUG print the queue */
    private void printQeueu(){
        for(int i = 0; i<size; ++i){
            System.out.println(items[i] + " ");
        }
    }


    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for(int i = 0; i<20; ++i){
            rq.enqueue(i);
        }

        for(int i : rq){
            System.out.print(i + " ");
        }
    }
}
