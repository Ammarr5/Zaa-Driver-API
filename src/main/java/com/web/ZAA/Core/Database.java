package com.web.ZAA.Core;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
    private static Database uniqueInstance = null;
    private static Connection con;

    private Database () throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:zaa.db");
        System.out.println("SQLite connected");
    }

    public static Connection getInstance() throws SQLException, ClassNotFoundException {
        if(uniqueInstance==null){
            uniqueInstance= new Database();
        }
        return con;
    }

    public static String getUpdateEmailSQL(String email,String username){
        return "UPDATE users SET email = '" + email + "' WHERE username = '" + username +"'";
    }

    public static String getUpdateActivitySQL(String username){
        return "UPDATE users SET is_active = 1, is_pending = 0 WHERE username = '" + username +"'";
    }

    public static String getAdminsSQL(String username,String password){
        return "SELECT * FROM admins WHERE username = '"+username+"' AND password = '" + password + "'";
    }

    public static String getAddDriverSQL(String username,String password,String mobilePhone,String email,String nationalID,String drivingLicense){
        return "INSERT INTO users" +
                "(username," +
                "password," +
                "mobile_number," +
                "email," +
                "is_driver," +
                "natID," +
                "drivingLicense," +
                "is_active," +
                "is_pending," +
                "areaSubscriptionID)" +
                "VALUES" +
                "('"+ username +"'," +
                "'"+password+"'," +
                "'"+mobilePhone+"'," +
                "'"+email+"'," +
                "1," +
                "'"+nationalID+"'," +
                "'"+drivingLicense+"'," +
                "0," +
                "1," +
                "null);";
    }

    public static String getAddUserSQL(String username,String password,String mobilePhone,String email,Date birthDay){
        SimpleDateFormat sqlDateFormatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String formattedBirthday = sqlDateFormatter.format(birthDay);
        return "INSERT INTO users" +
                "(username," +
                "password," +
                "mobile_number," +
                "email," +
                "is_driver," +
                "natID," +
                "drivingLicense," +
                "is_active," +
                "is_pending," +
                "areaSubscriptionID," +
                "birthDay)" +
                "VALUES" +
                "('"+ username +"'," +
                "'"+password+"'," +
                "'"+mobilePhone+"'," +
                "'"+email+"'," +
                "0," +
                "null," +
                "null," +
                "1," +
                "0," +
                "null,"+
                "'"+formattedBirthday+"');";
    }
    public static String getAddFavAreaSQL(String username, Area area){
        return "INSERT OR IGNORE INTO fav_areas" +
                "(driverUsername, areaName) VALUES ('"+username+"', '"+area.getName()+"')";
    }

    public static String getDriverRatingSQL(String username){
        return "SELECT * FROM ratings WHERE driverUsername = '"+username+"'";
    }

    public static String getAddRatingSQL(String username, Rating rating){
        return "INSERT OR IGNORE INTO ratings" +
                "(userUsername, rate, driverUsername) VALUES ('"+rating.getUsername()+"', '"+rating.getRate()+"', '"+username+"')";
    }

    public static String getDriverRateSQL(String username){
        return "SELECT rate FROM ratings WHERE driverUsername = '"+username+"'";
    }

    public static boolean checkIfHoliday() {
        Date now = new Date();
        SimpleDateFormat dayMonthFormatter = new SimpleDateFormat("MM/dd");
        String dayMonth = dayMonthFormatter.format(now);
        String sql = "SELECT * FROM public_holidays WHERE strftime('%m/%d', date) = '"+dayMonth+"'";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance();
        Statement stat = con.createStatement();
        // 1
//        String sql = "CREATE TABLE users (" +
//                "  username TEXT NOT NULL PRIMARY KEY," +
//                "  password TEXT NOT NULL," +
//                "  mobile_number TEXT NOT NULL," +
//                "  email TEXT NULL," +
//                "  is_driver BOOLEAN NULL," +
//                "  natID TEXT NULL," +
//                "  drivingLicense TEXT NULL," +
//                "  is_active BOOLEAN," +
//                "  is_pending BOOLEAN," +
//                "  areaSubscriptionID TEXT NULL," +
//                "  balance DOUBLE NOT NULL DEFAULT '0.00'," +
//                "  birthDay DATE NULL );";
//        String sql = "INSERT INTO users" +
//                "(username," +
//                "password," +
//                "mobile_number," +
//                "email," +
//                "is_driver," +
//                "natID," +
//                "drivingLicense," +
//                "areaSubscriptionID)" +
//                "VALUES" +
//                "('ammar'," +
//                "1234," +
//                "'010'," +
//                "'a@a.com'," +
//                "0," +
//                "'id'," +
//                "'lc'," +
//                "null);";

        // 2
//        String sql = "CREATE TABLE admins ("+
//                "username TEXT NOT NULL PRIMARY KEY,"+
//                "password TEXT NOT NULL)";

        // 3
//        String sql = "INSERT INTO admins"+
//                "(username, password)"+
//                "VALUES ('admin', 'admin')";

        // 4
//        String sql = "CREATE TABLE areas(" +
//                "name TEXT NOT NULL PRIMARY KEY,"+
//                "discount INTEGER NOT NULL DEFAULT '0')";

        // 5
//        String sql = "INSERT INTO areas"+
//                "(name)"+
//                "VALUES ('Giza')";

        // 6
//        String sql = "CREATE TABLE fav_areas("+
//                "areaName TEXT NOT NULL,"+
//                "driverUsername TEXT NOT NULL," +
//                "UNIQUE(areaName, driverUsername)"+
//                "FOREIGN KEY(areaName) REFERENCES area(name),"+
//                "FOREIGN KEY(driverUsername) REFERENCES users(username))";

        // 7
//        String sql = "CREATE TABLE rides("+
//                "source_name TEXT NOT NULL,"+
//                "destination_name TEXT NOT NULL,"+
//                "caller_username TEXT NOT NULL PRIMARY KEY,"+
//                "FOREIGN KEY(source_name) REFERENCES area(name),"+
//                "FOREIGN KEY(destination_name) REFERENCES area(name),"+
//                "FOREIGN KEY(caller_username) REFERENCES area(name))";

        // 8
//        String sql = "CREATE TABLE offer("+
//                "driverPrice DOUBLE NOT NULL,"+
//                "userPrice DOUBLE NOT NULL,"+
//                "ride_caller TEXT NOT NULL,"+
//                "FOREIGN KEY(ride_caller) REFERENCES rides(caller_username))";

        //9
//        String sql = "CREATE TABLE ratings("+
//                "userUsername TEXT NOT NULL,"+
//                "driverUsername TEXT NOT NULL," +
//                "rate INTEGER NOT NULL,"+
//                "UNIQUE(userUsername, driverUsername),"+
//                "FOREIGN KEY(userUsername) REFERENCES users(username),"+
//                "FOREIGN KEY(driverUsername) REFERENCES users(username))";

        //10
//        String sql = "CREATE TABLE public_holidays(" +
//                "date DATE NOT NULL PRIMARY KEY," +
//                "holiday_name TEXT NULL);";

        //11
//        String sql = "INSERT INTO public_holidays" +
//                "(date, holiday_name)" +
//                "VALUES ('2022-01-07 13:57:40', 'New Year');";

//        String sql = "SELECT * FROM users WHERE username in (SELECT driverUsername FROM fav_areas WHERE areaName = 'Haram')";
        String sql = "SELECT * FROM users;";

//        String sql = "DROP TABLE users;";

//        String sql = "INSERT OR IGNORE INTO fav_areas" +
//                "(driverUsername, areaName) VALUES ('adel', 'Haram')";

        ResultSet rs = stat.executeQuery(sql);
//        SimpleDateFormat dayMonth = new SimpleDateFormat("dd/MM");
        while (rs.next()){
            System.out.println(rs.getString("username"));
            System.out.println(rs.getString("password"));
            System.out.println(rs.getBoolean("is_active"));
        }

        stat.close();
    }
}