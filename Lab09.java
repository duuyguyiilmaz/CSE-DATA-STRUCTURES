import java.util.LinkedList;
import java.util.Queue;

public class Lab09 {
    public static void main(String[] args) {

    }

    public static <E extends Comparable<E>> void heapSort(E[] arr) {
        // Step 1: Build max-heap
        // Step 2: Extract elements from heap one by one
        // Move max to the end
        // Restore heap property for reduced heap
    }

    // Maintain max-heap property starting at index i
    private static <E extends Comparable<E>> void heapifyUp(E[] arr, int heapSize, int i) {
        // Check if left child is larger
        // Check if right child is larger
        // If root is not largest, swap & heapify subtree
    }

    // Utility swap
    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

interface Heap<E extends Comparable<E>> {

    int size();

    boolean isEmpty();

    void insert(E element);

    E min();

    E removeMin();
}

class ArrayHeap<E extends Comparable<E>> implements Heap<E> {

    private static final int DEFAULT_CAPACITY = 64;

    private E[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayHeap() {
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
        if (element == null)
            return;
        data[size] = element;
        heapUp(size);
        size++;
    }

    @Override
    public E min() {
        return null;
    }

    @Override
    public E removeMin() {
        if (size == 0)
            return null;
        E removed = data[0];
        data[0] = data[size - 1];
        data[size - 1] = null;
        size--;
        heapDown(0);
        return removed;
    }

    private void heapUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[parent].compareTo(data[index]) > 0) {
                swap(parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            if (left >= size)
                break;
            int right = left + 1;
            int smaller = left;
            if (right < size && data[right].compareTo(data[left]) < 0) {
                smaller = right;
            }
            if (data[index].compareTo(data[smaller]) <= 0)
                break;
            swap(index, smaller);
            index = smaller;

        }
    }

    private void swap(int i, int j) {
        E tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    // --------------------------------------------------
    // Static Merge: Create a new heap from two heaps
    // --------------------------------------------------
    public static <E extends Comparable<E>> ArrayHeap<E> merge(
            ArrayHeap<E> h1, ArrayHeap<E> h2) {

        // TODO:
        // copy both arrays into a bigger array,
        // then perform heapDown on all non-leaf nodes (build-heap O(n))
        //
        // Return the new merged heap

        return null;
    }
}

class Node<E> {

    E data;
    Node<E> left;
    Node<E> right;
    Node<E> parent;

    Node(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}

class LinkedHeap<E extends Comparable<E>> implements Heap<E> {

    private Node<E> root;
    private int size;

    public LinkedHeap() {
        root = null;
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
        if (element == null)
            return;
        if (root == null) {
            root = new Node<>(element);
            size = 1;
            return;
        }
        Queue<Node<E>> q = new LinkedList<>();
        q.add(root);

        Node<E> newNode = new Node<>(element);
        while (!q.isEmpty()) {
            Node<E> current = q.remove();

            if (current.left == null) {
                current.left = newNode;
                newNode.parent = current;
                break;
            } else {
                q.add(current.left);
            }

            if (current.right == null) {
                current.right = newNode;
                newNode.parent = current;
                break;
            } else {
                q.add(current.right);
            }
        }
        heapUp(newNode);
        size++;

    }

    @Override
    public E min() {
        if (root == null)
            return null;
        return root.data;
    }

    @Override
    public E removeMin() {

        if (root == null)
            return null;
        if (size == 1) {
            E removed = root.data;
            root = null;
            size = 0;
            return removed;
        }

        return null;
    }

    private void heapUp(Node<E> node) {
        while (node.parent != null && node.parent.data.compareTo(node.data) > 0) {
            E tmp = node.data;
            node.data = node.parent.data;
            node.parent.data = tmp;

            node = node.parent;
        }
    }

    private void heapDown(Node<E> node) {

    }
}
