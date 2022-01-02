package com.web.ZAA.Core;

public class Offer implements Subject, Notifiable {
    double driverPrice;
    double userPrice;
    Ride ride;
    DriverAccount driver;
    int totalDiscount;

    public Offer(double price, Ride ride, DriverAccount driver) {
        totalDiscount = ride.getDestination().getDiscount() + ride.getUser().getDiscount();
        userPrice = price-(price*totalDiscount/100);
        driverPrice = price;
        this.ride = ride;
        this.driver = driver;
    }

    public double getDriverPrice() {
        return driverPrice;
    }

    public double getUserPrice() {
        return userPrice;
    }

    public Ride getRide() {
        return ride;
    }

    public DriverAccount getDriver() {
        return driver;
    }

    public void accept(){
        ride.addEvent(new RideEvent(ride.getUser().getUsername()));
        notifyObservers(this);
    }

    @Override
    public String toString() {
        return driver.getUsername() + " offers " + userPrice + " L.E";
    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(Object object) {
        driver.update(object);
    }
}
