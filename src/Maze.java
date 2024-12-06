import java.io.*;
import java.util.*;

public class Maze {
    private Graph graph; // Graph representation of the maze
    private GraphNode entrance, exit; // Entrance and exit nodes
    private int coins; // Available coins

    public Maze(String inputFile) throws MazeException {
        BufferedReader br = null;
        try {
            // Open the file
            br = new BufferedReader(new FileReader(inputFile));
            System.out.println("Reading maze file: " + inputFile);

            // Read scale (not used)
            int scale = Integer.parseInt(br.readLine());

            // Read maze dimensions and coins
            int width = Integer.parseInt(br.readLine());
            int length = Integer.parseInt(br.readLine());
            coins = Integer.parseInt(br.readLine());

            // Create the graph
            graph = new Graph(width * length);

            // Read and construct the graph
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                if (row % 2 == 0) {
                    // Process room rows
                    for (int col = 0; col < line.length(); col++) {
                        char room = line.charAt(col);
                        int nodeIndex = (row / 2) * width + (col / 2);

                        // Identify entrance and exit
                        if (room == 's') {
                            entrance = graph.getNode(nodeIndex);
                        } else if (room == 'x') {
                            exit = graph.getNode(nodeIndex);
                        }

                        // Add horizontal edges for doors and corridors
                        if (col % 2 == 1) {
                            GraphNode u = graph.getNode((row / 2) * width + (col / 2));
                            GraphNode v = graph.getNode((row / 2) * width + (col / 2) + 1);

                            if (Character.isDigit(room)) {
                                graph.insertEdge(u, v, Character.getNumericValue(room), "door");
                            } else if (room == 'c') {
                                graph.insertEdge(u, v, 0, "corridor");
                            }
                        }
                    }
                } else {
                    // Process connection rows
                    for (int col = 0; col < line.length(); col++) {
                        char conn = line.charAt(col);

                        // Add vertical edges for doors and corridors
                        if (col % 2 == 0) {
                            GraphNode u = graph.getNode((row / 2) * width + (col / 2));
                            GraphNode v = graph.getNode(((row / 2) + 1) * width + (col / 2));

                            if (Character.isDigit(conn)) {
                                graph.insertEdge(u, v, Character.getNumericValue(conn), "door");
                            } else if (conn == 'c') {
                                graph.insertEdge(u, v, 0, "corridor");
                            }
                        }
                    }
                }
                row++;
            }
            System.out.println("Graph successfully built!");
        } catch (Exception e) {
            throw new MazeException("Error reading maze file: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }

    public Graph getGraph() throws MazeException {
        if (graph == null) {
            throw new MazeException("Graph is null");
        }
        return graph;
    }

    public Iterator<GraphNode> solve() {
        Stack<GraphNode> path = new Stack<>();
        try {
            if (DFS(entrance, path, coins)) {
                return path.iterator();
            }
        } catch (Exception e) {
            System.out.println("Error during DFS: " + e.getMessage());
        }
        return null;
    }

    private boolean DFS(GraphNode node, Stack<GraphNode> path, int remainingCoins) throws GraphException {
        // Mark the node and add it to the path
        node.mark(true);
        path.push(node);

        // Check if we reached the exit
        if (node.equals(exit))
            return true;

        // Traverse adjacent nodes
        Iterator<GraphEdge> edges = graph.incidentEdges(node);
        while (edges.hasNext()) {
            GraphEdge edge = edges.next();
            GraphNode neighbor = edge.firstEndpoint().equals(node) ? edge.secondEndpoint() : edge.firstEndpoint();

            if (!neighbor.isMarked()) {
                int cost = edge.getType();
                if (cost <= remainingCoins) { // Check if we have enough coins
                    if (DFS(neighbor, path, remainingCoins - cost)) {
                        return true;
                    }
                }
            }
        }

        // Backtrack: unmark the node and remove it from the path
        path.pop();
        node.mark(false);
        return false;
    }
}
