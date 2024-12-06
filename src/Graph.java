import java.util.*;

public class Graph implements GraphADT {
    private Map<Integer, GraphNode> nodes;
    private Map<GraphNode, List<GraphEdge>> adjacencyList;

    public Graph(int n) {
        nodes = new HashMap<>();
        adjacencyList = new HashMap<>();
        for (int i = 0; i < n; i++) {
            GraphNode node = new GraphNode(i);
            nodes.put(i, node);
            adjacencyList.put(node, new ArrayList<>());
        }
    }

    @Override
    public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {
        if (!nodes.containsValue(u) || !nodes.containsValue(v)) {
            throw new GraphException("One or both nodes do not exist");
        }
        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.secondEndpoint().equals(v)) {
                throw new GraphException("Edge already exists");
            }
        }
        GraphEdge edge = new GraphEdge(u, v, edgeType, label);
        adjacencyList.get(u).add(edge);
        adjacencyList.get(v).add(edge);
    }

    @Override
    public GraphNode getNode(int name) throws GraphException {
        if (!nodes.containsKey(name)) {
            throw new GraphException("Node does not exist");
        }
        return nodes.get(name);
    }

    @Override
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        if (!nodes.containsValue(u)) {
            throw new GraphException("Node does not exist");
        }
        return adjacencyList.get(u).iterator();
    }

    @Override
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.secondEndpoint().equals(v)) {
                return edge;
            }
        }
        throw new GraphException("Edge does not exist");
    }

    @Override
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.secondEndpoint().equals(v)) {
                return true;
            }
        }
        return false;
    }
}
