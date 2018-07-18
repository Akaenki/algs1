import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private static int RFACTOR = 2;
    private static double UFACTOR = 0.25;

    public ArrayDeque(){
        size = 0;
        items = (Item[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    /** Helper: Resize function */
    private void resize(int capacity){
        Item[] a = (Item[]) new Object[capacity];
        int size1 = size - nextLast;
        int size2 = size - size1;
        if(size1 < 0 || size2 <0){
            System.arraycopy(items,incrId(nextFirst),a,0,size);
        } else {
            System.arraycopy(items, size2, a, 0, size1);
            System.arraycopy(items, 0, a, size1, size2);
        }
        items = a;
        nextFirst = capacity-1;
        nextLast = size;
    }

    /** Helper: Add index by 1 with loop */
    private int incrId(int index){
        int id = index + 1;
        return id = id>=items.length ? 0 : id;
    }

    /** Helper: Minus index by 1 with loop */
    private int decrId(int index){
        int id = index - 1;
        return id = id < 0? items.length-1 : id;
    }

    /** Helper: Calculate current usage factor */
    private double usageFactor(){
        return 1.0*size/items.length;
    }


    public void addFirst(Item item){
        if(item == null) throw new IllegalArgumentException();
        if(size == items.length)
            resize(size * RFACTOR);
        items[nextFirst] = item;
        size += 1;
        nextFirst = decrId(nextFirst);
    }

    public void addLast(Item item){
        if(item == null) throw new IllegalArgumentException();
        if(size == items.length)
            resize(size * RFACTOR);
        items[nextLast] = item;
        size += 1;
        nextLast = incrId(nextLast);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public Item removeFirst(){
        if(isEmpty()) throw new NoSuchElementException();
        if(items.length>16 && usageFactor() < UFACTOR)
            resize(items.length / 2);
        nextFirst = incrId(nextFirst);
        Item x = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return x;
    }

    public Item removeLast(){
        if(isEmpty()) throw new NoSuchElementException();
        if(items.length>16 && usageFactor() < UFACTOR)
            resize(items.length / 2);
        nextLast = decrId(nextLast);
        Item x = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return x;
    }

    public Item get(int index){
        int ptr = nextFirst;
        for(int i = 0; i<=index; i++){
            ptr = incrId(ptr);
        }
        return items[ptr];
    }

    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int current = incrId(nextFirst), last = decrId(nextLast);
        @Override
        public boolean hasNext() { return current != last; }
        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            current = incrId(current);
            return items[current];
        }
        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }


    /** DEBUG: Print function */
    public void printDeque(){
        int ptr = incrId(nextFirst);
        int remain = size;
        while(remain > 0){
            System.out.print(items[ptr]+" ");
            remain -= 1;
            ptr = incrId(ptr);
        }
        System.out.println();
    }

    public static void main(String[] args){
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for(int i = 0; i<=10; i++){
            A.addLast(i);
        }

        for(int i : A){
            System.out.print(i+" ");
        }
    }
}
