package com.plemel;

import java.util.Date;
public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;

    //STATIC Counter - accessible to all Ticket objects.

    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Private
    private static int staticTicketIDCounter = 1;


    //The ID for each ticket - instance variable.
    private int ticketID;

    private String resolution;
    private Date resolutionDate;

    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }
    //override ticket constructor for ticket that already have IDs
    public Ticket(int id, String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = id;

    }
    public String toString() {
        return ("ID= " + this.getTicketID() + " Issue: " + this.description
                + " Priority: " + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
    //for resolved tickets
    public  String toString(String resolved){
        return ("ID= " + this.getTicketID() + " \nIssue: " + this.description
                + " \nPriority: " + this.priority + " \nReported by: "
                + this.reporter + " \nReported on: " + this.dateReported+
                "\nResolution: "+this.resolution+"\nResolution Date: "+this.resolutionDate);
    }
    public int getTicketID() {
        return ticketID;
    }
    //setter method for static ticket property
    public static void setStaticTicketIDCounter(int staticTicketIDCounter) {
        Ticket.staticTicketIDCounter = staticTicketIDCounter;
    }
    protected int getPriority() {
        return priority;
    }
    public String getDescription() {
        return description;
    }
    public String getReporter() {
        return reporter;
    }
    public Date getDateReported() {
        return dateReported;
    }
    public String getResolution() {
        return resolution;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public Date getResolutionDate() {
        return resolutionDate;
    }
    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }
}