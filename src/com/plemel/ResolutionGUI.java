package com.plemel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.LinkedList;

/**
 * Created by Jennifer Plemel on 11/20/2016.
 */
public class ResolutionGUI extends JFrame {
    private JPanel rootPanel1;
    private JLabel DateNameLabel;
    private JLabel ReporterLabel;
    private JLabel PriorityLabel;
    private JLabel IssueLabel;
    private JTextField txtResolution;
    private JButton resolveButton;

    protected ResolutionGUI(Ticket ticket, LinkedList<Ticket> resolved){
        setContentPane(rootPanel1);
        setPreferredSize(new Dimension(500,200));
        addListener(ticket, resolved);
        pack();
        setVisible(true);
        getRootPane().setDefaultButton(resolveButton);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //Data from Ticket to display on new GUI
        Date GUIDate = ticket.getDateReported();
        DateFormat df = new SimpleDateFormat("EEE MMM dd");
        String date = df.format(GUIDate);
        DateNameLabel.setText(date);
        IssueLabel.setText(ticket.getDescription());
        PriorityLabel.setText(Integer.toString(ticket.getPriority()));
        ReporterLabel.setText(ticket.getReporter());
    }


    private void addListener(Ticket ticket, LinkedList<Ticket> resolved){
        //Resolve Button event handler

        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Add resolution & date resolved to ticket

                ticket.setResolutionDate(new Date());
                ticket.setResolution(txtResolution.getText());

                writeResolved(resolved);
                dispose();
            }
        });
    }

    //Write tickets to text file

    private static void writeResolved(LinkedList<Ticket> resolvedTickets){

        //Get variables to generate filename with current date
        //get variables to generate a file name with current date parts
        Date date = new Date();
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
        String day = sdfDay.format(date);
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM");
        String month = sdfMonth.format(date);
        SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");
        String year= sdfYear.format(date);
        //generated file name
        String fileName = "Resolved_tickets_as_of_"+month+"_"+day+"_"+year+".txt";
        //appends
        try (BufferedWriter bufWrite = new BufferedWriter(new FileWriter(fileName, true))) {
            Ticket t = resolvedTickets.getLast();
            String writeFileLine = t.toString("resolved");
            bufWrite.write(writeFileLine);
        }
        catch(IOException ex){
            System.out.println("An IO Exception occurred");
        }

    }

}