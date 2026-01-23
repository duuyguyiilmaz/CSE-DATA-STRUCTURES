----------------LAB03 – LINKED LIST---------------------
This lab focuses on implementing linked list data structures from scratch in Java,
without using built-in collection classes.
------------ Implemented Structures
- Singly Linked List
- Circular Linked List
- Doubly Linked List
------------ Key Features
- Generic type support
- Index-based operations (`get`, `set`, `insertAt`, `removeAt`)
- Proper index validation with `IndexOutOfBoundsException`
- Null-safe search and removal operations
- Clean separation of list logic and node structures
- 
  -----------------Lab05 – RECURSION-------------------------  
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

