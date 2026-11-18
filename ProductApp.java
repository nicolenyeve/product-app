import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class ProductApp extends JFrame {
    private JTextField txtCustomer, txtPrice;
    private JComboBox<String> cmbProduct;
    private JButton btnProcess;

    public ProductApp() {
        setTitle("Product App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(processItem);
        fileMenu.add(clearItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

      
        add(new JLabel("Customer Name:"));
        txtCustomer = new JTextField();
        add(txtCustomer);

        add(new JLabel("Select Product:"));
        cmbProduct = new JComboBox<>(new String[]{"Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"});
        add(cmbProduct);

        add(new JLabel("Enter Price:"));
        txtPrice = new JTextField();
        add(txtPrice);

        btnProcess = new JButton("PROCESS");
        add(btnProcess);

        
        btnProcess.addActionListener(e -> processSale());
        processItem.addActionListener(e -> processSale());

        clearItem.addActionListener(e -> {
            txtCustomer.setText("");
            txtPrice.setText("");
            cmbProduct.setSelectedIndex(0);
        });

        exitItem.addActionListener(e -> System.exit(0));
    }

    private void processSale() {
        try {
            String customer = txtCustomer.getText();
            String product = (String) cmbProduct.getSelectedItem();
            double price = Double.parseDouble(txtPrice.getText());

            ProductCalculations calc = new ProductCalculations(price);
            double vat = calc.GetVat();
            double commission = calc.GetCommission();
            double total = calc.GetTotal();

            String message = "Customer Name: " + customer +
                    "\nProduct: " + product +
                    "\nVAT: R " + vat +
                    "\nCOMMISSION: R " + commission +
                    "\nTOTAL: R " + total;

            JOptionPane.showMessageDialog(this, message);

            
            try (FileWriter writer = new FileWriter("data.txt", true)) {
                writer.write("*********************************************\n");
                writer.write("DATA LOG\n");
                writer.write("*********************************************\n");
                writer.write("Customer Name: " + customer + "\n");
                writer.write("Product: " + product + "\n");
                writer.write("VAT: R " + vat + "\n");
                writer.write("COMMISSION: R " + commission + "\n");
                writer.write("TOTAL: R " + total + "\n");
                writer.write("*********************************************\n");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}
