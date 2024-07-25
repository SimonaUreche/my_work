package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.Bill;

/**
 * Clasa pentru accesul la datele tabelei 'bill' din baza de date.
 */
public class BillDAO {
    private static final String INSERT_QUERY = "INSERT INTO bill (orderId, clientId, productId, quantity, totalPrice, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_BILLS_QUERY = "SELECT * FROM bill";

    /**
     * Inserează o factură în baza de date.
     *
     * @param bill Obiectul 'Bill' care reprezintă factura de inserat.
     */
    public void insertBill(Bill bill) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, bill.getId());
            statement.setInt(2, bill.getClientId());
            statement.setInt(3, bill.getProductId());
            statement.setInt(4, bill.getQuantity());
            statement.setInt(5, bill.getTotalPrice());
            statement.setString(6, bill.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returnează o listă de toate facturile din baza de date.
     *
     * @return Lista de obiecte 'Bill' care reprezintă toate facturile.
     */
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BILLS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idOrder = resultSet.getInt("orderId");
                int idClient = resultSet.getInt("clientId");
                int idProduct = resultSet.getInt("productId");
                int quantity = resultSet.getInt("quantity");
                int price = resultSet.getInt("totalPrice");
                String email =  resultSet.getString("email");
                bills.add(new Bill(idOrder, idClient, idProduct, quantity, price, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
