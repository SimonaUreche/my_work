package MySnakePack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartButtonPanel extends JPanel
{
    private JButton startButton;
    private GUI gui;

    public StartButtonPanel(GUI gui)
    {
        this.gui = gui;

        //setam titlul si dimensiunile butonului
        startButton = new JButton("Start");
        Dimension buttonSize = new Dimension(150, 80);
        startButton.setPreferredSize(buttonSize);

        //actiunea butonului cand acesta este apasat
        //se porneste jocul butonul si dispare din ecran
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.startGame();
                StartButtonPanel.this.setVisible(false);
            }
        });
        this.add(startButton);
    }


    public static void main(String[] args)
    {
        GameFrame frame = new GameFrame();
    }
}
