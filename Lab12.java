import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;

public class Lab12 {
    public static void main(String[] args) {

    }
}

interface IUndirectedNode<T> {
    void addNeighbour(Node<T> node);

    void removeNeighbour(Node<T> node);
}

class Node<T> implements IUndirectedNode<T> {
    T data;
    List<Node<T>> neighbours;
    boolean visited;
    Node<T> parent;

    public Node(T data) {
        this.data = data;
        neighbours = new ArrayList<>();
        visited = false;
        parent = null;
    }

    @Override
    public void addNeighbour(Node<T> neighbour) {
        if (neighbour == null)
            return;
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }

    }

    @Override
    public void removeNeighbour(Node<T> neighbour) {
        neighbours.remove(neighbour);
    }

    public String toString() {
        return data.toString();
    }
}

interface AdjacencyList<T> {
    void addNode(T node);

    void addEdge(T data1, T data2);

    void removeNode(T data);

    void removeEdge(T data1, T data2);

    List<T> bfs(T startData);

    List<T> dfs(T startData);

    List<T> getShortestPath(T startData, T endData);
}

class Graph<T> implements AdjacencyList<T> {
    private Map<T, Node<T>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void addNode(T data) {
        if (data == null) {
            return;
        }
        Node<T> newNode = new Node<>(data);
        if (!nodes.containsKey(data)) {
            nodes.put(data, newNode);
        }

    }

    @Override
    public void addEdge(T data1, T data2) {
        if (data1 == null || data2 == null) {
            return;
        }
        addNode(data1);
        addNode(data2);
        Node<T> n1 = nodes.get(data1);
        Node<T> n2 = nodes.get(data2);
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);

    }

    @Override
    public void removeNode(T data) {
        Node<T> target = nodes.get(data);
        if (target == null) {
            return;
        }
        for (Node<T> other : nodes.values()) {
            other.removeNeighbour(target);
        }
        nodes.remove(data);
    }

    @Override
    public void removeEdge(T data1, T data2) {
        Node<T> n1 = nodes.get(data1);
        Node<T> n2 = nodes.get(data2);
        if (n1 == null || n2 == null) {
            return;
        }
        n1.removeNeighbour(n2);
        n2.removeNeighbour(n1);
    }

    @Override
    public List<T> bfs(T startData) {
        Node<T> start = nodes.get(startData);
        if (start == null) {
            return new ArrayList<>();
        }
        for (Node<T> node : nodes.values()) {
            node.visited = false;
            node.parent = null;
        }
        List<T> result = new ArrayList<>();
        Queue<Node<T>> queue = new LinkedList<>();
        start.visited = true;
        queue.add(start);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            result.add(node.data);
            for (Node<T> neighbour : node.neighbours) {
                if (!neighbour.visited) {
                    neighbour.visited = true;
                    queue.add(neighbour);
                }
            }
        }
        return result;
    }

    @Override
    public List<T> dfs(T startData) {
        Node<T> start = nodes.get(startData);
        if (start == null) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        Set<Node<T>> visited = new HashSet<>();
        dfsHelper(start, visited, result);
        return result;
    }

    private void dfsHelper(Node<T> current, Set<Node<T>> visited, List<T> result) {
        if (current == null) {
            return;
        }
        visited.add(current);
        result.add(current.data);
        for (Node<T> neighbour : current.neighbours) {
            if (!neighbour.visited) {
                dfsHelper(neighbour, visited, result);
            }
        }

    }

    @Override
    public List<T> getShortestPath(T startData, T endData) {
        return List.of();
    }
}