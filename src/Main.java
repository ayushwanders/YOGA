import javax.swing.*;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.xdevapi.Statement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JFrame implements ActionListener {
    private JLabel nameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    private JTextField nameTextField, emailTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JCheckBox agreeCheckBox;
    private JButton signupButton,loginButton;
    static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/customer"; // Replace with your database URL and name
    private static final String USERNAME = "root"; // Replace with your database username
    private static final String PASSWORD = "R@1234sql#";
    public Main() {
        initdb();
        setTitle("Signup Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add heading label
        JLabel headingLabel = new JLabel("Yoga Signup Form");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        mainPanel.add(headingLabel, gbc);

        // Add components to the main panel
        addComponentsToPanel(mainPanel, gbc);

        // Add the main panel to the frame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }

    private void addComponentsToPanel(JPanel panel, GridBagConstraints gbc) {
        // Initialize labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        // Initialize text fields with smaller size
        nameTextField = new JTextField(15);
        emailTextField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);

        // Initialize checkbox
        agreeCheckBox = new JCheckBox("I agree to the terms and conditions");

        // Initialize buttons
        signupButton = new JButton("Signup");
        signupButton.addActionListener(this);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		YogaLoginForm YogaLogin = new YogaLoginForm(connection);
                YogaLogin.setVisible(true);
                dispose();
        	}
        });

        // Add components to the panel with proper alignment
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset grid width
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(agreeCheckBox, gbc);

        gbc.gridy = 6;
        gbc.gridwidth = 1; // Reset grid width for buttons
        gbc.gridx = 0;
        panel.add(signupButton, gbc);

        gbc.gridx = 1;
        panel.add(loginButton, gbc);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String name = nameTextField.getText().trim();
            String email = emailTextField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

            // Check if any of the fields are empty
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            // Check if agree checkbox is selected
            if (!agreeCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please agree to the terms and conditions.");
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
                return;
            }

            // You can add further processing here, such as saving the data to a database.
            JOptionPane.showMessageDialog(this, "Signup Successful!\nName: " + name + "\nEmail: " + email);
           
            saveCustomer(email,name,password);
            YogaLoginForm YogaLogin = new YogaLoginForm(connection);
            YogaLogin.setVisible(true);
            dispose();
        }
    }

    public static void initdb() {
        
        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully!");

            // Check if the customer table exists
            createStudentTableIfNotExists();

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }


    private static void createStudentTableIfNotExists() {
	    try {
	        // Check if the table exists
	        String checkTableQuery = "SELECT 1 FROM customer LIMIT 1";
	        connection.prepareStatement(checkTableQuery).executeQuery();

	        // If the table exists, return
	        return;
	    } catch (SQLException e) {
	        // Table does not exist, create it
	        try {
	            String createTableQuery = "CREATE TABLE customer (" +
	                    "email VARCHAR(50) PRIMARY KEY," + // Making sap_id the primary key
	                    "name VARCHAR(255)," +
	                    "password VARCHAR(20)" +
	                    ")";
	            connection.prepareStatement(createTableQuery).executeUpdate();
	            System.out.println("Student table created successfully.");
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
    public static void saveCustomer(String email, String name, String password) {
        String insertCustomerSQL = "INSERT INTO customer (email, name, password) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerSQL)) {

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer saved successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// Method to validate email format
    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void main(String[] args) {
        // Run the SignupForm
        SwingUtilities.invokeLater(() -> new Main());
    }

}