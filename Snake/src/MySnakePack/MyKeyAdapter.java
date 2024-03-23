package MySnakePack;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//acesta clasa seteaza directia sarpelui in functie de tasta apasata
public class MyKeyAdapter extends KeyAdapter {
    private GUI gui;

    public MyKeyAdapter(GUI gui) {
        this.gui = gui;
    }

    //acesta metoda este apelata de fiecare data cand o tasta este apasata;
    //prin intermediul parametrului e de cunosc actiunile de la tastatura
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (gui.getDirection() != 'R') { //daca este apasat stanga, iar sarpele nu se misca deja spre dreapta(adica merge in sus sau in jos), directia lui o sa ife schimbata spre stanga;
                    gui.setDirection('L');
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (gui.getDirection() != 'L') {
                    gui.setDirection('R');
                }
                break;
            case KeyEvent.VK_UP:
                if (gui.getDirection() != 'D') {
                    gui.setDirection('U');
                }
                break;
            case KeyEvent.VK_DOWN:
                if (gui.getDirection() != 'U') {
                    gui.setDirection('D');
                }
                break;
            case KeyEvent.VK_P: //la apasarea tastei p este implementata logica de pauza a jocului
                gui.Pause();
                break;
        }
    }//obs: sarpele nu merge de sus in jos, de la stanga la dreapta si nici invers.

    public static void main(String[] args)
    {
        GameFrame frame = new GameFrame();
    }
}
