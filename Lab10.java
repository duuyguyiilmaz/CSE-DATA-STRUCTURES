public class Lab10 {
    public static void main(String[] args) {

        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(30);
        avl.insert(20);
        avl.insert(10);
        avl.insert(25);
        avl.insert(40);
        avl.insert(50);

        System.out.println("AVL height: " + avl.height());
        System.out.println("Contains 25 ? " + avl.contains(25));
        avl.remove(20);
        System.out.println("Contains 20 ? " + avl.contains(20));
        System.out.println("Size: " + avl.size());
    }
}

class AVLNode<E extends Comparable<E>> {
  E data;
   AVLNode<E> left;
     AVLNode<E> right;
   int height;

    AVLNode(E data) {
        this.data = data;
        this.height = 1;
    }
}

interface IBST<E extends Comparable<E>> {
    void insert(E element);

    void remove(E element);

    boolean contains(E element);

    int size();

    boolean isEmpty();

    AVLNode<E> findMin(AVLNode<E> n);
}

interface IAVL<E extends Comparable<E>> extends IBST<E> {
    int height();

    int getBalanceFactor(AVLNode<E> node);

    AVLNode<E> balance(AVLNode<E> node);

    AVLNode<E> rotateLeft(AVLNode<E> node);

    AVLNode<E> rotateRight(AVLNode<E> node);

    AVLNode<E> rotateLeftRight(AVLNode<E> node);

    AVLNode<E> rotateRightLeft(AVLNode<E> node);
}

class BST<E extends Comparable<E>> implements IBST<E> {

    protected AVLNode<E> root;
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        return containsRec(root, element);
    }

    protected boolean containsRec(AVLNode<E> n, E e) {
        if (n == null || e == null)
            return false;

        int cmp = e.compareTo(n.data);
        if (cmp == 0)
            return true;
        if (cmp < 0)
            return containsRec(n.left, e);
        return containsRec(n.right, e);
    }

    @Override
    public void insert(E element) {
        root = insertRec(root, element);
    }

    protected AVLNode<E> insertRec(AVLNode<E> n, E e) {
        if (e == null)
            return n;

        if (n == null) {
            size++;
            return new AVLNode<>(e);
        }

        int cmp = e.compareTo(n.data);
        if (cmp < 0) {
            n.left = insertRec(n.left, e);
        } else if (cmp > 0) {
            n.right = insertRec(n.right, e);
        } else {
            return n;
        }

        return n;
    }

    @Override
    public void remove(E element) {
        root = removeRec(root, element);
    }

    protected AVLNode<E> removeRec(AVLNode<E> n, E e) {
        if (n == null || e == null)
            return n;

        int cmp = e.compareTo(n.data);

        if (cmp < 0) {
            n.left = removeRec(n.left, e);
        } else if (cmp > 0) {
            n.right = removeRec(n.right, e);
        } else {
            if (n.left == null && n.right == null) {
                size--;
                return null;
            } else if (n.left == null) {
                size--;
                return n.right;
            } else if (n.right == null) {
                size--;
                return n.left;
            } else {
                AVLNode<E> successor = findMin(n.right);
                n.data = successor.data;
                n.right = removeRec(n.right, successor.data);
            }
        }

        return n;
    }

    @Override
    public AVLNode<E> findMin(AVLNode<E> n) {
        if (n == null)
            return null;
        AVLNode<E> cur = n;
        while (cur.left != null)
            cur = cur.left;
        return cur;
    }
}

class AVLTree<E extends Comparable<E>>
        extends BST<E>
        implements IAVL<E> {

    @Override
    protected AVLNode<E> insertRec(AVLNode<E> n, E e) {
        if (e == null)
            return n;

        if (n == null) {
            size++;
            return new AVLNode<>(e);
        }

        int cmp = e.compareTo(n.data);
        if (cmp < 0) {
            n.left = insertRec(n.left, e);
        } else if (cmp > 0) {
            n.right = insertRec(n.right, e);
        } else {
            return n;
        }
        updateHeight(n);
        return balance(n);
    }

    @Override
    protected AVLNode<E> removeRec(AVLNode<E> n, E e) {
        if (n == null || e == null)
            return n;

        int cmp = e.compareTo(n.data);

        if (cmp < 0) {
            n.left = removeRec(n.left, e);
        } else if (cmp > 0) {
            n.right = removeRec(n.right, e);
        } else {
            if (n.left == null && n.right == null) {
                size--;
                return null;
            } else if (n.left == null) {
                size--;
                return n.right;
            } else if (n.right == null) {
                size--;
                return n.left;
            } else {
                AVLNode<E> successor = findMin(n.right);
                n.data = successor.data;
                n.right = removeRec(n.right, successor.data);
            }
        }



        updateHeight(n);

        return balance(n);
    }

    private void updateHeight(AVLNode<E> n) {
        if (n == null)
            return;
        int lh = height(n.left);
        int rh = height(n.right);
        n.height = 1 + Math.max(lh, rh);
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? 0 : n.height;
    }

    @Override
    public int getBalanceFactor(AVLNode<E> n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    @Override
    public AVLNode<E> balance(AVLNode<E> n) {
        if (n == null)
            return null;

        int bf = getBalanceFactor(n);

        if (bf > 1) {
            if (getBalanceFactor(n.left) >= 0) {

                return rotateRight(n);
            } else {
                return rotateLeftRight(n);
            }
        }

        if (bf < -1) {
            if (getBalanceFactor(n.right) <= 0) {
                return rotateLeft(n);
            } else {
                return rotateRightLeft(n);
            }
        }

        return n;
    }

    @Override
    public AVLNode<E> rotateLeft(AVLNode<E> y) {
        if (y == null || y.right == null)
            return y;

        AVLNode<E> x = y.right;
        AVLNode<E> L = x.left;

        x.left = y;
        y.right = L;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    @Override
    public AVLNode<E> rotateRight(AVLNode<E> x) {
        if (x == null || x.left == null)
            return x;

        AVLNode<E> y = x.left;
        AVLNode<E> R = y.right;

        y.right = x;
        x.left = R;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    @Override
    public AVLNode<E> rotateLeftRight(AVLNode<E> n) {
        if (n == null)
            return null;
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    @Override
    public AVLNode<E> rotateRightLeft(AVLNode<E> n) {
        if (n == null)
            return null;
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }
}
