package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    private JFrame mainFrame = new JFrame("Polynom Calculator");
    private JPanel contentPane = new JPanel();
    private JPanel row1 = new JPanel();
    private JPanel row2 = new JPanel();
    private JPanel row3 = new JPanel();
    private JPanel row4 = new JPanel();
    private JLabel polynomialTitle = new JLabel("Polynomial Calculator");
    private JLabel firstPolynomialLabel = new JLabel("First Polynomial = ");
    private JLabel secondPolynomialLabel = new JLabel("Second Polynomial = ");
    private JLabel finalPolynomialLabel = new JLabel("Final Result = ");
    private JTextField firstPolynomialField = new JTextField(15);
    private JTextField secondPolynomialField = new JTextField(15);
    private JTextField finalPolynomialField = new JTextField(25);
    private JButton addButton = new JButton("Add");
    private JButton subButton = new JButton("Substract");
    private JButton multiplyButton = new JButton("Multiplicate");
    private JButton divideButton = new JButton("Divide");
    private JButton derivateButton = new JButton("Derivate");
    private JButton integrationButton = new JButton("Integrate");
    private JButton exitButton = new JButton("Exit");
    private JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    private JPanel column1 = new JPanel(new GridLayout(3, 1));
    private JPanel column2 = new JPanel(new GridLayout(3, 1));
    private Font f1 = new Font(Font.SERIF, Font.PLAIN, 20);
    private Font f2 = new Font(Font.SERIF, Font.PLAIN, 30);

    public GUI() {
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setContentPane(contentPane);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //components font
        polynomialTitle.setFont(f2);
        firstPolynomialLabel.setFont(f2);
        secondPolynomialLabel.setFont(f2);
        finalPolynomialLabel.setFont(f2);
        firstPolynomialField.setFont(f1);
        secondPolynomialField.setFont(f1);
        finalPolynomialField.setFont(f1);
        addButton.setFont(f1);
        subButton.setFont(f1);
        multiplyButton.setFont(f1);
        divideButton.setFont(f1);
        derivateButton.setFont(f1);
        integrationButton.setFont(f1);
        exitButton.setFont(f1);

        //we divide the contentPane into 4 rows using BoxLayout
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //placement of components in each row
        //the first rox
        row1.setPreferredSize(new Dimension(400, 30));
        row1.setBackground(new Color(176, 196, 222));
        row1.add(polynomialTitle);

        //the second row
        row2.setBackground(new Color(176, 196, 222));
        row2.add(firstPolynomialLabel);
        row2.add(firstPolynomialField);
        row2.add(secondPolynomialLabel);
        row2.add(secondPolynomialField);

        //the third row
        row3.setBackground(new Color(176, 196, 222));
        column1.add(addButton);
        column1.add(subButton);
        column1.add(multiplyButton);

        column2.add(divideButton);
        column2.add(derivateButton);
        column2.add(integrationButton);

        buttonPanel.add(column1);
        buttonPanel.add(column2);
        row3.add(buttonPanel);

        //the fourth row
        row4.setBackground(new Color(176, 196, 222));
        row4.add(finalPolynomialLabel);
        row4.add(finalPolynomialField);
        row4.add(exitButton);

        //adding rows to the contentPane
        contentPane.add(row1);
        contentPane.add(row2);
        contentPane.add(row3);
        contentPane.add(row4);
    }

    public String getFirstPolynom() {
        return firstPolynomialField.getText();
    }

    public String getSecondPolynom() {
        return secondPolynomialField.getText();
    }

    public void setFinalResult(String result) {
        finalPolynomialField.setText(result);
    }

    public void addistener(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void subListener(ActionListener action) {
        subButton.addActionListener(action);
    }

    public void multiplyListener(ActionListener action) {
        multiplyButton.addActionListener(action);
    }

    public void divideListener(ActionListener action) {
        divideButton.addActionListener(action);
    }

    public void derivateListener(ActionListener action) {
        derivateButton.addActionListener(action);
    }

    public void integrationListener(ActionListener action) {
        integrationButton.addActionListener(action);
    }
    public void exitListener(ActionListener action) {
        exitButton.addActionListener(action);
    }

}
