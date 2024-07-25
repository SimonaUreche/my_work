package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Fereastra pentru gestionarea comenzilor.
 * Această fereastră permite utilizatorului să vizualizeze, să plaseze și să șteargă comenzi, precum și să genereze facturi.
 */
public class OrderFrame extends JFrame {
    private DefaultTableModel clientTableModel;
    private DefaultTableModel productTableModel;
    private DefaultTableModel orderTableModel;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;
    private JButton orderButton;
    private JButton deleteButton;
    private JButton billButton;
    private JTable clientTable;
    private JTable productTable;
    private JTable orderTable;
    JTextField quantityTextField;
    private ControllerOrder controller;

    /**
     * Constructor pentru clasa OrderFrame.
     * Inițializează aspectul interfeței grafice și funcționalitățile pentru gestionarea comenzilor.
     */
    public OrderFrame() {
        setTitle("Commands window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();

        JPanel panel = new JPanel(new BorderLayout());

        clientTableModel = new DefaultTableModel();
        productTableModel = new DefaultTableModel();
        orderTableModel = new DefaultTableModel();

        loadColumnNamesClients();
        loadClientsIntoTable();
        loadColumnNamesProducts();
        loadProductsIntoTable();
        loadColumnNamesOrder();
        loadOrderIntoTable();

        clientTable = new JTable(clientTableModel);
        JScrollPane clientScrollPane = new JScrollPane(clientTable);

        productTable = new JTable(productTableModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);

        orderTable = new JTable(orderTableModel);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        tablesPanel.add(clientScrollPane);
        tablesPanel.add(productScrollPane);
        tablesPanel.add(orderScrollPane);
        panel.add(tablesPanel, BorderLayout.CENTER);

        add(panel);

        quantityTextField = new JTextField(10);
        JLabel quantityLabel = new JLabel("Quantity:");
        JPanel quantityPanel = new JPanel();
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityTextField);
        panel.add(quantityPanel, BorderLayout.NORTH);

        billButton = new JButton("BILL");
        billButton.setForeground(Color.WHITE);
        billButton.setBackground(Color.BLACK);
        billButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBillFrame();
            }
        });

        orderButton = new JButton("Order");
        deleteButton = new JButton("Delete");
        controller = new ControllerOrder(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(orderButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(billButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Deschide fereastra pentru generarea facturii.
     */
    private void openBillFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BillFrame billFrame = new BillFrame();
                billFrame.setVisible(true);
            }
        });
    }

    /**
     * Setează controller-ul pentru această fereastră.
     * @param controller Controller-ul pentru gestionarea comenzilor.
     */
    public void setController(ControllerOrder controller) {
        this.controller = controller;
    }

    /**
     * Încarcă numele de coloane pentru tabelul clienților.
     */
    public void loadColumnNamesClients() {
        Field[] fields = Client.class.getDeclaredFields();
        for (Field field : fields) {
            clientTableModel.addColumn(field.getName());
        }
    }

    /**
     * Încarcă clienții în tabel.
     */
    public void loadClientsIntoTable() {
        clientTableModel.setRowCount(0);

        List<Client> clients = null;
        try {
            clients = clientBLL.findAllClients();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lista de clienți este goală sau a apărut o excepție la preluarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (clients != null) {
            for (Client client : clients) {
                Object[] rowData = new Object[clientTableModel.getColumnCount()];
                int columnCount = 0;
                for (Field field : client.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(client);
                        rowData[columnCount++] = value;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                clientTableModel.addRow(rowData);
            }
        }
    }

    /**
     * Încarcă numele de coloane pentru tabelul produselor.
     */
    public void loadColumnNamesProducts() {
        Field[] fields = Product.class.getDeclaredFields();
        for (Field field : fields) {
            productTableModel.addColumn(field.getName());
        }
    }

    /**
     * Încarcă produsele în tabel.
     */
    public void loadProductsIntoTable() {
        productTableModel.setRowCount(0);

        List<Product> products = null;
        try {
            products = productBLL.findAllProducts();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lista de produse este goală sau a apărut o excepție la preluarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (products != null) {
            for (Product product : products) {
                Object[] rowData = new Object[productTableModel.getColumnCount()];
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
                productTableModel.addRow(rowData);
            }
        }
    }

    /**
     * Încarcă numele de coloane pentru tabelul comenzilor.
     */
    public void loadColumnNamesOrder() {
        Field[] fields = Orders.class.getDeclaredFields();
        for (Field field : fields) {
            orderTableModel.addColumn(field.getName());
        }
    }

    /**
     * Încarcă comenzile în tabel.
     */
    public void loadOrderIntoTable() {
        orderTableModel.setRowCount(0);

        List<Orders> orders = null;
        try {
            orders = orderBLL.findAllOrders();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lista de comenzi este goală sau a apărut o excepție la preluarea datelor.", "Eroare", JOptionPane.ERROR_MESSAGE);
        }

        if (orders != null) {
            for (Orders order : orders) {
                Object[] rowData = new Object[orderTableModel.getColumnCount()];
                int columnCount = 0;
                for (Field field : order.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(order);
                        rowData[columnCount++] = value;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                orderTableModel.addRow(rowData);
            }
        }
    }

    /**
     * Returnează ID-ul comenzii selectate în tabelul comenzilor.
     * @return ID-ul comenzii selectate.
     */
    public int getSelectedOrderId() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) orderTable.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    /**
     * Returnează ID-ul clientului selectat în tabelul clienților.
     * @return ID-ul clientului selectat.
     */
    public int getSelectedClientID() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) clientTable.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    /**
     * Returnează tabela produselor.
     * @return Tabela produselor.
     */
    public JTable getProductTable() {
        return productTable;
    }

    /**
     * Returnează tabela clienților.
     * @return Tabela clienților.
     */
    public JTable getClientTable() {
        return clientTable;
    }

    /**
     * Returnează ID-ul produsului selectat în tabelul produselor.
     * @return ID-ul produsului selectat.
     */
    public int getSelectedProductID() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) productTable.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    /**
     * Adaugă un ascultător pentru butonul de comandă.
     * @param listener Ascultătorul de evenimente.
     */
    public void addOrderButtonListener(ActionListener listener) {
        orderButton.addActionListener(listener);
    }

    /**
     * Adaugă un ascultător pentru butonul de ștergere.
     * @param listener Ascultătorul de evenimente.
     */
    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    /**
     * Returnează cantitatea introdusă în câmpul de text.
     * @return Cantitatea introdusă.
     */
    public int getQuantityTextField() {
        String quantityText = quantityTextField.getText();
        if (quantityText.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(quantityText);
        }
    }

    /**
     * Afișează un mesaj de eroare în fereastra curentă.
     * @param errorMessage Mesajul de eroare.
     */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
