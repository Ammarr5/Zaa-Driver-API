package com.web.ZAA.Core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Load {
    public static ArrayList<DriverAccount> drivers = new ArrayList<>();
    public static ArrayList<DriverAccount> pendingDrivers = new ArrayList<>();
    public static ArrayList<UserAccount> users = new ArrayList<>();
    public static ArrayList<Area> areas = new ArrayList<>();
    public static ArrayList<Ride> rides = new ArrayList<>();

    public static void loadInit() {
        loadDrivers();
        loadPendingDrivers();
        loadAreas();
        loadUsers();

        //Set drivers fav areas
        for(DriverAccount driver : drivers) {
            try {
                Statement stat = Database.getInstance().createStatement();
                String sql = "SELECT * FROM areas WHERE name in (SELECT areaName from fav_areas WHERE driverUsername = '"+driver.getUsername()+"')";
                ResultSet rs = stat.executeQuery(sql);
                ArrayList<Area> favAreas = new ArrayList<>();
                while (rs.next()) {
                    favAreas.add(findArea(rs.getString("name")));
                }
                driver.setFavAreas(favAreas);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

        //Set areas observers
        for(Area area : areas) {
            try {
                Statement stat = Database.getInstance().createStatement();
                String sql = "SELECT * FROM users WHERE username in (SELECT driverUsername from fav_areas WHERE areaName = '"+area.getName()+"')";
                ResultSet rs = stat.executeQuery(sql);
                ArrayList<Observer> observers = new ArrayList<>();
                while (rs.next()) {
                    observers.add(findActiveDriver(rs.getString("username")));
                }
                area.setObservers(observers);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void loadDrivers() {
        try{
            Statement stat = Database.getInstance().createStatement();
            String sql = "SELECT * FROM users WHERE is_driver = 1 AND is_pending = 0";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                DriverAccount driver = new DriverAccount(rs.getString("username"), rs.getString("password"), rs.getString("mobile_number"), rs.getString("email"), rs.getString("natID"), rs.getString("drivingLicense"));
                driver.setActive(true);
                drivers.add(driver);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void loadPendingDrivers() {
        try{
            Statement stat = Database.getInstance().createStatement();
            String sql = "SELECT * FROM users WHERE is_driver = 1 AND is_pending = 1";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                pendingDrivers.add(new DriverAccount(rs.getString("username"), rs.getString("password"), rs.getString("mobile_number"), rs.getString("email"), rs.getString("natID"), rs.getString("drivingLicense")));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void loadAreas() {
        try {
            Statement stat = Database.getInstance().createStatement();
            String sql = "SELECT * FROM areas";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                areas.add(new Area(rs.getString("name")));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void loadUsers() {
        try{
            Statement stat = Database.getInstance().createStatement();
            String sql = "SELECT * FROM users WHERE is_driver = 0";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()){
                users.add(new UserAccount(rs.getString("username"), rs.getString("password"), rs.getString("mobile_number"), rs.getString("email"),rs.getDate("birthDay")));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //Helper methods
    public static Area findArea(String name) {
        for(Area area : areas) {
            if(area.getName().equals(name)){
                return area;
            }
        }
        return null;
    }

    public static DriverAccount findActiveDriver(String username) {
        for(DriverAccount driver : drivers) {
            if(driver.getUsername().equals(username)){
                return driver;
            }
        }
        return null;
    }

    public static DriverAccount findPendingDriver(String username) {
        for(DriverAccount driver : pendingDrivers) {
            if(driver.getUsername().equals(username)){
                return driver;
            }
        }
        return null;
    }

    public static UserAccount findUser(String username) {
        for(UserAccount user : users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static Ride findRide(String username) {
        for (Ride ride : rides) {
            if (ride.getUser().getUsername().equals(username)) {
                return ride;
            }
        }
        return null;
    }
}
