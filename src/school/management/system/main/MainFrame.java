/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package school.management.system.main;

import school.management.system.view.*;
import school.management.system.model.*;
import school.management.system.controller.*;

/**
 *
 * @author Mohammed Hidarah
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;



    

    public class MainFrame extends JFrame{
        
        
         
        private StudentController stController;
        private TeacherController teacherController;
        private CourseController courseController;
        private GradeController gradeController;
        
        
        private StudentView stView;
        private TeacherView teacherView;
        private CourseView courseView;
        private GradeView gradeView;
        
        private StudentDAO stModel;
        private TeacherDAO teacherModel;
        private CourseDAO courseModel;
        private GradeDAO gradeModel;
         
         
        
                 
        
        public MainFrame() throws IOException{
     
            ImageIcon studentsIcon = new ImageIcon("/resources/images/studentsicon.jpg");
            Image scaledIStudensImage = studentsIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            
            ImageIcon teachersIcon = new ImageIcon("/resources/images/teachersicon.jpg");
            Image scaledTeachersImage = teachersIcon.getImage().getScaledInstance(80,80, Image.SCALE_SMOOTH);
            
            ImageIcon couresesIcon = new ImageIcon("/resources/images/coursesicon.jpg");
            Image scaledCoursesImage = couresesIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            
            ImageIcon gradessIcon = new ImageIcon("/resources/images/coursesicon.jpg");
            Image scaledGradesImage = gradessIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            
            JPanel buttonsPanel = new JPanel();
            JButton studentFrameButton = new RoundedButton(new ImageIcon(scaledIStudensImage),10);
            JButton courseFrameButton = new RoundedButton(new ImageIcon(scaledTeachersImage),10);
            JButton teacherFrameButton = new RoundedButton(new ImageIcon(scaledCoursesImage),10);
            JButton gradeFrameButton = new RoundedButton(new ImageIcon(scaledGradesImage),10);
            
            JLabel studentsLabel = new JLabel("Students");
            studentsLabel.setFont(new Font("New Roman", 20, 20));
            JLabel teachersLabel = new JLabel("Teachers");
            teachersLabel.setFont(new Font("New Roman", 20, 20));
            JLabel coursesLabel = new JLabel("courses");
            coursesLabel.setFont(new Font("New Roman", 20, 20));
            JLabel gradesLabel = new JLabel("Grades");
            gradesLabel.setFont(new Font("New Roman", 20, 20));
              
            JPanel studentBtnPanel = new JPanel();
            studentBtnPanel.setLayout(new BorderLayout());
            studentBtnPanel.add(studentFrameButton, BorderLayout.NORTH);
            studentBtnPanel.add(studentsLabel, BorderLayout.SOUTH);
            
            JPanel teacherBtnPanel = new JPanel();
            teacherBtnPanel.setLayout(new BorderLayout());
            teacherBtnPanel.add(teacherFrameButton, BorderLayout.NORTH);
            teacherBtnPanel.add(teachersLabel, BorderLayout.SOUTH);
            
            
            JPanel coursesBtnPanel = new JPanel();
            coursesBtnPanel.setLayout(new BorderLayout());
            coursesBtnPanel.add(courseFrameButton, BorderLayout.NORTH);
            coursesBtnPanel.add(coursesLabel, BorderLayout.SOUTH);
            
            JPanel gradesBtnPanel = new JPanel();
            gradesBtnPanel.setLayout(new BorderLayout());
            gradesBtnPanel.add(gradeFrameButton, BorderLayout.NORTH);
            gradesBtnPanel.add(gradesLabel, BorderLayout.SOUTH);
            
  
            studentFrameButton.addActionListener((e) -> {
                try {
                        stView = new StudentView(this);
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                stModel = new StudentDAO();
                stController = new StudentController(stModel, stView);
                setVisible(false);
            });
             courseFrameButton.addActionListener((e) -> {
                
                    courseView = new CourseView(this);
                try {
                    courseModel = new CourseDAO();
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                    courseController = new CourseController(courseView, courseModel);
                    setVisible(false);
               
            });
            
             teacherFrameButton.addActionListener((e) -> {
                try {
                    teacherView = new TeacherView(this);
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                teacherModel = new TeacherDAO();
                teacherController = new TeacherController(teacherModel, teacherView);
                
                 setVisible(false);
            });
             
             gradeFrameButton.addActionListener((e) -> {
                gradeView = new GradeView( this);
                try {
                    gradeModel = new GradeDAO();
                } catch (SQLException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                gradeController = new GradeController(gradeView, gradeModel);
                
        });
            
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
            
            buttonsPanel.add(studentBtnPanel);
            buttonsPanel.add(teacherBtnPanel);
            buttonsPanel.add(coursesBtnPanel);
            buttonsPanel.add(gradesBtnPanel);
            
            
//            frame.setLocationRelativeTo(null);
           setSize(600,500);
           setLayout(new GridBagLayout());
           GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0; // Column
            gbc.gridy = 0; // Row
            gbc.weightx = 1; // Give extra space to the column
            gbc.weighty = 1; // Give extra space to the row
            gbc.anchor = GridBagConstraints.CENTER; // Center the panel

            // Add panel to frame
            add(buttonsPanel, gbc);                        
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
            setSize(600,400);
            setLocationRelativeTo(null);
        }
        
        
        public static void main(String[] args) throws IOException{
            
            MainFrame mainFrame = new MainFrame(); 
        }        
    }

class RoundedButton extends JButton {
    private int radius;
    private ImageIcon icon;
    

   public RoundedButton(ImageIcon icon, int radius) {
        super(); // Call the JButton constructor
        this.icon = icon;
        this.radius = radius;
        setContentAreaFilled(false); // Make the button transparent
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false); // Remove border
        setOpaque(false); // Make button background transparent
        setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight())); // Set button size
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Set background color (if desired)
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        // Draw the icon in the center
        int iconX = (getWidth() - icon.getIconWidth()) / 2;
        int iconY = (getHeight() - icon.getIconHeight()) / 2;
        icon.paintIcon(this, g, iconX, iconY);
         super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}


