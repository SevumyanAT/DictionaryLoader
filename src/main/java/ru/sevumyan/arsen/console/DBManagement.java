package ru.sevumyan.arsen.console;

import ru.sevumyan.arsen.entity.Employee;
import java.sql.*;

public class DBManagement implements AutoCloseable {
    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String EMPLOYEE = "Select * from employee";
    private static final String DEPARTMENT = "Select * from department";
    private static final String WORKING_HOURS = "select * from working_hours";
    private static final String ABSENCE = "Select * from absence";
    private static final String POST = "Select * from post";
    private static final String PAID_SALARY = "Select * from paid_salary";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private final Connection connection;
    private final Statement statement;

    public DBManagement() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayEmployees() throws SQLException {
        try (ResultSet rs = statement.executeQuery(EMPLOYEE)) {
            while (rs.next()) {
                System.out.print("First Name: " + rs.getString("first_name") + "\t");
                System.out.print(ANSI_BLUE + " Last Name: " + rs.getString("last_name") + "\t");
                System.out.print(ANSI_RESET + " Passport number: " + rs.getInt("passport_no") + "\t");
                System.out.print(ANSI_BLUE + " University education: " + rs.getString("university_education") + "\t");
                System.out.print(ANSI_RESET + " Birth date: " + rs.getDate("birth_date") + "\t");
                System.out.print(ANSI_BLUE + " Bank account: " + rs.getInt("bank_account") + "\t");
                System.out.print(ANSI_RESET + " Post ID: " + rs.getInt("post_id") + "\t");
                System.out.print(ANSI_BLUE + " Department ID: " + rs.getInt("department_id") + "\t\n" + ANSI_RESET);
            }
        }
    }

    public void displayPost() throws SQLException {
        try (ResultSet rs = statement.executeQuery(POST)) {
            while (rs.next()) {

                System.out.print("Post name: " + rs.getString("post_name") + "\t");
                System.out.print(ANSI_BLUE + " Wage: " + rs.getInt("wage") + "\t\n" + ANSI_RESET);
            }
        }
    }

    public void displayDepartment() throws SQLException {
        try (ResultSet rs = statement.executeQuery(DEPARTMENT)) {
            while (rs.next()) {
                System.out.print("Department Location: " + rs.getString("department_location") + "\t\n");
            }
        }
    }

    public void displayWorkingHours() throws SQLException {
        try (ResultSet rs = statement.executeQuery(WORKING_HOURS)) {
            while (rs.next()) {
                System.out.print("Employee ID: " + rs.getString("employee_id") + "\t");
                System.out.println(ANSI_BLUE + "Working hours: " + rs.getInt("working_hours") + "\t\n" + ANSI_RESET);
            }
        }
    }

    public void displayPaidSalary() throws SQLException {
        try (ResultSet rs = statement.executeQuery(PAID_SALARY)) {
            while (rs.next()) {
                System.out.print("Employee ID: " + rs.getString("employee_id") + "\t");
                System.out.println(ANSI_BLUE + "Payment date: " + rs.getDate("payment_date") + "\t\n" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Bank account: " + rs.getInt("bank_account") + "\t\n" + ANSI_RESET);
            }
        }
    }

    public void displayAbsence() throws SQLException {
        try (ResultSet rs = statement.executeQuery(ABSENCE)) {
            while (rs.next()) {
                System.out.print("Employee ID: " + rs.getString("employee_id") + "\t");
                System.out.println(ANSI_BLUE + "Absence date: " + rs.getDate("absence_date") + "\t\n" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Bank account: " + rs.getInt("bank_account") + "\t\n" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Cause: " + rs.getString("cause") + "\t\n" + ANSI_RESET);
            }
        }
    }

    public void displayAvailableCommands() {
        System.out.println("The console program started working!\n" +
                "Available commands:\n" +
                ANSI_BLUE + "0" + ANSI_RESET + "- shutting down the application\n" +
                ANSI_BLUE + "1" + ANSI_RESET + "- output the values of the employees table\n" +
                ANSI_BLUE + "2" + ANSI_RESET + "- output the values of the post table\n" +
                ANSI_BLUE + "3" + ANSI_RESET + "- output the values of the department table\n" +
                ANSI_BLUE + "4" + ANSI_RESET + "- output the values of the working hours table\n" +
                ANSI_BLUE + "5" + ANSI_RESET + "- output the values of the paid salary table\n" +
                ANSI_BLUE + "6" + ANSI_RESET + "- output the values of the absence table\n" +
                ANSI_BLUE + "7" + ANSI_RESET + "-add employee values to table\n");
        System.out.print("Enter the command: ");
    }

    public void employeeInput(Employee employee) throws SQLException {
        String sql = "insert into employee " +
                "(first_name, last_name, passport_no, university_education, " +
                "birth_date, bank_account, post_id, department_id)" +
                "values ( ?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setInt(3, Integer.parseInt(employee.getPassportNumber()));
            preparedStatement.setString(4,employee.getUniversityEducation());
            preparedStatement.setDate(5, Date.valueOf(employee.getBirthDate()));
            preparedStatement.setInt(6, Integer.parseInt(employee.getBankAccount()));
            preparedStatement.setInt(7, Integer.parseInt(employee.getPostId()));
            preparedStatement.setInt(8, Integer.parseInt(employee.getDepartmentId()));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        statement.close();
    }
}
