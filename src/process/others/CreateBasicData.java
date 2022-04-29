package process.others;

import databean.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CreateBasicData {
    public List<Order> Conclude(List<Order> orderDatas){
        //处理目标：对涉及的地点进行归类，输出排序后的List
        // System.out.println("---------------------");
        List<Order> orderedList= new ArrayList<>();
        Map<String, Integer> cnt = new TreeMap<>();
        for ( Order order : orderDatas ) {
            cnt.merge(order.cargoType, 1, Integer::sum);
        }

        for ( String key : cnt.keySet() ) {
            for(Order order : orderDatas ) {
                if (order.cargoType.equals(key)) {
                    orderedList.add(order);
                }
            }
        }
        return orderedList;
    }
}
