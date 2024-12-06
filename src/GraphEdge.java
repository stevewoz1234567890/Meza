public class GraphEdge {
    private GraphNode u, v;
    private int type;
    private String label;

    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    public GraphNode firstEndpoint() {
        return u;
    }

    public GraphNode secondEndpoint() {
        return v;
    }

    public int getType() {
        return type;
    }

    public void setType(int newType) {
        this.type = newType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String newLabel) {
        this.label = newLabel;
    }
}
