package com.web.ZAA;

import com.web.ZAA.Core.Area;
import com.web.ZAA.Core.DriverAccount;
import com.web.ZAA.Core.Load;
import com.web.ZAA.Core.UserAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.sql.SQLException;
import java.util.Date;

@RestController
public class UserController {

    @GetMapping("/user/{username}/birthday")
    public Date getBirthday(@PathVariable String username) {
        UserAccount user = Load.findUser(username);
        if (user != null) {
            return user.getBirthDate();
        }
        return null;
    }

    @PostMapping("/user/request-ride")
    public boolean requestRide(@PathParam("source") String source,
                               @PathParam("destination") String destination,
                               @PathParam("noOfPassengers") int noOfPassengers,
                               @PathParam("username") String username,
                               @PathParam("password") String password) throws SQLException, ClassNotFoundException {
        UserAccount user = (UserAccount) (new UserAuthentication()).login(username, password);
        Area sourceArea = Load.findArea(source);
        Area destinationArea = Load.findArea(destination);
        if (user == null || sourceArea == null || destinationArea == null) {
            return false;
        }
        user.requestRide(sourceArea, destinationArea, noOfPassengers);
        return true;
    }

    @PostMapping("/user/rate-driver")
    public boolean rateDriver(@PathParam("username") String username,
                              @PathParam("password") String password,
                              @PathParam("driverUsername") String driverUsername,
                              @PathParam("rate") int rate) throws SQLException, ClassNotFoundException {
        UserAccount user = (UserAccount) (new UserAuthentication()).login(username, password);
        DriverAccount driver = Load.findActiveDriver(driverUsername);
        if (user == null || driver == null) {
            return false;
        }
        user.rateDriver(driver, rate);
        return true;
    }
}
