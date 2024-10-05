import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class YogaFeedbackForm extends JFrame {
    private JLabel titleLabel;
    private JLabel likeLabel;
    private JCheckBox likeCheckBox1;
    private JCheckBox likeCheckBox2;
    private JCheckBox likeCheckBox3;
    private JCheckBox likeCheckBox4;
    private JCheckBox likeCheckBox5;
    private JLabel rateLabel;
    private JRadioButton verySatisfiedRadioButton;
    private JRadioButton satisfiedRadioButton;
    private JRadioButton unsatisfiedRadioButton;
    private JLabel teacherKnowledgeLabel;
    private JRadioButton teacherKnowledgeYesRadioButton;
    private JRadioButton teacherKnowledgeNoRadioButton;
    private JButton submitButton;
    static Connection connection;
    private String name;
	private static String username;
    public YogaFeedbackForm(Connection connection, String name, String username) {
    	this.connection=connection;
    	this.name =name;
    	this.username= username;
    	
    	setTitle("Yoga Class Feedback Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(12, 1)); // Increased rows for better spacing

        // Title Label
        titleLabel = new JLabel("Yoga Feedback Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Setting font and style
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for left alignment
        titlePanel.add(titleLabel);
        add(titlePanel);

        // Like Label
        likeLabel = new JLabel("What are the things you liked most about the class?");
        add(likeLabel);

        // Checkboxes for likes
        likeCheckBox1 = new JCheckBox("Breathing techniques");
        likeCheckBox2 = new JCheckBox("Postures and stretches");
        likeCheckBox3 = new JCheckBox("Relaxation techniques");
        likeCheckBox4 = new JCheckBox("Meditation practices");
        likeCheckBox5 = new JCheckBox("Instructor's guidance");
        JPanel likePanel = new JPanel(new GridLayout(5, 1));
        likePanel.add(likeCheckBox1);
        likePanel.add(likeCheckBox2);
        likePanel.add(likeCheckBox3);
        likePanel.add(likeCheckBox4);
        likePanel.add(likeCheckBox5);
        add(likePanel);

        // Rate Label
        rateLabel = new JLabel("Please rate your overall satisfaction with the class:");
        add(rateLabel);

        // Radio buttons for satisfaction
        verySatisfiedRadioButton = new JRadioButton("Very Satisfied");
        satisfiedRadioButton = new JRadioButton("Satisfied");
        unsatisfiedRadioButton = new JRadioButton("Unsatisfied");
        ButtonGroup satisfactionGroup = new ButtonGroup();
        satisfactionGroup.add(verySatisfiedRadioButton);
        satisfactionGroup.add(satisfiedRadioButton);
        satisfactionGroup.add(unsatisfiedRadioButton);
        JPanel satisfactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        satisfactionPanel.add(verySatisfiedRadioButton);
        satisfactionPanel.add(satisfiedRadioButton);
        satisfactionPanel.add(unsatisfiedRadioButton);
        add(satisfactionPanel);

        // Teacher Knowledge Label
        teacherKnowledgeLabel = new JLabel("Was the teacher knowledgeable and helpful?");
        add(teacherKnowledgeLabel);

        // Radio buttons for teacher knowledge
        teacherKnowledgeYesRadioButton = new JRadioButton("Yes");
        teacherKnowledgeNoRadioButton = new JRadioButton("No");
        ButtonGroup teacherKnowledgeGroup = new ButtonGroup();
        teacherKnowledgeGroup.add(teacherKnowledgeYesRadioButton);
        teacherKnowledgeGroup.add(teacherKnowledgeNoRadioButton);
        JPanel teacherKnowledgePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        teacherKnowledgePanel.add(teacherKnowledgeYesRadioButton);
        teacherKnowledgePanel.add(teacherKnowledgeNoRadioButton);
        add(teacherKnowledgePanel);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        submitPanel.add(submitButton);
        add(submitPanel);

        pack();
        setVisible(true);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get values from checkboxes
            boolean breathingTechniques = likeCheckBox1.isSelected();
            boolean posturesStretches = likeCheckBox2.isSelected();
            boolean relaxationTechniques = likeCheckBox3.isSelected();
            boolean meditationPractices = likeCheckBox4.isSelected();
            boolean instructorGuidance = likeCheckBox5.isSelected();

            // Get value from radio buttons
            String overallSatisfaction = null;
            if (verySatisfiedRadioButton.isSelected()) {
                overallSatisfaction = "Very Satisfied";
            } else if (satisfiedRadioButton.isSelected()) {
                overallSatisfaction = "Satisfied";
            } else if (unsatisfiedRadioButton.isSelected()) {
                overallSatisfaction = "Unsatisfied";
            }

            // Get value from teacher knowledge radio buttons
            boolean teacherKnowledge = teacherKnowledgeYesRadioButton.isSelected();

            // Save the response to the database
            saveSurveyResponse(breathingTechniques, posturesStretches, relaxationTechniques, meditationPractices, instructorGuidance, overallSatisfaction, teacherKnowledge);
        }
    }

    public  void saveSurveyResponse(
            boolean breathingTechniques,
            boolean posturesStretches,
            boolean relaxationTechniques,
            boolean meditationPractices,
            boolean instructorGuidance,
            String overallSatisfaction,
            boolean teacherKnowledge) {

        String query = "INSERT INTO survey_responses (email,breathing_techniques, postures_stretches, relaxation_techniques, meditation_practices, instructor_guidance, overall_satisfaction, teacher_knowledge) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

        try {
        	PreparedStatement pstmt = connection.prepareStatement(query);
        	pstmt.setString(1,username);
            pstmt.setBoolean(2, breathingTechniques);
            pstmt.setBoolean(3, posturesStretches);
            pstmt.setBoolean(4, relaxationTechniques);
            pstmt.setBoolean(5, meditationPractices);
            pstmt.setBoolean(6, instructorGuidance);
            pstmt.setString(7, overallSatisfaction);
            pstmt.setBoolean(8, teacherKnowledge);

            pstmt.executeUpdate();
            System.out.println("Survey response saved successfully!");
            JOptionPane.showMessageDialog(this, "data daved successfully");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e);
        }
    }

}