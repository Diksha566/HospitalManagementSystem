package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class Patients{
    private Connection Connection;
    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner) {
        this.Connection = connection;
        this.scanner = scanner;
    }

    public void addPatients(){
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?,?)";
            PreparedStatement preparedStatement = Connection.prepareStatement(query);

            //setting values
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient added Succesfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        
    }

    
}