package com.web.ZAA;

import com.web.ZAA.Core.Account;
import com.web.ZAA.Core.Database;
import com.web.ZAA.Core.Load;
import com.web.ZAA.Core.UserAccount;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UserAuthentication {
    private Connection system;
    private Account user;

    public UserAuthentication() throws SQLException, ClassNotFoundException {
        system= Database.getInstance();
    }
//    public Connection getSystem(){
//        return system;
//    }

    @PostMapping("/user/login")
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
                System.out.println("Error : can't log in (Either suspended or not found)");
                user = null;
            }
        }
        return user;
    }

    @PostMapping("/user/register")

    public Account register(@PathParam("username") String username, @PathParam("password") String password, @PathParam("mobilephone") String mobilephone, @PathParam("email") String email, @PathParam("birthdate") String birthdate)
    {

        Date birthDate=null;
        UserAccount userAccount = null;
        try {
            birthDate=new SimpleDateFormat("MM-dd").parse(birthdate);
        } catch (ParseException e) {
            System.out.println("Error: wrong birthday format!");
        }
        try {
            Statement stat = system.createStatement();
            String sql = Database.getAddUserSQL(username,password,mobilephone,email,birthDate);
            stat.executeUpdate(sql);
            userAccount = new UserAccount(username, password, mobilephone, email,birthDate);
            Load.users.add(userAccount);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Username already registered");
        }
        return userAccount;
    }
}