package com.plemel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Jennifer Plemel on 11/20/2016.
 */
public class NewTicketGUI extends JFrame {
    private JPanel rootPanel;
    private JLabel dateLabel;
    private JTextField textReporter;
    private JTextField textProblem;
    private JComboBox comboPriority;
    private JButton AddNewButton;
    private JButton ResolveTicketButton;
    private JList jpJList;
    private JLabel InstructUserLabel;
    private DefaultListModel<Ticket>listModel;

    //Initialize GUI form + J FRAME

    protected NewTicketGUI(LinkedList<Ticket> resolved, LinkedList<Ticket> queue){

        setContentPane(rootPanel);
        setPreferredSize(new Dimension(700,500));
        listModel = new DefaultListModel<Ticket>();

        //initialize jpJList box with list model
        jpJList.setModel(listModel);
        LinkedList<Ticket> resolvedTickets = new LinkedList<>();

        //listener methods
        addListener(resolved, queue);
        addListener(queue);
        pack();
        setVisible(true);

        //click event
        getRootPane().setDefaultButton(AddNewButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //GUI date
        Date GUIDate = new Date();
        DateFormat df = new SimpleDateFormat("EEE MMM dd");
        String date = df.format(GUIDate);
        dateLabel.setText(date);

        //Combo Box

       // String [] choosePriority = {"1" ,"2" , "3", "4", "5"};

//        comboPriority = new JComboBox();
//
//        for (int x = 0; x <= 4; x++){
//
//            comboPriority.addItem(choosePriority[x]);
//        }

        comboPriority.addItem("1");
        comboPriority.addItem("2");
        comboPriority.addItem("3");
        comboPriority.addItem("4");
        comboPriority.addItem("5");






        //Display open tickets in J LIST

        if (!queue.isEmpty()){
            for (Ticket t : queue){
                listModel.addElement(t);
                InstructUserLabel.setText("Select ticket to resolve, then click Resolve Ticket button");
            }
        }
        else {
            InstructUserLabel.setText("No tickets in queue");
        }
    }



    private void addListener(LinkedList<Ticket> queue){

        //Add New Button event handler
        AddNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //New Ticket
                Date ticketDate = new Date();
                Ticket tNew = new Ticket(textProblem.getText(), comboPriority.getSelectedIndex()+1, textReporter.getText(), ticketDate);

                //Add Ticket to queue
                addTicketTopPriority(queue, tNew);

                //Write New Ticket to file
                writeOpen(queue);

                //Clear jp J LIST
                listModel.clear();

                //Fill jp J LIST with tickets (sorted by top priority)

                for (Ticket t : queue)
                {
                    listModel.addElement(t);
                }

                //Instruct user with label
                InstructUserLabel.setText("Select a ticket to resolve, then click Resolve Ticket button");
            }
        });
    }

    private void addListener(LinkedList<Ticket> resolved, LinkedList<Ticket> queue){
        jpJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = jpJList.getSelectedIndex();
            }
        });
        //resolve button click event handler
        ResolveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //calls delete Ticket method
                deleteTicketbyID(resolved, queue, jpJList.getSelectedIndex());
                //removes element from J list
                listModel.removeElementAt(jpJList.getSelectedIndex());
                //removes ticket from open ticket file
                writeOpen(queue);
            }
        });
    }
    protected static void addTicketTopPriority(LinkedList<Ticket> tickets, Ticket newTicket){

        //Assuming the list is empty or sorted...

        if (tickets.size() == 0){
            tickets.add(newTicket);
            return;
        }
        //Tickets with the HIGHEST priority number go @ front of the list. ( 5 = server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size(); x++){

            if (newTicketPriority >= tickets.get(x).getPriority()){
                tickets.add(x, newTicket);
                return;
            }
        }

        //If ticket is not added in loop, it is lower priority than all other tickets, so add last

        tickets.addLast(newTicket);
    }
    protected static void deleteTicketbyID(LinkedList<Ticket> resolvedTickets, LinkedList<Ticket> ticketQueue, int index) {
        Ticket ticket = ticketQueue.get(index);
        setResolutionAndDate(ticket, resolvedTickets);
        ticketQueue.remove(ticket);
        //add resolved tickets for file storage
        resolvedTickets.add(ticket);
    }
    private static void setResolutionAndDate(Ticket ticket, LinkedList<Ticket> resolved){

        //time stamp resolution
        ResolutionGUI resGUI = new ResolutionGUI(ticket, resolved);
        ticket.setResolutionDate(new Date());
    }

    private static void writeOpen(LinkedList<Ticket> ticketQueue){
        if (!ticketQueue.isEmpty()) {
            try {
                //set up writing to facilitate future reading of the file to generate objects
                //overwrites file with current list
                try (BufferedWriter bufWrite = new BufferedWriter(new FileWriter("openTickets.txt"))) {
                    for (Ticket t : ticketQueue) {
                        int id = t.getTicketID();
                        bufWrite.write(Integer.toString(id));
                        bufWrite.newLine();
                        bufWrite.write(t.getDescription());
                        bufWrite.newLine();
                        int p = t.getPriority();
                        bufWrite.write(Integer.toString(p));
                        bufWrite.newLine();
                        bufWrite.write(t.getReporter());
                        bufWrite.newLine();
                        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
                        String date = df.format(t.getDateReported());
                        bufWrite.write(date);
                        bufWrite.newLine();
                    }
                }
            } catch (IOException ex) {
                System.out.println("An IO Exception occured");
            }

        }
    }

}

