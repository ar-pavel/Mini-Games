package Snake;

import javax.swing.*;
import java.util.ArrayList;

class SnakeApp extends Thread {
    StatusBar statusBar;
    BoardPoint snakeHeadPos;
    BoardPoint foodPosition;

    private int score = 0;
    private int boardSize = 34;
    int snakeSize=3;
    long speed = 100;
    public static int snakeCurDirection ;

    ArrayList<ArrayList<Square>> Squares= new ArrayList<ArrayList<Square>>();
    ArrayList<BoardPoint> positions = new ArrayList<>();

    SnakeApp(BoardPoint positionDepart){
        //Get all the threads
        Squares = SnakeView.board;

        snakeHeadPos=new BoardPoint(positionDepart.x,positionDepart.y);
        snakeCurDirection = 1;

        // Point to the head
        BoardPoint headPos = new BoardPoint(snakeHeadPos.getX(),snakeHeadPos.getY());
        positions.add(headPos);

        foodPosition= new BoardPoint(SnakeView.height-1, SnakeView.width-1);
        generateFood(foodPosition);

    }

    // Game Runner
    public void run() {
        while(true){
            internalMove(snakeCurDirection);
            checkCollision();
            externalMove();
            resizeSnake();
            delayTime();
            statusBar.setMessage("" + score);
        }
    }

    //delay between each move of the snake
    private void delayTime(){
        try {
            sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Checking if the snake bites itself or is eating
    private void checkCollision() {
        BoardPoint pos = positions.get(positions.size()-1);
        for(int i = 0;i<=positions.size()-2;i++){
            boolean biteItself = pos.getX()==positions.get(i).getX() && pos.getY()==positions.get(i).getY();
            if(biteItself){
                gameEnd();
            }
        }

        boolean canEat = pos.getX()==foodPosition.y && pos.getY()==foodPosition.x;
        if(canEat){
            // increasing snake size and score
            snakeSize=snakeSize+1;
            foodPosition = getValAleaNotInSnake();
            generateFood(foodPosition);
            score += 10;

            System.out.println("Tasty!");
        }
    }

    //Stops The Game
    private void gameEnd(){
        System.out.println("COLLISION! \n");
        JOptionPane.showMessageDialog(null, "You score is : " + score + "\nGame Over!");
        System.exit(0);
    }

    // Generate a random food
    private void generateFood(BoardPoint foodPositionIn){
        Squares.get(foodPositionIn.x).get(foodPositionIn.y).lightMeUp(3);
    }

    //return a position not occupied by the snake
    private BoardPoint getValAleaNotInSnake(){
        BoardPoint p ;
        int ranX= 0 + (int)(Math.random()*boardSize);
        int ranY= 0 + (int)(Math.random()*boardSize);

        p=new BoardPoint(ranX,ranY);

        for(int i = 0;i<=positions.size()-1;i++){
            if(p.getY()==positions.get(i).getX() && p.getX()==positions.get(i).getY()){
                ranX= 0 + (int)(Math.random()*boardSize);
                ranY= 0 + (int)(Math.random()*boardSize);

                p=new BoardPoint(ranX,ranY);
                i=0;
            }
        }
        return p;
    }

    //Moves the head of the snake and refreshes the positions in the arraylist
    //1:right 2:left 3:top 4:bottom 0:nothing
    private void internalMove(int direction){
        switch(direction){
            case 4:
                snakeHeadPos.exchange(snakeHeadPos.x,(snakeHeadPos.y+1)%(boardSize+1));
                positions.add(new BoardPoint(snakeHeadPos.x,snakeHeadPos.y));
                break;
            case 3:
                if(snakeHeadPos.y-1<0){
                    snakeHeadPos.exchange(snakeHeadPos.x,boardSize);
                }
                else{
                    snakeHeadPos.exchange(snakeHeadPos.x,Math.abs(snakeHeadPos.y-1)%(boardSize+1));
                }
                positions.add(new BoardPoint(snakeHeadPos.x,snakeHeadPos.y));
                break;
            case 2:
                if(snakeHeadPos.x-1<0){
                    snakeHeadPos.exchange(boardSize,snakeHeadPos.y);
                }
                else{
                    snakeHeadPos.exchange(Math.abs(snakeHeadPos.x-1)%(boardSize+1),snakeHeadPos.y);
                }
                positions.add(new BoardPoint(snakeHeadPos.x,snakeHeadPos.y));

                break;
            case 1:
                snakeHeadPos.exchange(Math.abs(snakeHeadPos.x+1)%(boardSize+1),snakeHeadPos.y);
                positions.add(new BoardPoint(snakeHeadPos.x,snakeHeadPos.y));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    //Refresh the squares that needs to be
    private void externalMove(){
        for(BoardPoint t : positions)
            Squares.get(t.getY()).get(t.getX()).lightMeUp(0);

    }

    //Refreshes the tail of the snake, by removing the superfluous data in positions arraylist
    //and refreshing the display of the things that is removed
    private void resizeSnake(){
        int cnt = snakeSize;
        for(int i = positions.size()-1;i>=0;i--){
            if(cnt==0){
                BoardPoint t = positions.get(i);
                Squares.get(t.y).get(t.x).lightMeUp(2);
            }
            else{
                cnt--;
            }
        }
        cnt = snakeSize;
        for(int i = positions.size()-1;i>=0;i--){
            if(cnt==0){
                positions.remove(i);
            }
            else{
                cnt--;
            }
        }
    }
}