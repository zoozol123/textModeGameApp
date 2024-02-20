import java.util.Objects;
import java.util.Random;

public class CarModel {
    private int width = 24, height = 24;
    private int[] enemyX = new int[2];
    private int[] enemyY = new int[2];
    private boolean[] enemyFlag = new boolean[2];
    private int eraseIndex;
    private char[][] car = {
            {' ', '±', '±', ' '},
            {'±', '±', '±', '±'},
            {' ', '±', '±', ' '},
            {'±', '±', '±', '±'}
    };
    private int carPos;
    private int score = 0;
    private boolean gameover = false;

    public void generateEnemy(int i){
        Random random = new Random();
        enemyX[i] = 10 + random.nextInt(20);
    }

    private int collision(){
        if( enemyY[0]+4 >= 23 ){
            if( enemyX[0] + 4 - carPos >= 0 && enemyX[0] + 4 - carPos < 9  ){
                return 1;
            }
        }
        if( enemyY[1]+4 >= 23 ){
            if( enemyX[1] + 4 - carPos >= 0 && enemyX[1] + 4 - carPos < 9  ){
                return 1;
            }
        }
        return 0;
    }

    public void setup() {
        carPos = 18;
        score = 0;
        enemyFlag[0] = true;
        enemyFlag[1] = false;
        enemyY[0] = enemyY[1] = 1;

        generateEnemy(0);
        generateEnemy(1);
    }
    public void direction(String input) {
        if(Objects.equals(input, "ArrowLeft")){
            if( carPos >= 13 )
                carPos -= 4;
        }
        if(Objects.equals(input, "ArrowRight")){
            if( carPos <= 29 )
                carPos += 4;
        }
    }
    public void checkCollision(){
        if(collision() == 1){
            gameover = true;
        }
    }
    public boolean logic(){
        if(enemyY[0] == 15)
            if(!enemyFlag[1])
                enemyFlag[1] = true;

        if(enemyFlag[0])
            enemyY[0] += 1;

        if(enemyFlag[1])
            enemyY[1] += 1;

        if(enemyY[0] > height-2){
            enemyY[0] = 1;
            generateEnemy(0);
            score++;
            eraseIndex = 0;
            return true;
        }
        if(enemyY[1] > height-2){
            enemyY[1] = 1;
            generateEnemy(1);
            score++;
            eraseIndex = 1;
            return true;
        }
        return false;
    }

    public char[][] getCar() {
        return car;
    }
    public int getCarPos() {
        return carPos;
    }
    public boolean[] getEnemyFlag() {
        return enemyFlag;
    }
    public int[] getEnemyX() {
        return enemyX;
    }
    public int[] getEnemyY() {
        return enemyY;
    }
    public boolean getGameOver() {
        return gameover;
    }
    public int getScore() {
        return score;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getEraseIndex() {
        return eraseIndex;
    }
}
