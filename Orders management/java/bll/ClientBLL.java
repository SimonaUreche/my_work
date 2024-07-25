package bll;

import bll.validators.EmailValidator;
import bll.validators.ClientAgeValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Această clasă gestionează logica legată de clienți.
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * Constructorul clasei ClientBLL.
     * Inițializează lista de validatori și obiectul ClientDAO.
     */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Returnează o listă cu toți clienții.
     * @return Lista de clienți.
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Inserează un client în baza de date.
     * @param client Clientul de inserat.
     * @return Clientul inserat.
     */
    public Client insertClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        clientDAO.insert(client);
        return client;
    }

    /**
     * Actualizează un client în baza de date.
     * @param client Clientul de actualizat.
     */
    public void updateClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        clientDAO.update(client);
    }

    /**
     * Șterge un client din baza de date după id.
     * @param id Id-ul clientului de șters.
     * @throws NoSuchElementException Dacă clientul nu există în baza de date.
     */
    public void deleteClient(int id) {
        try {
            clientDAO.delete("id", id);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Client with id '" + id + "' not found.");
        }
    }

    /**
     * Setează obiectul ClientDAO.
     * @param clientDAO Obiectul ClientDAO.
     */
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    /**
     * Returnează obiectul ClientDAO.
     * @return Obiectul ClientDAO.
     */
    public ClientDAO getClientDAO() {
        return clientDAO;
    }

}
