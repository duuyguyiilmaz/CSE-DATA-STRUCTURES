import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Lab07 {
    public static void main(String[] args) {

    }
}

interface ITree<E> {
    int size();

    boolean isEmpty();

    void insert(E element);

    boolean remove(E element);

    boolean contains(E element);

    void BFS(List<E> list); // Breadth-first

    void DFS(List<E> list); // Depth-first with Stack

    void inorder(List<E> list);

    void preorder(List<E> list);

    void postorder(List<E> list);
}

class Node<E> {
    E data;
    Node<E> left;
    Node<E> right;

    Node(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class ArrayBT<E> implements ITree<E> {

    private E[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayBT(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    private void recInorder(int index, List<E> list) {
        if (index >= size) {
            return;
        }
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        recInorder(left, list);
        list.add(data[index]);
        recInorder(right, list);
    }

    private void recPreorder(int index, List<E> list) {
        if (index >= size)
            return;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        list.add(data[index]);
        recPreorder(left, list);
        recPreorder(right, list);
    }

    private void recPostorder(int index, List<E> list) {
        if (index >= size)
            return;

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        recPostorder(left, list);
        recPostorder(right, list);
        list.add(data[index]);
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
        if (element == null) {
            return;
        }
        if (size == data.length) {

            int newCapacity;
            if (data.length == 0) {
                newCapacity = 1;
            } else {
                newCapacity = data.length * 2;
            }

            E[] newData = (E[]) new Object[newCapacity];

            System.arraycopy(data, 0, newData, 0, data.length);

            data = newData;
        }

        data[size] = element;
        size++;

    }

    @Override
    public boolean remove(E element) {
        if (element == null || size == 0)
            return false;
        int target = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(element)) {
                target = i;
                break;
            }
        }
        if (target == -1)
            return false;
        data[target] = data[size - 1];
        data[size - 1] = null;
        size--;
        return true;
    }

    @Override
    public boolean contains(E element) {
        if (element == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void BFS(List<E> list) {
        list.addAll(Arrays.asList(data));
    }

    @Override
    public void DFS(List<E> list) {
        preorder(list);
    }

    @Override
    public void inorder(List<E> list) {
        recInorder(0, list);
    }

    @Override
    public void preorder(List<E> list) {
        recPreorder(0, list);
    }

    @Override
    public void postorder(List<E> list) {
        recPostorder(0, list);
    }

}

class LinkedBT<E> implements ITree<E> {

    private Node<E> root;
    private int size;

    public LinkedBT() {
        root = null;
        size = 0;
    }

    private void recInorder(Node<E> node, List<E> list) {
        // TODO: left → root → right
        if (node == null)
            return;
        recInorder(node.left, list);
        list.add(node.data);
        recInorder(node.right, list);
    }

    private void recPreorder(Node<E> node, List<E> list) {
        // TODO: root → left → right
        if (node == null)
            return;
        list.add(node.data);
        recPreorder(node.left, list);
        recPreorder(node.right, list);
    }

    private void recPostorder(Node<E> node, List<E> list) {
        // TODO: left → right → root
        if (node == null)
            return;
        recPostorder(node.left, list);
        recPostorder(node.right, list);
        list.add(node.data);
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
        Node<E> newNode = new Node<>(element);
        if (root == null) {
            root = newNode;
            size = 1;
            return;
        }
        Queue<Node<E>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node<E> n = q.remove();
            if (n.left == null) {
                n.left = newNode;
                size++;
                return;
            } else {
                q.add(n.left);
            }
            if (n.right == null) {
                n.right = newNode;
                size++;
                return;
            } else {
                q.add(n.right);
            }
        }
    }

    @Override
    public boolean remove(E element) {
        if (element == null || root == null)
            return false;
        if (size == 1) {
            if (root.data.equals(element)) {
                root = null;
                size = 0;
                return true;
            }
            return false;
        }
        Node<E> target = null;
        Queue<Node<E>> q = new LinkedList<>();
        q.add(root);
        return false;
    }

    @Override
    public boolean contains(E element) {
        if (element == null || root == null)
            return false;
        Queue<Node<E>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node<E> cur = q.remove();
            if (cur.data.equals(element)) {
                return true;
            }
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }
        return false;
    }

    @Override
    public void BFS(List<E> list) {
        if (root == null)
            return;
        Queue<Node<E>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node<E> cur = q.remove();
            list.add(cur.data);
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }

    }

    @Override
    public void DFS(List<E> list) {
        if (root == null)
            return;
        Stack<Node<E>> st = new Stack<>();
        st.push(root);
        while (!st.isEmpty()) {
            Node<E> n = st.pop();
            list.add(n.data);
            if (n.right != null) {
                st.push(n.right);
            }
            if (n.left != null) {
                st.push(n.left);
            }
        }

    }

    @Override
    public void inorder(List<E> list) {
        recInorder(root, list);

    }

    @Override
    public void preorder(List<E> list) {
        recPreorder(root, list);
    }

    @Override
    public void postorder(List<E> list) {
        recPostorder(root, list);

    }
}
