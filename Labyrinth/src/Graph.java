import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT{

    private int n;

    private ArrayList<ArrayList<Edge>> adjList;

    private ArrayList<Node> nodes;

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Graph(int n) {
        this.n = n;

        this.adjList = new ArrayList<ArrayList<Edge>>(n);
        this.nodes = new ArrayList<>(n);

        // initializing the lists
        for (int i = 0; i < n; i++) {
            this.adjList.add(new ArrayList<>());
            this.nodes.add(new Node(i));
        }

    }

    private boolean notValidNode(Node x){
        return (x == null || x.getName() < 0 || x.getName() >= n);
    }

    @Override
    public void insertEdge(Node nodeu, Node nodev, int type, String label) throws GraphException {

        if( notValidNode(nodeu) || notValidNode(nodev) ||
               areAdjacent(nodeu, nodev))
           throw new GraphException("insertion error");

       Edge edge = new Edge(nodeu, nodev, type, label);

        adjList.get(nodeu.getName()).add(edge);
        adjList.get(nodev.getName()).add(edge);
    }

    @Override
    public void insertEdge(Node nodeu, Node nodev, int type) throws GraphException {

        if(notValidNode((nodeu)) || notValidNode((nodev))
                || areAdjacent(nodeu, nodev))
            throw new GraphException("insertion error");

        Edge edge = new Edge(nodeu, nodev, type);

        // for the iterator
        adjList.get(nodeu.getName()).add(edge);
        adjList.get(nodev.getName()).add(edge);

    }

    @Override
    public Node getNode(int u) throws GraphException {
        if(u<0 || u >=n || nodes.get(u)==null)
            throw new GraphException("");

        return nodes.get(u);
    }

    @Override
    public Iterator incidentEdges(Node u) throws GraphException {
        if(adjList.get(u.getName())==null)
            throw new GraphException("");

        return adjList.get(u.getName()).iterator();
    }

    @Override
    public Edge getEdge(Node u, Node v) throws GraphException {
        if( notValidNode(u) || notValidNode(v))
            throw new GraphException("");

        for ( Edge edge : adjList.get(u.getName())) {
            if(edge.secondEndpoint().getName() == v.getName())
                return edge;
        }

        return null;
    }

    @Override
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        if(notValidNode(u) || notValidNode(v))
            throw new GraphException("error");

        for ( Edge edge : adjList.get(u.getName())) {
            if(edge.secondEndpoint().getName() == v.getName() || edge.firstEndpoint().getName()==v.getName())
                return true;
        }
        return  false;
    }
}
