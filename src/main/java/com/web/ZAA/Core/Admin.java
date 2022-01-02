package com.web.ZAA.Core;

import java.sql.Connection;
import java.sql.SQLException;

public class Admin {
    private String username;
    private String password;
    private Connection system;

    public Admin(String username,String password) throws SQLException, ClassNotFoundException {
        this.username=username;
        this.password=password;
        system = Database.getInstance();
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void listPendingDrivers() {
        for(DriverAccount driver : Load.pendingDrivers) {
            System.out.println("Username: " + driver.getUsername() + ", License number: " + driver.getDrivingLicense());
        }
    }

    public void verifyDriver(String username) {
        DriverAccount driver = Load.findPendingDriver(username);
        if(driver != null) {
            driver.setActive(true);
        }
    }

    public void suspendPerson(String username) {
        if(Load.findActiveDriver(username) != null){
            Load.findActiveDriver(username).setActive(false);
        }
        else if(Load.findUser(username) != null) {
            Load.findUser(username).setActive(false);
        }
        else {
            System.out.println("Error: This person is either suspended or doesn't exist");
        }
    }

    public void showRides() {
        for (Ride ride : Load.rides) {
            System.out.println(ride);
        }
    }

    public void applyDiscount(String areaName, int discount){
        boolean found=false;
        for(Area area:Load.areas){
            System.out.println(area.getName());
            if(area.getName().equals(areaName)){
                area.setDiscount(discount);
                found=true;
            }
        }
        if(found){
            System.out.println(discount+"% Discount added to "+areaName);
        }
        else{
            System.out.println("Area is not available!");
        }
    }

//    public Connection getSystem(){
//        return system;
//    }
}
