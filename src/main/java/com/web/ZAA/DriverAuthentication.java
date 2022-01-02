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
public class DriverAuthentication {

    private Connection system;
    private Account driver;

    public DriverAuthentication() throws SQLException, ClassNotFoundException {
        system= Database.getInstance();
    }


    @PostMapping("/driver/login")
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
    @PostMapping("/driver/register")
    public DriverAccount register(@PathParam("username") String username,
                            @PathParam("password") String password,
                            @PathParam("mobilephone") String mobilephone,
                            @PathParam("email") String email,
                            @PathParam("nationalid") String nationalid,
                            @PathParam("drivinglicense") String drivinglicense) {

        DriverAccount driverAccount = null;

        try {
            Statement stat = system.createStatement();
            String sql = Database.getAddDriverSQL(username,password,mobilephone,email,nationalid,drivinglicense);
            stat.executeUpdate(sql);
            driverAccount = new DriverAccount(username, password, mobilephone, email, nationalid, drivinglicense);
            Load.pendingDrivers.add(driverAccount);
            System.out.println("Driver account created successfully");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Error: User already exists");
        }
        return driverAccount;
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
