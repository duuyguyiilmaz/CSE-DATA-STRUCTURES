public class Lab03 {
    public static void main(String[] args) {
        System.out.print("==========SinglyLinkedList Demo==========");
        SinglyLinkedList<Integer> singlyList = new SinglyLinkedList<>();
        singlyList.addFirst(5);
        singlyList.addFirst(6);
        singlyList.addLast(10);
        singlyList.insertAt(0, 7);
        System.out.println("\nSinglyLinkedList: " + listToSinglyLinkedString(singlyList));
        System.out.println("get(2): " + singlyList.get(2));
        singlyList.set(2, 15);
        System.out.println("After set(2, 15): " + listToSinglyLinkedString(singlyList));
        System.out.println("indexOf(10): " + singlyList.indexOf(10));
        singlyList.remove((Integer) 6);
        System.out.println("After remove(6): " + listToSinglyLinkedString(singlyList));
        System.err.println("=============================================\n");
        System.out.print("==========CircularLinkedList Demo==========");
        CircularLinkedList<String> circularList = new CircularLinkedList<>();
        circularList.addLast("A");
        circularList.addLast("B");
        circularList.addFirst("Start");
        circularList.insertAt(2, null);
        System.out.println("\nCircularLinkedList: " + listToStringCircular(circularList));
        System.out.println("get(2): " + circularList.get(2));
        circularList.set(2, "C");
        System.out.println("After set(2, 'C'): " + listToStringCircular(circularList));
        System.out.println("indexOf(null): " + circularList.indexOf(null));
        circularList.remove("A");
        System.out.println("After remove('A'): " + listToStringCircular(circularList));
        System.err.println("=============================================\n");
        System.out.print("==========DoublyLinkedList Demo==========");
        DoublyLinkedList<Double> doublyList = new DoublyLinkedList<>();
        doublyList.addFirst(1.1);
        doublyList.addLast(2.2);
        doublyList.addFirst(0.0);
        doublyList.insertAt(1, 0.5);
        System.out.println("\nDoublyLinkedList: " + listToStringDoubly(doublyList));
        System.out.println("get(2): " + doublyList.get(2));
        doublyList.set(2, 3.3);
        System.out.println("After set(2, 3.3): " + listToStringDoubly(doublyList));
        System.out.println("indexOf(2.2): " + doublyList.indexOf(2.2));
        doublyList.remove((Double) 0.0);
        System.out.println("After remove(0.0): " + listToStringDoubly(doublyList));
        System.out.println("=============================================\n");

    }
    private static<E> String listToSinglyLinkedString(SinglyLinkedList<E> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        SinglyLinkedList.Node<E> current = list.getHead();
        while (current != null) {
            sb.append(current.item);
            current = current.next;
            if (current != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
        private static <E> String listToStringCircular(CircularLinkedList<E> list) {
        StringBuilder sb = new StringBuilder("[");
        CircularLinkedList.Node<E> tail = list.getTail();
        if (tail != null) {
            CircularLinkedList.Node<E> cur = tail.next; // head
            for (int i = 0; i < list.size(); i++) {
                sb.append(cur.item);
                cur = cur.next;
                if (i < list.size() - 1) sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
        private static <E> String listToStringDoubly(DoublyLinkedList<E> list) {
        StringBuilder sb = new StringBuilder("[");
        DNode<E> cur = list.getHead();
        while (cur != null) {
            sb.append(cur.item);
            cur = cur.next;
            if (cur != null) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

interface MyList<E> {
    int size();

    boolean isEmpty();

    void clear();

    void addFirst(E e);

    void addLast(E e);

    void insertAt(int index, E e);

    E removeFirst();

    E removeLast();

    E removeAt(int index);

    E get(int index);

    E set(int index, E e);

    int indexOf(E o);

    boolean remove(E o);
}

class SinglyLinkedList<E> implements MyList<E> {
    private int size;
    private Node<E> head;

    static class Node<E> {
        public E item;
        public Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    public Node<E> getHead() {
        return head;
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
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, head);
        head = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        Node<E> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        size++;
    }

    @Override
    public void insertAt(int index, E e) {
        if(index<0 || index>size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            addFirst(e);
            return;
        }
            if (index == size) {
        addLast(e);
        return;
    }
        Node<E> current = head;
         for (int i = 0; i < index - 1; i++) {
        current = current.next;
    }
        Node<E> newNode = new Node<>(e, current.next);
        current.next = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        if (head == null) {
            return null;
        }
        E old = head.item;
        head = head.next;
        size--;
        return old;
    }

    @Override
    public E removeLast() {
        if (head == null) {
            return null;
        }
        if (size == 1) {
            E old = head.item;
            head = null;
            size--;
            return old;
        }
        Node<E> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        E old = current.next.item;
        current.next = null;
        size--;
        return old;
    }

    @Override
    public E removeAt(int index) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeFirst();
        }
        if(index==size-1){
          return  removeLast();
        }
        Node<E> current = head;
        int count = 0;
        while (count != index - 1) {
            current = current.next;
            count++;
        }
        E old = current.next.item;
        current.next = current.next.next;
        size--;
        return old;
    }

    @Override
    public E get(int index) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = head;
        for(int i=0;i<index;i++){
            current = current.next;
        }

        return current.item;
    }

    @Override
    public E set(int index, E e) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<E> current = head;
        int count = 0;
        while (count != index) {
            current = current.next;
            count++;
        }
        E old = current.item;
        current.item = e;
        return old;
    }

    @Override
    public int indexOf(E o) {
        Node<E> current = head;
        int count = 0;
        while (current != null) {
            if (o == null) {
                if (current.item == null) {
                    return count;
                }
            } else {
                if (o.equals(current.item)) {
                    return count;
                }
            }
            current = current.next;
            count++;
        }

        return -1;
    }

    @Override
    public boolean remove(E o) {
        int index = indexOf(o);
        if (index != -1) {
            removeAt(index);
            return true;
        }
        return false;
    }
}

class CircularLinkedList<E> implements MyList<E> {
    static class Node<E> {
        public E item;
        public Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node<E> tail;
    private int size;

    public Node<E> getTail() {
        return tail;
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
    public void clear() {
        tail = null;
        size = 0;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (tail == null) {
            tail = newNode;
            tail.next = newNode;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (tail == null) {
            newNode.next = newNode;
            tail = newNode;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void insertAt(int index, E e) {
        if(index<0 || index>size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            addFirst(e);
            return;
        }
        if (index == size) {
            addLast(e);
            return;
        }
                Node<E> newNode = new Node<>(e, null);
        Node<E> current = tail.next;
        int count = 0;
        while (count != index - 1) {
            current = current.next;
            count++;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            E old = tail.item;
            tail = null;
            size--;
            return old;
        }
        Node<E> current = tail.next;
        E old = current.item;
        tail.next = current.next;
        size--;
        return old;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            E old = tail.item;
            tail = null;
            size--;
            return old;
        }
        Node<E> current = tail.next;
        while (current.next != tail) {
            current = current.next;
        }
        E old = tail.item;
        current.next = tail.next;
        tail = current;
        size--;
        return old;
    }

    @Override
    public E removeAt(int index) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        Node<E> current = tail.next;
        Node<E> prev = null;
        int count = 0;
        while (count != index) {
            prev = current;
            current = current.next;
            count++;
        }
        E old = current.item;
        prev.next = current.next;
        size--;
        return old;
    }

    @Override
    public E get(int index) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size - 1) {
            return tail.item;
        }
        Node<E> current = tail.next;
        if (index == 0) {
            return current.item;
        }
        int count = 0;
        while (count != index - 1) {
            current = current.next;
            count++;
        }
        E old = current.next.item;
        return old;
    }

    @Override
    public E set(int index, E e) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = tail.next;
        int count = 0;
        while (count != index) {
            current = current.next;
            count++;
        }
        E old = current.item;
        current.item = e;
        return old;
    }

@Override
public int indexOf(E o) {
    if (tail == null) {
        return -1;
    }
    Node<E> current = tail.next;
    for (int index = 0; index < size; index++) {
        if (o == null) {
            if (current.item == null) {
                return index;
            }
        } else {
            if (o.equals(current.item)) {
                return index;
            }
        }
        current = current.next;
    }
    return -1;
}


    @Override
    public boolean remove(E o) {
        int index = indexOf(o);
        if (index != -1) {
            removeAt(index);
            return true;
        }
        return false;
    }
}

class DNode<E> {
    public E item;
    public DNode<E> prev;
    public DNode<E> next;

    public DNode(E item, DNode<E> prev, DNode<E> next) {
        this.item = item;
        this.prev = prev;
        this.next = next;
    }
}

class DoublyLinkedList<E> implements MyList<E> {
   private DNode<E> head;
    private DNode<E> tail;
    private int size;

    public DNode<E> getHead() {
        return head;
    }

    public DNode<E> getTail() {
        return tail;
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
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void addFirst(E e) {
        DNode<E> newNode = new DNode<>(e, null, head);
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        DNode<E> newNode = new DNode<>(e, tail, null);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void insertAt(int index, E e) {
        if (index < 0 || index > size) {
    throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
}

        if (index == 0) {
            addFirst(e);
            return;
        }
        if (index == size) {
            addLast(e);
            return;
        }
        DNode<E> current = head;
        int count = 0;
        while (count != index) {
            current = current.next;
            count++;
        }
        DNode<E> newNode = new DNode<>(e, current.prev, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;

    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E data = head.item;
        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E data = tail.item;
        if (size == 1) {
            head = tail = null;
        } else {
            DNode<E> oldTail = tail;
            tail = tail.prev;
            tail.next = null;
            oldTail.prev = null;
            oldTail.next = null;
        }
        size--;
        return data;
    }
    @Override
    public E removeAt(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        if (index == 0)
            return removeFirst();
        if (index == size - 1)
            return removeLast();

        int count = 0;
        DNode<E> current = head;

        while (count != index) {
            current = current.next;
            count++;
        }
        E data = current.item;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current.next = null;
        current.prev = null;

        size--;
        return data;
    }

    @Override
    public E get(int index) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        DNode<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public E set(int index, E e) {
        if(index<0 || index>=size){
           throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        DNode<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E old = current.item;
        current.item = e;
        return old;
    }

  @Override
public int indexOf(E o) {
    DNode<E> current = head;
    int count = 0;

    while (current != null) {
        if (o == null) {
            if (current.item == null) {
                return count;
            }
        } else {
            if (o.equals(current.item)) {
                return count;
            }
        }
        current = current.next;
        count++;
    }
    return -1;
}



   @Override
public boolean remove(E o) {
    if (isEmpty()) {
        return false;
    }

    DNode<E> current = head;
    while (current != null) {

        boolean match;
        if (o == null) {
            match = (current.item == null);
        } else {
            match = o.equals(current.item);
        }

        if (match) {
            if (current == head) {
                removeFirst();
            } else if (current == tail) {
                removeLast();
            } else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                current.next = null;
                current.prev = null;
                size--;
            }
            return true;
        }

        current = current.next;
    }
    return false;
}

}
