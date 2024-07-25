package start;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import bll.ProductBLL;
import dao.ClientDAO;
import model.Client;
import model.Product;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {
//		ClientBLL clientBLL = new ClientBLL();
//
//		List<Client> clients = null;
//
//		try {
//			clients = clientBLL.findClientByName("Ureche");
//		} catch (NoSuchElementException ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//
//		if (clients != null) {
//			for (Client client : clients) {
//				ReflectionExample.retrieveProperties(client);
//			}
//		}

//		ProductBLL productBLL = new ProductBLL();
//
//		List<Product> products = null;
//
//		try {
//			products = productBLL.findProductByName("matura");
//		} catch (NoSuchElementException ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//
//		if (products != null) {
//			for (Product product : products) {
//				ReflectionExample.retrieveProperties(product);
//			}
//		}


//
//		ClientBLL clientBLL = new ClientBLL();
//
//		List<Client> clients = null;
//		try {
//			clients = clientBLL.findAllClients();
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//
//		if (clients != null) {
//			for (Client client : clients) {
//				ReflectionExample.retrieveProperties(client);
//				System.out.println();
//			}
//		} else {
//			System.out.println("Lista de clienți este goală sau a apărut o excepție la preluarea datelor.");
//		}

//		ClientBLL clientBLL = new ClientBLL();
//		Client newClient = new Client();
//		newClient.setName("Pop");
//		newClient.setAddress("Main St 123 ");
//		newClient.setEmail("Pop@example.com");
//		newClient.setAge(30);
//
//		Client insertedClient = clientBLL.insertClient(newClient);
//
//		if (insertedClient != null) {
//			System.out.println("Client inserted successfully:");
//			ReflectionExample.retrieveProperties(insertedClient);
//		} else {
//			System.out.println("Failed to insert client.");
//		}
//
//			ClientBLL clientBLL = new ClientBLL();
//			try {
//				clientBLL.deleteClient("Pop");
//				System.out.println("Client deleted successfully.");
//			} catch (Exception ex) {
//				System.out.println("Error deleting client: " + ex.getMessage());
//			}


//		ClientBLL clientBLL = new ClientBLL();
//
//
//		Client clientToUpdate = clientBLL.findClientByName("Pop").get(0);
//
//		if (clientToUpdate != null) {
//
//			clientToUpdate.setName("John");
//
//			clientBLL.updateClient(clientToUpdate);
//			System.out.println("Clientul a fost actualizat cu succes.");
//		} else {
//			System.out.println("Clientul nu a fost găsit în baza de date.");
//		}

	}

}
