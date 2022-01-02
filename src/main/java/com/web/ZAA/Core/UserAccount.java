package com.web.ZAA.Core;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserAccount extends Account implements Observer {
    private boolean firstRide;
    private int discount;
    private Date birthDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UserAccount(String username, String password, String mobilePhone, String email, Date birthDate) throws SQLException, ClassNotFoundException {
        super();
        setUsername(username);
        setPassword(password);
        setMobilePhone(mobilePhone);
        setEmail(email);
        active = true;
        firstRide = true;
        discount = 0;
        this.birthDate=birthDate;
    }

    public boolean getIsFirstRide() {
        return firstRide;
    }

    public void setIsFirstRide(boolean firstRide) {
        this.firstRide = firstRide;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Ride requestRide(Area source, Area destination, int noOfPassengers) {
        discount = 0;
        Date now = new Date();
        SimpleDateFormat dayMonthFormatter = new SimpleDateFormat("MM/dd");
        String dayMonth = dayMonthFormatter.format(now);
        if (this.firstRide)
        {
            discount += 10;
            firstRide = false;
        }
        if (noOfPassengers >= 2)
        {
            discount+=5;
        }
        if (Database.checkIfHoliday())
        {
            discount += 5;
        }
        if(dayMonthFormatter.format(birthDate).equals(dayMonth)){
            discount += 10;
        }
        Ride ride = new Ride(source, destination, this, noOfPassengers);
        return ride;
    }

    public void acceptOffer(Offer offer) {
        offer.accept();
    }

    @Override
    public void update(Object offer) {
        Offer o = (Offer) offer;
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        notifications.add(new Notification(offer.toString(), now, o));
    }

    public void rateDriver(DriverAccount driver, int rate) // this function in the UserAccount class
    {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.print("Enter your rating in numbers: ");
        Rating rating = new Rating();
//        int rate = keyboard.nextInt();
        rating.setRate(rate);
        rating.setUsername(this.getUsername());
        driver.insertAndUpdateAverageRating(rating);
    }
}