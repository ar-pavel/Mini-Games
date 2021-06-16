package Snake;

public class BoardPoint {
    public  int x;
    public  int y;

    public BoardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void exchange(int x, int y){
        this.x = x;
        this.y = y;
    }
}
