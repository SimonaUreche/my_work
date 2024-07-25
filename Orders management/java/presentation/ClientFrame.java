package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.List;
import bll.ClientBLL;
import model.Client;

/**
 * Fereastra pentru gestionarea clienților.
 */
public class ClientFrame extends JFrame {
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private ClientBLL clientBLL;
    private JTextField addNameField;
    private JTextField addAddressField;
    private JTextField addEmailField;
    private JTextField addAgeField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField editNameField;
    private JTextField editAddressField;
    private JTextField editEmailField;
    private JTextField editAgeField;
    private ControllerClient controller;
    /**
     * Construiește o nouă instanță a clasei ClientFrame.
     */
    public ClientFrame() {
        setTitle("Client Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // Inițializare obiect ClientBLL
        clientBLL = new ClientBLL();

        // Inițializare model tabel și adăugare coloane
        tableModel = new DefaultTableModel();
        loadColumnNames();
        loadClientsIntoTable();

        // Creare tabel și panou de defilare
        clientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clientTable);
        add(scrollPane, BorderLayout.CENTER);

        // Inițializare câmpuri pentru adăugare și editare
        // Creare panouri pentru adăugare și editare
        // Creare panou pentru butoane
        // Adăugare butoane la panou

        // Adăugare panouri la panou de jos al ferestrei
        addNameField = new JTextField(20);
        addAddressField = new JTextField(20);
        addEmailField = new JTextField(20);
        addAgeField = new JTextField(5);

        JPanel addClientPanel = new JPanel(new GridBagLayout());
        addClientPanel.setBorder(BorderFactory.createTitledBorder("Add Client"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        addClientPanel.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        addClientPanel.add(new JLabel("Address:"), gbc);
        gbc.gridy++;
        addClientPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        addClientPanel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        addClientPanel.add(addNameField, gbc);
        gbc.gridy++;
        addClientPanel.add(addAddressField, gbc);
        gbc.gridy++;
        addClientPanel.add(addEmailField, gbc);
        gbc.gridy++;
        addClientPanel.add(addAgeField, gbc);

        editNameField = new JTextField(20);
        editAddressField = new JTextField(20);
        editEmailField = new JTextField(20);
        editAgeField = new JTextField(5);

        JPanel editClientPanel = new JPanel(new GridBagLayout());
        editClientPanel.setBorder(BorderFactory.createTitledBorder("Edit Client"));
        GridBagConstraints gbcEdit = new GridBagConstraints();
        gbcEdit.gridx = 0;
        gbcEdit.gridy = 0;
        gbcEdit.anchor = GridBagConstraints.WEST;
        editClientPanel.add(new JLabel("Name:"), gbcEdit);
        gbcEdit.gridy++;
        editClientPanel.add(new JLabel("Address:"), gbcEdit);
        gbcEdit.gridy++;
        editClientPanel.add(new JLabel("Email:"), gbcEdit);
        gbcEdit.gridy++;
        editClientPanel.add(new JLabel("Age:"), gbcEdit);

        gbcEdit.gridx = 1;
        gbcEdit.gridy = 0;
        gbcEdit.anchor = GridBagConstraints.EAST;
        editClientPanel.add(editNameField, gbcEdit);
        gbcEdit.gridy++;
        editClientPanel.add(editAddressField, gbcEdit);
        gbcEdit.gridy++;
        editClientPanel.add(editEmailField, gbcEdit);
        gbcEdit.gridy++;
        editClientPanel.add(editAgeField, gbcEdit);

        JPanel deleteClientPanel = new JPanel();
        deleteClientPanel.setBorder(BorderFactory.createTitledBorder("Delete Client"));

         addButton = new JButton("Add Client");
         editButton = new JButton("Edit Client");
        deleteButton = new JButton("Delete Client");
        controller = new ControllerClient(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(addClientPanel);
        bottomPanel.add(editClientPanel);
        bottomPanel.add(deleteClientPanel);
        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }
    /**
     * Curăță câmpurile destinate adăugării unui client nou.
     */
    public void clearAddFields() {
        addNameField.setText("");
        addAddressField.setText("");
        addEmailField.setText("");
        addAgeField.setText("");
    }
    /**
     * Curăță câmpurile destinate editării unui client existent.
     */
    public void clearEditFields() {
        editNameField.setText("");
        editAddressField.setText("");
        editEmailField.setText("");
        editAgeField.setText("");
    }
    /**
     * Încarcă numele coloanelor în tabel.
     */
    public void loadColumnNames() {
        Field[] fields = Client.class.getDeclaredFields(); //obtineem capul de tabel(campurile clasei client)
        for (Field field : fields) {
            tableModel.addColumn(field.getName());
        }
    }
    /**
     * Încarcă clienții în tabel.
     */
    public void loadClientsIntoTable() {
        tableModel.setRowCount(0);

        List<Client> clients = null;
        try {
            clients = clientBLL.findAllClients(); //lista de clienti
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lista de clienți este goală sau a apărut o excepție la preluarea datelor.", "Eroare", JOptionPane.ERROR_MESSAGE);
        }

        if (clients != null) {
            for (Client client : clients) {
                Object[] rowData = new Object[tableModel.getColumnCount()];
                int columnCount = 0;
                for (Field field : client.getClass().getDeclaredFields()) { //obtinem valoarea fiecarui camp folosind reflexia
                    field.setAccessible(true);
                    try {
                        Object value = field.get(client);
                        rowData[columnCount++] = value;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                tableModel.addRow(rowData);
            }
        }
    }
    /**
     * Adaugă un ascultător pentru butonul de adăugare.
     * @param listener Ascultătorul care va fi adăugat butonului de adăugare.
     */
    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    /**
     * Adaugă un ascultător pentru butonul de editare.
     * @param listener Ascultătorul care va fi adăugat butonului de editare.
     */
    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    /**
     * Adaugă un ascultător pentru butonul de ștergere.
     * @param listener Ascultătorul care va fi adăugat butonului de ștergere.
     */
    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    /**
     * Returnează textul introdus în câmpul pentru numele de adăugat.
     * @return Textul introdus în câmpul pentru nume.
     */
    public String getAddNameFieldText() {
        return addNameField.getText();
    }
    /**
     * Returnează textul introdus în câmpul pentru adresa de adăugat.
     * @return Textul introdus în câmpul pentru adresă.
     */
    public String getAddAddressFieldText() {
        return addAddressField.getText();
    }
    /**
     * Returnează textul introdus în câmpul pentru adresa de email de adăugat.
     * @return Textul introdus în câmpul pentru adresa de email.
     */
    public String getAddEmailFieldText() {
        return addEmailField.getText();
    }
    /**
     * Returnează valoarea introdusă în câmpul pentru vârsta de adăugat.
     * @return Valoarea introdusă în câmpul pentru vârstă.
     */
    public int getAddAgeFieldValue() {
        String ageText = addAgeField.getText();
        if (ageText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(ageText);
        }
    }
    /**
     * Returnează textul introdus în câmpul pentru numele de editat.
     * @return Textul introdus în câmpul pentru nume.
     */
    public String getEditNameFieldText() {
        return editNameField.getText();
    }
    /**
     * Returnează textul introdus în câmpul pentru adresa de editat.
     * @return Textul introdus în câmpul pentru adresă.
     */
    public String getEditAddressFieldText() {
        return editAddressField.getText();
    }
    /**
     * Returnează textul introdus în câmpul pentru adresa de email de editat.
     * @return Textul introdus în câmpul pentru adresa de email.
     */
    public String getEditEmailFieldText() {
        return editEmailField.getText();
    }
    /**
     * Returnează valoarea introdusă în câmpul pentru vârsta de editat.
     * @return Valoarea introdusă în câmpul pentru vârstă.
     */
    public int getEditAgeFieldValue() {
        String ageText = editAgeField.getText();
        if (ageText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(editAgeField.getText());
        }
    }
    /**
     * Returnează tabelul de clienți.
     * @return Tabelul de clienți.
     */
    public JTable getClientTable() {
        return clientTable;
    }
    /**
     * Returnează modelul de tabel.
     * @return Modelul de tabel.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    /**
     * Setează controllerul pentru fereastra de client.
     * @param controller ControllerClient care va fi setat.
     */
    public void setController(ControllerClient controller) {
        this.controller = controller;
    }
    /**
     * Returnează controllerul asociat fereastrei de client.
     * @return ControllerClient asociat fereastrei de client.
     */
    public ControllerClient getController() {
        return controller;
    }
    /**
     * Returnează ID-ul clientului selectat în tabel.
     * @return ID-ul clientului selectat, sau -1 dacă niciun client nu este selectat.
     */
    public int getSelectedClientId() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }
    /**
     * Afisează un mesaj de eroare.
     * @param errorMessage Mesajul de eroare ce va fi afișat.
     */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
