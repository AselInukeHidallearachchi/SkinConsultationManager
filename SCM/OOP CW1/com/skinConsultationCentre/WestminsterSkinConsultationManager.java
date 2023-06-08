package com.skinConsultationCentre;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import com.skinConsultationCentre._GUI.SkinConsultationManagerGUI;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    Scanner input = new Scanner(System.in);
    private static final int DOCTORS_COUNT = 10;
    public ArrayList<Doctor> doctorsList = new ArrayList<>();
    public ArrayList<Patient> patientsList = new ArrayList<>();
    public ArrayList<Consultation> consultationsList = new ArrayList<>();

    private static WestminsterSkinConsultationManager manager;

    public static WestminsterSkinConsultationManager getManager() {
        return manager;
    }

    public static void main(String[] args) {
        manager = new WestminsterSkinConsultationManager();
        manager.run();
    }
    public void run() {
        String option;

        loadDataFromFile();
        do {
            System.out.println("   ///--- Welcome to Westminster Skin Consultation Centre ---///");
            System.out.println("========================================================================");
            System.out.println("            ////- CONSOLE MENU -///");
            System.out.println("========================================================================");

            System.out.println("1. Add a new doctor");
            System.out.println("2. Delete a doctor");
            System.out.println("3. Print list of doctors");
            System.out.println("4. Save data in file");
            System.out.println("5. Open GUI");

            System.out.println("Select option (Enter 0 to exit): ");
            System.out.println("========================================================================");

            option = input.next();

            switch (option) {
                case "0":
                    break;

                case "1":
                    addDoctor();
                    break;

                case "2":
                    deleteDoctor();
                    break;

                case "3":
                    printDoctors();
                    break;

                case "4":
                    saveDataToFile();
                    break;

                case "5":
                    startGUI();
                    break;

                default:
                    System.out.println("\n****Please enter a valid input***");
                    break;
            }

        } while (!"0".equals(option));
        System.out.println("--- Exiting program ---");
    }
    @Override
        public void addDoctor() {
            String choice = "";
            do {
                try {
                    System.out.println("\n--- Add a new doctor ---");
                    System.out.print("Name: ");
                    String name = input.next();

                    System.out.print("Surname: ");
                    String surname = input.next();

                    System.out.print("Date of birth (YYYY-MM-DD): ");
                    String doctorDob = input.next();
                    LocalDate dob = LocalDate.parse(doctorDob);

                    System.out.print("Mobile number: ");
                    String mobileNumber = input.next();

                    System.out.print("Medical license number: ");
                    int medicalLicenseNumber = input.nextInt();

                    System.out.println("Select the specialization from the below:");
                    System.out.println("1. Allergy and immunology\n2. Dermatology,\n3. Emergency medicine\n4. Other");

                    int num = input.nextInt();
                    String specialisation = "";
                    if (num == 1){
                        specialisation = "Allergy and immunology";
                    } else if (num == 2) {
                        specialisation = "Dermatology";
                    } else if (num == 3) {
                        specialisation = "Emergency medicine";
                    } else if (num == 4) {
                        System.out.println("Enter the specialisation:");
                        specialisation = input.next();
                    }else {
                        System.out.println("You entered the wrong selection");
                        break;
                    }

                    Doctor newDoctor = new Doctor(name, surname, dob, mobileNumber, medicalLicenseNumber, specialisation);
                    if (doctorsList.size() <= DOCTORS_COUNT) {
                        doctorsList.add(newDoctor);
                        System.out.println("Doctor added successfully");
                        System.out.println("\nYou can add " + (10 - (doctorsList.size())) + " more doctors to the center");

                        System.out.println("Do you want add another doctor?(Y/N):");
                        choice = input.next();
                    } else {
                        System.out.println("The centre can only allocate a maximum of 10 doctors");
                        break;
                    }

                } catch (Exception a) {
                    System.out.println("Doctor can't add to the center,something went wrong!");

                }
            }while (choice.equalsIgnoreCase("Y"));
            System.out.println("Returning to the console menu.......\n");
        }

    @Override
    public void deleteDoctor() {

          try{
                boolean foundFlag = false;
                System.out.println("\n--- Delete doctor ---");
                System.out.print("Medical license number of doctor to be deleted: ");
                int medicalLicenseNumber = input.nextInt();
                for (Doctor d : doctorsList) {
                    if (d.getMedicalLicenseNumber() == medicalLicenseNumber) {
                        foundFlag = true;
                        Doctor currentDoctor = d;
                        doctorsList.remove(currentDoctor);
                        System.out.println(
                                "Doctor with medical license number " + medicalLicenseNumber + " deleted successfully");
                        System.out.println(currentDoctor);
                        System.out.println("No. of doctors in centre: " + doctorsList.size());
                        break;
                    }
                }

                if (!foundFlag) {
                    System.out.println("Doctor with medical license number " + medicalLicenseNumber + " not found");
                }
          }catch (Exception e){
              System.out.println("Doctor can't delete from the center, Something went wrong!");

          }
    }

    @Override
    public void printDoctors() {
        System.out.println("\n--- List of doctors in consultation centre ---");
        if (doctorsList.isEmpty()) {
            System.out.println("No doctors");
        } else {
            System.out.println();
            doctorsList.sort((d1, d2) -> d1.getSurname().compareTo(d2.getSurname()));
            for (Doctor doctor : doctorsList) {
                System.out.println(doctor);
            }
        }
    }

    @Override
    public void saveDataToFile() {
        File doctorsFile = new File("doctors");

        try (FileOutputStream fileOutput = new FileOutputStream(doctorsFile)) {
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            for (Doctor doctor : doctorsList) {
                objectOutput.writeObject(doctor);
            }

            objectOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("All data saved to file: doctors");
        System.out.println();

    }

    @Override
    public void loadDataFromFile() {
        System.out.println("Trying to load data from file: doctors");
        File doctorData = new File("doctors");
        try {
            FileInputStream fileInput = new FileInputStream(doctorData);

            try (ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
                while (true) {
                    Doctor doctor = (Doctor) objectInput.readObject();
                    if (doctor != null) {
                        doctorsList.add(doctor);
                    } else {
                        break;
                    }
                }

            } catch (EOFException e) {
                System.out.println("Doctor data loaded from file");
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("No data file found");
        }
    }

    public static JFrame startGUI()  {
        SkinConsultationManagerGUI gui = new SkinConsultationManagerGUI();
        return gui.start();
    }
    
}