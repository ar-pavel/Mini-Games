public class Node {
    private int name;
    private boolean mark;

    Node(int name){
        this.name = name;
        this.mark =false;
    }
    void setMark(boolean mark){
        this.mark = mark;
    }
    boolean getMark(){
        return  this.mark;
    }

    public int getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name=" + name +
                ", mark=" + mark +
                '}';
    }
}
