package com.infoman.controller;

import com.infoman.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.infoman.databaseConnection;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MainController {

    // FXML fields from your FXML file
    @FXML
    private TextField fname;

    @FXML
    private TextField mname;

    @FXML
    private TextField lname;

    @FXML
    private TextField number;

    @FXML
    private TextField email;

    @FXML
    private RadioButton genderF;

    @FXML
    private RadioButton genderM;

    @FXML
    private RadioButton genderLGBT;

    private ToggleGroup genderGroup;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> fnameCol;

    @FXML
    private TableColumn<Student, String> mnameCol;

    @FXML
    private TableColumn<Student, String> lnameCol;

    @FXML
    private TableColumn<Student, String> numCol;

    @FXML
    private TableColumn<Student, String> emailCol;

    @FXML
    private TableColumn<Student, String> genderCol;

    private databaseConnection db = new databaseConnection();
    private ObservableList<Student> newstudents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize database connection
        connection = new databaseConnection();

        // Set up table columns
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        mnameCol.setCellValueFactory(new PropertyValueFactory<>("mname"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        numCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Initialize ToggleGroup for gender
        genderGroup = new ToggleGroup();
        genderM.setToggleGroup(genderGroup);
        genderF.setToggleGroup(genderGroup);
        genderLGBT.setToggleGroup(genderGroup);
        // Load data into the table
        try {
            loadStudents();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void loadStudents() throws SQLException {
        String sql = "SELECT * FROM newstudents";
        Statement stmt = db.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Student s = new Student(
                    rs.getString("fname"),
                    rs.getString("mname"),
                    rs.getString("lname"),
                    rs.getString("number"),
                    rs.getString("email"),
                    rs.getString("gender")
            );
            newstudents.add(s);
        }
        table.setItems(newstudents);
    }

    private databaseConnection connection;

    private String getSelectedGender() {
        if (genderM.isSelected()) {
            return "Male";
        } else if (genderF.isSelected()) {
            return "Female";
        } else if (genderLGBT.isSelected()) {
            return "Others";
        } else {
            return "Unspecified";
        }
    }

    @FXML
    private void save() {
        try {
            String sql = "INSERT INTO newStudents(fname, mname, lname, number, email, gender) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = connection.getConnection().prepareStatement(sql);
            stmt.setString(1, fname.getText());
            stmt.setString(2, mname.getText());
            stmt.setString(3, lname.getText());
            stmt.setString(4, number.getText());
            stmt.setString(5, email.getText());
            stmt.setString(6, getSelectedGender());
            stmt.execute();

            // Create a new Student object and add it to the ObservableList
            Student newStudent = new Student(fname.getText(), mname.getText(), lname.getText(), number.getText(), email.getText(), getSelectedGender());
            newstudents.add(newStudent);

            // Refresh the table view
            table.setItems(newstudents);

            // Show success message
            JOptionPane.showMessageDialog(null, "Data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // Show error message
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    @FXML
    private void delete() {
        // Get the selected student
        Student selectedStudent = table.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            try {
                // Prepare the SQL delete statement
                String sql = "DELETE FROM newstudents WHERE number = ?";
                PreparedStatement stmt = connection.connection.prepareStatement(sql);
                stmt.setString(1, selectedStudent.getNumber());

                // Execute the delete operation
                stmt.executeUpdate();

                // Remove the selected student from the observable list
                newstudents.remove(selectedStudent);

                // Optional: Show success message
                JOptionPane.showMessageDialog(null, "Student deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                // Show error message
                JOptionPane.showMessageDialog(null, "Error deleting student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            // Optional: Show warning message if no student is selected
            JOptionPane.showMessageDialog(null, "Please select a student to delete", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    private void edit() {
        // Get the selected student
        Student selectedStudent = table.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Populate the text fields with the selected student's information
            fname.setText(selectedStudent.getFname());
            mname.setText(selectedStudent.getMname());
            lname.setText(selectedStudent.getLname());
            number.setText(selectedStudent.getNumber());
            email.setText(selectedStudent.getEmail());

            // Optionally, set the gender radio button
            switch (selectedStudent.getGender()) {
                case "Male":
                    genderM.setSelected(true);
                    break;
                case "Female":
                    genderF.setSelected(true);
                    break;
                case "Others":
                    genderLGBT.setSelected(true);
                    break;
            }
        } else {
            // Show warning message if no student is selected
            JOptionPane.showMessageDialog(null, "Please select a student to edit", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    @FXML
    private void saveEditedStudent() {
        Student selectedStudent = table.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            try {
                String sql = "UPDATE newstudents SET fname = ?, mname = ?, lname = ?, number = ?, email = ?, gender = ? WHERE number = ?";
                PreparedStatement stmt = connection.connection.prepareStatement(sql);
                stmt.setString(1, fname.getText());
                stmt.setString(2, mname.getText());
                stmt.setString(3, lname.getText());
                stmt.setString(4, number.getText());
                stmt.setString(5, email.getText());
                stmt.setString(6, getSelectedGender());
                stmt.setString(7, selectedStudent.getNumber()); // Use the original number as identifier

                stmt.executeUpdate();

                // Update the observable list with the new information
                selectedStudent.setFname(fname.getText());
                selectedStudent.setMname(mname.getText());
                selectedStudent.setLname(lname.getText());
                selectedStudent.setNumber(number.getText());
                selectedStudent.setEmail(email.getText());
                selectedStudent.setGender(getSelectedGender());

                // Refresh the table
                table.refresh();

                // Show success message
                JOptionPane.showMessageDialog(null, "Student updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                // Show error message
                JOptionPane.showMessageDialog(null, "Error updating student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
