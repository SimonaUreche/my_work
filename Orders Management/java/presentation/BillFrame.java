package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Bill;
import dao.BillDAO;

/**
 * O fereastră pentru afișarea unei tabele de facturi.
 */
public class BillFrame extends JFrame {
    private JTable billTable;
    private JScrollPane scrollPane;
    private BillDAO billDAO;

    /**
     * Construiește o nouă instanță a clasei BillFrame.
     */
    public BillFrame() {
        setTitle("Log table");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        billDAO = new BillDAO();

        List<Bill> bills = billDAO.getAllBills();

        String[] columnNames = {"ID Comandă", "ID Client", "ID Produs", "Cantitate", "Preț", "Email"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Bill bill : bills) {
            Object[] rowData = {
                    bill.getId(),
                    bill.getClientId(),
                    bill.getProductId(),
                    bill.getQuantity(),
                    bill.getTotalPrice(),
                    bill.getEmail()
            };
            model.addRow(rowData);
        }

        billTable = new JTable(model);

        scrollPane = new JScrollPane(billTable);
        add(scrollPane, BorderLayout.CENTER);
    }
}
