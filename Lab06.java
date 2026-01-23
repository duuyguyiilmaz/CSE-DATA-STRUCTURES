
public class Lab06 {
    public static void main(String[] args) {
      System.out.println("=== ArrayStack ===");
        ArrayStack<Integer> aStack = new ArrayStack<>();
        System.out.println("Empty: " + aStack.isEmpty());
        aStack.push(10);
        aStack.push(20);
        System.out.println("Top: " + aStack.top());
        System.out.println("Pop: " + aStack.pop());
        System.out.println("Size: " + aStack.size());

        System.out.println("\n=== LinkedStack ===");
        LinkedStack<String> lStack = new LinkedStack<>();
        lStack.push("A");
        lStack.push("B");
        lStack.push("C");
        System.out.println("Top: " + lStack.top());
        while (!lStack.isEmpty()) {
            System.out.println("Pop: " + lStack.pop());
        }

        System.out.println("\n=== ArrayQueue ===");
        ArrayQueue<Integer> aQueue = new ArrayQueue<>();
        aQueue.enqueue(1);
        aQueue.enqueue(2);
        aQueue.enqueue(3);
        System.out.println("Front: " + aQueue.front());
        System.out.println("Dequeue: " + aQueue.dequeue());
        System.out.println("Front after dequeue: " + aQueue.front());
        System.out.println("Size: " + aQueue.size());

        System.out.println("\n=== LinkedQueue ===");
        LinkedQueue<String> lQueue = new LinkedQueue<>();
        lQueue.enqueue("X");
        lQueue.enqueue("Y");
        lQueue.enqueue("Z");
        System.out.println("Front: " + lQueue.front());
        while (!lQueue.isEmpty()) {
            System.out.println("Dequeue: " + lQueue.dequeue());
        }

        System.out.println("\n=== Empty structure checks ===");
        System.out.println("Empty stack pop: " + aStack.pop());
        System.out.println("Empty queue dequeue: " + lQueue.dequeue());

    }
}

interface List {
    boolean isEmpty();

    int size();
}

interface Stack<E> extends List {
    void push(E item);

    E pop();

    E top();
}

interface Queue<E> extends List {
    void enqueue(E item);

    E dequeue();

    E front();
}

class ArrayStack<E> implements Stack<E> {

    private static final int DEFAULT_CAPACITY = 4;
    private final E[] data;
    private int top;

    public ArrayStack() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        top = 0;
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public void push(E item) {
        if (top == data.length) throw new IllegalStateException("Stack is full");
        data[top] = item;
        top++;
    }

    @Override
    public E pop() {
        if (top == 0) {
            return null;
        }
        E old = data[top - 1];
        data[top - 1] = null;
        top--;
        return old;
    }

    @Override
    public E top() {
        if (top == 0) {
            return null;
        }
        return data[top - 1];
    }

}

class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 4;
    private final E[] data;
    private int front;
    private int size;

    public ArrayQueue() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void enqueue(E item) {
        if (size == data.length) throw new IllegalStateException("Queue is full");

        int available = (front + size) % data.length;
        data[available] = item;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) return null;

        E old = data[front];
        front = (front + 1) % data.length;
        size--;
        return old;
    }

    @Override
    public E front() {
        return data[front];
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

class LinkedStack<E> implements Stack<E> {

    private Node<E> top;
    private int size;

    public LinkedStack() {
        top = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(E item) {
        Node<E> newItem = new Node<>(item);
        newItem.next = top;
        top = newItem;
        size++;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E old = top.data;
        top = top.next;
        size--;
        return old;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

}

class LinkedQueue<E> implements Queue<E> {

    private Node<E> front;
    private Node<E> rear;
    private int size;

    public LinkedQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void enqueue(E item) {
        Node<E> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        E old = front.data;
        front = front.next;
        size--;
        if (isEmpty()) {
            rear = null;
        }
        return old;
    }

    @Override
    public E front() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

}
