package presentation;

import bll.OrderBLL;
import bll.ProductBLL;
import dao.BillDAO;
import model.Bill;
import model.Orders;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa ControllerOrder gestionează interacțiunea între GUI-ul OrderFrame și logica din OrderBLL.
 * Ascultă butoanele de plasare a unei comenzi și ștergere a unei comenzi și acționează în consecință.
 */
public class ControllerOrder {
    private OrderBLL orderBLL;
    private OrderFrame orderFrame;
    private BillDAO billDAO;

    /**
     * Constructor pentru ControllerOrder.
     * @param orderFrame Frame-ul comenzilor asociat controllerului.
     */
    public ControllerOrder(OrderFrame orderFrame) {
        this.orderFrame = orderFrame;
        orderBLL = new OrderBLL();
        billDAO = new BillDAO();
        orderFrame.setController(this);
        orderFrame.addOrderButtonListener(new OrderButtonListener());
        orderFrame.addDeleteButtonListener(new DeleteButtonListener());
    }

    /**
     * Listener pentru butonul de ștergere a unei comenzi.
     */
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int orderId = orderFrame.getSelectedOrderId();
            deleteOrder(orderId);
        }
    }

    /**
     * Listener pentru butonul de plasare a unei comenzi.
     */
    public class OrderButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int clientID = orderFrame.getSelectedClientID();
            int productID = orderFrame.getSelectedProductID();
            int quantity = orderFrame.getQuantityTextField();

            if (quantity <= 0) {
                orderFrame.displayErrorMessage("Cantitatea introdusă nu este validă.");
                return;
            }

            int selectedRow = orderFrame.getProductTable().getSelectedRow();
            int availableQuantity = (int) orderFrame.getProductTable().getValueAt(selectedRow, 1);
            int availablePrice = (int) orderFrame.getProductTable().getValueAt(selectedRow, 2);

            if (quantity > availableQuantity) {
                orderFrame.displayErrorMessage("Cantitatea dorită nu este disponibilă.");
                return;
            }

            int totalPrice = quantity * availablePrice;

            Orders newOrder = new Orders(clientID, productID, quantity, totalPrice);
            orderBLL.insertOrder(newOrder);
            orderFrame.loadOrderIntoTable();//creare+inserare baza de date si tabel order nou

            int updatedQuantity = availableQuantity - quantity;
            orderFrame.getProductTable().setValueAt(updatedQuantity, selectedRow, 1);
            ProductBLL productBLL = new ProductBLL();
            productBLL.updateProductQuantity(productID, updatedQuantity);//setam in tabel+baza de date noua cantitate

            newOrder.setQuantity(updatedQuantity); //actualizam newOrder pentru factura
            orderBLL.updateOrder(newOrder);

            int selectedRow2 = orderFrame.getClientTable().getSelectedRow();
            String email = (String) orderFrame.getClientTable().getValueAt(selectedRow2, 3);
            Bill newBill = new Bill(newOrder.getId(), clientID, productID, quantity, totalPrice, email);
            billDAO.insertBill(newBill); //factura
        }
    }

    /**
     * Șterge o comandă din baza de date și împrospătează tabelul cu comenzile.
     * @param id ID-ul comenzii de șters.
     */
    public void deleteOrder(int id) {
        orderBLL.deleteOrder(id);
        orderFrame.loadOrderIntoTable();
    }
}
