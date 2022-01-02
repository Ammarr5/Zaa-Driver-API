package com.web.ZAA;

import com.web.ZAA.Core.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DriverAccountController {
    DriverAccount driverAccount = null;
    @PostMapping("/driver/addfav")
    public String addfav(@PathParam("username") String username, @PathParam("password") String password, @PathParam("favarea") String favarea) throws SQLException, ClassNotFoundException {
        DriverAuthentication driverAuthentication = new DriverAuthentication();
         driverAccount = (DriverAccount) driverAuthentication.login(username,password);
         Area area = new Area(favarea);
         driverAccount.addFavArea(area);
         return favarea;
    }

    @RequestMapping("/driver/notif")
    public List<Notification> returnNoti(@PathParam("username") String username, @PathParam("password") String password) throws SQLException, ClassNotFoundException, ParseException {
        DriverAccount driverAccount = null;
        DriverAuthentication driverAuthentication = new DriverAuthentication();
        driverAccount = (DriverAccount) driverAuthentication.login(username,password);
        Date birthDate=null;
        birthDate=new SimpleDateFormat("MM-dd").parse("28-10");
        UserAccount userAccount = new UserAccount("adel", "123", "q34", "3ljl", birthDate);
        Area area1 = new Area("Haram");
        Area area2 = new Area("Dokki");
        Ride ride = new Ride(area1, area2, userAccount, 2);
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        driverAccount.addNotification(new Notification("new ride from Haram", now, ride));
        return driverAccount.getNotifications();
    }
    @PostMapping("driver/listratings")
    public List<String> listRatings(@PathParam("username") String username, @PathParam("password") String password) throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<String> list = new ArrayList<>();
        DriverAccount driverAccount = null;
        DriverAuthentication driverAuthentication = new DriverAuthentication();
        driverAccount = (DriverAccount) driverAuthentication.login(username,password);
        Date birthDate=null;
        birthDate=new SimpleDateFormat("MM-dd").parse("28-10");
        UserAccount userAccount = new UserAccount("adelo", "123", "q34", "3ljl", birthDate);
        Rating rating  = new Rating();
        rating.setRate(5);
        rating.setUsername("ehab");
        driverAccount.insertAndUpdateAverageRating(rating);
        try {
            Statement stat = Database.getInstance().createStatement();
            String sql = Database.getDriverRatingSQL(driverAccount.getUsername());
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("userUsername") + " - " + rs.getInt("rate") + " stars");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
