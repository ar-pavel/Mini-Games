import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Labyrinth {

    private Graph graph;
    private final int roomSize;
    private final int labWidth;
    private final int labLength;
    private final ArrayList<Integer>numberOfKeys;

    private Node EXIT;
    private Node START;

    /* reads the input file and builds the graph representing the labyrinth. If the input file does not exist, or the format of the input file is incorrect this
      method should throw a LabyrinthException. Read below to learn about the format of the input
     file. */
    public Labyrinth(String fileName) throws LabyrinthException {
        numberOfKeys = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String st;
         /*
            S
            A
            L
            k0 k1 k2 k3 k4 k5 k6 k7 k8 k9
            RHRHRH· · ·RHR
            VWVWVW· · ·VWV
            RHRHRH· · ·RHR
            VWVWVW· · ·VWV
            .
            RHRHRH· · ·RHR
        */
            // input first three lines
            roomSize = Integer.parseInt(br.readLine());
            labWidth = Integer.parseInt(br.readLine());
            labLength = Integer.parseInt(br.readLine());

            // read number of keys
            st = br.readLine();
            Arrays.stream(st.split(" ")).mapToInt(Integer::parseInt).forEach(numberOfKeys::add);
//            System.out.println(numberOfKeys.toString());

            // read the grid
            int lineLength = -1;
            ArrayList<String> lines = new ArrayList<>();
            while ((st = br.readLine()) != null){
//                System.out.println(st);
                // check if this is the first line (-1) or has same length as previous
                if(lineLength!=-1 && st.length()!=lineLength)
                    throw new LabyrinthException("");
                lineLength = st.length();
                lines.add(st);
            }

            addToGraph(lines);

        } catch (Exception e) {
//            System.err.println(e.getMessage());
            throw new LabyrinthException("");
        }
    }

    private void addToGraph(ArrayList<String> lines) throws LabyrinthException {
        /*
            - ’s’: entrance to the labyrinth
            - ’x’: exit of the labyrinth
            - ’i’: room
            - ’c’: corridor
            - ’w’: wall
            - ’0’, ’1’, ... ’9’: door of type given by the digit; so ’0’ represents a door of type 0, ’1’ represents a door
            of type 1, and so on.
         */
        this.graph = new Graph(labWidth*labLength);

        try {
            // create an matrix to track correspond node number
            int[][] nodes = new int[lines.size()][lines.get(0).length()];
            for (int i = 0, nodeCnt = 0; i < lines.size(); i += 2)
                for (int j = 0; j < lines.get(0).length(); j += 2) {
                    nodes[i][j]=nodeCnt;
                    nodeCnt++;
                }
//            for (int i = 0, nodeCnt = 0; i < lines.size(); i += 2) {
//                for (int j = 0; j < lines.get(0).length(); j += 2)
//                    System.out.print(nodes[i][j] + " ");
//                System.out.println();
//            }

            for (int i = 0; i < lines.size(); i += 2) {
                for (int j = 0; j < lines.get(0).length(); j += 2) {
                    // marks starting and ending points
                    if (lines.get(i).charAt(j) == 's')
                        START = new Node(nodes[i][j]);
                    else if (lines.get(i).charAt(j) == 'x')
                        EXIT = new Node(nodes[i][j]);

                    // right node
                    if (j < lines.get(i).length() - 1) {
                        if (lines.get(i).charAt(j + 1) != 'w') {
                            this.graph.insertEdge(new Node(nodes[i][j]),
                                    new Node(nodes[i][j + 2]),
                                    Character.isDigit(lines.get(i).charAt(j + 1)) ?
                                            Integer.parseInt(String.valueOf(lines.get(i).charAt(j + 1))) :
                                            -1
                            );
                        }
                    }
                    // upper node
                    if (i > 0) {
                        if (lines.get(i - 1).charAt(j) != 'w') {
                            this.graph.insertEdge(new Node(nodes[i][j]),
                                    new Node(nodes[i - 2][j]),
                                    Character.isDigit(lines.get(i - 1).charAt(j)) ?
                                            Integer.parseInt(String.valueOf(lines.get(i - 1).charAt(j))) :
                                            -1
                            );
                        }
                    }
                }
            }
        }catch (Exception e){
//            System.err.println("error happened here");
            throw new LabyrinthException("");
        }
//        System.out.println("Start : " + START.getName() + ", EXIT : " + EXIT.getName());
    }


    /*returns a java Iterator containing the nodes of the path from the entrance to
     the exit of the labyrinth, if such a path exists. If the path does not exist, this method returns the value
     null */
    public Iterator solve() throws LabyrinthException, GraphException {
        if(graph == null)
            throw new LabyrinthException("");
        ArrayList<Integer> curKeyCount = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));

//        ArrayList<Node> lst = new ArrayList<>(Arrays.asList(new Node(1), new Node(5),new Node(6),new Node(10),new Node(11)));
//        return lst.iterator();

        Stack<Node> st = new Stack<>();
        Iterator solution = findSolution(START, curKeyCount, st);

        Iterator tmp = solution;

        st.add(graph.getNode(EXIT.getName()));
//        while(!st.empty()){
//            System.out.println(st.peek().getName());
//            st.pop();
//        }


        return st.iterator();
    }

    private Iterator findSolution(Node curNode, ArrayList<Integer> curKeyCount, Stack<Node> stack) throws GraphException {
        // find adjacent nodes
        Iterator edges = this.graph.incidentEdges(curNode);
//        System.out.println(curNode.getName());

        // add current node to the stack
        stack.add(this.graph.getNode(curNode.getName()));

        if(curNode.getName()==EXIT.getName())
            return  stack.iterator();

        // mark this node as visited
//        curNode.setMark(true);
        this.graph.getNode(curNode.getName()).setMark(true);

        while (edges.hasNext()){

            // check if this node can be considered
            Edge edge = (Edge) edges.next();
            Node next = (edge.secondEndpoint().getName()==curNode.getName()?edge.firstEndpoint():edge.secondEndpoint());

            // if this node is the exit point
            if(next.getName()==EXIT.getName()){
                stack.add(this.graph.getNode(EXIT.getName()));

//                stack.add(next);
                // exit point found
//                System.out.println("Found exit point");

//                System.out.println("DONE!");
                return stack.iterator();
            }

            if(this.graph.getNode(next.getName()).getMark())
                continue;

//            System.out.println(next.getName());

            // run dfs from this node
            // if this is a door
            if(edge.getType()==-1) {
                findSolution(next, curKeyCount, stack);
            }
            else {
                // skip this node if this can't be taken
                if(curKeyCount.get(edge.getType())>=numberOfKeys.get(edge.getType()))
                    continue;

                // increase the count
                curKeyCount.set(edge.getType(),curKeyCount.get(edge.getType())+1);
                findSolution(next, curKeyCount, stack);

                // reset the count
                curKeyCount.set(edge.getType(),curKeyCount.get(edge.getType())-1);
            }
        }

        // reset mark
//        curNode.setMark(false);
        this.graph.getNode(curNode.getName()).setMark(false);

        // remove current node from the stack
        if(curNode.getName()==EXIT.getName())
            stack.clear();
        else
            stack.pop();

        return stack.iterator();
    }

    /* returns a reference to the Graph object representing the labyrinth. Throws a
     LabyrinthException if the graph is null.*/
    Graph getGraph() throws LabyrinthException {
        if(graph == null)
            throw new LabyrinthException("");
       return this.graph;
    }
}
