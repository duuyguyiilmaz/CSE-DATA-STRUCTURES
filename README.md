----------------LAB03 – LINKED LIST---------------------
This lab focuses on implementing linked list data structures from scratch in Java,
without using built-in collection classes.
---

--------- Implemented Structures

- Singly Linked List
- Circular Linked List
- Doubly Linked List
  ------------ Key Features
- Generic type support
- Index-based operations (`get`, `set`, `insertAt`, `removeAt`)
- Proper index validation with `IndexOutOfBoundsException`
- Null-safe search and removal operations
- Clean separation of list logic and node structures
- -----------------Lab05 – RECURSION-------------------------
  Recursive implementations of fundamental algorithms including factorial, array sum,
  power calculation, binary search, string reversal, Fibonacci sequence, tail recursion,
  and linked list operations.
  
----------------- Lab06 – STACK AND QUEUE IMPLEMENTATIONS---------------
ArrayStack is an array-based stack implementation that uses a top variable
to manage stack operations, allowing push, pop, and top operations to run
in constant time (O(1)).

ArrayQueue is a circular array-based queue implementation that maintains
front and size variables, enabling enqueue, dequeue, and front
operations to execute in constant time (O(1)).

LinkedStack and LinkedQueue are linked-list–based implementations where all
core operations run in constant time (O(1)), as elements are added or removed
directly from the head or tail without traversal.

-------------- Lab07 – BINARY TREE IMPLEMENTATIONS AND TRAVERSALS-------------------
This lab includes array-based and linked binary tree implementations.
ArrayBT represents a complete binary tree using an array structure, where
insertion and level-order traversal (BFS) run in constant time per operation
(O(1) amortized for insertion, O(n) for traversal).
LinkedBT implements a binary tree using linked nodes and supports insertion,
search, and removal using breadth-first traversal. Operations such as
insert, contains, and remove run in linear time (O(n)), as the tree
is traversed level by level.
Both implementations support BFS and DFS traversals, as well as recursive
inorder, preorder, and postorder traversals, each with time complexity
O(n), where n is the number of nodes in the tree.
---------------- Lab08 – PRIORITY QUEUE IMPLEMENTATIONS ----------------
This lab focuses on implementing the Priority Queue ADT using different underlying data
structures. UnsortedArrayPQ is an array-based priority queue where insertion runs in
constant time (O(1)), while finding and removing the minimum element requires linear
time (O(n)). SortedLinkedPQ is a linked-list-based implementation that maintains elements
in sorted order, allowing min and removeMin operations to run in constant time (O(1)),
with insertion taking linear time (O(n)).
---------------- Lab10 – AVL TREE IMPLEMENTATION ----------------
This lab focuses on implementing a Binary Search Tree (BST) and an AVL Tree in Java.
The BST provides basic operations such as insertion, deletion, and search using recursive
methods. The AVL Tree extends the BST by maintaining balance after insert and remove
operations through height updates and rotations, ensuring that the tree height remains
logarithmic and all operations run efficiently.
---------------- Lab11 – RED-BLACK TREE IMPLEMENTATION ----------------
This lab focuses on implementing a Red-Black Tree (RBTree) in Java with support for balanced insertion. The tree maintains Red-Black Tree properties by applying recoloring and left/right rotations after each insertion to ensure balance. Each newly inserted node starts as red, and violations are resolved using standard RBTree cases involving the parent, uncle, and grandparent nodes. As a result, the tree remains approximately balanced, guaranteeing efficient search and insertion operations with logarithmic time complexity.
---------------- Lab12 – GRAPH IMPLEMENTATION (BFS & DFS) ----------------
This lab focuses on implementing an undirected graph using an adjacency list representation in Java. Each graph node maintains a list of its neighboring nodes, and edges are added symmetrically to represent an undirected structure. The graph supports basic operations such as adding and removing nodes and edges, as well as graph traversal algorithms. Breadth-First Search (BFS) is implemented using a queue to explore nodes level by level, while Depth-First Search (DFS) is implemented recursively to traverse the graph in a depth-oriented manner. Both traversal methods correctly track visited nodes to prevent revisiting and ensure proper traversal order.

