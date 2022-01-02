package com.web.ZAA.Core;

import java.util.ArrayList;
import java.util.Date;

public class Ride implements Subject, Notifiable{
    Area source;
    Area destination;
    UserAccount user;
    ArrayList<Offer> offers;
    ArrayList<RideEvent> events;
    Date startTime;
    Date endTime;
    int numberOfPassengers;
    int numberOfEmptySeats;

    public Ride(Area source, Area destination, UserAccount user, int numberOfPassengers) {
        this.source = source;
        this.destination = destination;
        this.user = user;
        source.notifyObservers(this);
        offers = new ArrayList<>();
        events = new ArrayList<>();
        Load.rides.add(this);
        startTime=null;
        endTime=null;
        this.numberOfPassengers = numberOfPassengers;
        this.numberOfEmptySeats = numberOfPassengers-1;
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
        addEvent(new RideEvent(offer.getDriver().getUsername(), offer.getUserPrice()));
        notifyObservers(offer);
    }

    public void showEvents() {
        for (RideEvent event : events) {
            System.out.println(event);
        }
    }

    public void addEvent(RideEvent event) {
        events.add(event);
    }


    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public void setSource(Area source) {
        this.source = source;
    }

    public void setDestination(Area destination) {
        this.destination = destination;
    }

    public Area getSource() {
        return source;
    }

    public Area getDestination() {
        return destination;
    }

    public UserAccount getUser() {
        return user;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(Object object) {
        user.update(object);
    }

    @Override
    public String toString() {
        return "Ride from " + source + " to " + destination;
    }

    public void startRide(Offer offer){
        startTime=new Date();
        System.out.println("Ride started at "+startTime);
        addEvent(new RideEvent(offer.getDriver().getUsername(), getUser().getUsername(), EventType.LOCATION_ARRIVAL));
    }

    public void endRide(Offer offer){
        endTime=new Date();
        offer.getDriver().updateBalance(offer.getDriverPrice());
        System.out.println("Ride ended at "+endTime);
        addEvent(new RideEvent(offer.getDriver().getUsername(), getUser().getUsername(), EventType.DESTINATION_ARRIVAL));
    }
}