
public class Lab08 {
   public static void main(String[] args) {

        PriorityQueue<Integer> pq1 = new UnsortedArrayPQ<>();
        pq1.insert(5);
        pq1.insert(2);
        pq1.insert(9);
        pq1.insert(1);

        System.out.println(pq1.min());        // 1
        System.out.println(pq1.removeMin()); // 1
        System.out.println(pq1.min());        // 2

        PriorityQueue<Integer> pq2 = new SortedLinkedPQ<>();
        pq2.insert(5);
        pq2.insert(2);
        pq2.insert(9);
        pq2.insert(1);

        System.out.println(pq2.min());        // 1
        System.out.println(pq2.removeMin()); // 1
        System.out.println(pq2.min());        // 2
    }
}

interface PriorityQueue<E extends Comparable<E>> {
    int size();

    boolean isEmpty();

    void insert(E element);

    E min();

    E removeMin();
}

class UnsortedArrayPQ<E extends Comparable<E>> implements PriorityQueue<E> {

    private static final int DEFAULT_CAPACITY = 16;
    private E[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public UnsortedArrayPQ() {
        data = (E[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }
@Override
    public int size() {

        return size;
    }
@Override
    public boolean isEmpty() {

        return size == 0;
    }
@Override
    public void insert(E element) {
      if (element == null) throw new IllegalArgumentException("Null not allowed");

        if (size == data.length) {
            throw new IllegalStateException("PQ is full");

        }
        data[size] = element;
        size++;

    }

    private int findMinIndex() {
        if (size == 0)
            return -1;
        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (data[i].compareTo(data[minIndex]) < 0) {
                minIndex = i;
            }
        }

        return minIndex;
    }
    @Override
    public E min() {
        if (isEmpty())
            return null;

        return data[findMinIndex()];
    }
    @Override
    public E removeMin() {
        if (isEmpty())
            return null;

        int minIdx = findMinIndex();


        E removed = data[minIdx];

        data[minIdx] = data[size - 1];
        data[size - 1] = null;
        size--;

        return removed;
    }
}



class SortedLinkedPQ<E extends Comparable<E>> implements PriorityQueue<E> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
    private Node<E> head;
    private int size;

    public SortedLinkedPQ() {
        head = null;
        size = 0;
    }
    @Override
    public int size() {

        return size;
    }
    @Override
    public boolean isEmpty() {

        return size == 0;
    }
    @Override
    public void insert(E element) {
        if (element == null) throw new IllegalArgumentException("Null not allowed");

        Node<E> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        if (element.compareTo(head.data) <= 0) {
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node<E> current = head;
        while (current.next != null && current.next.data.compareTo(element) < 0) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;

    }
    @Override
    public E min() {
        return (head == null) ? null : head.data;

    }
    @Override
    public E removeMin() {
        if (head == null)
            return null;
        E removed = head.data;
        head = head.next;
        size--;
        return removed;
    }
}
