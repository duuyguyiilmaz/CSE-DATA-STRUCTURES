public class Lab05 {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }

        public static void main(String[] args) {
            System.out.println(factorial(5));
            int[] array = { 1, 2, 3 };
            System.out.println(sum(array, 2));
            System.out.println(power(2, 0));
            System.out.println(binarySearch(array, 8, 0, 0));
            System.out.println(reverse("null"));

        }

        public static long factorial(int n) {
            if (n == 0) {
                return 1;
            }
            return n * factorial(n - 1);
        }

        public static int sum(int[] arr, int n) {
            int sum = 0;
            if (n == 0) {
                return sum;
            }
            return arr[n - 1] + sum + sum(arr, n - 1);
        }

        public static long power(long base, int exp) {
            if (exp == 0) {
                return 1;
            }
            return base * power(base, exp - 1);
        }

        public static int binarySearch(int[] arr, int target, int low, int high) {
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
            if (s.length() <= 1 || s == null) {
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

}
