package org.example.Logic;

import org.example.Model.Polynomial;
import org.example.GUI.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private GUI gui;
    Operations operations = new Operations();

    public Controller(GUI gui) {
        this.gui = gui;
        gui.addistener(new AddListener());
        gui.subListener(new SubListener());
        gui.multiplyListener(new MultiplyListener());
        gui.divideListener(new DivideListener());
        gui.derivateListener(new DeriveListener());
        gui.integrationListener(new IntegrateListener());
        gui.exitListener(new ExitListener());
    }

    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial a = new Polynomial(gui.getFirstPolynom());
            Polynomial b = new Polynomial(gui.getSecondPolynom());
            Polynomial result = operations.add(a, b);
            gui.setFinalResult(result.toString());
        }
    }

    public class SubListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial a = new Polynomial(gui.getFirstPolynom());
            Polynomial b = new Polynomial(gui.getSecondPolynom());
            Polynomial result = operations.subtract(a, b);
            gui.setFinalResult(result.toString());
        }
    }

    public class MultiplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial a = new Polynomial(gui.getFirstPolynom());
            Polynomial b = new Polynomial(gui.getSecondPolynom());
            Polynomial result = operations.multiply(a, b);
            gui.setFinalResult(result.toString());
        }
    }

    public class DeriveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial a = new Polynomial(gui.getFirstPolynom());
            Polynomial result = operations.derivate(a);
            gui.setFinalResult(result.toString());
        }
    }

    public class IntegrateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial a = new Polynomial(gui.getFirstPolynom());
            Polynomial result = operations.integrate(a);
            gui.setFinalResult(result.toString());
        }
    }

    public class DivideListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial a = new Polynomial(gui.getFirstPolynom());
            Polynomial b = new Polynomial(gui.getSecondPolynom());
            Divade result = operations.divide(a, b);
            gui.setFinalResult("Q: " + result.getQuotient() + "    R: " + result.getRemainder());
        }
    }

    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}