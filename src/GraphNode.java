public class GraphNode {
    private int name;
    private boolean marked;

    public GraphNode(int name) {
        this.name = name;
        this.marked = false;
    }

    public void mark(boolean mark) {
        this.marked = mark;
    }

    public boolean isMarked() {
        return marked;
    }

    public int getName() {
        return name;
    }
}
