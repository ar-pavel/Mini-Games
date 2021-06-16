public class Edge {
    private Node u;
    private Node v;
    private int type;
    private String label;

    public Edge(Node u, Node v, int type) {
        this.u = u;
        this.v = v;
        this.type = type;
    }

    public Edge(Node u, Node v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    Node firstEndpoint(){
        return this.u;
    }
    Node secondEndpoint(){
        return this.v;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
