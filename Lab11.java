import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Lab11 {
    public static void main(String[] args) {
      RBTree<Integer> tree = new RBTree<>();

    tree.insert(33);
    tree.insert(13);
    tree.insert(53);
    tree.insert(11);
    tree.insert(21);
    tree.insert(41);
    tree.insert(61);
    tree.insert(15);
    tree.insert(31);

    System.out.println(tree);


    }
}

class RBNODE1<E> {
    E data;
    RBNODE1<E> left, right,parent;
    boolean color = true; // true = RED, false = BLACK

    public RBNODE1(E data) {
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
    RBNODE1<E> rotateRight(RBNODE1<E> node);

    RBNODE1<E> rotateLeft(RBNODE1<E> node);
}

interface IRBTree<E> extends IBalancedTree<E> {
    void check(RBNODE1<E> node);

    RBNODE1<E> balance(RBNODE1<E> node);

    void recolor(RBNODE1<E> node);
}

class RBTree<E extends Comparable<E>> implements IRBTree<E> {
    private RBNODE1<E> root;
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
    public RBNODE1<E> rotateLeft(RBNODE1<E> node) {
        if (node == null)
            return null;

        RBNODE1<E> x = node.right; // new root of this subtree
        if (x == null)
            return node; // cannot rotate

        RBNODE1<E> B = x.left; // will move to node.right

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
    public RBNODE1<E> rotateRight(RBNODE1<E> node) {
        if (node == null)
            return null;

        RBNODE1<E> y = node.left; // new root of this subtree
        if (y == null)
            return node; // cannot rotate

        RBNODE1<E> B = y.right; // will move to node.left

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

    private boolean isRed(RBNODE1<E> node) {
        return node != null && node.color; // true = RED
    }

    private RBNODE1<E> getSibling(RBNODE1<E> node) {
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
            root = new RBNODE1<>(data);
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

    private void insertRec(RBNODE1<E> node, E data) {
        int cmp = data.compareTo(node.data);

        if (cmp < 0) {
            if (node.left == null) {
                RBNODE1<E> newNode = new RBNODE1<>(data);
                node.left = newNode;
                newNode.parent = node;
                size++;
                check(newNode);
            } else {
                insertRec(node.left, data);
            }
        } else if (cmp > 0) {
            if (node.right == null) {
                RBNODE1<E> newNode = new RBNODE1<>(data);
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
    public void check(RBNODE1<E> newNode) {
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

        RBNODE1<E> parent = newNode.parent;
        RBNODE1<E> grand = parent.parent;

        // If parent is root (grand null): just force parent black
        if (grand == null) {
            parent.color = false;
            return;
        }

        // Uncle is sibling of parent
        RBNODE1<E> uncle = (parent == grand.left) ? grand.right : grand.left;

        // Case 1: Uncle is red -> recolor grand (and recurse upward)
        if (isRed(uncle)) {
            recolor(grand);
            check(grand);
            return;
        }

        // Case 2/3: Uncle is black -> rotations
        RBNODE1<E> newSubRoot = balance(newNode);

        // After balancing, ensure root reference is correct if it changed
        while (root != null && root.parent != null)
            root = root.parent;
        if (root != null)
            root.color = false;
    }

    @Override
    public void recolor(RBNODE1<E> grandParent) {
        if (grandParent == null)
            return;

        RBNODE1<E> parent = null;
        RBNODE1<E> uncle = null;

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
    public RBNODE1<E> balance(RBNODE1<E> newNode) {
        if (newNode == null)
            return null;

        RBNODE1<E> parent = newNode.parent;
        RBNODE1<E> grand = (parent != null) ? parent.parent : null;
        if (parent == null || grand == null)
            return newNode;

        boolean parentIsLeft = (parent == grand.left);
        boolean nodeIsLeft = (newNode == parent.left);

        RBNODE1<E> subRoot;

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
            RBNODE1<E> oldGrand = (subRoot.left == grand || subRoot.right == grand) ? grand : null;
            if (oldGrand != null)
                oldGrand.color = true; // RED
        }

        return subRoot;
    }

    @Override
    public boolean contains(E data) {
        return containsRec(root, data) != null;
    }

    private RBNODE1<E> containsRec(RBNODE1<E> node, E data) {
        if (node == null || data == null)
            return null;

        int cmp = data.compareTo(node.data);
        if (cmp == 0)
            return node;
        if (cmp < 0)
            return containsRec(node.left, data);
        return containsRec(node.right, data);
    }

    public void BFS(List<RBNODE1<E>> list) {
        Queue<RBNODE1<E>> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            RBNODE1<E> current = q.poll();
            list.add(current);
            if (current != null) {
                q.offer(current.left);
                q.offer(current.right);
            }
        }
    }

    @Override
    public String toString() {
        LinkedList<RBNODE1<E>> list = new LinkedList<>();
        this.BFS(list);

        StringBuilder sbNodes = new StringBuilder();
        StringBuilder sbIndexes = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            RBNODE1<E> node = list.get(i);

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
