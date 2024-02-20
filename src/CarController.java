import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class CarController {
    private CarModel carModel;
    private CarView carView;
    private Terminal terminal;
    private Screen screen;
    private boolean gameOver = false;
    private int delay = 50;
    public CarController(Terminal terminal, Screen screen) throws IOException, InterruptedException {
        this.terminal = terminal;
        this.screen = screen;

        this.carModel = new CarModel();
        this.carView = new CarView();

        this.carModel.setup();
        carView.drawBorder(screen, carModel.getHeight(), carModel.getWidth());
        carView.createCarView(terminal, screen);

        String input;
        while(!gameOver){
            input = carView.input(terminal, screen);
            carModel.direction(input);
            carView.drawCar(screen, carModel.getCar(), carModel.getCarPos());
            carView.drawEnemy(screen, 0, carModel.getEnemyFlag(), carModel.getEnemyX(), carModel.getEnemyY());
            carView.drawEnemy(screen, 1, carModel.getEnemyFlag(), carModel.getEnemyX(), carModel.getEnemyY());
            carModel.checkCollision();
            Thread.sleep(delay);
            carView.eraseCar(screen, carModel.getCarPos());
            carView.eraseEnemy(screen, 0, carModel.getEnemyFlag(), carModel.getEnemyX(), carModel.getEnemyY());
            carView.eraseEnemy(screen, 1, carModel.getEnemyFlag(), carModel.getEnemyX(), carModel.getEnemyY());
            if(carModel.logic())
                carView.eraseEnemy(screen, carModel.getEraseIndex(), carModel.getEnemyFlag(), carModel.getEnemyX(), carModel.getEnemyY());
            carView.updateScore(carModel.getScore());

            gameOver = carModel.getGameOver();
            if(gameOver) {
                carView.drawGameOver(terminal, screen, carModel.getScore());
                break;
            }
        }
    }
}
