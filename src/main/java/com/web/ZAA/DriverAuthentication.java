package com.web.ZAA;

import com.web.ZAA.Core.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@RestController
public class DriverAuthentication implements Authentication {

    private Connection system;
    private Account driver;

    public DriverAuthentication() throws SQLException, ClassNotFoundException {
        system= Database.getInstance();
    }
    public Connection getSystem(){
        return system;
    }

    @PostMapping("/driver/login")
    @Override
    public Account login(@PathParam("username") String username, @PathParam("password") String password) {
        //Scanner read=new Scanner(System.in);
        System.out.println("Driver Login:");
        System.out.print("Username: ");
        System.out.print("Password: ");
        driver = Load.findActiveDriver(username);
        if(driver != null) {
            if(driver.getPassword().equals(password) && driver.getActive()) {
                System.out.println("Logged in as: " + username);
            } else {
                System.out.println("Error: Cannot log in (Either suspended or pending)");
                driver = null;
            }
        }
        return driver;
    }

    @Override
    public void register() {
        Scanner read=new Scanner(System.in);
        System.out.println("Driver Register:");
        System.out.print("Username: ");
        String username=read.nextLine();
        System.out.print("Password: ");
        String password=read.nextLine();
        System.out.print("Mobile Phone: ");
        String mobilePhone=read.nextLine();
        System.out.print("Email: ");
        String email=read.nextLine();
        System.out.print("National ID: ");
        String nationalID=read.nextLine();
        System.out.print("Driving License: ");
        String drivingLicense=read.nextLine();
        try {
            Statement stat = system.createStatement();
            String sql = Database.getAddDriverSQL(username,password,mobilePhone,email,nationalID,drivingLicense);
            stat.executeUpdate(sql);
            Load.pendingDrivers.add(new DriverAccount(username, password, mobilePhone, email, nationalID, drivingLicense));
            System.out.println("Driver account created successfully");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Error: User already exists");
        }
    }

//    //Helper methods
//    private ArrayList<Area> loadDriverAreas(String username) {
//        ArrayList<Area> favAreas = new ArrayList<>();
//        try {
//            Statement stat = system.createStatement();
//            String sql = "SELECT * FROM areas WHERE name = (SELECT areaName from fav_areas WHERE driverUsername = '"+username+"')";
//            ResultSet rs = stat.executeQuery(sql);
//            while (rs.next()) {
//                favAreas.add(new Area(rs.getString("name")));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return favAreas;
//    }
}
