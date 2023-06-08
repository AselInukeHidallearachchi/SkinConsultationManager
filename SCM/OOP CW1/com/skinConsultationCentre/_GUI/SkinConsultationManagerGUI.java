package com.skinConsultationCentre._GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.skinConsultationCentre.Consultation;
import com.skinConsultationCentre.Doctor;
import com.skinConsultationCentre.WestminsterSkinConsultationManager;

public class SkinConsultationManagerGUI {

    JFrame frame;
    JTable tableDoctors;

    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;
    JPanel consultationsListPanel;
    Doctor selectedDoctor;
    JButton btnSort;
    JButton btnAddConsultation;

    JLabel lbl1;
    JLabel heading1;
    JLabel heading2;
    JLabel headingdoc;
    JScrollPane sp1;
    JScrollPane sp2;

    // Doctor table headers
    private static final String[] doctorsTableColumns = { "Name", "Surname", "Date of birth", "Mobile number",
            "Medical license number", "Specialisation" };

    static WestminsterSkinConsultationManager westminsterSkinConsultationManager = WestminsterSkinConsultationManager
            .getManager();

    private void generateDoctorTableData(boolean isSorted) {

        // Sort doctors by surname
        if (isSorted) {
            westminsterSkinConsultationManager.doctorsList.sort((d1, d2) -> d1.getSurname().compareTo(d2.getSurname()));
        }

        DefaultTableModel dtm = (DefaultTableModel) tableDoctors.getModel();
        dtm.setRowCount(0);

        // Add doctors to table
        for (Doctor d : westminsterSkinConsultationManager.doctorsList) {

            String[] rowData = new String[] { d.getName(), d.getSurname(),
                    d.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy MM dd")),
                    d.getMobileNumber(),
                    String.valueOf(d.getMedicalLicenseNumber()),
                    d.getSpecialisation()
            };

            dtm.addRow(rowData);
        }

        frame.revalidate();
        frame.repaint();
    }

    // Generate consultations list
    public void generateConsultationListData() {
        if (westminsterSkinConsultationManager.consultationsList.isEmpty()) {
            // Show consultations label
            panel5 = new JPanel();
            panel5.setPreferredSize(new Dimension(800, 40));
            panel5.setBorder(null);
            panel5.setBackground(Color.WHITE);
            JLabel lbl = new JLabel("No consultations", JLabel.CENTER);
            lbl.setFont(new Font("Sans-serif", Font.PLAIN, 17));
            lbl.setPreferredSize(new Dimension(600, 100));
            lbl.setForeground(new Color(231, 76, 60));
            panel5.add(lbl);
            consultationsListPanel.add(panel5);

        } else {
            consultationsListPanel.removeAll();
            // Add consultations to list
            for (Consultation c : westminsterSkinConsultationManager.consultationsList) {
                JPanel itemPanel = new JPanel();
                itemPanel.setPreferredSize(new Dimension(800, 10));
                itemPanel.setBackground(Color.WHITE);


                JLabel lbl1 = new JLabel(c.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm")));
                lbl1.setFont(new Font("Sans-serif", Font.BOLD, 13));
                lbl1.setBorder(new EmptyBorder(0, 0, 0, 20));
                itemPanel.add(lbl1);

                JLabel lbl2 = new JLabel(String.format("Patient %d with Dr. %s %s", c.getPatient().getPatientId(),
                        c.getDoctor().getName(), c.getDoctor().getSurname()));
                lbl2.setPreferredSize(new Dimension(450, 10));
                lbl2.setFont(new Font("Sans-serif", Font.PLAIN, 13));
                lbl2.setBorder(new EmptyBorder(0, 15, 0, 40));
                itemPanel.add(lbl2);

                JButton btn1 = new JButton("View");
                btn1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openViewConsultationFrame(c);
                    }
                });

                itemPanel.add(btn1);
                consultationsListPanel.add(itemPanel);
            }
        }

        // Update frame
        SwingUtilities.updateComponentTreeUI(frame);
        frame.revalidate();
        frame.repaint();

    }

    public JFrame start()  {
        // GUI elements

        // Main frame --------------------------------------------------------------
        frame = new JFrame("Skin Consultation Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(false);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        // ----------------------------------------------------------------------------

        // fix
        panel4 = new JPanel();
        // panel4.setPreferredSize(new Dimension(frame.getWidth(), 10));

        panel4.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Panel 1 -------------------------------------------------------------------
        panel1 = new JPanel();
        panel1.setBackground(Color.red);
        panel1.setPreferredSize(new Dimension(frame.getWidth(), 40));
        panel1.setBorder(new EmptyBorder(20, 50, 0, 50));

        lbl1 = new JLabel("Westminster Skin Consultation Manager GUI", JLabel.CENTER);
        lbl1.setPreferredSize(new Dimension(frame.getWidth(), 60));
        lbl1.setFont(new Font("Sans-serif", Font.PLAIN, 25));
        panel1.add(lbl1);
        frame.getContentPane().add(panel1);
        // ----------------------------------------------------------------------------

        // Panel 2 -------------------------------------------------------------------
        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(frame.getWidth(), 60));
        panel2.setBorder(new EmptyBorder(0, 50, 0, 50));

        heading1 = new JLabel("Doctors List", JLabel.CENTER);
        heading1.setPreferredSize(new Dimension(frame.getWidth(), 50));
        heading1.setFont(new Font("Sans-serif", Font.BOLD, 19));
        heading1.setForeground(new Color(41, 128, 185));
        panel2.add(heading1);

        btnSort = new JButton("Sort by last name (A-Z)");
        btnSort.setFont(new Font("Sans-serif", Font.BOLD, 13));
        btnSort.setPreferredSize(new Dimension(200, 36));
        btnSort.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBtnSortClick();
            }
        });
        panel2.add(btnSort);
        frame.getContentPane().add(panel2);
        // ----------------------------------------------------------------------------

        // Scroll pane 1 with doctor table ----------------------------------------------
        tableDoctors = new JTable(new DefaultTableModel(doctorsTableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JTableHeader doctorsTableColumns = tableDoctors.getTableHeader();
        doctorsTableColumns.setBackground(Color.black);
        doctorsTableColumns.setForeground(Color.white);


        tableDoctors.setPreferredScrollableViewportSize(new Dimension(400, 100));
        tableDoctors.setShowHorizontalLines(true);
        tableDoctors.setRowHeight(28);
        tableDoctors.setDragEnabled(false);
        tableDoctors.setSelectionMode(0);
        tableDoctors.setGridColor(Color.blue);
        tableDoctors.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                setSelectedDoctor(
                        Integer.parseInt(tableDoctors.getValueAt(tableDoctors.getSelectedRow(), 4).toString()));
                btnAddConsultation.setEnabled(true);

            }
        });
        generateDoctorTableData(false);
        sp1 = new JScrollPane(tableDoctors);
        sp1.setBorder(new EmptyBorder(5, 30, 5, 30));
        frame.getContentPane().add(sp1);
        // ----------------------------------------------------------------------------

        // Panel 3 -------------------------------------------------------------------
        panel3 = new JPanel();
        panel3.setBackground(new Color(66, 109, 210));
        panel3.setPreferredSize(new Dimension(frame.getWidth(), 20));
        panel3.setBorder(new EmptyBorder(5, 50, 0, 50));
        headingdoc = new JLabel("(*Please choose a doctor before Add consultation)", JLabel.CENTER);
        headingdoc.setPreferredSize(new Dimension(frame.getWidth(), 25));
        headingdoc.setFont(new Font("Arial", Font.BOLD, 13));
        headingdoc.setForeground(new Color(255, 255, 255));

        // Add consultation button ---------------------------------------------------
        btnAddConsultation = new JButton("Add consultation");
        btnAddConsultation.setFont(new Font("Sans-serif", Font.BOLD, 13));
        btnAddConsultation.setPreferredSize(new Dimension(200, 36));
        btnAddConsultation.setEnabled(false);
        btnAddConsultation.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBtnAddConsultationClick();

            }
        });
        panel3.add(headingdoc);
        panel3.add(btnAddConsultation);
        frame.getContentPane().add(panel3);

        // Panel 4 -------------------------------------------------------------------
        panel4 = new JPanel();
        panel4.setBackground(Color.white);
        panel4.setPreferredSize(new Dimension(frame.getWidth(), 10));
        panel4.setBorder(new EmptyBorder(0, 50, 0, 50));
        heading2 = new JLabel("Consultations list", JLabel.CENTER);
        heading2.setPreferredSize(new Dimension(frame.getWidth(), 50));
        heading2.setFont(new Font("Sans-serif", Font.ITALIC, 17));
        heading2.setForeground(new Color(4, 173, 64));
        panel4.add(heading2);
        frame.getContentPane().add(panel4);



        // Scroll pane 2 with consultations list ---------------------------------------
        consultationsListPanel = new JPanel();
        consultationsListPanel.setLayout(new BoxLayout(consultationsListPanel, BoxLayout.Y_AXIS));
        consultationsListPanel.setBackground(Color.WHITE);
        generateConsultationListData();
        sp2 = new JScrollPane(consultationsListPanel);
        sp2.setPreferredSize(new Dimension(800, 120));
        sp2.setBackground(Color.WHITE);
        sp2.setBorder(null);
        frame.getContentPane().add(sp2);
        // ----------------------------------------------------------------------------

        frame.setVisible(true);
        return frame;
    }

    private void handleBtnSortClick() {
        generateDoctorTableData(true);
    }

    private void handleBtnAddConsultationClick() {
        if (selectedDoctor != null) {
            openAddConsultationFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a doctor first");
        }
    }

    private void setSelectedDoctor(int medicalLicenseNumber) {
        for (Doctor d : westminsterSkinConsultationManager.doctorsList) {
            if (d.getMedicalLicenseNumber() == medicalLicenseNumber) {
                selectedDoctor = d;
                break;
            }
        }
    }

    private JFrame openAddConsultationFrame() {
        AddConsultationGUI gui = new AddConsultationGUI(this, selectedDoctor);
        return gui.start();
    }

    private JFrame openViewConsultationFrame(Consultation consultation) {
        ViewConsultationGUI gui = new ViewConsultationGUI(consultation);
        return gui.start();
    }
}
