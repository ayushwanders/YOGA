import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class YogaRegistrationForm extends JFrame implements ActionListener {
    private JLabel nameLabel, emailLabel, phoneLabel, genderLabel, ageLabel, batchLabel, yogaLabel, experienceLabel, healthLabel;
    private JTextField nameField, emailField, phoneField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private JComboBox<String> ageComboBox, batchComboBox, yogaComboBox;
    private JCheckBox beginnerCheckBox, intermediateCheckBox, advancedCheckBox;
    private JTextArea healthTextArea;
    private JButton registerButton;
    static Connection con;
    private String name ,username;
    public YogaRegistrationForm(Connection connectionDb, String name, String username) {
    	this.name = name;
    	this.username = username;
    	con = connectionDb;
        setTitle("Yoga Class Registration Form");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Heading Label
        JLabel headingLabel = new JLabel("Yoga Registration Form", SwingConstants.CENTER); // Centering the text
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Setting font and style
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(headingLabel, gbc);

        // Name
        nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(nameLabel, gbc);

        nameField = new JTextField(name);
        nameField.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        // Email
        emailLabel = new JLabel("Email Address:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField(username);
        emailField.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        // Phone
        phoneLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(phoneField, gbc);

        // Gender
        genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        gbc.gridx = 1;
        mainPanel.add(genderPanel, gbc);

        // Age
        ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(ageLabel, gbc);

        String[] ageOptions = {"Below 18", "18-30", "31-50", "Above 50"};
        ageComboBox = new JComboBox<>(ageOptions);
        gbc.gridx = 1;
        mainPanel.add(ageComboBox, gbc);

        // Batch
        batchLabel = new JLabel("Batch:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(batchLabel, gbc);

        String[] batchOptions = {"Morning: 6:30 to 8:30", "Morning: 8:30 to 10:30", "Evening: 4:30 to 6:30", "Evening: 6:30 to 8:30"};
        batchComboBox = new JComboBox<>(batchOptions);
        gbc.gridx = 1;
        mainPanel.add(batchComboBox, gbc);

        // Yoga Type
        yogaLabel = new JLabel("Yoga you want to register for:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(yogaLabel, gbc);

        String[] yogaOptions = {"Vinyasa", "Hatha", "Ashtanga", "Kundalini", "Bikram", "Power Yoga", "Breathing Techniques", "Meditation", "Sound Meditation", "Pranayama"};
        yogaComboBox = new JComboBox<>(yogaOptions);
        gbc.gridx = 1;
        mainPanel.add(yogaComboBox, gbc);

        // Experience
        experienceLabel = new JLabel("Experience:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(experienceLabel, gbc);

        beginnerCheckBox = new JCheckBox("Beginner");
        intermediateCheckBox = new JCheckBox("Intermediate");
        advancedCheckBox = new JCheckBox("Advanced");
        JPanel experiencePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        experiencePanel.add(beginnerCheckBox);
        experiencePanel.add(intermediateCheckBox);
        experiencePanel.add(advancedCheckBox);
        gbc.gridx = 1;
        mainPanel.add(experiencePanel, gbc);

        // Health Concerns/Allergies
        healthLabel = new JLabel("Health Concerns/Allergies:");
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(healthLabel, gbc);

        healthTextArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(healthTextArea);
        gbc.gridx = 1;
        mainPanel.add(scrollPane, gbc);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            if (!isEmailValid(emailField.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid email address. Please enter a valid email.");
            } else if (!isPhoneValid(phoneField.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid phone number. Please enter a 10-digit phone number.");
            } else if (isAnyTextFieldEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the details in any text field.");
            } else {
            	try {
            		saveToDatabase();
            		JOptionPane.showMessageDialog(this, "Registration Successful!");
                    YogaClassManagement management = new YogaClassManagement(con,name,username);
                    management.setVisible(true);
                    dispose();
            	}
            	catch(Exception e1 ) {
            		JOptionPane.showMessageDialog(this, "Error"+e1);
            	}
                
            }
        }
    }

    private void saveToDatabase() throws SQLException {
    	String query = "insert into yogaregistration (email,name,phone_number,gender,age_group,batch,yoga_type,experience,health_concerns) values(?,?,?,?,?,?,?,?,?)";
    	PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, emailField.getText());
        statement.setString(2, nameField.getText());
        statement.setString(3, phoneField.getText());
        statement.setString(4, maleRadioButton.isSelected() ? "Male":"Female");
        statement.setString(5, (String) ageComboBox.getSelectedItem());
        statement.setString(6, (String) batchComboBox.getSelectedItem());
        statement.setString(7, (String) yogaComboBox.getSelectedItem());
        StringBuilder experience = new StringBuilder();
        if(beginnerCheckBox.isSelected()) experience.append("Beginner");
        if(intermediateCheckBox.isSelected()) experience.append("Intermediate");
        if(advancedCheckBox.isSelected()) experience.append("Advanced");
        statement.setString(8, experience.toString());
        statement.setString(9, healthTextArea.getText());
        statement.executeUpdate();
        statement.close();
		
	}

	private boolean isAnyTextFieldEmpty() {
        // Check if any of the text fields are empty
        return nameField.getText().isEmpty() ||
               emailField.getText().isEmpty() ||
               phoneField.getText().isEmpty() ||
               healthTextArea.getText().isEmpty();
    }

    private boolean isEmailValid(String email) {
        // Regular expression pattern for email validation
        String emailPattern = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    private boolean isPhoneValid(String phone) {
        // Check if phone number has 10 digits
        return phone.matches("\\d{10}");
    }

}