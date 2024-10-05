import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YogaLoginForm extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, headingLabel; // Added headingLabel
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    static Connection connectionDb;
    public YogaLoginForm(Connection connection) {
    	connectionDb = connection;
        setTitle("Yoga Login Form");
        setSize(600, 600); // Adjusted height for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel with GridBagLayout for better component alignment
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add insets for spacing

        // Initialize heading label
        headingLabel = new JLabel("Yoga Login Form");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center align the heading
        mainPanel.add(headingLabel, gbc);

        // Initialize labels
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        // Initialize text fields with reduced preferred size
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // Initialize login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        // Add components to the main panel with proper alignment using GridBagConstraints
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(loginButton, gbc);

        // Add main panel to the frame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }


	@Override
    public void actionPerformed(ActionEvent e) {
        // Handle login button click event
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            // Dummy authentication - check if username and password are not empty
            if (!username.isEmpty() && !password.isEmpty()&& credentialCheck(username,password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                String name =getNameAndEmail(username);
                YogaRegistrationForm yogaRegistration = new YogaRegistrationForm(connectionDb,name ,username);
                yogaRegistration.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
            }
        }
    }

	private String getNameAndEmail(String username) {
		String name = null;
		String query = "SELECT * FROM customer WHERE email = ? ";
		try{
        	PreparedStatement statement = connectionDb.prepareStatement(query);
            statement.setString(1, username);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
            	name = resultSet.getString("name");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return name;
        
		
	}


	private boolean credentialCheck(String username, String password) {
		String query = "SELECT * FROM customer WHERE email = ? AND password = ?";
        
        try{
        	PreparedStatement statement = connectionDb.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // returns true if a record is found
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
	}
}