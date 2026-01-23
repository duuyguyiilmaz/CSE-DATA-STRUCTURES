import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * CS201 – Data Structures
 * Lab 11 – Red-Black Trees
 *
 * In this lab, you will:
 * - Implement a Red-Black Tree
 * - Maintain all RB Tree insertion properties
 * - Implement recoloring and rotations
 * - Ensure the tree remains balanced after each insertion
 *
 */

public class Lab11 {
    public static void main(String[] args) {
        RBTree<Integer> tree = new RBTree<>();
        /*
         * tree.insert(33);
         * tree.insert(13);
         * tree.insert(53);
         * tree.insert(11);
         * tree.insert(21);
         * tree.insert(41);
         * tree.insert(61);
         * tree.insert(15);
         * tree.insert(31);
         */

        /*
         * tree.insert(50);
         * tree.insert(20);
         * tree.insert(10);
         */

        /*
         * tree.insert(36);
         * tree.insert(15);
         * tree.insert(50);
         * tree.insert(70);
         * tree.insert(5);
         * tree.insert(30);
         * tree.insert(3);
         * tree.insert(6);
         * tree.insert(23);
         * tree.insert(33);
         * tree.insert(32);
         */
        System.out.println(tree);

        // You may test manually here if you want.
        // Official testing will be done using JUnit tests.
    }
}

class Node<E> {
    E data;
    Node<E> left, right, parent;
    boolean color = true; // true = RED, false = BLACK

    public Node(E data) {
        this.data = data;
        this.color = true; // new nodes start red
    }

    @Override
    public String toString() {
        return String.format("[%s|%s]", data, color ? "R" : "B");
    }
}

interface IList<E> {
    boolean isEmpty();

    int size();
}

interface ITree<E> extends IList<E> {
    void insert(E data);

    boolean contains(E data);
    // void remove(E data);
}

interface IBalancedTree<E> extends ITree<E> {
    Node<E> rotateRight(Node<E> node);

    Node<E> rotateLeft(Node<E> node);
}

interface IRBTree<E> extends IBalancedTree<E> {
    void check(Node<E> node);

    Node<E> balance(Node<E> node);

    void recolor(Node<E> node);
}

class RBTree<E extends Comparable<E>> implements IRBTree<E> {
    private Node<E> root;
    private int size;

    public RBTree() {
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
    public Node<E> rotateLeft(Node<E> node) {
        if (node == null)
            return null;

        Node<E> x = node.right; // new root of this subtree
        if (x == null)
            return node; // cannot rotate

        Node<E> B = x.left; // will move to node.right

        // 1) move B
        node.right = B;
        if (B != null)
            B.parent = node;

        // 2) link x with node.parent
        x.parent = node.parent;
        if (node.parent == null) {
            root = x;
        } else if (node == node.parent.left) {
            node.parent.left = x;
        } else {
            node.parent.right = x;
        }

        // 3) put node under x
        x.left = node;
        node.parent = x;

        return x;
    }

    @Override
    public Node<E> rotateRight(Node<E> node) {
        if (node == null)
            return null;

        Node<E> y = node.left; // new root of this subtree
        if (y == null)
            return node; // cannot rotate

        Node<E> B = y.right; // will move to node.left

        // 1) move B
        node.left = B;
        if (B != null)
            B.parent = node;

        // 2) link y with node.parent
        y.parent = node.parent;
        if (node.parent == null) {
            root = y;
        } else if (node == node.parent.left) {
            node.parent.left = y;
        } else {
            node.parent.right = y;
        }

        // 3) put node under y
        y.right = node;
        node.parent = y;

        return y;
    }

    private boolean isRed(Node<E> node) {
        return node != null && node.color; // true = RED
    }

    private Node<E> getSibling(Node<E> node) {
        if (node == null || node.parent == null)
            return null;
        if (node == node.parent.left)
            return node.parent.right;
        return node.parent.left;
    }

    @Override
    public void insert(E data) {
        if (data == null)
            return;

        if (root == null) {
            root = new Node<>(data);
            root.color = false; // root is BLACK
            root.parent = null;
            size = 1;
            return;
        }

        insertRec(root, data);

        // root must always be BLACK
        if (root != null)
            root.color = false;
    }

    private void insertRec(Node<E> node, E data) {
        int cmp = data.compareTo(node.data);

        if (cmp < 0) {
            if (node.left == null) {
                Node<E> newNode = new Node<>(data);
                node.left = newNode;
                newNode.parent = node;
                size++;
                check(newNode);
            } else {
                insertRec(node.left, data);
            }
        } else if (cmp > 0) {
            if (node.right == null) {
                Node<E> newNode = new Node<>(data);
                node.right = newNode;
                newNode.parent = node;
                size++;
                check(newNode);
            } else {
                insertRec(node.right, data);
            }
        } else {
            // duplicate: ignore (typical for labs unless stated otherwise)
            return;
        }
    }

    @Override
    public void check(Node<E> newNode) {
        if (newNode == null)
            return;

        // If I'm root: force black, done
        if (newNode.parent == null) {
            newNode.color = false;
            root = newNode;
            return;
        }

        // If parent is black: no violation
        if (!isRed(newNode.parent))
            return;

        Node<E> parent = newNode.parent;
        Node<E> grand = parent.parent;

        // If parent is root (grand null): just force parent black
        if (grand == null) {
            parent.color = false;
            return;
        }

        // Uncle is sibling of parent
        Node<E> uncle = (parent == grand.left) ? grand.right : grand.left;

        // Case 1: Uncle is red -> recolor grand (and recurse upward)
        if (isRed(uncle)) {
            recolor(grand);
            check(grand);
            return;
        }

        // Case 2/3: Uncle is black -> rotations
        Node<E> newSubRoot = balance(newNode);

        // After balancing, ensure root reference is correct if it changed
        while (root != null && root.parent != null)
            root = root.parent;
        if (root != null)
            root.color = false;
    }

    @Override
    public void recolor(Node<E> grandParent) {
        if (grandParent == null)
            return;

        Node<E> parent = null;
        Node<E> uncle = null;

        // Determine which child is parent by checking which side has a red child-parent
        // chain
        // But we can infer via children colors/parent pointers during check:
        // grandParent has two children: one is parent of the violating node, the other
        // is uncle.
        // We'll compute by using getSibling on the known parent when available in
        // check(),
        // but recolor signature only has grandParent. So do it generically:
        // If one child is red, it can be parent; the other is uncle. (Both should be
        // red in case 1.)
        // We'll just recolor both children if they exist.
        if (grandParent.left != null)
            grandParent.left.color = false;
        if (grandParent.right != null)
            grandParent.right.color = false;

        // grandParent becomes red unless it's root
        if (grandParent.parent != null)
            grandParent.color = true;
        else
            grandParent.color = false;
    }

    @Override
    public Node<E> balance(Node<E> newNode) {
        if (newNode == null)
            return null;

        Node<E> parent = newNode.parent;
        Node<E> grand = (parent != null) ? parent.parent : null;
        if (parent == null || grand == null)
            return newNode;

        boolean parentIsLeft = (parent == grand.left);
        boolean nodeIsLeft = (newNode == parent.left);

        Node<E> subRoot;

        // LL
        if (parentIsLeft && nodeIsLeft) {
            subRoot = rotateRight(grand);
        }
        // RR
        else if (!parentIsLeft && !nodeIsLeft) {
            subRoot = rotateLeft(grand);
        }
        // LR
        else if (parentIsLeft && !nodeIsLeft) {
            rotateLeft(parent);
            subRoot = rotateRight(grand);
        }
        // RL
        else { // (!parentIsLeft && nodeIsLeft)
            rotateRight(parent);
            subRoot = rotateLeft(grand);
        }

        // Color swap: new subtree root black, old grand red
        // After rotations, "subRoot" is the root of rotated subtree, and grand became
        // its child.
        if (subRoot != null) {
            subRoot.color = false; // BLACK
            // find the node that is now under subRoot (old grand)
            // In all cases, old grand is either subRoot.left or subRoot.right depending on
            // rotation.
            Node<E> oldGrand = (subRoot.left == grand || subRoot.right == grand) ? grand : null;
            if (oldGrand != null)
                oldGrand.color = true; // RED
        }

        return subRoot;
    }

    @Override
    public boolean contains(E data) {
        return containsRec(root, data) != null;
    }

    private Node<E> containsRec(Node<E> node, E data) {
        if (node == null || data == null)
            return null;

        int cmp = data.compareTo(node.data);
        if (cmp == 0)
            return node;
        if (cmp < 0)
            return containsRec(node.left, data);
        return containsRec(node.right, data);
    }

    public void BFS(List<Node<E>> list) {
        Queue<Node<E>> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            Node<E> current = q.poll();
            list.add(current);
            if (current != null) {
                q.offer(current.left);
                q.offer(current.right);
            }
        }
    }

    @Override
    public String toString() {
        LinkedList<Node<E>> list = new LinkedList<>();
        this.BFS(list);

        StringBuilder sbNodes = new StringBuilder();
        StringBuilder sbIndexes = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            Node<E> node = list.get(i);

            String nodeStr = (node == null) ? "[null|B]" : node.toString();
            String indexStr = String.valueOf(i);
            int columnWidth = Math.max(nodeStr.length(), indexStr.length()) + 2;
            String formatSpecifier = "%-" + columnWidth + "s";

            sbNodes.append(String.format(formatSpecifier, nodeStr));
            sbIndexes.append(String.format(formatSpecifier, indexStr));
        }

        return sbNodes.toString() + "\n" + sbIndexes.toString();
    }
}
