import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Collections;

public class Lab13 {

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

    }
}

class Edge<T> {
    Node<T> target;
    int weight;

    public Edge(Node<T> target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Node<T> implements Comparable<Node<T>> {
    T data;
    List<Edge<T>> edges;

    Node<T> parent;
    int gCost;

    public Node(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
        gCost = Integer.MAX_VALUE;
    }

    public void addNeighbor(Node<T> target, int weight) {
        this.edges.add(new Edge<>(target, weight));
    }

    @Override
    public int compareTo(Node<T> o) {
        return Integer.compare(this.gCost, o.gCost);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

class Graph<T> {
    private Map<T, Node<T>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    // Adds a node if it doesn't exist
    public void addNode(T data) {
        if (data == null)
            return;
        if (!nodes.containsKey(data)) {
            nodes.put(data, new Node<>(data));
        }
    }

    // Adds an undirected edge
    public void addEdge(T data1, T data2, int weight) {
        if (data1 == null || data2 == null)
            return;
        if (weight < 0)
            return; // genelde lablar negatif istemez

        addNode(data1);
        addNode(data2);

        Node<T> n1 = nodes.get(data1);
        Node<T> n2 = nodes.get(data2);

        // undirected: iki yönlü ekle
        n1.addNeighbor(n2, weight);
        n2.addNeighbor(n1, weight);
    }

    // Prints edges for debugging MST
    public void printEdges() {
        for (T key : nodes.keySet()) {
            Node<T> n = nodes.get(key);
            for (Edge<T> e : n.edges) {
                if (n.data.hashCode() < e.target.data.hashCode())
                    System.out.println(n.data + " - " + e.target.data + " : " + e.weight);
            }
        }
    }

    // Task: Implement a greedy search.
    // Always pick the cheapest *immediate* neighbor.
    // Handles dead ends by stopping.
    public List<T> greedyWalk(T startData, T endData) {
        Node<T> start = nodes.get(startData);
        Node<T> end = nodes.get(endData);
        if (start == null || end == null)
            return new ArrayList<>();

        // reset parents (greedy için yeterli)
        for (Node<T> n : nodes.values()) {
            n.parent = null;
        }

        Queue<Node<T>> q = new LinkedList<>();
        Set<Node<T>> visited = new HashSet<>();

        visited.add(start);
        q.offer(start);

        while (!q.isEmpty()) {
            Node<T> current = q.poll();

            if (current == end) {
                return reconstructPath(current);
            }

            // 1) current'ın visited olmayan komşuları içinde
            // edge.weight en küçük olanı seç
            Node<T> bestNext = null;
            int bestW = Integer.MAX_VALUE;

            for (Edge<T> e : current.edges) {
                Node<T> nb = e.target;
                if (nb == null)
                    continue;
                if (visited.contains(nb))
                    continue;

                if (e.weight < bestW) {
                    bestW = e.weight;
                    bestNext = nb;
                }
            }

            // dead-end: ilerleyecek yeni komşu yoksa dur
            if (bestNext == null) {
                break;
            }

            bestNext.parent = current;
            visited.add(bestNext);
            q.offer(bestNext);
        }

        // end'e ulaşamadı
        return new ArrayList<>();
    }

    // Task: Implement Dijkstra to find the true shortest path.
    // Use a PriorityQueue to explore the globally cheapest path
    public List<T> dijkstra(T startData, T endData) {
        Node<T> start = nodes.get(startData);
        Node<T> end = nodes.get(endData);
        if (start == null || end == null)
            return new ArrayList<>();

        // reset
        for (Node<T> n : nodes.values()) {
            n.parent = null;
            n.gCost = Integer.MAX_VALUE;
        }

        Set<Node<T>> visited = new HashSet<>();
        PriorityQueue<Node<T>> pq = new PriorityQueue<>(); // compareTo -> gCost

        start.gCost = 0;
        pq.offer(start);

        while (!pq.isEmpty()) {
            Node<T> current = pq.poll();

            if (visited.contains(current))
                continue;
            visited.add(current);

            if (current == end) {
                return reconstructPath(current);
            }

            // relax neighbors
            for (Edge<T> e : current.edges) {
                Node<T> nb = e.target;
                if (nb == null)
                    continue;
                if (visited.contains(nb))
                    continue;

                if (current.gCost == Integer.MAX_VALUE)
                    continue;

                int newDist = current.gCost + e.weight;
                if (newDist < nb.gCost) {
                    nb.gCost = newDist;
                    nb.parent = current;
                    pq.offer(nb); // decrease-key yok, tekrar ekliyoruz
                }
            }
        }

        return new ArrayList<>();
    }

    // Task: Return a NEW Graph representing the Minimum Spanning Tree.
    public Graph<T> mstPrims(T startData) {
        Node<T> start = nodes.get(startData);
        if (start == null)
            return new Graph<>();

        // reset: Prim'de gCost = key (node'u MST'ye bağlayan en ucuz edge)
        for (Node<T> n : nodes.values()) {
            n.parent = null;
            n.gCost = Integer.MAX_VALUE;
        }

        Graph<T> mst = new Graph<>();
        Set<Node<T>> inMst = new HashSet<>();
        PriorityQueue<Node<T>> pq = new PriorityQueue<>(); // gCost = key

        start.gCost = 0;
        pq.offer(start);

        while (!pq.isEmpty()) {
            Node<T> u = pq.poll();

            if (inMst.contains(u))
                continue;
            inMst.add(u);

            // MST grafına node'u ekle
            mst.addNode(u.data);

            // parent varsa, parent-edge'i MST'ye ekle
            if (u.parent != null) {
                mst.addEdge(u.parent.data, u.data, u.gCost);
            }

            // neighbors: edge ağırlığı daha küçükse key update
            for (Edge<T> e : u.edges) {
                Node<T> v = e.target;
                if (v == null)
                    continue;
                if (inMst.contains(v))
                    continue;

                if (e.weight < v.gCost) {
                    v.gCost = e.weight;
                    v.parent = u;
                    pq.offer(v);
                }
            }
        }

        return mst;
    }

    // Helper: Backtracks from end node to start using parent pointers
    private List<T> reconstructPath(Node<T> end) {
        List<T> list = new ArrayList<>();
        Node<T> current = end;
        while (current != null) {
            list.add(current.data);
            current = current.parent;
        }
        Collections.reverse(list);
        return list;
    }
}
