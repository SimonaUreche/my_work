package bll;

import java.util.List;
import java.util.NoSuchElementException;

import dao.OrderDAO;
import model.Orders;

/**
 * Această clasă gestionează logica legată de comenzile clienților.
 */
public class OrderBLL {
    private OrderDAO orderDAO;

    /**
     * Constructorul clasei OrderBLL.
     * Inițializează obiectul OrderDAO.
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    /**
     * Returnează o listă cu toate comenzile.
     * @return Lista de comenzi.
     */
    public List<Orders> findAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Actualizează o comandă în baza de date.
     * @param orders Comanda de actualizat.
     */
    public void updateOrder(Orders orders) {
        orderDAO.update(orders);
    }

    /**
     * Inserează o comandă în baza de date.
     * @param order Comanda de inserat.
     */
    public void insertOrder(Orders order) {
        orderDAO.insert(order);
    }

    /**
     * Șterge o comandă din baza de date după id.
     * @param id Id-ul comenzii de șters.
     * @throws NoSuchElementException Dacă comanda nu există în baza de date.
     */
    public void deleteOrder(int id) {
        try {
            orderDAO.delete("id", id);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Order with id '" + id + "' not found.");
        }
    }

    /**
     * Setează obiectul OrderDAO.
     * @param orderDAO Obiectul OrderDAO.
     */
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * Returnează obiectul OrderDAO.
     * @return Obiectul OrderDAO.
     */
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }
}
