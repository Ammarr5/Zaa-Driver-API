package com.web.ZAA.Core;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

enum EventType {
    LOCATION_ARRIVAL, DESTINATION_ARRIVAL
}

public class RideEvent {
    String name;
    String description;
    Timestamp time;
    HashMap<String, String> eventProps;

    public RideEvent(String capName, double price) {
        name = "New price offer";
        initEvent();
        eventProps.put("Price", ""+price);
        eventProps.put("Captain Name", capName);
        description += ", Event Type: New price offer" + ", Captain Name: " + capName + ", Price: " + price;
    }

    public RideEvent(String userName) {
        name = "User accepted offer";
        initEvent();
        eventProps.put("User Name", userName);
        description += ", User Name: " + userName;
    }

    public RideEvent(String driverName, String userName, EventType type) {
        if (type == EventType.LOCATION_ARRIVAL) {
            name = "Driver arrived to user location";
        }
        else if (type == EventType.DESTINATION_ARRIVAL) {
            name = "Driver arrived to destination";
        }
        initEvent();
        eventProps.put("User Name", userName);
        eventProps.put("Driver Name", driverName);
        description += ", User Name: " + userName + ", Driver Name: " + driverName;
    }

    private void initEvent() {
        eventProps = new HashMap<>();
        Date date = new Date();
        time = new Timestamp(date.getTime());
        description = "Event Name: " + name + ", Time: " + time;
    }

    @Override
    public String toString() {
        return description;
    }
}
