package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.List;
import bll.ProductBLL;
import model.Product;

/**
 * Fereastra pentru gestionarea produselor.
 * Această fereastră permite utilizatorului să vizualizeze, să adauge, să editeze și să șteargă produse.
 */
public class ProductFrame extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ProductBLL productBLL;

    private JTextField addNameField;
    private JTextField addPriceField;
    private JTextField addStockField;

    JButton addButton;
    JButton editButton;
    JButton deleteButton;

    private JTextField editNameField;
    private JTextField editPriceField;
    private JTextField editStockField;

    private ControllerProduct controller;

    /**
     * Constructor pentru clasa ProductFrame.
     * Inițializează aspectul interfeței grafice și funcționalitățile pentru gestionarea produselor.
     */
    public ProductFrame() {
        setTitle("Commands window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        productBLL = new ProductBLL();

        tableModel = new DefaultTableModel();
        loadColumnNames();
        loadProductsIntoTable();

        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        addNameField = new JTextField(20);
        addPriceField = new JTextField(20);
        addStockField = new JTextField(20);

        JPanel addProductPanel = new JPanel(new GridBagLayout());
        addProductPanel.setBorder(BorderFactory.createTitledBorder("Add"));
        GridBagConstraints gbcAdd = new GridBagConstraints();
        gbcAdd.gridx = 0;
        gbcAdd.gridy = 0;
        gbcAdd.anchor = GridBagConstraints.WEST;
        addProductPanel.add(new JLabel("Name:"), gbcAdd);
        gbcAdd.gridy++;
        addProductPanel.add(new JLabel("Price:"), gbcAdd);
        gbcAdd.gridy++;
        addProductPanel.add(new JLabel("Quantity:"), gbcAdd);

        gbcAdd.gridx = 1;
        gbcAdd.gridy = 0;
        gbcAdd.anchor = GridBagConstraints.EAST;
        addProductPanel.add(addNameField, gbcAdd);
        gbcAdd.gridy++;
        addProductPanel.add(addPriceField, gbcAdd);
        gbcAdd.gridy++;
        addProductPanel.add(addStockField, gbcAdd);

        editNameField = new JTextField(20);
        editPriceField = new JTextField(20);
        editStockField = new JTextField(20);

        JPanel editProductPanel = new JPanel(new GridBagLayout());
        editProductPanel.setBorder(BorderFactory.createTitledBorder("Edit"));
        GridBagConstraints gbcEdit = new GridBagConstraints();
        gbcEdit.gridx = 0;
        gbcEdit.gridy = 0;
        gbcEdit.anchor = GridBagConstraints.WEST;
        editProductPanel.add(new JLabel("Name:"), gbcEdit);
        gbcEdit.gridy++;
        editProductPanel.add(new JLabel("Price:"), gbcEdit);
        gbcEdit.gridy++;
        editProductPanel.add(new JLabel("Quantity:"), gbcEdit);

        gbcEdit.gridx = 1;
        gbcEdit.gridy = 0;
        gbcEdit.anchor = GridBagConstraints.EAST;
        editProductPanel.add(editNameField, gbcEdit);
        gbcEdit.gridy++;
        editProductPanel.add(editPriceField, gbcEdit);
        gbcEdit.gridy++;
        editProductPanel.add(editStockField, gbcEdit);

        JPanel deleteProductPanel = new JPanel();
        deleteProductPanel.setBorder(BorderFactory.createTitledBorder("Delete"));

        addButton = new JButton("Add Product");
        editButton = new JButton("Edit Product");
        deleteButton = new JButton("Delete Product");

        controller = new ControllerProduct(this);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(addProductPanel);
        bottomPanel.add(editProductPanel);
        bottomPanel.add(deleteProductPanel);
        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Încarcă numele de coloane pentru tabelul produselor.
     */
    private void loadColumnNames() {
        Field[] fields = Product.class.getDeclaredFields();
        for (Field field : fields) {
            tableModel.addColumn(field.getName());
        }
    }

    /**
     * Încarcă produsele în tabel.
     */
    public void loadProductsIntoTable() {
        tableModel.setRowCount(0);

        List<Product> products = null;
        try {
            products = productBLL.findAllProducts();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lista de produse este goală sau a apărut o excepție la preluarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (products != null) {
            for (Product product : products) {
                Object[] rowData = new Object[tableModel.getColumnCount()];
                int columnCount = 0;
                for (Field field : product.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(product);
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
     * Curăță câmpurile pentru adăugare.
     */
    public void clearAddFields() {
        addNameField.setText("");
        addPriceField.setText("");
        addStockField.setText("");
    }

    /**
     * Curăță câmpurile pentru editare.
     */
    public void clearEditFields() {
        editNameField.setText("");
        editPriceField.setText("");
        editStockField.setText("");
    }

    /**
     * Adaugă un ascultător pentru butonul de adăugare.
     * @param listener Ascultătorul de evenimente.
     */
    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    /**
     * Adaugă un ascultător pentru butonul de editare.
     * @param listener Ascultătorul de evenimente.
     */
    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    /**
     * Adaugă un ascultător pentru butonul de ștergere.
     * @param listener Ascultătorul de evenimente.
     */
    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    /**
     * Returnează textul introdus în câmpul pentru nume la adăugare.
     * @return Numele introdus.
     */
    public String getAddNameFieldText() {
        return addNameField.getText();
    }

    /**
     * Returnează prețul introdus în câmpul pentru preț la adăugare.
     * @return Prețul introdus.
     */
    public int getAddPriceFieldText() {
        String priceText = addPriceField.getText();
        if (priceText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(priceText);
        }
    }

    /**
     * Returnează cantitatea introdusă în câmpul pentru stoc la adăugare.
     * @return Cantitatea introdusă.
     */
    public int getAddQuantityFieldText() {
        String stockText = addStockField.getText();
        if (stockText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(stockText);
        }
    }

    /**
     * Returnează textul introdus în câmpul pentru nume la editare.
     * @return Numele introdus.
     */
    public String getEditNameFieldText() {
        return editNameField.getText();
    }

    /**
     * Returnează prețul introdus în câmpul pentru preț la editare.
     * @return Prețul introdus.
     */
    public int getEditPriceFieldText() {
        String priceText = editPriceField.getText();
        if (priceText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(priceText);
        }
    }

    /**
     * Returnează cantitatea introdusă în câmpul pentru stoc la editare.
     * @return Cantitatea introdusă.
     */
    public int getEditQuantityFieldText() {
        String stockText = editStockField.getText();
        if (stockText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(stockText);
        }
    }

    /**
     * Returnează tabela produselor.
     * @return Tabela produselor.
     */
    public JTable getProductTable() {
        return productTable;
    }

    /**
     * Returnează modelul tabelului.
     * @return Modelul tabelului.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Setează controlerul pentru această fereastră.
     * @param controller Controlerul de produse.
     */
    public void setController(ControllerProduct controller) {
        this.controller = controller;
    }

    /**
     * Returnează controlerul asociat acestei fereastra.
     * @return Controlerul de produse.
     */
    public ControllerProduct getController() {
        return controller;
    }

    /**
     * Returnează ID-ul produsului selectat în tabel.
     * @return ID-ul produsului selectat.
     */
    public int getSelectedProductId() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    /**
     * Afișează un mesaj de eroare în fereastra curentă.
     * @param errorMessage Mesajul de eroare.
     */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
