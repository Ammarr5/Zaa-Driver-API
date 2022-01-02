package com.web.ZAA.Core;

import java.util.ArrayList;

public class Area implements Subject {
    String name;
    int discount;
    ArrayList<Observer> observers;

    public Area(String name) {
        discount=0;
        this.name = name;
        observers = new ArrayList<>();
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object o) {
        for(Observer observer : observers){
            observer.update(o);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(int discount){this.discount = discount;}

    public int getDiscount(){return discount;}

    @Override
    public String toString() {
        return name;
    }
}
