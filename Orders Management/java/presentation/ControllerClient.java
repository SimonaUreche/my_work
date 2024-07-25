package presentation;

import bll.ClientBLL;
import model.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Clasa ControllerClient gestionează interacțiunea între GUI-ul ClientFrame și logica din ClientBLL.
 * Ascultă butoanele de adăugare, editare și ștergere a clienților și acționează în consecință.
 */
public class ControllerClient {
    private ClientBLL clientBLL;
    private ClientFrame clientFrame;
    /**
     * Constructor pentru ControllerClient.
     * @param clientFrame Frame-ul clientului asociat controllerului.
     */
    public ControllerClient(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        clientBLL = new ClientBLL();
        clientFrame.setController(this);
        clientFrame.addAddButtonListener(new AddButtonListener());
        clientFrame.addEditButtonListener(new EditButtonListener());
        clientFrame.addDeleteButtonListener(new DeleteButtonListener());
    }
    /**
     * Listener pentru butonul de adăugare a unui client.
     */
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = clientFrame.getAddNameFieldText();
            String address = clientFrame.getAddAddressFieldText();
            String email = clientFrame.getAddEmailFieldText();
            int age = clientFrame.getAddAgeFieldValue();

            if (!name.isEmpty() && !address.isEmpty() && !email.isEmpty()) {
                addClient(name, address, email, age);
                clientFrame.clearAddFields();
            } else {
                clientFrame.displayErrorMessage("All fields are required for adding a client.");
            }
        }
    }
    /**
     * Listener pentru butonul de editare a unui client.
     */
    class EditButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTable clientTable = clientFrame.getClientTable();

            int selectedRow = clientTable.getSelectedRow();
            if (selectedRow == -1) {
                clientFrame.displayErrorMessage("Select a client to edit.");
                return;
            }

            DefaultTableModel tableModel = clientFrame.getTableModel();

            int clientID = (int) tableModel.getValueAt(selectedRow, 0);

            String oldName = (String) tableModel.getValueAt(selectedRow, 1);
            String oldAddress = (String) tableModel.getValueAt(selectedRow, 2);
            String oldEmail = (String) tableModel.getValueAt(selectedRow, 3);
            int oldAge = (int) tableModel.getValueAt(selectedRow, 4);

            String newName = clientFrame.getEditNameFieldText();
            String newAddress = clientFrame.getEditAddressFieldText();
            String newEmail = clientFrame.getEditEmailFieldText();
            int newAge = clientFrame.getEditAgeFieldValue();

            if (!newName.isEmpty() && !newName.equals(oldName)) {
                tableModel.setValueAt(newName, selectedRow, 1);
                oldName = newName;
            }

            if (!newAddress.isEmpty() && !newAddress.equals(oldAddress)) {
                tableModel.setValueAt(newAddress, selectedRow, 2);
                oldAddress = newAddress;
            }

            if (!newEmail.isEmpty() && !newEmail.equals(oldEmail)) {
                tableModel.setValueAt(newEmail, selectedRow, 3);
                oldEmail = newEmail;
            }

            if (newAge != 0 && newAge != oldAge) {
                tableModel.setValueAt(newAge, selectedRow, 4);
                oldAge = newAge;
            }

            clientFrame.getController().editClient(clientID, oldName, oldAddress, oldEmail, newAge);
            JOptionPane.showMessageDialog(clientFrame, "Client updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clientFrame.clearEditFields();
        }
    }
    /**
     * Listener pentru butonul de ștergere a unui client.
     */
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int clientId = clientFrame.getSelectedClientId();
            deleteClient(clientId);
        }
    }
    /**
     * Adaugă un nou client în baza de date și împrospătează tabelul cu clienți.
     * @param name Numele clientului.
     * @param address Adresa clientului.
     * @param email Email-ul clientului.
     * @param age Vârsta clientului.
     */
    public void addClient(String name, String address, String email, int age) {
        Client newClient = new Client(name, address, email, age);
        clientBLL.insertClient(newClient);
        clientFrame.loadClientsIntoTable();
    }
    /**
     * Editează un client existent în baza de date și împrospătează tabelul cu clienți.
     * @param id ID-ul clientului de editat.
     * @param name Noul nume al clientului.
     * @param address Noua adresă a clientului.
     * @param email Noul email al clientului.
     * @param age Noua vârstă a clientului.
     */
    public void editClient(int id, String name, String address, String email, int age) {
        Client updatedClient = new Client(id, name, address, email, age);
        clientBLL.updateClient(updatedClient);
        clientFrame.loadClientsIntoTable();
    }
    /**
     * Șterge un client din baza de date și împrospătează tabelul cu clienți.
     * @param id ID-ul clientului de șters.
     */
    public void deleteClient(int id) {
        clientBLL.deleteClient(id);
        clientFrame.loadClientsIntoTable();
    }
}
