import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class YogaSurveyForm extends JFrame implements ActionListener {
    // JLabels
    JLabel headingLabel, nameLabel, cityLabel, ageLabel, weightLabel, yogaLabel, timeSpentLabel, alcoholLabel, sleepLabel, wakeUpLabel, sleepTimeLabel, dietLabel, medicalProblemLabel, yogaSessionLabel;

    // TextFields
    static JTextField nameField, cityField, ageField, weightField;

    // RadioButtons and ButtonGroups
    JRadioButton doYogaYesRadioButton, doYogaNoRadioButton;
    static ButtonGroup doYogaGroup;
    JRadioButton timeSpent1RadioButton, timeSpent2RadioButton;
    static ButtonGroup timeSpentGroup;
    JRadioButton alcoholYesRadioButton, alcoholNoRadioButton;
    static ButtonGroup alcoholGroup;
    JRadioButton sleep8RadioButton, sleep6RadioButton, sleep5RadioButton;
    static ButtonGroup sleepGroup;
    JRadioButton wakeUp6RadioButton, wakeUp7RadioButton, wakeUp8RadioButton;
    static ButtonGroup wakeUpGroup;
    JRadioButton sleepTime10RadioButton, sleepTime1030RadioButton, sleepTime11RadioButton;
    static ButtonGroup sleepTimeGroup;
    JRadioButton dietYesRadioButton, dietNoRadioButton;
    static ButtonGroup dietGroup;
    JRadioButton medicalProblemYesRadioButton, medicalProblemNoRadioButton;
    static ButtonGroup medicalProblemGroup;
    JRadioButton yogaSessionYesRadioButton, yogaSessionNoRadioButton;
    static ButtonGroup yogaSessionGroup;

    // Button
    JButton submitButton;

    // Database connection
    static Connection con;

    // User information
    String name, username;

    public YogaSurveyForm(Connection connection, String name, String username) {
        // Initialize user information and database connection
        this.con = connection;
        this.name = name;
        this.username = username;

        // Setting up the JFrame
        setTitle("Yoga Survey Form");
        setSize(700, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Use null layout for precise positioning

        // Initializing JLabels
        headingLabel = new JLabel("Yoga Survey Form");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setBounds(20, 10, 200, 30);

        nameLabel = new JLabel("Name:");
        cityLabel = new JLabel("City:");
        ageLabel = new JLabel("Age:");
        weightLabel = new JLabel("Weight:");
        yogaLabel = new JLabel("Do you do yoga?");
        timeSpentLabel = new JLabel("How much time do you spend in a day for yoga/exercise/meditation?");
        alcoholLabel = new JLabel("Do you consume alcohol/smoke?");
        sleepLabel = new JLabel("How many hours do you sleep?");
        wakeUpLabel = new JLabel("At what time do you wake up?");
        sleepTimeLabel = new JLabel("At what time do you sleep?");
        dietLabel = new JLabel("Do you have fruits and vegetables in your diet?");
        medicalProblemLabel = new JLabel("Do you have any medical problem?");
        yogaSessionLabel = new JLabel("Do you wish to have yoga session and guidance?");

        // Setting bounds for JLabels
        nameLabel.setBounds(20, 50, 100, 30);
        cityLabel.setBounds(20, 100, 100, 30);
        ageLabel.setBounds(20, 150, 100, 30);
        weightLabel.setBounds(20, 200, 100, 30);
        yogaLabel.setBounds(20, 250, 200, 30);
        timeSpentLabel.setBounds(20, 300, 400, 30);
        alcoholLabel.setBounds(20, 350, 250, 30);
        sleepLabel.setBounds(20, 400, 200, 30);
        wakeUpLabel.setBounds(20, 450, 200, 30);
        sleepTimeLabel.setBounds(20, 500, 200, 30);
        dietLabel.setBounds(20, 550, 300, 30);
        medicalProblemLabel.setBounds(20, 600, 300, 30);
        yogaSessionLabel.setBounds(20, 650, 400, 30);

        // Initializing TextFields
        nameField = new JTextField();
        cityField = new JTextField();
        ageField = new JTextField();
        weightField = new JTextField();

        // Setting bounds for TextFields
        nameField.setBounds(130, 50, 200, 30);
        cityField.setBounds(130, 100, 200, 30);
        ageField.setBounds(130, 150, 200, 30);
        weightField.setBounds(130, 200, 200, 30);

        // Initializing RadioButtons and ButtonGroups
        doYogaYesRadioButton = new JRadioButton("Yes");
        doYogaNoRadioButton = new JRadioButton("No");
        doYogaGroup = new ButtonGroup();
        doYogaGroup.add(doYogaYesRadioButton);
        doYogaGroup.add(doYogaNoRadioButton);

        timeSpent1RadioButton = new JRadioButton("1 hour");
        timeSpent2RadioButton = new JRadioButton("2 hours");
        timeSpentGroup = new ButtonGroup();
        timeSpentGroup.add(timeSpent1RadioButton);
        timeSpentGroup.add(timeSpent2RadioButton);

        alcoholYesRadioButton = new JRadioButton("Yes");
        alcoholNoRadioButton = new JRadioButton("No");
        alcoholGroup = new ButtonGroup();
        alcoholGroup.add(alcoholYesRadioButton);
        alcoholGroup.add(alcoholNoRadioButton);

        sleep8RadioButton = new JRadioButton("8 hours");
        sleep6RadioButton = new JRadioButton("6 hours");
        sleep5RadioButton = new JRadioButton("5 hours");
        sleepGroup = new ButtonGroup();
        sleepGroup.add(sleep8RadioButton);
        sleepGroup.add(sleep6RadioButton);
        sleepGroup.add(sleep5RadioButton);

        wakeUp6RadioButton = new JRadioButton("6 AM");
        wakeUp7RadioButton = new JRadioButton("7 AM");
        wakeUp8RadioButton = new JRadioButton("8 AM");
        wakeUpGroup = new ButtonGroup();
        wakeUpGroup.add(wakeUp6RadioButton);
        wakeUpGroup.add(wakeUp7RadioButton);
        wakeUpGroup.add(wakeUp8RadioButton);

        sleepTime10RadioButton = new JRadioButton("10 PM");
        sleepTime1030RadioButton = new JRadioButton("10:30 PM");
        sleepTime11RadioButton = new JRadioButton("11 PM");
        sleepTimeGroup = new ButtonGroup();
        sleepTimeGroup.add(sleepTime10RadioButton);
        sleepTimeGroup.add(sleepTime1030RadioButton);
        sleepTimeGroup.add(sleepTime11RadioButton);

        dietYesRadioButton = new JRadioButton("Yes");
        dietNoRadioButton = new JRadioButton("No");
        dietGroup = new ButtonGroup();
        dietGroup.add(dietYesRadioButton);
        dietGroup.add(dietNoRadioButton);

        medicalProblemYesRadioButton = new JRadioButton("Yes");
        medicalProblemNoRadioButton = new JRadioButton("No");
        medicalProblemGroup = new ButtonGroup();
        medicalProblemGroup.add(medicalProblemYesRadioButton);
        medicalProblemGroup.add(medicalProblemNoRadioButton);

        yogaSessionYesRadioButton = new JRadioButton("Yes");
        yogaSessionNoRadioButton = new JRadioButton("No");
        yogaSessionGroup = new ButtonGroup();
        yogaSessionGroup.add(yogaSessionYesRadioButton);
        yogaSessionGroup.add(yogaSessionNoRadioButton);

        // Setting bounds for RadioButtons
        doYogaYesRadioButton.setBounds(240, 250, 70, 30);
        doYogaNoRadioButton.setBounds(340, 250, 70, 30);
        timeSpent1RadioButton.setBounds(440, 300, 100, 30);
        timeSpent2RadioButton.setBounds(540, 300, 100, 30);
        alcoholYesRadioButton.setBounds(240, 350, 70, 30);
        alcoholNoRadioButton.setBounds(340, 350, 70, 30);
        sleep8RadioButton.setBounds(240, 400, 70, 30);
        sleep6RadioButton.setBounds(340, 400, 70, 30);
        sleep5RadioButton.setBounds(440, 400, 70, 30);
        wakeUp6RadioButton.setBounds(240, 450, 70, 30);
        wakeUp7RadioButton.setBounds(340, 450, 70, 30);
        wakeUp8RadioButton.setBounds(440, 450, 70, 30);
        sleepTime10RadioButton.setBounds(240, 500, 70, 30);
        sleepTime1030RadioButton.setBounds(340, 500, 100, 30);
        sleepTime11RadioButton.setBounds(440, 500, 70, 30);
        dietYesRadioButton.setBounds(340, 550, 70, 30);
        dietNoRadioButton.setBounds(440, 550, 70, 30);
        medicalProblemYesRadioButton.setBounds(340, 600, 70, 30);
        medicalProblemNoRadioButton.setBounds(440, 600, 70, 30);
        yogaSessionYesRadioButton.setBounds(440, 650, 70, 30);
        yogaSessionNoRadioButton.setBounds(540, 650, 70, 30);

        // Initializing and setting bounds for the submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(20, 700, 80, 30);
        submitButton.addActionListener(this);

        // Adding components to the JFrame
        add(headingLabel);
        add(nameLabel);
        add(cityLabel);
        add(ageLabel);
        add(weightLabel);
        add(yogaLabel);
        add(timeSpentLabel);
        add(alcoholLabel);
        add(sleepLabel);
        add(wakeUpLabel);
        add(sleepTimeLabel);
        add(dietLabel);
        add(medicalProblemLabel);
        add(yogaSessionLabel);
        add(nameField);
        add(cityField);
        add(ageField);
        add(weightField);
        add(doYogaYesRadioButton);
        add(doYogaNoRadioButton);
        add(timeSpent1RadioButton);
        add(timeSpent2RadioButton);
        add(alcoholYesRadioButton);
        add(alcoholNoRadioButton);
        add(sleep8RadioButton);
        add(sleep6RadioButton);
        add(sleep5RadioButton);
        add(wakeUp6RadioButton);
        add(wakeUp7RadioButton);
        add(wakeUp8RadioButton);
        add(sleepTime10RadioButton);
        add(sleepTime1030RadioButton);
        add(sleepTime11RadioButton);
        add(dietYesRadioButton);
        add(dietNoRadioButton);
        add(medicalProblemYesRadioButton);
        add(medicalProblemNoRadioButton);
        add(yogaSessionYesRadioButton);
        add(yogaSessionNoRadioButton);
        add(submitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                // Fetch data from fields
                String name = nameField.getText();
                String city = cityField.getText();
                int age = Integer.parseInt(ageField.getText());
                double weight = Double.parseDouble(weightField.getText());
                String doYoga = doYogaYesRadioButton.isSelected() ? "Yes" : "No";
                String timeSpent = timeSpent1RadioButton.isSelected() ? "1 hour" : "2 hours";
                String alcohol = alcoholYesRadioButton.isSelected() ? "Yes" : "No";
                String sleep = sleep8RadioButton.isSelected() ? "8 hours" : sleep6RadioButton.isSelected() ? "6 hours" : "5 hours";
                String wakeUp = wakeUp6RadioButton.isSelected() ? "6 AM" : wakeUp7RadioButton.isSelected() ? "7 AM" : "8 AM";
                String sleepTime = sleepTime10RadioButton.isSelected() ? "10 PM" : sleepTime1030RadioButton.isSelected() ? "10:30 PM" : "11 PM";
                String diet = dietYesRadioButton.isSelected() ? "Yes" : "No";
                String medicalProblem = medicalProblemYesRadioButton.isSelected() ? "Yes" : "No";
                String yogaSession = yogaSessionYesRadioButton.isSelected() ? "Yes" : "No";

                // Insert data into the database
                String query = "INSERT INTO YogaSurvey (email,name, city, age, weight, do_yoga, exercise_time, consume_alcohol_smoke, sleep_hours, wake_up_time, sleep_time, fruits_vegetables, medical_problem, yoga_session) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, name);
                ps.setString(3, city);
                ps.setInt(4, age);
                ps.setDouble(5, weight);
                ps.setString(6, doYoga);
                ps.setString(7, timeSpent);
                ps.setString(8, alcohol);
                ps.setString(9, sleep);
                ps.setString(10, wakeUp);
                ps.setString(11, sleepTime);
                ps.setString(12, diet);
                ps.setString(13, medicalProblem);
                ps.setString(14, yogaSession);

                // Execute the insert statement
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Survey Submitted Successfully!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values for age and weight.");
            }
        }
    }

    public static void main(String[] args) {
        // Connection object should be passed here
        // new YogaSurveyForm(connection, "John Doe", "john_doe");
    }
}