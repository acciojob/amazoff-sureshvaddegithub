package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {


    HashMap<String,Order>  orderHashMap = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();

    HashMap<String, List<String>> orderPartnerPair = new HashMap<>();
    HashMap<String,String> partnerOrderPair = new HashMap<>();

    public void addOrderToDb(Order order){
        orderHashMap.put(order.getId(),order);
    }
    public void addPatnerToDb(DeliveryPartner deliveryPartner){
        deliveryPartnerHashMap.put(deliveryPartner.getId(),deliveryPartner);
    }
     public void addOrderPartnerPair(String OrderId,String PartnerId){


         if(partnerOrderPair.containsKey(OrderId)){
             return;
         }
         else{
             DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(PartnerId);
             deliveryPartner.setNumberOfOrders( deliveryPartner.getNumberOfOrders()+1);
             deliveryPartnerHashMap.put(PartnerId,deliveryPartner);
             partnerOrderPair.put(OrderId,PartnerId);
         }

        if(orderPartnerPair.containsKey((PartnerId))){
            orderPartnerPair.get(PartnerId).add(OrderId);
        }
        else{
            List<String> orders = new ArrayList<>();
            orders.add(OrderId);
            orderPartnerPair.put(PartnerId,orders);
        }

     }

    public Order getOrderByIdFromDb(String orderId){
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPatnerByIdFromDb(String patnerId){
        return deliveryPartnerHashMap.get(patnerId);
    }

    public int getOrderCountByPartnerIdFromDb(String partnerId){
        return orderPartnerPair.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerIdFromDb(String partnerId){
          return orderPartnerPair.get(partnerId);
    }

    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for(String order:orderHashMap.keySet()){
            orders.add(order);
        }
        return orders;
    }

    public int getCountOfUnassignedOrders(){

        return orderHashMap.size()-partnerOrderPair.size();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerIdFromDb(String time,String partnerId){
        int count = 0;
        int HH = Integer.parseInt(time.substring(0,2));
        int MM = Integer.parseInt(time.substring(3,5));
        int Time = HH*60 + MM;
        if(orderPartnerPair.containsKey(partnerId)){
            for(String names:orderPartnerPair.get(partnerId)){
                if(orderHashMap.get(names).getDeliveryTime()>Time)
                    count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String name = orderPartnerPair.get(partnerId).get(orderPartnerPair.get(partnerId).size()-1);
        return String.valueOf(orderHashMap.get(name).getDeliveryTime());
    }

    public void deletePartnerByIdFromDb(String partnerId){
        deliveryPartnerHashMap.remove(partnerId);
       if( orderPartnerPair.containsKey(partnerId)) {
           for (String name : partnerOrderPair.keySet()) {
               if (partnerOrderPair.get(name).equals(partnerId)) {
                   partnerOrderPair.remove(name);
               }
           }
           orderPartnerPair.remove(partnerId);
       }
    }
    public void deleteOrderByIdFromDb(String orderId){
        orderHashMap.remove(orderId);
        if(partnerOrderPair.containsKey(orderId)){
            String partnerId = partnerOrderPair.get(orderId);
            int n = orderPartnerPair.get(partnerId).size();
            for(int i = 0;i<n;i++){
                if(orderPartnerPair.get(partnerId).get(i).equals(orderId)) {
                    orderPartnerPair.get(partnerId).remove(i);
                    break;
                }
            }
            partnerOrderPair.remove(orderId);
        }
    }
}
