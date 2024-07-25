package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Frame-ul principal al Sistemului de Management al Comenzilor.
 * Acesta oferă butoane pentru accesarea diferitelor funcționalități, cum ar fi gestionarea clienților, produselor și comenzilor.
 */
public class MainFrame extends JFrame {
    /**
     * Constructor pentru clasa MainFrame.
     * Inițializează aspectul interfeței grafice și butoanele pentru gestionarea clienților, produselor și comenzilor.
     */
    public MainFrame() {
        setTitle("Order Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);

        setLayout(new BorderLayout());

        JButton clientButton = new JButton("Client");
        JButton productButton = new JButton("Product");
        JButton orderButton = new JButton("Order");

        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        clientButton.setFont(buttonFont);
        productButton.setFont(buttonFont);
        orderButton.setFont(buttonFont);

        Dimension buttonSize = new Dimension(200, 50);
        clientButton.setPreferredSize(buttonSize);
        productButton.setPreferredSize(buttonSize);
        orderButton.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        buttonPanel.add(clientButton);
        buttonPanel.add(productButton);
        buttonPanel.add(orderButton);

        buttonPanel.setBackground(new Color(176, 196, 222));
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientFrame clientFrame = new ClientFrame();
                clientFrame.setVisible(true);
            }
        });

        productButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductFrame productFrame = new ProductFrame();
                productFrame.setVisible(true);
            }
        });

        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OrderFrame orderFrame = new OrderFrame();
                orderFrame.setVisible(true);
            }
        });

    }
    /**
     * Metodă principală pentru lansarea aplicației.
     * Creează o instanță a clasei MainFrame și o face vizibilă.
     * @param args Argumentele liniei de comandă (nu sunt folosite).
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
