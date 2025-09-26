package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "diksha23";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username, password);
            Patients patients = new Patients(connection, scanner);
            Doctors doctors = new Doctors(connection);

            while (true) {
                System.out.println("Hospital Management System ");
                System.out.println("1. Add Pateint");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointmnet");
                System.out.println("5. Exit");

                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //add patient
                        patients.addPatients();
                        System.out.println();
                    case 2:
                        // view patients
                        patients.viewPatients();
                        System.out.println();

                    case 3:
                        // view doctos
                        doctors.viewDoctors();
                        System.out.println();
                    
                    case 4: 
                        //book appoints
                        bookAppointment(patients, doctors, connection, scanner);
                        System.out.println();

                    case 5:
                        return;

                    default:
                        System.out.println("Enter vaild values dear<3 ");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patients patients, Doctors doctors, Connection connection, Scanner scanner){
        System.out.println("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter DoctorId: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointmnet date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if(patients.getPatientById(patientId) && doctors.getDoctorsById(doctorId)){
            //if dono exit kerta hai then check doc aviable hai ki nhi 
            if(checkDoctorAvailablity(doctorId, appointmentDate, connection)){
                String appointmnetQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?,?.?)";

                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmnetQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAfftected = preparedStatement.executeUpdate();

                    if(rowsAfftected > 0) {
                        System.out.println("Appointment booked !!");
                    } else {
                        System.out.println("Failed to book appointent");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Doctor not avaible on this date");
            }

        } else {
            System.out.println("Either doctor or patient doesn't exits!!");
        }

    }

    public static boolean checkDoctorAvailablity(int doctor_id, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ? "; 
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count == 0) {
                    return true;

                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
}
