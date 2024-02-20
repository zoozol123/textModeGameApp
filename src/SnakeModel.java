import java.util.Random;

public class SnakeModel {
    private int x1, x2, y1, y2, width, height, score;
    private int snakePosX, snakePosY, fruitX, fruitY, tailLength = 0;
    private int[] tailX = new int[50];
    private int[] tailY = new int[50];
    private boolean gameOver, arrowUp, arrowDown, arrowLeft, arrowRight;

    public SnakeModel(int width, int height, int x1,  int x2,  int y1, int y2) {
        this.width = width;
        this.height = height;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    private void setFruitPosition() {
        Random random = new Random();
        fruitX = random.nextInt(x2 - x1 - 1) + x1 + 1;
        fruitY = random.nextInt(y2 - y1 - 1) + y1 + 1;
        for(int i = 0; i < tailLength; i++) {
            if (fruitX == tailX[i] && fruitY == tailY[i])
                setFruitPosition();
        }
        if(fruitX == snakePosX && fruitY == snakePosY)
            setFruitPosition();
    }

    public void setup(){
        gameOver = false;
        snakePosX = width/2;
        snakePosY = height/2;
        setFruitPosition();
        for(int i = 0; i < tailLength; i++){
            tailX[i] = 0;
            tailY[i] = 0;
        }
        arrowUp = false;
        arrowDown = false;
        arrowLeft = false;
        arrowRight = true;
    }

    public void logic() {
        int prevX = tailX[0];
        int prevY = tailY[0];
        int prev2X, prev2Y;
        tailX[0] = snakePosX;
        tailY[0] = snakePosY;

        for(int i = 1; i < tailLength; i++){
            prev2X = tailX[i];
            prev2Y = tailY[i];
            tailX[i] = prevX;
            tailY[i] = prevY;
            prevX = prev2X;
            prevY = prev2Y;
        }

        if(arrowUp && !arrowDown) { snakePosY--; }
        if(arrowDown && !arrowUp) { snakePosY++; }
        if(arrowLeft && !arrowRight) { snakePosX--; }
        if(arrowRight && !arrowLeft) { snakePosX++; }

        if(snakePosX == x1 || snakePosX == x2 || snakePosY == y1 || snakePosY == y2){
            gameOver = true;
        }

        for(int i = 0; i < tailLength; i++){
            if(snakePosX == tailX[i] && snakePosY == tailY[i]) {
                gameOver = true;
                break;
            }
        }

        if(snakePosX == fruitX && snakePosY == fruitY){
            score += 1;
            setFruitPosition();
            tailLength += 1;
        }
    }
    public boolean getGameOver(){
        return gameOver;
    }

    public int getX1() {
        return x1;
    }
    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }
    public int getY2() {
        return y2;
    }

    public int getSnakePosX() {
        return snakePosX;
    }
    public int getSnakePosY() {
        return snakePosY;
    }

    public int getfruitX() {
        return fruitX;
    }
    public int getFruitY() {
        return fruitY;
    }

    public int getTailLength(){
        return tailLength;
    }

    public int[] getTailX(){
        return tailX;
    }

    public int[] getTailY(){
        return tailY;
    }

    public int getScore(){
        return score;
    }

    public void setArrowDown(boolean arrowDown) {
        this.arrowDown = arrowDown;
    }

    public void setArrowUp(boolean arrowUp) {
        this.arrowUp = arrowUp;
    }

    public void setArrowLeft(boolean arrowLeft) {
        this.arrowLeft = arrowLeft;
    }

    public void setArrowRight(boolean arrowRight) {
        this.arrowRight = arrowRight;
    }
    public boolean getArrowUp(){
        return arrowUp;
    }

    public boolean getArrowDown() {
        return arrowDown;
    }

    public boolean getArrowRight() {
        return arrowRight;
    }

    public boolean getArrowLeft() {
        return arrowLeft;
    }
}
