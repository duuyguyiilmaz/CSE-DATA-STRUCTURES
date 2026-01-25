public class Lab05 {
   static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

        public static void main(String[] args) {
        System.out.println("factorial(5) = " + factorial(5));
            int[] array = { 1, 2, 3 };
        System.out.println("sum(array, 2) = " + sum(array, 2));
        System.out.println("power(2, 0) = " + power(2, 0));

        System.out.println("binarySearch(array, 8) = " +
                binarySearch(array, 8, 0, array.length - 1));
        System.out.println("reverse(\"null\") = " + reverse("null"));
          System.out.println("fib(5) = " + fib(5));
                  System.out.println("tailSum(5) = " + tailSum(5, 0));
       Node head = buildList(1, 2, 3, 4);
        System.out.println("listSum = " + listSum(head));
        System.out.println("contains 3 = " + contains(head, 3));
        System.out.println("contains 9 = " + contains(head, 9));

        Node rev = reverseList(head);
        System.out.println("reversed list head = " + (rev != null ? rev.data : "null"));

        }

        public static long factorial(int n) {
                    if (n < 0) throw new IllegalArgumentException("n must be >= 0");

            if (n == 0) {
                return 1;
            }
            return n * factorial(n - 1);
        }

        public static int sum(int[] arr, int n) {
                    if (arr == null) throw new IllegalArgumentException("arr cannot be null");
        if (n < 0 || n > arr.length) throw new IndexOutOfBoundsException("n: " + n);

            if(n==0){
                return 0;
            }
            return arr[n - 1] +  sum(arr, n - 1);
        }

        public static long power(long base, int exp) {
           if (exp < 0) throw new IllegalArgumentException("exp must be >= 0");
        if (exp == 0) return 1;
            return base * power(base, exp - 1);
        }

        public static int binarySearch(int[] arr, int target, int low, int high) {
                    if (arr == null) throw new IllegalArgumentException("arr cannot be null");

            if (low > high) {
                return -1;
            }
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            else if (target < arr[mid]) {
                return binarySearch(arr, target, low, mid - 1);
            } else {
                return binarySearch(arr, target, mid + 1, high);
            }
        }

        public static String reverse(String s) {
            if (s.length() <= 1 ) {
                return s;
            }
            return reverse(s.substring(1)) + s.charAt(0);
        }

        public static long fib(int n) {
            if (n == 0) {
                return 1;
            }
            if (n == 1) {
                return 1;
            }
            return fib(n - 1) + fib(n - 2);
        }

        public static long tailSum(int n, long acc) {
                    if (n < 0) throw new IllegalArgumentException("n must be >= 0");

            if (n == 0) {
                return acc;
            }
            return tailSum(n - 1, acc + n);
        }

        public static int listSum(Node head) {

            if (head == null) {
                return 0;
            }
            return head.data + listSum(head.next);
        }

        public static boolean contains(Node head, int target) {
            if (head == null) {
                return false;
            }
            if (head.data == target) {
                return true;
            }

            return contains(head.next, target);
        }

        public static Node reverseList(Node head) {
            if (head == null || head.next == null) {
                return head;
            }
            Node newHead = reverseList(head.next);

            head.next.next = head;
            head.next = null;
            return newHead;
        }

        public static Node buildList(int... vals) {
            if (vals.length == 0) {
                return null;
            } else {
                Node head = new Node(vals[0]);
                Node curr = head;
                for (int i = 1; i < vals.length; i++) {
                    curr.next = new Node(vals[i]);
                    curr = curr.next;
                }
                return head;
            }
        }
    }


