package MySnakePack;
import javax.swing.*;

public class GameFrame extends JFrame //extinde JFrame, deci GameFRame este o fereastra care poate contine alte componente
{
    public GameFrame() {

        GUI panel = new GUI();

        this.add(panel); //adaugam panoul de joc
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //inchiderea ferestrei cand se apasa X
        this.setResizable(false); //nu se poate modifica dimensiunea ferestrei
        this.pack(); //se ajusteaza dimensiunile ferestrei pentru incadrarea continutului
        this.setVisible(true); //face fereastra vizibila
        this.setLocationRelativeTo(null); //afiseaza fereastra in centrul ecranului
    }

    public static void main(String[] args)
    {
        GameFrame frame = new GameFrame();
    }
}
