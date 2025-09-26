package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class Doctors {
    private Connection Connection;

    public Doctors(Connection connection) {
        this.Connection = connection;
    }



    public void viewDoctors(){
        String query = "SELECT * from doctors";
        try{
            PreparedStatement preparedStatement = Connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            //fromating the output like db
            System.out.println("Doctors: ");
            System.out.println("+--------------+--------------+--------------------+");
            System.out.println("| Doctor Id   | Name         | Specialization      |");
            System.out.println("+--------------+--------------+--------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-14s|%-14s|%10-s|%21-s\n", id, name, specialization);
                System.out.println("+--------------+--------------+--------------------+");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorsById(int id) {
        String query = "SELECT * FROM patients id = ?";
        try{
            PreparedStatement preparedStatement = Connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            } else {
                return false;
            }

        } catch(SQLException e){
            e.printStackTrace();;
        }

        return false;
    }
}