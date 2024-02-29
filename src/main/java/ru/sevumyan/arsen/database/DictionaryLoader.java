package ru.sevumyan.arsen.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DictionaryLoader {
    static final String URL = "jdbc:postgresql://localhost/postgres";
    static final String QUERY = "Select * from employee";
    static final String path1 = "src\\main\\resources\\post.csv";
    static final String path2 = "src\\main\\resources\\Department.csv";

    public static void main(String[] args) throws SQLException, IOException {

    }

    public static void postorder() throws SQLException, IOException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        Connection conn = DriverManager.getConnection(URL, props);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        System.out.println("Inserting records into the table...");

        String sql = "insert into post(post_name,wage) values (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        String line = "";
        String splitBy = ",";

        BufferedReader bf = new BufferedReader(new FileReader(path1));
        while ((line = bf.readLine()) != null) {
            String[] post = line.split(splitBy);
            int wage = Integer.parseInt(post[1]);
            preparedStatement.setString(1, post[0]);
            preparedStatement.setInt(2, wage);
            preparedStatement.executeUpdate();
        }
    }

    public static void departmentAdder() throws SQLException, IOException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        Connection conn = DriverManager.getConnection(URL, props);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        System.out.println("Inserting records into the table...");

        String sql = "Insert into department(department_location) values (?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        String line = "";
        String splitBy = ",";

        BufferedReader bf = new BufferedReader(new FileReader(path2));
        while ((line = bf.readLine()) != null) {
            String[] post = line.split(splitBy);
            preparedStatement.setString(1, post[0]);

            preparedStatement.executeUpdate();
        }
    }
}

