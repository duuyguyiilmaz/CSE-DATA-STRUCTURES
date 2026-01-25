import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Lab12 {
    public static void main(String[] args) {
         Graph<String> graph = new Graph<>();

    // Nodes & edges (undirected graph)
    graph.addEdge("A", "B");
    graph.addEdge("A", "C");
    graph.addEdge("B", "D");
    graph.addEdge("C", "D");
    graph.addEdge("C", "E");
    graph.addEdge("D", "F");
    graph.addEdge("E", "F");

    // BFS
    System.out.println("BFS from A:");
    System.out.println(graph.bfs("A"));

    // DFS
    System.out.println("DFS from A:");
    System.out.println(graph.dfs("A"));


    }
}

interface IUndirectedNode<T> {
    void addNeighbour(GNode<T> node);

    void removeNeighbour(GNode<T> node);
}

class GNode<T> implements IUndirectedNode<T> {
    T data;
    List<GNode<T>> neighbours;
    boolean visited;
    GNode<T> parent;

    public GNode(T data) {
        this.data = data;
        neighbours = new ArrayList<>();
        visited = false;
        parent = null;
    }

    @Override
    public void addNeighbour(GNode<T> neighbour) {
        if (neighbour == null)
            return;
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }

    }

    @Override
    public void removeNeighbour(GNode<T> neighbour) {
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

}

class Graph<T> implements AdjacencyList<T> {
    private Map<T, GNode<T>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void addNode(T data) {
        if (data == null) {
            return;
        }
        GNode<T> newNode = new GNode<>(data);
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
        GNode<T> n1 = nodes.get(data1);
        GNode<T> n2 = nodes.get(data2);
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);

    }

    @Override
    public void removeNode(T data) {
        GNode<T> target = nodes.get(data);
        if (target == null) {
            return;
        }
        for (GNode<T> other : nodes.values()) {
            other.removeNeighbour(target);
        }
        nodes.remove(data);
    }

    @Override
    public void removeEdge(T data1, T data2) {
        GNode<T> n1 = nodes.get(data1);
        GNode<T> n2 = nodes.get(data2);
        if (n1 == null || n2 == null) {
            return;
        }
        n1.removeNeighbour(n2);
        n2.removeNeighbour(n1);
    }

    @Override
    public List<T> bfs(T startData) {
        GNode<T> start = nodes.get(startData);
        if (start == null) {
            return new ArrayList<>();
        }
        for (GNode<T> node : nodes.values()) {
            node.visited = false;
            node.parent = null;
        }
        List<T> result = new ArrayList<>();
        Queue<GNode<T>> queue = new LinkedList<>();
        start.visited = true;
        queue.add(start);
        while (!queue.isEmpty()) {
            GNode<T> node = queue.poll();
            result.add(node.data);
            for (GNode<T> neighbour : node.neighbours) {
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
        GNode<T> start = nodes.get(startData);
        if (start == null) {
            return new ArrayList<>();
        }
        for (GNode<T> node : nodes.values()) {
            node.visited = false;
            node.parent = null;
        }
                List<T> result = new ArrayList<>();
        dfsHelper(start,  result);
        return result;
    }

    private void dfsHelper(GNode<T> current, List<T> result) {
        if (current == null) {
            return;
        }
      current.visited = true;
        result.add(current.data);
        for (GNode<T> neighbour : current.neighbours) {
            if (!neighbour.visited) {
                dfsHelper(neighbour,  result);
            }
        }

    }


}