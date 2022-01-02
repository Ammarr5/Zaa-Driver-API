package com.web.ZAA;

import com.web.ZAA.Core.Account;
import com.web.ZAA.Core.Area;
import com.web.ZAA.Core.DriverAccount;
import com.web.ZAA.Core.Load;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.server.PathParam;
import java.sql.SQLException;
import java.util.List;

@RestController
public class AdminController {
    @PostMapping("/admin/pendingDrivers")
    public List<DriverAccount> listPendingDrivers(@PathParam("adminUsername") String adminUsername, @PathParam("adminPassword") String adminPassword) throws SQLException, ClassNotFoundException {
        Admin admin = (Admin) (new AdminAuthentication()).login(adminUsername, adminPassword);
        if(admin!=null) {
            return Load.pendingDrivers;
        }
        return null;
    }

    @PostMapping("/admin/verifyDriver")
    public DriverAccount verifyDriver(@PathParam("username") String username, @PathParam("adminUsername") String adminUsername, @PathParam("adminPassword") String adminPassword) throws SQLException, ClassNotFoundException {
        Admin admin = (Admin) (new AdminAuthentication()).login(adminUsername, adminPassword);
        DriverAccount driver = Load.findPendingDriver(username);
        if(driver != null) {
            driver.setActive(true);
        }
        if(admin!=null) {
            return driver;
        }
        return null;
    }

    @PostMapping("/admin/suspendAccount")
    public Account suspendPerson(@PathParam("adminUsername") String adminUsername, @PathParam("adminPassword") String adminPassword, @PathParam("username") String username) throws SQLException, ClassNotFoundException {
        Admin admin = (Admin) (new AdminAuthentication()).login(adminUsername, adminPassword);
        Account account=null;
        if(Load.findActiveDriver(username) != null){
            account=Load.findActiveDriver(username);
            account.setActive(false);
        }
        else if(Load.findUser(username) != null) {
            account=Load.findUser(username);
            account.setActive(false);
        }
        if(admin!=null) {
            return account;
        }
        return null;
    }

    @PostMapping("/admin/addAreaDiscount")
    public Area applyDiscount(@PathParam("adminUsername") String adminUsername, @PathParam("adminPassword") String adminPassword,@PathParam("areaName") String areaName) throws SQLException, ClassNotFoundException {
        Admin admin = (Admin) (new AdminAuthentication()).login(adminUsername, adminPassword);
        Area foundArea=null;
        for(Area area:Load.areas){
            System.out.println(area.getName());
            if(area.getName().equals(areaName)){
                area.setDiscount(10);
                foundArea=area;
            }
        }
        if(admin!=null) {
            if(foundArea!=null){
                return foundArea;
            }
            return null;
        }
        return null;
    }
}
