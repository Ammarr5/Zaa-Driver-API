package com.web.ZAA;

import com.web.ZAA.Core.Load;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class ZAA {

    private static AdminAuthentication admin;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
//        System.out.println("This is a static test case, instructions will be written before executing them (instructions start with '**':-");
//
        Load.loadInit();
//        Scanner read=new Scanner(System.in);
//
//        user=new UserAuthentication();
//        UserAccount userAcc=(UserAccount) user.login();
//        if(userAcc!=null){
//            System.out.println("You logged in successfully!");
//        }
//        else{
//            System.out.println("login failed");
//        }
//        driver=new DriverAuthentication();
//
//        admin= new AdminAuthentication();
//        Admin adminAcc=admin.login();
//        if(adminAcc!=null){
//            System.out.println("You logged in successfully!");
//            adminAcc.applyDiscount("dokki",10);
//        }
//        else{
//            System.out.println("login failed");
//        }
//
//        DriverAccount driverAcc = (DriverAccount) driver.login();
//        if(driverAcc!=null){
//            System.out.println("You logged in successfully!");
//        }
//        else{
//            System.out.println("login failed");
//        }
//        Area haram=Load.findArea("haram");
//        Area dokki=Load.findArea("dokki");
//        System.out.println("**Setting haram as a fav area for this driver");
//        driverAcc.addFavArea(haram);
//        System.out.println("**User requesting a ride to haram");
//        Ride userRide = userAcc.requestRide(haram, dokki, 1);
//        System.out.println("**Driver making an offer to that ride");
//        Offer driverOffer = driverAcc.makeOffer(25, userRide);
//        System.out.println(dokki.getDiscount());
//        System.out.println("**User accepts offer");
//        userAcc.acceptOffer(driverOffer);
//        System.out.println("**driver's notifications array:");
//        System.out.println(userAcc.getNotifications());
//        System.out.println("**driver notifications array:");
//        System.out.println(driverAcc.getNotifications());
//        driverAcc.startTrip(driverOffer);
//        driverAcc.endTrip(driverOffer);
//        System.out.println("Driver balance is "+driverAcc.getBalance());
//        UserInterface ui=new UserInterface();
//        ui.chooseEntityMenu();
    }
}
