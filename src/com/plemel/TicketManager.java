package com.plemel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;


import java.util.Scanner;

public class TicketManager {

    public static void main(String[] args) {

        //To store Open Tickets
        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();

        //To store resolved Tickets
        LinkedList<Ticket> resolvedTickets = new LinkedList<>();

        //Scanner scan = new Scanner(System.in);

        //TRY w/resources
        try ( BufferedReader bufReader = new BufferedReader(new FileReader("openTickets.txt"))) {

            //Generate string values from file
            LinkedList<String> fileList = new LinkedList<>();
            String line = bufReader.readLine();

            while (line != null) {

                //generate list of lines from file
                fileList.add(line);
                line = bufReader.readLine();
            }

            if (!fileList.isEmpty()){

                //Constructors for tickets
                for (int x = 0; x < fileList.size(); x += 5) {
                    try {
                        int id = Integer.parseInt(fileList.get(x));
                        String des = fileList.get(x+1);
                        int priority = Integer.parseInt(fileList.get(x + 2));
                        String rep = fileList.get(x + 3);
                        //converts date value from string
                        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
                        Date date = df.parse(fileList.get(x + 4));
                        Ticket readTic = new Ticket(id, des, priority, rep, date);
                        //sorts tickets by priority
                        addTicketInPriorityOrder(ticketQueue, readTic);

                    } catch (ParseException pe) {
                        {
                            System.out.println("WOOPS!!! Date failed to parse!");
                        }
                    }
                }
                //keep ticket id from restarting at 1
                Ticket lastID= ticketQueue.get(0);
                int most=lastID.getTicketID();
                Ticket.setStaticTicketIDCounter(most+1);
                //gets largest number for ticket ID
                for (Ticket ticket:ticketQueue){
                    if (ticket.getTicketID()>most){
                        most = ticket.getTicketID();
                        Ticket.setStaticTicketIDCounter(most+1);
                    }
                }

                //printAllTickets(ticketQueue);
            }
        }
        catch (IOException ex){
            System.out.println("File not found");
        }

        NewTicketGUI newTGUI = new NewTicketGUI(resolvedTickets, ticketQueue);


       /* while(true){


            System.out.println("1. Enter Ticket\n2. Delete By ID \n3. Search by Name"+
                    "\n4. Delete by Issue\n5. Display All Tickets\n6. Quit");
            int task = getPositiveIntInput();

            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);

            } else if (task == 2) {
                //delete a ticket
                deleteTicketbyID(resolvedTickets, ticketQueue);
                //search for keyword in ticket description
            }  else if (task == 3) {
                //search description for keyword and display list
                searchTicketbyName(ticketQueue);
                //search for keyword in description then remove using a displayed list
            }  else if (task == 4) {
                //delete a ticket
                LinkedList<Ticket> n3 =searchTicketbyName(ticketQueue);
                deleteTicketbyID(resolvedTickets, ticketQueue, n3);
            }  else if (task == 5){
                printAllTickets(ticketQueue);
            }  else if (task == 6) {
                //Quit. Future prototype may want to save all tickets to a file
                System.out.println("Quitting program");
                break;
            } else {
                //requests valid data from the user
                System.out.println("Please choose an option from the list");
            }
*/
        }

//        //methods to write data to files
//        writeResolved(resolvedTickets);
//        writeOpen(ticketQueue);
//        scan.close();
//
//
//    }
//    //Write resolved tickets to a text file
//
//    private static void writeResolved(LinkedList<Ticket> resolvedTickets){
//        //get variables to generate a file name with current date parts
//        Date date = new Date();
//        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
//        String day = sdfDay.format(date);
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");
//        String month = sdfMonth.format(date);
//        SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");
//        String year= sdfYear.format(date);
//        //generated file name
//        String fileName = "Resolved_tickets_as_of_"+month+"_"+day+"_"+year+".txt";
//        try (BufferedWriter bufWrite = new BufferedWriter(new FileWriter(fileName))) {
//            for (Ticket t : resolvedTickets) {
//                //call overridden toString from ticket class
//                String writeFileLine = t.toString("resolved");
//                bufWrite.write(writeFileLine);
//            }
//        }
//        catch(IOException ex){
//            System.out.println("An IO Exception occurred");
//        }
//
//    }
//    private static void writeOpen(LinkedList<Ticket> ticketQueue){
//        if (!ticketQueue.isEmpty()) {
//            try {
//                //set up writing to facilitate reading of the file to generate objects
//                try (BufferedWriter bufWrite = new BufferedWriter(new FileWriter("openTickets.txt"))) {
//                    for (Ticket t : ticketQueue) {
//                        int id = t.getTicketID();
//                        bufWrite.write(Integer.toString(id));
//                        bufWrite.newLine();
//                        bufWrite.write(t.getDescription());
//                        bufWrite.newLine();
//                        int p=t.getPriority();
//                        bufWrite.write(Integer.toString(p));
//                        bufWrite.newLine();
//                        bufWrite.write(t.getReporter());
//                        bufWrite.newLine();
//                        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
//                        String date = df.format(t.getDateReported());
//                        bufWrite.write(date);
//                        bufWrite.newLine();
//                    }
//                }
//            } catch (IOException ex) {
//                System.out.println("WHOOPS! An IO Exception occurred");
//            }
//        }
//    }
//    private static LinkedList<Ticket> searchTicketbyName(LinkedList<Ticket> ticketQueue) {
//        LinkedList<Ticket> searchResults = new LinkedList<>();
//        if (ticketQueue.isEmpty()){
//            System.out.println("No open tickets to search");
//            return searchResults;
//        }
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter the keyword(s) you would like to search for");
//        String search = scanner.nextLine();
//        //search list of tickets for keyword
//        for (Ticket t :ticketQueue) {
//            if (t.getDescription().contains(search)) {
//                searchResults.add(t);
//            }
//        }
//        printAllTickets(searchResults);
//        return searchResults;
//    }
//
//    protected static void deleteTicketbyID(LinkedList<Ticket> resolvedTickets, LinkedList<Ticket> ticketQueue) {
//        printAllTickets(ticketQueue);   //display list for user
//
//        if (ticketQueue.size() == 0) {    //no tickets!
//            System.out.println("No tickets to delete!\n");
//            return;
//        }
//
//
//        System.out.println("Enter ID of ticket to delete");
//
//
//        if (found == false) {
//            System.out.println("Ticket ID not found, no ticket deleted");
//            System.out.println("Would you like to try again? Y for yes");
//            Scanner sc = new Scanner(System.in);
//            String more = sc.nextLine();
//            if (more.equalsIgnoreCase("y")) {
//                //allow user to re-enter the ticket id
//                deleteTicketbyID(resolvedTickets, ticketQueue);
//            }
//        }
//        printAllTickets(ticketQueue);  //print updated list
//
//    }
//    //override method to include search results from search method
//    protected static void deleteTicketbyID(LinkedList<Ticket> resolvedTickets,
//                                           LinkedList<Ticket> ticketQueue,
//                                           LinkedList<Ticket> searchResults) {
//        if (ticketQueue.size()==0) {
//            System.out.println("No tickets found!\n");
//            return;
//        }
//        if (searchResults.size() == 0) {    //no tickets!
//            System.out.println("No tickets found!\n");
//            return;
//        }
//        printAllTickets(searchResults);
//        System.out.println("Enter ID of ticket to delete");
//        int deleteID = getPositiveIntInput();
//
//        //Loop over all tickets. Delete the one with this ticket ID
//        boolean found = false;
//
//        for (Ticket ticket : searchResults) {
//            if (ticket.getTicketID() == deleteID) {
//                found = true;
//                //get/set resolution for ticket to be deleted
//                setResolutionAndDate(ticket);
//                ticketQueue.remove(ticket);
//                //add ticket to resolved list
//                resolvedTickets.add(ticket);
//                System.out.println(String.format("Ticket %d deleted", deleteID));
//                break; //don't need loop any more.
//            }
//        }
//
//        if (found == false) {
//            System.out.println("Ticket ID not found, no ticket deleted");
//            System.out.println("Would you like to try again? Y for yes");
//            Scanner sc = new Scanner(System.in);
//            String more = sc.nextLine();
//            if (more.equalsIgnoreCase("y")) {
//                deleteTicketbyID(resolvedTickets, ticketQueue, searchResults);
//            }
//        }
//        printAllTickets(ticketQueue);  //print updated list
//
//    }
//
//    //Move the adding ticket code to a method
//    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
//        Scanner sc = new Scanner(System.in);
//        boolean moreProblems = true;
//        String description, reporter;
//        Date dateReported = new Date(); //Default constructor creates date with current date/time
//        int priority;
//
//        while (moreProblems){
//            System.out.println("Enter problem");
//            description = sc.nextLine();
//            System.out.println("Who reported this issue?");
//            reporter = sc.nextLine();
//            System.out.println("Enter priority of " + description);
//            priority = Integer.parseInt(sc.nextLine());
//
//            Ticket t = new Ticket(description, priority, reporter, dateReported);
//            //ticketQueue.add(t);
//            addTicketInPriorityOrder(ticketQueue, t);
//
//            printAllTickets(ticketQueue);
//
//            System.out.println("More tickets to add?");
//            String more = sc.nextLine();
//            if (more.equalsIgnoreCase("N")) {
//                moreProblems = false;
//            }
//        }
//    }

    protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket){

        //This list is either empty or sorted

        if (tickets.size() == 0 ) {
            //If list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }

        //Tickets with highest priority go to front
        //Tickets with the lowest value of their priority go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size() ; x++) {

            //LOOPS: if newTicket is higher or equal priority than the this element, add it in front, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }

        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }
//    protected static void printAllTickets(LinkedList<Ticket> tickets) {
//        System.out.println(" ------- List of Open Tickets ----------");
//
//        for (Ticket t : tickets) {
//
//            //Write a toString method in Ticket class
//            System.out.println(t);
//        }
//
//        System.out.println(" ------- End of ticket list ----------");
//    }
//
//    private static void setResolutionAndDate(Ticket ticket){
//        Scanner scanner= new Scanner(System.in);
//        System.out.println("Please give a reason for deleting the ticket:");
//        String res = scanner.nextLine();
//        //time stamp resolution
//        ticket.setResolutionDate(new Date());
//        ticket.setResolution(res);
//
//    }
//    //Validation method
//    private static int getPositiveIntInput() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            try {
//                String stringInput = scanner.nextLine();
//                int intInput = Integer.parseInt(stringInput);
//                if (intInput >= 0) {
//                    return intInput;
//                } else {
//                    System.out.println("Please enter a valid Ticket ID#");
//
//                    continue;
//                }
//            } catch (NumberFormatException ime) {
//                System.out.println("Please enter a valid Ticket ID#");
//            }
//
//        }

    }

