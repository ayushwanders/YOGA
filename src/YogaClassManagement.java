import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class YogaClassManagement extends JFrame implements ActionListener {
    // Declare labels
    JLabel headingLabel, nameLabel, classLabel, hoursLabel, focusLabel, teacherLabel, genderLabel, paymentLabel;
    // Declare text field
    JTextField nameField;
    // Declare checkboxes for class mode, hours, focus, teacher preference, gender preference, and payment
    JCheckBox onlineCheckbox, offlineCheckbox, hour1Checkbox, hour2Checkbox, hour3Checkbox, hour4Checkbox;
    JCheckBox meditationCheckbox, yogaCheckbox, asthangaCheckbox, hathaCheckbox, maleCheckbox, femaleCheckbox;
    JCheckBox rs2000Checkbox, rs3000Checkbox, rs4000Checkbox;
    // Declare buttons
    JButton addButton, surveyButton, feedbackButton;
    // Declare text area for display
    static Connection connection;
    String name,username;
    public YogaClassManagement(Connection con,String name,String username) {
        connection = con;
        // Set title
        this.name = name;
        this.username =username;
        setTitle("Yoga Class Management");

        // Initialize components
        headingLabel = new JLabel("Yoga Class Management");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel = new JLabel("Name: ");
        nameField = new JTextField(name);
        nameField.setEditable(false);
        

        classLabel = new JLabel("How would you like to take class? ");
        onlineCheckbox = new JCheckBox("Online");
        offlineCheckbox = new JCheckBox("Offline");

        hoursLabel = new JLabel("How many hours you want to take classes?");
        hour1Checkbox = new JCheckBox("1 hour");
        hour2Checkbox = new JCheckBox("2 hours");
        hour3Checkbox = new JCheckBox("3 hours");
        hour4Checkbox = new JCheckBox("4 hours");

        focusLabel = new JLabel("You want to focus on?");
        meditationCheckbox = new JCheckBox("Meditation");
        yogaCheckbox = new JCheckBox("Yoga");

        teacherLabel = new JLabel("Which teacher will you prefer?");
        asthangaCheckbox = new JCheckBox("Asthanga");
        hathaCheckbox = new JCheckBox("Hatha");

        genderLabel = new JLabel("Gender preference:");
        maleCheckbox = new JCheckBox("Male");
        femaleCheckbox = new JCheckBox("Female");

        paymentLabel = new JLabel("How much can you pay?");
        rs2000Checkbox = new JCheckBox("Rs 2000");
        rs3000Checkbox = new JCheckBox("Rs 3000");
        rs4000Checkbox = new JCheckBox("Rs 4000");

        addButton = new JButton("Add");
        surveyButton = new JButton("Survey");
        feedbackButton = new JButton("Feedback");


        // Set layout to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components to the frame using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(headingLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(classLabel, gbc);

        gbc.gridx = 1;
        add(onlineCheckbox, gbc);

        gbc.gridy = 3;
        add(offlineCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(hoursLabel, gbc);

        gbc.gridx = 1;
        add(hour1Checkbox, gbc);

        gbc.gridy = 5;
        add(hour2Checkbox, gbc);

        gbc.gridy = 6;
        add(hour3Checkbox, gbc);

        gbc.gridy = 7;
        add(hour4Checkbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(focusLabel, gbc);

        gbc.gridx = 1;
        add(meditationCheckbox, gbc);

        gbc.gridy = 9;
        add(yogaCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(teacherLabel, gbc);

        gbc.gridx = 1;
        add(asthangaCheckbox, gbc);

        gbc.gridy = 11;
        add(hathaCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        add(genderLabel, gbc);

        gbc.gridx = 1;
        add(maleCheckbox, gbc);

        gbc.gridy = 13;
        add(femaleCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        add(paymentLabel, gbc);

        gbc.gridx = 1;
        add(rs2000Checkbox, gbc);

        gbc.gridy = 15;
        add(rs3000Checkbox, gbc);

        gbc.gridy = 16;
        add(rs4000Checkbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 17;
        add(addButton, gbc);

        gbc.gridx = 1;
        add(surveyButton, gbc);

        gbc.gridx = 2; // Placed feedbackButton next to the displayButton
        add(feedbackButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 3; // Adjusted grid width to span across all columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(scrollPane, gbc);

        // Set default close operation and visibility
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Add action listeners
        addButton.addActionListener(this);
        surveyButton.addActionListener(this);
        feedbackButton.addActionListener(this); // Added action listener for feedback button
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Check if the name field is empty
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.");
                return;
            }

            // Check if any class mode checkbox is selected
            if (!onlineCheckbox.isSelected() && !offlineCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one class mode.");
                return;
            }

            // Check if any hour checkbox is selected
            if (!hour1Checkbox.isSelected() && !hour2Checkbox.isSelected() &&
                !hour3Checkbox.isSelected() && !hour4Checkbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one hour option.");
                return;
            }

            // Check if any focus checkbox is selected
            if (!meditationCheckbox.isSelected() && !yogaCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one focus area.");
                return;
            }

            // Check if any teacher preference checkbox is selected
            if (!asthangaCheckbox.isSelected() && !hathaCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one teacher preference.");
                return;
            }

            // Check if any gender preference checkbox is selected
            if (!maleCheckbox.isSelected() && !femaleCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one gender preference.");
                return;
            }

            // Check if any payment checkbox is selected
            if (!rs2000Checkbox.isSelected() && !rs3000Checkbox.isSelected() &&
                !rs4000Checkbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select at least one payment option.");
                return;
            }

            // Save data to database
            savedatatoDB();
        } else if (e.getSource() == surveyButton) {
            // Display button functionality
        	YogaSurveyForm yogaSurveyForm = new YogaSurveyForm(connection,name,username);
        	yogaSurveyForm.setVisible(true);
            dispose();
        	
        } else if (e.getSource() == feedbackButton) { // Feedback button functionality
            // Show a dialog box to gather feedback
            YogaFeedbackForm YogaFeedback = new YogaFeedbackForm(connection,name,username);
            YogaFeedback.setVisible(true);
            dispose();
        }
    }

    private void savedatatoDB() {
        try {
            String query = "INSERT INTO yogadetail (email,name, class_mode, class_hours, focus, teacher_preference, gender_preference, payment) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            // Get the selected class modes
            StringBuilder classModes = new StringBuilder();
            if (onlineCheckbox.isSelected()) classModes.append("Online ");
            if (offlineCheckbox.isSelected()) classModes.append("Offline ");

            // Get the selected hours
            StringBuilder classHours = new StringBuilder();
            if (hour1Checkbox.isSelected()) classHours.append("1 hour ");
            if (hour2Checkbox.isSelected()) classHours.append("2 hours ");
            if (hour3Checkbox.isSelected()) classHours.append("3 hours ");
            if (hour4Checkbox.isSelected()) classHours.append("4 hours ");

            // Get the selected focus areas
            StringBuilder focusAreas = new StringBuilder();
            if (meditationCheckbox.isSelected()) focusAreas.append("Meditation ");
            if (yogaCheckbox.isSelected()) focusAreas.append("Yoga ");

            // Get the selected teacher preferences
            StringBuilder teacherPreferences = new StringBuilder();
            if (asthangaCheckbox.isSelected()) teacherPreferences.append("Asthanga ");
            if (hathaCheckbox.isSelected()) teacherPreferences.append("Hatha ");

            // Get the selected gender preferences
            StringBuilder genderPreferences = new StringBuilder();
            if (maleCheckbox.isSelected()) genderPreferences.append("Male ");
            if (femaleCheckbox.isSelected()) genderPreferences.append("Female ");

            // Get the selected payment options
            int paymentOptions = 0;
            if (rs2000Checkbox.isSelected()) paymentOptions=2000;
            if (rs3000Checkbox.isSelected())paymentOptions=3000;
            if (rs4000Checkbox.isSelected()) paymentOptions=4000;

            // Set the parameters in the PreparedStatement
            statement.setString(1,username);
            statement.setString(2, nameField.getText());
            statement.setString(3, classModes.toString().trim());
            statement.setString(4, classHours.toString().trim());
            statement.setString(5, focusAreas.toString().trim());
            statement.setString(6, teacherPreferences.toString().trim());
            statement.setString(7, genderPreferences.toString().trim());
            statement.setInt(8, paymentOptions);

            // Execute the statement
            statement.executeUpdate();
            statement.close();

            // Notify the user
            JOptionPane.showMessageDialog(this, "Data saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}