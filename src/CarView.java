import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Objects;

public class CarView {
    private TextGraphics tg;
    long keyStrokeTime;
    private boolean playAgain = false;

    public void createCarView(Terminal terminal, Screen screen) throws IOException, InterruptedException  {
        tg = screen.newTextGraphics();

        tg.putString(57, 2, "CAR GAME");
        tg.putString(57, 5, "Wynik: ");
        tg.putString(45, 19, "Używaj lewej i prawej");
        tg.putString(45, 20, "strzałki do sterowania <-  ->");
        tg.putString(45, 21, "Naciśnij żeby wrócić do menu <M>");
        tg.putString(45, 22, "Naciśnij ESCAPE żeby wyjść <ESC>");
        screen.refresh();
    }
    public String input(Terminal terminal, Screen screen) throws IOException, InterruptedException {
        KeyStroke keyStroke = terminal.pollInput();
        String s="";
        if(keyStroke != null)
            s = String.valueOf(keyStroke.getCharacter());
        if (Objects.equals(s, "m") || Objects.equals(s, "M")) {
            screen.clear();
            MenuView menu = new MenuView();
            menu.createMenuView(terminal, screen);
        } else if(keyStroke != null){
            switch (keyStroke.getKeyType()) {
                case ArrowLeft -> {
                    return "ArrowLeft";
                }
                case ArrowRight -> {
                    return "ArrowRight";
                }
            }
        }
        if(keyStroke != null)
            if(keyStroke.getKeyType() == KeyType.Escape) {
                terminal.close();
                System.exit(0);
            }
        return null;
    }

    public void drawGameOver(Terminal terminal, Screen screen, int score) throws IOException, InterruptedException {
        screen.clear();
        tg = screen.newTextGraphics();
        tg.putString(35, 2, "GAME OVER! :(");
        tg.putString(34, 4, "Twój wynik: ");
        String s = String.valueOf(score);
        tg.putString(47, 4, s);
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
                new CarController(terminal, screen);
                break;
            }
            keyStroke = terminal.readInput();
        }
        if(keyStroke.getKeyType() == KeyType.Escape) {
            terminal.close();
            System.exit(0);
        }

    }

    public void drawEnemy(Screen screen, int i, boolean[] enemyFlag, int[] enemyX, int[] enemyY) throws IOException {
        tg = screen.newTextGraphics();
        if(enemyFlag[i] == true){
            tg.putString(enemyX[i], enemyY[i], "****");
            tg.putString(enemyX[i], enemyY[i]+1, " ** ");
            tg.putString(enemyX[i], enemyY[i]+2, "****");
            tg.putString(enemyX[i], enemyY[i]+3, " ** ");
            screen.refresh();
        }
    }
    public void eraseEnemy(Screen screen, int i, boolean[] enemyFlag, int[] enemyX, int[] enemyY) throws IOException {
        tg = screen.newTextGraphics();
        if(enemyFlag[i] == true){
            tg.putString(enemyX[i], enemyY[i], "    ");
            tg.putString(enemyX[i], enemyY[i]+1, "    ");
            tg.putString(enemyX[i], enemyY[i]+2, "    ");
            tg.putString(enemyX[i], enemyY[i]+3, "    ");
            screen.refresh();
        }
    }
    public void drawBorder(Screen screen, int width, int height) throws IOException {
        screen.clear();
        tg = screen.newTextGraphics();
        for(int i=0; i<height; i++){
            for( int j=0; j<9; j++){
                tg.putString(0+j, i, "±");
                tg.putString(34+j, i, "±");
                screen.refresh();
            }
        }
    }
    public void drawCar(Screen screen, char[][] car, int carPos) throws IOException {
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                tg.putString(j+carPos, i+19, String.valueOf(car[i][j]));
                screen.refresh();
            }
        }
    }
    public void eraseCar(Screen screen, int carPos) throws IOException {
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                tg.putString(j+carPos, i+19, " ");
                screen.refresh();
            }
        }
    }
    public void updateScore(int score) throws IOException {
        tg.putString(65, 5, String.valueOf(score));
    }
}
