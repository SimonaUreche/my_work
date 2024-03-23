package MySnakePack;

import javax.swing.*; //ofera componente pentru crearea interfetelor grafice; ne folosim ce clase cum ar fi JPanel, JFrame, JButton
import java.awt.*; //ofera functionalitati pentru desenare si pentru gestionarea anumitor evenimente
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;//gestionarea evenimentelor; trateaza evinimentele asociate butonului de start
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;//pentru detectarea apasarii de taste(misacrea sarpelui)
import java.util.Random; //plasarea sarpelui si a matului la pozitii aleatoare
import java.util.prefs.Preferences; //pentru a salva permanent scorul maxim al joclui

public class GUI extends JPanel implements ActionListener {

    private static final int screenWidth = 800;
    private static final int screenHeight = 800;
    private static final int unitSize = 25; //dimensiune unui patrat, dimensiunea unei parti din sarpe, dar si dimensiunea marului
    private static final int nrOfUnits = (screenWidth * screenHeight) / (unitSize);

    private final int[] coordonateSnakeX = new int[nrOfUnits];
    private final int[] coordonateSnakeY = new int[nrOfUnits];
    private int snakeParts = 1;

    private int nrOfapplesEaten;

    private static final String MAX_SCORE_KEY = "MaxScore";
    private Preferences preferences = Preferences.userNodeForPackage(GUI.class);
    private int maxScore = preferences.getInt(MAX_SCORE_KEY, 0);
    private int coordonateAppleX;
    private int coordonateAppleY;
    private char direction = 'R';

    private boolean running = false;
    private Timer timer;
    private Random random;

    private boolean hasWin = false;
    private int snakeSpeed = 75;

    private StartButtonPanel startButtonPanel; //buton de start
    private boolean paused = false; //folosim de la tasta p
    private boolean firstRun = true; //la prima pornine avem doar butonul de start pe ecran

    public GUI()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //ajusteza dimensiunea în funcție de contextul layout-urilor.
        this.setBackground(Color.BLACK);
       this.setFocusable(true); //primeste intrati de la utilizator; se poate interationa cu componenta prin tastatura, mouse, etc.
        this.addKeyListener(new MyKeyAdapter(this));  //apelam la clasa MyKeyAdapter pentru reactii la apasarea tastelor

        //cream butonul de start si il adaugam in panou
        startButtonPanel = new StartButtonPanel(this);
        this.add(startButtonPanel);

        //de fiecare data are loc initialiazarea jocului
        initializeGame();
    }

    private void initializeGame()
    {
        snakeParts = 1;
        nrOfapplesEaten = 0;
        running = false;
        paused = false;
        snakeSpeed = 75;
        firstRun = true;

        //nu avem input harcodat pentru inceperea jocului
        //atat directia, cat si pozitia sarpelui sunt generate random
        direction =  generateRandomDirection();
        coordonateSnakeX[0] = random.nextInt((screenWidth / unitSize)) * unitSize; //prin inmultirea cu unitSize asiguram pozitionarea intr un patrat de dimensiune 'unitSize'
        coordonateSnakeY[0] = random.nextInt((screenHeight / unitSize)) * unitSize;
    }

    // Metoda pentru a genera o direcție aleatoare
    private char generateRandomDirection() {
        int randomDirection = random.nextInt(4);

        switch (randomDirection) {
            case 0:
                return 'L'; // 0 pentru stânga
            case 1:
                return 'R'; //1 pentru dreapta,
            case 2:
                return 'U'; //2 pentru sus
            case 3:
                return 'D'; //3 pentru jos
            default:
                return 'R';  // În cazul în care ceva nu merge bine, revenim la direcția  'R'
        }
    }

    //la apasarea butonui de start se executa
    public void startGame() {
        initializeGame(); //se intializeaza jocul
        newApple(); //se genereaza un nou mar
        running = true; //se porneste jocul
        timer = new Timer(snakeSpeed, this); //'viteza' sarpelui
        timer.start(); //prin pornirea timerului, generam evenimente din logica jocului(miscarea sarpelui, verificare coliziuni)
        startButtonPanel.setVisible(false); //in timpul jocului, este acsuns butonul de start
        firstRun = false; //dupa prima pornire a jocului negam firstRun
    }


    //ne folosim de Graphics pentru a putea desena grafica pe componenente
    public void paintComponent(Graphics G)
    {
        super.paintComponent(G); //responsabila de desenarea elementelor panoului
        draw(G);
    }

    public void draw(Graphics G)
    {
        if (hasWin)  //conditia este adevarata cand numarul de mere mancate este de 50
        {
            //finalul joculu; numarul de mere maxim este atins si apare pe ecran textul: "Congratulations! You've won!
            G.setColor(Color.yellow);
            G.setFont(new Font("Ink Free", Font.BOLD, 60));
            FontMetrics metrics = getFontMetrics(G.getFont()); //latimea sirului de caractere
            G.drawString("Congratulations! You've won!", (screenWidth - metrics.stringWidth("Congratulations! You've won!")) / 2, screenHeight / 2); //pesntru a pozitiona textul estetic de folosim de FontMetrics
        }
        else {
            if (running) {
                G.setColor(Color.red);
                G.fillOval(coordonateAppleX, coordonateAppleY, unitSize, unitSize);

                //desenarea sarpelui
                for (int i = 0; i < snakeParts; i++) {
                    if (i == 0) {
                        //desenam capul sarpelui cu verde inchis
                        G.setColor(new Color(0, 128, 0));
                        G.fillRect(coordonateSnakeX[i], coordonateSnakeY[i], unitSize, unitSize);
                    } else {//restul corpului il desenam cu verde normal, pentru a se distinge de cap
                        G.setColor(Color.green);
                        G.fillRect(coordonateSnakeX[i], coordonateSnakeY[i], unitSize, unitSize);
                    }
                }

                //in timpul jocului, avem desenat permanent pe ecran scorul actual care este actualizat la fiecare mar mancat si scorul maxim care a fost realizat
                G.setColor(Color.red);
                G.setFont(new Font("Ink Free", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(G.getFont());
                G.drawString("Score: " + nrOfapplesEaten, (screenWidth - metrics.stringWidth("Score: " + nrOfapplesEaten)) / 2, G.getFont().getSize());
                G.setColor(Color.blue);
                G.drawString("Max Score: " + maxScore, (screenWidth - metrics.stringWidth("Max Score: " + maxScore)) / 2, G.getFont().getSize() + 50);

                //desenare atunci cand jocul este pus pe pauza
                if (paused)
                {
                    G.setColor(Color.yellow);
                    G.setFont(new Font("Ink Free", Font.BOLD, 40));
                    FontMetrics metrics1 = getFontMetrics(G.getFont());
                    G.drawString("Paused", (screenWidth - metrics1.stringWidth("Paused")) / 2, screenHeight / 2);
                }
            } else
            {
                if (!firstRun)  //doar daca nu este prima rulare a jocului apare 'Game Over' si butonul de start
                {
                    G.setColor(Color.red);
                    G.setFont(new Font("Ink Free", Font.BOLD, 75));
                    FontMetrics metrics2 = getFontMetrics(G.getFont());
                    G.drawString("Game Over", (screenWidth - metrics2.stringWidth("Game Over")) / 2, screenHeight / 2);
                    startButtonPanel.setVisible(true);
                } else
                {
                    //daca e prima rulare avem doar butonul de start
                    startButtonPanel.setVisible(true);
                }
            }
        }
    }

    //generarea unui nou mar intr un patrat de dimensiune 'unitSize'
    public void newApple()
    {
        coordonateAppleX = random.nextInt((int) (screenWidth / unitSize)) * unitSize;
        coordonateAppleY = random.nextInt((int) (screenHeight / unitSize)) * unitSize;
    }

    private void increaseSnakeSpeed() {
        snakeSpeed -= 5;
        timer.setDelay(snakeSpeed);
    }
    public void checkApple()
    {
        //cand sarpele intersecteaza un mar, creste numarul de mere manacate, creste sarpele si este generat un nou mar
        if ((coordonateSnakeX[0] == coordonateAppleX) && (coordonateSnakeY[0] == coordonateAppleY)) {
            snakeParts++;
            nrOfapplesEaten++;
            newApple();

            //creste viteza dupa fiecare 5 mere mancate
            if (nrOfapplesEaten % 5 == 0)
            {
                increaseSnakeSpeed();
            }

            // Verificarea câștigul
            //cand numarul de mere este de 50 jocul este oprit si se afiseaza mesaj pe ecran
            if (nrOfapplesEaten == 50) {
                running = false;
                timer.stop();
                hasWin = true;
                startButtonPanel.setVisible(true);

            }
        }
    }
    public void move()
    {
        //parcurgem sarpele de la coada la cap si legam fiecare segment al sarpelui de segmentul din fata sa pentru a vedea evolutia acestuia
        for (int i = snakeParts; i > 0; i--)
        {
            coordonateSnakeX[i] = coordonateSnakeX[i - 1];
            coordonateSnakeY[i] = coordonateSnakeY[i - 1];
        }
        switch (direction) {
            case 'U': //pentru tasta up si down se modifica coorodata Y a sarpelui
                coordonateSnakeY[0] = coordonateSnakeY[0] - unitSize;
                break;
            case 'D':
                coordonateSnakeY[0] = coordonateSnakeY[0] + unitSize;
                break;
            case 'L': //pentru tasta left si right se modifica coorodata X a sarpelui
                coordonateSnakeX[0] = coordonateSnakeX[0] - unitSize;
                break;
            case 'R':
                coordonateSnakeX[0] = coordonateSnakeX[0] + unitSize;
                break;
        }
    }

    public void checkCollisions()
    {
        //verificarea coliziunii cu propriul corp; capul nu poate atinge nici o alta bucata din corp
        for (int i = snakeParts; i > 0; i--)
        {
            if ((coordonateSnakeX[0] == coordonateSnakeX[i]) && (coordonateSnakeY[0] == coordonateSnakeY[i]))
            {
                running = false; //la aparitia unei coliziuni jocul ia sfarsit
            }
        }

        //verificare coliziune stanga, dreapta, jos si sus
        if (coordonateSnakeX[0] < 0 || coordonateSnakeX[0] > screenWidth || coordonateSnakeY[0] < 0 || coordonateSnakeY[0] > screenHeight)
        {
            running = false; //la aparitia unei coliziuni jocul ia sfarsit
        }

        //cand jocul este oprit apare din nou pe ecran butonul de start, iar timer-ul este oprit
        if (!running)
        {
            timer.stop();
            startButtonPanel.setVisible(true);

            //actualizare scor maxim
            if (nrOfapplesEaten > maxScore)
            {
                maxScore = nrOfapplesEaten;
                preferences.putInt(MAX_SCORE_KEY, maxScore); //variabila ramane actualizata permanent prin metoda preferences
            }
        }
    }

    //acesta metoda este legata la un timer si controleaza daca jocul este in desfasurare
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (running && !paused) {
            move();
            checkApple();
            checkCollisions();
        }
       repaint();
    }

    //jocul poate fi pus pe pauza
    public void Pause()
    {
        paused = !paused;
        if (paused)
        {
            repaint();
            timer.stop();
        }
        else
        {
            timer.start();
        }
    }

    public char getDirection()
    { //ofera directia actuala
        return direction;
    }

    public void setDirection(char direction) //seteaza directia
    {
        this.direction = direction;
    }

    public static void main(String[] args)
    {
        GameFrame frame = new GameFrame();
    }
}