import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Objects;
import javax.swing.Timer;

public class SnakeController {
    private int width = 0, height = 0, delay = 200;
    private boolean gameOver;
    private SnakeModel snake;
    private SnakeView snakeView;
    private Terminal terminal;
    private Screen screen;
    public SnakeController(int width, int height, Terminal terminal, Screen screen) throws IOException, InterruptedException {
        this.width = width;
        this.height = height;
        this.terminal = terminal;
        this.screen = screen;

        this.snake = new SnakeModel(width, height, 1, 55, 4, 20);
        this.snakeView = new SnakeView();
        this.snake.setup();
        this.gameOver = snake.getGameOver();

        while (!gameOver) {
            snakeView.createSnakeView(terminal, screen);
            snakeView.draw(snake.getX1(), snake.getX2(), snake.getY1(), snake.getY2(), snake.getSnakePosX(), snake.getSnakePosY(),
                    snake.getfruitX(), snake.getFruitY(), snake.getTailLength(), snake.getTailX(), snake.getTailY(), terminal, screen, snake.getScore());

            snakeView.input(terminal, screen, snake,this);
            snake.logic();

            this.gameOver = snake.getGameOver();
            if(gameOver) {
                snakeView.drawGameOver(terminal, screen, snake.getScore());
                break;
            }
            Thread.sleep(delay);
        }
    }
    public void setDelay(int delay){
        this.delay = delay;
    }
}
