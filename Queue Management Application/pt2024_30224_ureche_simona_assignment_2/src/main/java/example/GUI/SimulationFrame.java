package example.GUI;

import example.Logic.SimulationManager;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimulationFrame extends JFrame {
    private JPanel contentPane = new JPanel(new GridBagLayout());
    private JPanel col1 = new JPanel(new GridBagLayout());
    private JPanel col2 = new JPanel(new GridBagLayout());

    //input data
    private JLabel titleLabelInput = new JLabel("Data Input");
    private JLabel clientsLabel = new JLabel("Number of Clients:");
    private JLabel queuesLabel = new JLabel("Number of Queues:");
    private JLabel intervalLabel = new JLabel("Simulation Interval:");
    private JLabel arrivalTimeLabel = new JLabel("Minimum Arrival Time");
    private JLabel arrivalTimeLabel2 = new JLabel("Maximum Arrival Time");
    private JLabel serviceTimeLabel = new JLabel("Minimum Service Time ");
    private JLabel serviceTimeLabel2 = new JLabel("Minimum Service Time ");
    private JLabel selectStrategyLabel = new JLabel("Select Type Of Strategy");
    private JTextField clientsField = new JTextField(10);
    private JTextField queuesField = new JTextField(10);
    private JTextField intervalField = new JTextField(10);
    private JTextField arrivalTimeField = new JTextField(10);
    private JTextField arrivalTimeField2 = new JTextField(10);
    private JTextField serviceTimeField = new JTextField(10);
    private JTextField serviceTimeField2 = new JTextField(10);
    private JComboBox<String> strategyComboBox = new JComboBox<>(new String[]{"Time Strategy", "Queue Strategy"});
    private JButton validateButton = new JButton("Validate Input Data");

    //simulation results
    private JLabel titleLabel2 = new JLabel("Simulation Results");
    private JLabel timeLabel = new JLabel("Time: ");
    private JLabel timeLabelSufix = new JLabel();
    private JLabel waitingClientsLabel = new JLabel("Waiting Clients");
    private JLabel queueEvolutionLabel = new JLabel("Queue Evolution");
    private JLabel averageWaitingTimeLabel = new JLabel("Average Waiting Time: ");
    private JLabel averageWaitingTimeLabelSufix = new JLabel();
    private JLabel averageServiceTimeLabel = new JLabel("Average Service Time: ");
    private JLabel averageServiceTimeLabelSufix = new JLabel();
    private JLabel peckTimeLabel = new JLabel("Peak Time: ");
    private JLabel peckTimeLabelSufix = new JLabel();
    private JTextArea waitingClientsTextArea = new JTextArea(5,15);
    private JTextArea queueEvolutionTextArea = new JTextArea(10, 15);

    public SimulationFrame() {
        setTitle("Queue Simulation Interface");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
        contentPane.setBackground(new Color(176, 196, 222));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        col1.setBackground(new Color(176, 196, 222));
        col2.setBackground(new Color(176, 196, 222));

        // Adding components to col1
        col1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

        waitingClientsTextArea.setLineWrap(true);
        waitingClientsTextArea.setWrapStyleWord(true);

        col1.add(titleLabel2, gbc);
        gbc.gridy++;
        col1.add(timeLabel, gbc);
        gbc.gridy++;
        col1.add(timeLabelSufix, gbc);
        gbc.gridy++;
        col1.add(waitingClientsLabel, gbc);
        gbc.gridy++;
        col1.add(waitingClientsTextArea, gbc);
        gbc.gridy++;
        col1.add(queueEvolutionLabel, gbc);
        gbc.gridy++;
        col1.add(queueEvolutionTextArea, gbc);
        gbc.gridy++;
        col1.add(averageWaitingTimeLabel, gbc);
        gbc.gridx++;
        col1.add(averageWaitingTimeLabelSufix, gbc);
        gbc.gridx--;
        gbc.gridy++;
        col1.add(averageServiceTimeLabel, gbc);
        gbc.gridx++;
        col1.add(averageServiceTimeLabelSufix, gbc);
        gbc.gridx--;
        gbc.gridy++;
        col1.add(peckTimeLabel, gbc);
        gbc.gridx++;
        col1.add(peckTimeLabelSufix, gbc);
        gbc.gridx--;

        // Adding components to col2
        col2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

        gbc.gridy = 0;
        col2.add(titleLabelInput, gbc);
        gbc.gridy++;
        col2.add(clientsLabel, gbc);
        gbc.gridy++;
        col2.add(clientsField, gbc);
        gbc.gridy++;
        col2.add(queuesLabel, gbc);
        gbc.gridy++;
        col2.add(queuesField, gbc);
        gbc.gridy++;
        col2.add(intervalLabel, gbc);
        gbc.gridy++;
        col2.add(intervalField, gbc);
        gbc.gridy++;
        col2.add(arrivalTimeLabel, gbc);
        gbc.gridy++;
        col2.add(arrivalTimeField, gbc);
        gbc.gridy++;
        col2.add(arrivalTimeLabel2, gbc);
        gbc.gridy++;
        col2.add(arrivalTimeField2, gbc);
        gbc.gridy++;
        col2.add(serviceTimeLabel, gbc);
        gbc.gridy++;
        col2.add(serviceTimeField, gbc);
        gbc.gridy++;
        col2.add(serviceTimeLabel2, gbc);
        gbc.gridy++;
        col2.add(serviceTimeField2, gbc);
        gbc.gridy++;
        col2.add(selectStrategyLabel, gbc);
        gbc.gridy++;
        col2.add(strategyComboBox, gbc);
        gbc.gridy++;
        col2.add(validateButton, gbc);

        // Adding col1 and col2 to contentPane
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(col1, gbc);

        gbc.gridx = 1;
        contentPane.add(col2, gbc);

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateInput(); //validează datele și pornește simularea
            }
        });
    }

    public String getClients() {
        return clientsField.getText();
    }
    public String getQueues() {
        return queuesField.getText();
    }
    public String getInterval() {
        return intervalField.getText();
    }
    public String getMinServiceTime() {
        return serviceTimeField.getText();
    }
    public String getMaxServiceTime() {
        return serviceTimeField2.getText();
    }
    public String getMinArrivalTime() {
        return arrivalTimeField.getText();
    }
    public String getMaxArrivalTime() {
        return arrivalTimeField2.getText();
    }
    public void showValidate(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    public void setTime(String time) {
        timeLabelSufix.setText(time);
    }
    public void setWaitingQueue(String queue) {
        waitingClientsTextArea.setText(queue);
    }
    public void setQueueEvolution(String queue) {
        queueEvolutionTextArea.setText(queue);
    }
    public void setAverageWaitingTimeJLabel(String average) {
        averageWaitingTimeLabelSufix.setText(average);
    }
    public void setAverageServiceTimeJLabel(String service) {
        averageServiceTimeLabelSufix.setText(service);
    }
    public void setPeakTimeJLabel(String peak) { peckTimeLabelSufix.setText(peak); }
    public Object getComboBox() {
        return strategyComboBox.getSelectedItem();
    }
    private void validateInput() {
        //verific input-urile
        if (clientsField.getText().isEmpty() || queuesField.getText().isEmpty() || intervalField.getText().isEmpty() ||
                arrivalTimeField.getText().isEmpty() || arrivalTimeField2.getText().isEmpty() ||
                serviceTimeField.getText().isEmpty() || serviceTimeField2.getText().isEmpty()) {
            showValidate("Please enter correct fill in all required fields.");
        } else {
            showValidate("Simulation started");
            SimulationManager sim = new SimulationManager(this);
            Thread thread = new Thread(sim);
            thread.start();
        }
    }
}
