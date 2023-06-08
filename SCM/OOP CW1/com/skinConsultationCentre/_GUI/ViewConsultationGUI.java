package com.skinConsultationCentre._GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.skinConsultationCentre.Consultation;
import com.skinConsultationCentre.Patient;

public class ViewConsultationGUI {

        JFrame frame;
        JPanel mainPanel;
        JPanel doctorDetailsPanel;
        JPanel patientDetailsPanel;
        JPanel consultationDetailsPanel;
        JPanel formsPanel;
        JPanel buttonsPanel;
        JPanel panel1;
        JPanel panel2;
        JPanel panel3;
        JPanel panel4;
        JPanel panel5;
        JPanel panel6;
        JPanel panel7;
        JPanel panel8;
        JPanel panel9;
        JPanel panel10;
        JLabel dateLabel;
        JLabel timeLabel;
        JLabel heading1;
        JLabel heading2;
        JLabel heading3;
        JLabel heading4;
        JLabel patientNameLabel;
        JLabel patientSurnameLabel;
        JLabel patientMobileNumberLabel;
        JLabel patientDOBLabel;
        JLabel patientIdLabel;
        JLabel doctorMedicalLicenseNoLabel;
        JLabel doctorMedicalLicenseNoValue;
        JLabel consultationHoursLabel;
        JLabel consultationCostLabel;
        JLabel consultationNotesLabel;
        JLabel patientNameValue;
        JLabel patientSurnameValue;
        JLabel patientDOBValue;
        JLabel patientMobileNumberValue;
        JLabel patientIdValue;
        JLabel consultationHoursValue;
        JLabel consultationCostValue;
        JLabel consultationNotesValue;

        JTextArea consultationNotesTextArea;

        Consultation consultation;

        public ViewConsultationGUI(Consultation consultation) {
                this.consultation = consultation;
        }

        public JFrame start() {
                // GUI elements
                // Main frame
                frame = new JFrame("View Consultation Details");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(900, 360);
                frame.setLocationRelativeTo(null);
                frame.setAlwaysOnTop(false);
                frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),
                                BoxLayout.Y_AXIS));

                // Main panel init with headings
                // ------------------------------------------------
                mainPanel = new JPanel();
                mainPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
                mainPanel.setBorder(new EmptyBorder(30, 20, 0, 20));

                heading1 = new JLabel(
                                String.format("Doctor: %s %s", consultation.getDoctor().getName(),
                                                consultation.getDoctor().getSurname()),
                                JLabel.CENTER);
                heading1.setPreferredSize(new Dimension(frame.getWidth(), 30));
                heading1.setFont(new Font("Sans-serif", Font.BOLD, 16));
                mainPanel.add(heading1);

                // Pateint details panel ------------------------------------------------
                Patient p = consultation.getPatient();

                patientDetailsPanel = new JPanel();
                patientDetailsPanel.setLayout(new BoxLayout(patientDetailsPanel,
                                BoxLayout.Y_AXIS));
                panel1 = new JPanel();
                panel1.setBorder(new EmptyBorder(0, 0, 10, 0));

                heading3 = new JLabel("  Patient details  ",
                                JLabel.CENTER);
                heading3.setPreferredSize(new Dimension(300, 40));
                heading3.setFont(new Font("Sans-serif", Font.ITALIC, 13));
                heading3.setForeground(new Color(41, 128, 185));
                Border blueline = BorderFactory.createLineBorder(Color.blue);
                heading3.setBorder(blueline);
                patientDetailsPanel.add(heading3);

                panel3 = new JPanel();
                patientNameLabel = new JLabel("Name: ");
                patientNameLabel.setPreferredSize(new Dimension(120, 24));
                patientNameValue = new JLabel(p.getName());
                patientNameValue.setPreferredSize(new Dimension(180, 24));
                panel3.add(patientNameLabel);
                panel3.add(patientNameValue);
                patientDetailsPanel.add(panel3);

                panel4 = new JPanel();
                patientSurnameLabel = new JLabel("Surname: ");
                patientSurnameLabel.setPreferredSize(new Dimension(120, 24));
                patientSurnameValue = new JLabel(p.getSurname());
                patientSurnameValue.setPreferredSize(new Dimension(180, 24));
                panel4.add(patientSurnameLabel);
                panel4.add(patientSurnameValue);
                patientDetailsPanel.add(panel4);

                panel5 = new JPanel();
                patientDOBLabel = new JLabel("Date of birth: ");
                patientDOBLabel.setPreferredSize(new Dimension(120, 24));
                patientDOBValue = new JLabel(p.getDateOfBirth().toString());
                patientDOBValue.setPreferredSize(new Dimension(180, 24));
                panel5.add(patientDOBLabel);
                panel5.add(patientDOBValue);
                patientDetailsPanel.add(panel5);

                panel6 = new JPanel();
                patientMobileNumberLabel = new JLabel("Mobile number: ");
                patientMobileNumberLabel.setPreferredSize(new Dimension(120, 24));
                patientMobileNumberValue = new JLabel(p.getMobileNumber());
                patientMobileNumberValue.setPreferredSize(new Dimension(180, 24));
                panel6.add(patientMobileNumberLabel);
                panel6.add(patientMobileNumberValue);
                patientDetailsPanel.add(panel6);

                panel7 = new JPanel();
                patientIdLabel = new JLabel("Patient ID: ");
                patientIdLabel.setPreferredSize(new Dimension(120, 24));
                patientIdValue = new JLabel(String.valueOf(p.getPatientId()));
                patientIdValue.setPreferredSize(new Dimension(180, 24));
                panel7.add(patientIdLabel);
                panel7.add(patientIdValue);
                patientDetailsPanel.add(panel7);

                // ------------------------------------------------
                // Consultation details panel ------------------------------------------------

                consultationDetailsPanel = new JPanel();
                consultationDetailsPanel.setLayout(new BoxLayout(consultationDetailsPanel,
                                BoxLayout.Y_AXIS));
                // consultationDetailsPanel.setPreferredSize(new Dimension(300, 200));

                heading4 = new JLabel("  Consultation details  ",
                                JLabel.CENTER);
                heading4.setPreferredSize(new Dimension(300, 40));
                heading4.setFont(new Font("Sans-serif", Font.ITALIC, 13));
                heading4.setForeground(new Color(41, 128, 185));
                Border blueline2 = BorderFactory.createLineBorder(Color.blue);
                heading4.setBorder(blueline2);
                consultationDetailsPanel.add(heading4);

                panel8 = new JPanel();
                consultationHoursLabel = new JLabel("Duration (hrs): ");
                consultationHoursLabel.setPreferredSize(new Dimension(100, 24));
                consultationHoursValue = new JLabel(String.valueOf(consultation.getHours()));
                consultationHoursValue.setPreferredSize(new Dimension(200, 24));
                panel8.add(consultationHoursLabel);
                panel8.add(consultationHoursValue);
                consultationDetailsPanel.add(panel8);

                panel9 = new JPanel();
                consultationCostLabel = new JLabel("Cost (£): ");
                consultationCostLabel.setPreferredSize(new Dimension(100, 24));
                consultationCostValue = new JLabel(String.valueOf(consultation.getCost()));
                consultationCostValue.setPreferredSize(new Dimension(200, 24));
                panel9.add(consultationCostLabel);
                panel9.add(consultationCostValue);
                consultationDetailsPanel.add(panel9);

                panel10 = new JPanel();
                panel10.setBorder(new EmptyBorder(5, 0, 0, 0));
                consultationNotesLabel = new JLabel("Notes: ");
                consultationNotesLabel.setPreferredSize(new Dimension(100, 56));
                consultationNotesValue = new JLabel(consultation.getNotes());
                consultationNotesValue.setPreferredSize(new Dimension(200, 72));
                panel10.add(consultationNotesLabel);
                panel10.add(consultationNotesValue);
                consultationDetailsPanel.add(panel10);

                // ------------------------------------------------
                // Add panels to forms panel
                formsPanel = new JPanel();
                formsPanel.add(patientDetailsPanel);
                formsPanel.add(consultationDetailsPanel);

                // Add panel1 and forms panel to main panel
                mainPanel.add(panel1);
                mainPanel.add(formsPanel);

                frame.getContentPane().add(mainPanel);
                frame.setVisible(true);

                return frame;
        }

}
