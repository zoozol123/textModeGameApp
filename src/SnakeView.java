import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Objects;

public class SnakeView {
    private TextGraphics tg;
    long keyStrokeTime;
    private boolean playAgain = false;

    public void createSnakeView(Terminal terminal, Screen screen) throws IOException {
        screen.startScreen();
        tg = screen.newTextGraphics();

        screen.clear();
        tg.putString(37, 2, "SNAKE");
        tg.drawLine(new TerminalPosition(1, 4), new TerminalPosition(55, 4), Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(1, 4, String.valueOf(Symbols.SINGLE_LINE_TOP_LEFT_CORNER));
        tg.putString(55, 4, String.valueOf(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER));
        tg.drawLine(new TerminalPosition(1, 5), new TerminalPosition(1, 19), Symbols.SINGLE_LINE_VERTICAL);
        tg.drawLine(new TerminalPosition(55, 5), new TerminalPosition(55, 19), Symbols.SINGLE_LINE_VERTICAL);
        tg.putString(1, 20, String.valueOf(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER));
        tg.putString(55, 20, String.valueOf(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER));
        tg.drawLine(new TerminalPosition(2, 20), new TerminalPosition(54, 20), Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(58, 5, "Wynik: ");
        tg.putString(1, 21, "Używaj strzałek do sterowania");
        tg.putString(45, 21, "Naciśnij żeby wrócić do menu <M>");
        tg.putString(45, 22, "Naciśnij ESCAPE żeby wyjść <ESC>");
        screen.refresh();
    }
    public void input(Terminal terminal, Screen screen, SnakeModel snake, SnakeController snakeController) throws IOException, InterruptedException {
        KeyStroke keyStroke = terminal.pollInput();
        String s="";
        if(keyStroke != null)
            s = String.valueOf(keyStroke.getCharacter());
        if (Objects.equals(s, "m")) {
            screen.clear();
            MenuView menu = new MenuView();
            menu.createMenuView(terminal, screen);
        } else if(keyStroke != null){
            switch (keyStroke.getKeyType()) {
                case ArrowDown -> {
                    if(!snake.getArrowUp()) {
                        snake.setArrowUp(false);
                        snake.setArrowDown(true);
                        snake.setArrowLeft(false);
                        snake.setArrowRight(false);
                        snakeController.setDelay(180);
                    }
                }
                case ArrowUp -> {
                    if(!snake.getArrowDown()) {
                        snake.setArrowUp(true);
                        snake.setArrowDown(false);
                        snake.setArrowLeft(false);
                        snake.setArrowRight(false);
                        snakeController.setDelay(180);
                    }
                }
                case ArrowLeft -> {
                    if(!snake.getArrowRight()) {
                        snake.setArrowUp(false);
                        snake.setArrowDown(false);
                        snake.setArrowLeft(true);
                        snake.setArrowRight(false);
                        snakeController.setDelay(80);
                    }
                }
                case ArrowRight -> {
                    if(!snake.getArrowLeft()) {
                        snake.setArrowUp(false);
                        snake.setArrowDown(false);
                        snake.setArrowLeft(false);
                        snake.setArrowRight(true);
                        snakeController.setDelay(80);
                    }
                }
            }
        }
        if(keyStroke != null)
            if(keyStroke.getKeyType() == KeyType.Escape) {
                terminal.close();
                System.exit(0);
            }
    }

    public void draw(int x1, int x2, int y1, int y2, int snakePosX, int snakePosY, int fruitX,  int fruitY, int tailLength, int[] tailX, int[] tailY, Terminal terminal, Screen screen, int score) throws IOException {
        tg = screen.newTextGraphics();
        for(int i = y1 + 1; i < y2; i++){
            for(int j = x1 + 1; j < x2; j++){
                if(i == snakePosY && j == snakePosX){
                    tg.putString(j, i, "O");
                    screen.refresh();
                }
                else if(i == fruitY && j == fruitX){
                    tg.putString(j, i, String.valueOf(Symbols.FACE_BLACK));
                    screen.refresh();
                }
                else {
                    for(int k=0; k < tailLength; k++)
                        if(i == tailY[k] && j == tailX[k]) {
                            tg.putString(j, i, "o");
                            screen.refresh();
                        }
                }
            }
        }
        String s = String.valueOf(score);
        tg.putString(65, 5, s);
        screen.refresh();
    }
    public void drawGameOver(Terminal terminal, Screen screen, int score) throws IOException, InterruptedException {
        screen.clear();
        tg.putString(34, 2, "GAME OVER! :(");
        tg.putString(34, 4, "Twój wynik: ");
        String s = String.valueOf(score);
        tg.putString(48, 4, s);
        tg.putString(44, 20, "Naciśnij P żeby zagrać ponownie <P>");
        tg.putString(44, 21, "Naciśnij żeby wrócić do menu <M>");
        tg.putString(44, 22, "Naciśnij ESCAPE żeby wyjść <ESC>");
        screen.refresh();
        KeyStroke keyStroke = terminal.readInput();
        while(keyStroke.getKeyType() != KeyType.Escape) {
            s = String.valueOf(keyStroke.getCharacter());
            if (Objects.equals(s, "m") || Objects.equals(s, "M")) {
                screen.clear();
                MenuView menu = new MenuView();
                menu.createMenuView(terminal, screen);
                break;
            } else if (Objects.equals(s, "p") || Objects.equals(s, "P")) {
                screen.clear();
                new SnakeController(55, 17, terminal, screen);
                break;
            }
            keyStroke = terminal.readInput();
        }
        if(keyStroke.getKeyType() == KeyType.Escape) {
            terminal.close();
            System.exit(0);
        }
    }
}
