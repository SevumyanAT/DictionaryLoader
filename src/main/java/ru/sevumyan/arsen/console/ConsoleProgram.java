package ru.sevumyan.arsen.console;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleProgram {
    public static void main(String[] args) throws SQLException {
        DBManagement dbManagement = new DBManagement();
        try (Scanner sc = new Scanner(System.in)) {
            boolean stop = false;

            do {
                dbManagement.displayAvailableCommands();

                String value = sc.next();
                int command = -1;


                try {
                    command = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    System.out.println("Sorry, but this is clearly not a number. Restart the program and try again!");
                }

                switch (command) {
                    case 0 -> stop = true;
                    case 1 -> dbManagement.displayEmployees();
                    case 2 -> dbManagement.displayPost();
                    case 3 -> dbManagement.displayDepartment();
                    case 4 -> dbManagement.displayWorkingHours();
                    case 5 -> dbManagement.displayPaidSalary();
                    case 6 -> dbManagement.displayAbsence();
                }

            } while (!stop);
        }
    }
}

