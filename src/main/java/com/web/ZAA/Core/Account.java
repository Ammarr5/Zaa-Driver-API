package com.web.ZAA.Core;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class Account {
    private String username;
    private String password;
    private String mobilePhone;
    private String email;
    private String type;
    protected boolean active;
    private Connection system;
    ArrayList<Notification> notifications;

    public Account() throws SQLException, ClassNotFoundException {
        notifications = new ArrayList<>();
        system = Database.getInstance();
    }
    public void setSystem() throws SQLException, ClassNotFoundException {
        system=Database.getInstance();
    }
//    public Connection getSystem(){
//        return system;
//    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public void setMobilePhone(String mobilePhone){
        this.mobilePhone = mobilePhone;
    }
    public String getMobilePhone(){
        return mobilePhone;
    }
    public void setEmail(String email){
        this.email = email;
        try{
            Statement stat = Database.getInstance().createStatement();
            String sql = Database.getUpdateEmailSQL(email,username);
            stat.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public String getEmail(){
        return email;
    }
    public void setActive(boolean active){
        try{
            this.active = active;
            Statement stat = Database.getInstance().createStatement();
            String sql = Database.getUpdateActivitySQL(username);
            stat.executeUpdate(sql);
            if(this instanceof DriverAccount) {
                Load.pendingDrivers.remove(this);
                Load.drivers.add((DriverAccount) this);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean getActive(){
        return active;
    }
    public void setType(Account account){
        if(account instanceof UserAccount){
            type="user";
        }
        else{
            type="driver";
        }
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }


    public String getType(){
        return type;
    }
    @Override
    public String toString() {
        return username;
    }
}