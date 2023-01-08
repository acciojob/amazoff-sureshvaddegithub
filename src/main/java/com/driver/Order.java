package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String time;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int HH = Integer.parseInt(deliveryTime.substring(0,2));
        int MM = Integer.parseInt(deliveryTime.substring(3,5));
        this.id = id;
        this.deliveryTime = HH*60 + MM;
        this.time = deliveryTime;
    }

    public String getId() {

        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public String getTime() {
        return time;
    }
}
