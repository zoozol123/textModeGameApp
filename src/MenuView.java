import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Objects;

public class MenuView {

    void createMenuView(Terminal terminal, Screen screen) throws IOException, InterruptedException {
        screen.startScreen();
        TextGraphics tg = screen.newTextGraphics();

        tg.putString(29, 2, "Witaj w Game Center!");
        tg.putString(16, 7, "SNAKE");
        tg.drawLine(new TerminalPosition(10, 4), new TerminalPosition(25, 4), Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(9, 4, String.valueOf(Symbols.SINGLE_LINE_TOP_LEFT_CORNER));
        tg.putString(26, 4, String.valueOf(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER));
        tg.drawLine(new TerminalPosition(9, 5), new TerminalPosition(9, 9), Symbols.SINGLE_LINE_VERTICAL);
        tg.drawLine(new TerminalPosition(26, 5), new TerminalPosition(26, 9), Symbols.SINGLE_LINE_VERTICAL);
        tg.putString(9, 10, String.valueOf(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER));
        tg.putString(26, 10, String.valueOf(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER));
        tg.drawLine(new TerminalPosition(10, 10), new TerminalPosition(25, 10), Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(2, 12, "Naciśnij S żeby zagrac w Snake <S>");

        tg.putString(60, 7, "CAR");
        tg.drawLine(new TerminalPosition(53, 4), new TerminalPosition(68, 4), Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(52, 4, String.valueOf(Symbols.SINGLE_LINE_TOP_LEFT_CORNER));
        tg.putString(69, 4, String.valueOf(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER));
        tg.drawLine(new TerminalPosition(52, 5), new TerminalPosition(52, 9), Symbols.SINGLE_LINE_VERTICAL);
        tg.drawLine(new TerminalPosition(69, 5), new TerminalPosition(69, 9), Symbols.SINGLE_LINE_VERTICAL);
        tg.putString(52, 10, String.valueOf(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER));
        tg.putString(69, 10, String.valueOf(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER));
        tg.drawLine(new TerminalPosition(53, 10), new TerminalPosition(68, 10), Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(39, 12, "Naciśnij C żeby zagrać w samochodzik <C>");

        tg.putString(45, 22, "Naciśnij ESCAPE żeby wyjść <ESC>");
        screen.refresh();
        KeyStroke keyStroke = terminal.readInput();

        while(keyStroke.getKeyType() != KeyType.Escape) {
            if(keyStroke.getKeyType() == KeyType.Character) {
                if(keyStroke.getCharacter() == 's' || keyStroke.getCharacter() == 'S') {
                    new SnakeController(55, 17, terminal, screen);
                    break;
                }
                if(keyStroke.getCharacter() == 'c' || keyStroke.getCharacter() == 'C') {
                    new CarController(terminal, screen);
                    break;
                }
            } else {
                tg.putString(1, 22, "Wybrałeś niewłaściwy przycisk!");
                screen.refresh();
            }
            keyStroke = terminal.readInput();
        }
        if(keyStroke.getKeyType() == KeyType.Escape) {
            terminal.close();
            System.exit(0);
        }
    }

    public MenuView() throws IOException {}

}
