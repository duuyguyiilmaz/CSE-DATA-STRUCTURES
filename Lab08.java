
public class Lab08 {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new UnsortedArrayPQ<>();
        pq.insert(5);
        pq.insert(2);
        pq.insert(9);
        pq.insert(1);

        System.out.println(pq.min()); // 1
        System.out.println(pq.removeMin()); // 1
        System.out.println(pq.min()); // 2

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

    public int size() {

        return size;
    }

    public boolean isEmpty() {

        return size == 0;
    }

    public void insert(E element) {
        if (element == null)
            return;
        if (size == data.length) {
            System.out.println("PQ is full!");
            return;
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

    public E min() {
        if (isEmpty())
            return null;
        int minIdx = findMinIndex();
        return (minIdx == -1) ? null : data[minIdx];
    }

    public E removeMin() {
        if (isEmpty())
            return null;

        int minIdx = findMinIndex();
        if (minIdx == -1)
            return null;

        E removed = data[minIdx];

        data[minIdx] = data[size - 1];
        data[size - 1] = null;
        size--;

        return removed;
    }
}

class Node<E> {
    E data;
    Node<E> next;

    Node(E data) {
        this.data = data;
        this.next = null;
    }
}

class SortedLinkedPQ<E extends Comparable<E>> implements PriorityQueue<E> {

    private Node<E> head;
    private int size;

    public SortedLinkedPQ() {
        head = null;
        size = 0;
    }

    public int size() {

        return size;
    }

    public boolean isEmpty() {

        return size == 0;
    }

    public void insert(E element) {
        if (element == null)
            return;
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

    public E min() {
        return (head == null) ? null : head.data;

    }

    public E removeMin() {
        if (head == null)
            return null;
        E removed = head.data;
        head = head.next;
        size--;
        return removed;
    }
}
