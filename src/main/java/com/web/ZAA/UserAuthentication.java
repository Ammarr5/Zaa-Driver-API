package com.web.ZAA;

import com.web.ZAA.Authentication;
import com.web.ZAA.Core.Account;
import com.web.ZAA.Core.Database;
import com.web.ZAA.Core.Load;
import com.web.ZAA.Core.UserAccount;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserAuthentication implements Authentication {
    private Connection system;
    private Account user;

    public UserAuthentication() throws SQLException, ClassNotFoundException {
        system= Database.getInstance();
    }
    public Connection getSystem(){
        return system;
    }

    @PostMapping("/user/login")
    @Override
    public Account login(@PathParam("username") String username, @PathParam("password") String password) {
        //Scanner read=new Scanner(System.in);
        System.out.println("User Login:");
        System.out.print("Username: ");
        System.out.print("Password: ");
        user = Load.findUser(username);
        if(user != null) {
            if(user.getPassword().equals(password) && user.getActive()) {
                System.out.println("Logged in as: " + username);
            } else {
                user = null;
                System.out.println("Error : can't log in (Either suspended or not found)");
            }
        }
        return user;
    }

    @Override
    public void register() {
        Scanner regRead=new Scanner(System.in);
        System.out.println("User Register:");
        System.out.print("Username: ");
        String username=regRead.nextLine();
        System.out.print("Password: ");
        String password=regRead.nextLine();
        System.out.print("Mobile Phone: ");
        String mobilePhone=regRead.nextLine();
        System.out.print("Email: ");
        String email=regRead.nextLine();
        System.out.print("Birthday (mm-dd): ");
        String birthday=regRead.nextLine();
        Date birthDate=null;
        try {
            birthDate=new SimpleDateFormat("MM-dd").parse(birthday);
        } catch (ParseException e) {
            System.out.println("Error: wrong birthday format!");
        }
        try {
            Statement stat = system.createStatement();
            String sql = Database.getAddUserSQL(username,password,mobilePhone,email,birthDate);
            stat.executeUpdate(sql);
            Load.users.add(new UserAccount(username, password, mobilePhone, email,birthDate));
            System.out.println("User account created successfully");
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Username already registered");
        }
    }
}