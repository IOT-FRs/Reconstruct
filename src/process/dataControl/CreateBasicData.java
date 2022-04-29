package process.dataControl;

import databean.Order;
import databean.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreateBasicData {
    public List<Order> concludeOrderDatas(List<Order> orderDatas){
        //处理目标：对涉及的地点进行归类，输出排序后的List
        // System.out.println("---------------------");
        List<Order> orderedList= new ArrayList<>();
        Map<String, Integer> cnt = new TreeMap<>();
        for ( Order order : orderDatas ) {
            cnt.merge(order.cargoType, 1, Integer::sum);
        }
//        Map<String, Double> cnt_weight = new TreeMap<>();
//        for ( Order order : orderDatas ) {
//            cnt_weight.merge(order.cargoType, order.weight, Double::sum);
//        }
//        Map<String, Double> cnt_distance = new TreeMap<>();
//        for ( Order order : orderDatas ) {
//            cnt_distance.merge(order.cargoType, order.distance, Double::sum);
//        }

        for ( String key : cnt.keySet() ) {
            for(Order order : orderDatas ) {
                if (order.cargoType.equals(key)) {
                    orderedList.add(order);
                }
            }
        }
//        for (String key: cnt.keySet()) {
//            System.out.println(key+","+cnt.get(key));
//            System.out.println(cnt.get(key));
//            System.out.print("\""+key+"\",");
//        }

//        for (String key: cnt_weight.keySet()) {
////            System.out.println(key+","+cnt_weight.get(key));
//            System.out.println(cnt_weight.get(key));
//        }

//        for (String key: cnt_distance.keySet()) {
////            System.out.println(key+","+cnt_distance.get(key));
//            System.out.println(cnt_distance.get(key));
//        }


        return orderedList;
    }
    public List<Vehicle> concludeVehicleDatas(List<Vehicle> vehicleDatas){
        //处理目标：对涉及的地点进行归类，输出排序后的List
        // System.out.println("---------------------");
        List<Vehicle> vehicleList=new ArrayList<>();
        Map<Double, Integer> cnt = new TreeMap<>();
        for ( Vehicle vehicle:vehicleDatas ) {
            cnt.merge(vehicle.maxLoad, 1, Integer::sum);
        }

        Map<Double, Integer> cnt_maxDistance=new TreeMap<>();
        for (Vehicle vehicle:vehicleDatas) {
            cnt_maxDistance.merge(vehicle.maxDistance,1,Integer::sum);
        }
        for ( Double key : cnt.keySet()) {
            for(Vehicle vehicle:vehicleDatas ) {
                if (vehicle.maxLoad==key) {
                    vehicleList.add(vehicle);
                }
            }
        }

//        for ( Double key : cnt.keySet()){
////            System.out.println("maxLoad:"+key+",count:"+cnt.get(key));
////            System.out.println(key);
//            System.out.println(cnt.get(key));
//        }
        for ( Double key : cnt_maxDistance.keySet()){
//            System.out.println(key+"千米");
            System.out.println(cnt_maxDistance.get(key));
        }
            return vehicleList;
    }
}
