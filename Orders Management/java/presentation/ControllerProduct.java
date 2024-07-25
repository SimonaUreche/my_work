package presentation;

import bll.ProductBLL;
import model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa ControllerProduct gestionează interacțiunea între GUI-ul ProductFrame și logica din ProductBLL.
 * Ascultă butoanele de adăugare, editare și ștergere a unui produs și acționează în consecință.
 */
public class ControllerProduct {
    private ProductBLL productBLL;
    private ProductFrame productFrame;

    /**
     * Constructor pentru ControllerProduct.
     * @param productFrame Frame-ul produselor asociat controllerului.
     */
    public ControllerProduct(ProductFrame productFrame) {
        this.productFrame = productFrame;
        productBLL = new ProductBLL();
        productFrame.setController(this);
        productFrame.addAddButtonListener(new AddButtonListener());
        productFrame.addEditButtonListener(new EditButtonListener());
        productFrame.addDeleteButtonListener(new DeleteButtonListener());
    }

    /**
     * Listener pentru butonul de adăugare a unui produs.
     */
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = productFrame.getAddNameFieldText();
            int price = productFrame.getAddPriceFieldText();
            int quantity = productFrame.getAddQuantityFieldText();

            if (!name.isEmpty()) {
                addProduct(name, price, quantity);
                productFrame.clearAddFields();
            } else {
                productFrame.displayErrorMessage("All fields are required for adding a product.");
            }
        }
    }

    /**
     * Listener pentru butonul de editare a unui produs.
     */
    class EditButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTable productTable = productFrame.getProductTable();

            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                productFrame.displayErrorMessage("Select a product to edit.");
                return;
            }

            DefaultTableModel tableModel = productFrame.getTableModel();

            int productId = (int) tableModel.getValueAt(selectedRow, 0);

            int oldQuantity = (int) tableModel.getValueAt(selectedRow, 1);
            int oldPrice = (int) tableModel.getValueAt(selectedRow, 2);
            String oldName = (String) tableModel.getValueAt(selectedRow, 3);

            String newName = productFrame.getEditNameFieldText();
            int newPrice = productFrame.getEditPriceFieldText();
            int newQuantity = productFrame.getEditQuantityFieldText();

            if (!newName.isEmpty() && !newName.equals(oldName)) {
                tableModel.setValueAt(newName, selectedRow, 3);
                oldName = newName;
            }

            if (newPrice != 0 && newPrice != oldPrice) {
                tableModel.setValueAt(newPrice, selectedRow, 2);
                oldPrice = newPrice;
            }

            if (newQuantity != 0 && newQuantity != oldQuantity) {
                tableModel.setValueAt(newQuantity, selectedRow, 1);
                oldQuantity = newQuantity;
            }

            productFrame.getController().editProduct(oldName, oldPrice, oldQuantity, productId);
            JOptionPane.showMessageDialog(productFrame, "Product updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            productFrame.clearEditFields();
        }
    }

    /**
     * Listener pentru butonul de ștergere a unui produs.
     */
    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int productId = productFrame.getSelectedProductId();
            deleteProduct(productId);
        }
    }

    /**
     * Adaugă un produs nou în baza de date și împrospătează tabelul cu produsele.
     * @param name Numele produsului de adăugat.
     * @param price Prețul produsului de adăugat.
     * @param quantity Cantitatea produsului de adăugat.
     */
    public void addProduct(String name, int price, int quantity) {
        Product newProduct = new Product(quantity, price, name);
        productBLL.insertProduct(newProduct);
        productFrame.loadProductsIntoTable();
    }

    /**
     * Editează un produs existent în baza de date și împrospătează tabelul cu produsele.
     * @param name Noul nume al produsului.
     * @param price Noul preț al produsului.
     * @param quantity Noua cantitate a produsului.
     * @param productId ID-ul produsului de editat.
     */
    public void editProduct(String name, int price, int quantity, int productId) {
        Product updatedProduct = new Product(productId, quantity, price, name);
        productBLL.updateProduct(updatedProduct);
        productFrame.loadProductsIntoTable();
    }

    /**
     * Șterge un produs din baza de date și împrospătează tabelul cu produsele.
     * @param id ID-ul produsului de șters.
     */
    public void deleteProduct(int id) {
        productBLL.deleteProduct(id);
        productFrame.loadProductsIntoTable();
    }
}
