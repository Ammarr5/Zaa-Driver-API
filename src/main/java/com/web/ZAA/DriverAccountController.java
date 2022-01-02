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
    @PostMapping("/driver/addfavorite")
    public String addFavorite(@PathParam("username") String username, @PathParam("password") String password, @PathParam("favarea") String favarea) throws SQLException, ClassNotFoundException {
        DriverAuthentication driverAuthentication = new DriverAuthentication();
         DriverAccount driverAccount = (DriverAccount) driverAuthentication.login(username,password);
        if (driverAccount == null)
            return null;
         Area area = new Area(favarea);
         driverAccount.addFavArea(area);
         return favarea;
    }

    @RequestMapping("/driver/notification")
    public List<Notification> returnNotifications(@PathParam("username") String username, @PathParam("password") String password) throws SQLException, ClassNotFoundException, ParseException {
        DriverAccount driverAccount = null;
        DriverAuthentication driverAuthentication = new DriverAuthentication();
        driverAccount = (DriverAccount) driverAuthentication.login(username,password);
        if (driverAccount == null)
            return null;
        return driverAccount.getNotifications();
    }
    @PostMapping("driver/listratings")
    public List<String> listRatings(@PathParam("username") String username, @PathParam("password") String password) throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<String> list = new ArrayList<>();
        DriverAuthentication driverAuthentication = new DriverAuthentication();
        DriverAccount driverAccount = (DriverAccount) driverAuthentication.login(username,password);
        if (driverAccount == null)
            return null;
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
