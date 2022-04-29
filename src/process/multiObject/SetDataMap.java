package process.multiObject;

import com.sun.org.apache.xpath.internal.operations.Or;
import databean.Order;
import databean.Vehicle;
import process.dataControl.DistanceUtils;
import process.load.BasicMode;
import process.load.LoadPort;
import java.util.*;

public class SetDataMap {
    private Map<String,List<Vehicle>> cargoTypeVehiclesMap=new TreeMap<>();
    private Map<String,Double> cargoTypeMiniLoadMap=new TreeMap<>();
    private Map<String,Integer> orderIdCountMap=new TreeMap<>();
    private Map<String,Integer> orderIdLocationMap=new TreeMap<>();
    private int[] locationArr=new int[300];
    private Map<String, Order> orderIdOrderMap=new TreeMap<>();
    private Map<Integer,Order> locationOrderMap=new TreeMap<>();

//    private Map<String,Integer> tmpBitMap=new BitSet<String,Integer>();
//    BiMap<String,String> britishToAmerican = HashBiMap.create();
    public void start(List<Order> orderList, List<Vehicle> vehicleList){
        /*
         * 在加入运输车数量限制的基础上，对运输过程加以模拟，制定一周内的运输计划。
         * @orderList:待运输订单列表
         * @vehicleList:基本车型一览
         * */
        setCargoTypeVehicleMap(orderList,vehicleList);
        setCargoTypeMiniLoadMap(orderList,vehicleList);
        setOrderIdCountMap(orderList);
        setOrderIdLocationMap();
        setOrderIdOrderMap(orderList);
        setLocationOrderMap(orderList);
        System.out.println("SetDataMap:start finished.");
    }

    public void setLocationOrderMap(List<Order> orderList){
        int locationIndex=0,index=0;
        for (String str:this.getOrderIdCountMap().keySet()) {
            locationIndex+=this.getOrderIdCountMap().get(str);
            locationOrderMap.put(locationIndex,orderIdOrderMap.get(str));
        }
    }

    public void setOrderIdOrderMap(List<Order> orderList){
        for (Order order:orderList) {
            orderIdOrderMap.put(order.orderId,order);
        }
    }

    public void setOrderIdLocationMap(){
        int locationIndex=0,index=0;
        for (String str:this.getOrderIdCountMap().keySet()) {
            locationIndex+=this.getOrderIdCountMap().get(str);
            orderIdLocationMap.put(str,locationIndex);
            locationArr[index++]=locationIndex;
        }
    }

    public void setOrderIdCountMap(List<Order> orderList) {
        for (Order order:orderList) {
            int count=0;
            count= (int) Math.ceil(order.weight/cargoTypeMiniLoadMap.get(order.cargoType));
            orderIdCountMap.put(order.orderId,count);
        }
    }

    public void setCargoTypeMiniLoadMap(List<Order> orderList, List<Vehicle> vehicleList){
        for (Order order : orderList) {
            Vehicle vehicle = new Vehicle();
            vehicle=getVehicle(order.cargoType);
            cargoTypeMiniLoadMap.put(order.cargoType,vehicle.maxLoad);
        }
    }
    public Vehicle getVehicle(String cargoType){
        Vehicle vehicle=new Vehicle();
        List<Vehicle> loadableVehicles=new ArrayList<>();
        loadableVehicles=cargoTypeVehiclesMap.get(cargoType);
        vehicle=getFittestVehicle(loadableVehicles);
        return vehicle;
    }

    public void setCargoTypeVehicleMap(List<Order> orderList, List<Vehicle> vehicleList){
        LoadPort loadPort=new LoadPort();
        loadPort.setBasicModeMap();
        Map<String, Integer> cnt = new TreeMap<>();
        for ( Order order : orderList ) {
            cnt.merge(order.cargoType, 1, Integer::sum);
        }
        for ( String key : cnt.keySet() ) {
            cargoTypeVehiclesMap.put(key,getLoadableVehicles(key, vehicleList, loadPort));
        }
    }

    public List<Vehicle> getLoadableVehicles(String cargoType,List<Vehicle> vehicleList,LoadPort loadPort){
        Vehicle vehicle=new Vehicle();
        List<Vehicle> loadableVehicles=new ArrayList<>();
        String modeType=getCargoModeType(cargoType,loadPort);
        for (Vehicle v:vehicleList) {
            if(v.supportList.contains(modeType)){
                loadableVehicles.add(v);
            }
        }
        loadableVehicles.sort(new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle vehicle1, Vehicle vehicle2) {
                return vehicle1.compareAccordingToMaxLoad(vehicle2);
            }
        });
        return loadableVehicles;
    }

    public String getCargoModeType(String cargoType,LoadPort loadPort){
        for (BasicMode basicMode:loadPort.getBasicModeMap().values()) {
            if(Arrays.toString(basicMode.getSupportCargoType()).contains(cargoType)){
                return basicMode.getModeType();
            }
        }
        return null;
    }

    public Vehicle getFittestVehicle(List<Vehicle> loadableVehicles){
        Vehicle fittestVehicle=new Vehicle();
        fittestVehicle=loadableVehicles.get(0);
        return fittestVehicle;
    }

    public double distanceCompute(Order order,double presentLongitude,double presentLatitude){
        return order.distance+new DistanceUtils().getDistance(presentLongitude,presentLatitude,order.startLongitude,order.startLatitude);
    }

    public boolean overWeight(double weight,double freeLoad){
        return weight > freeLoad;
    }

    public boolean overDistance(double distance,double freeDistance){
        return distance>freeDistance;
    }

    public Map<String,List<Vehicle>> getCargoTypeVehiclesMap(){ return this.cargoTypeVehiclesMap; }

    public Map<String,Double> getCargoTypeMiniLoadMap(){ return this.cargoTypeMiniLoadMap; }

    public Map<String,Integer> getOrderIdCountMap(){
        return this.orderIdCountMap;
    }

    public Map<String,Integer> getOrderIdLocationMap(){return this.orderIdLocationMap; }

    public int[] getLocationArr() {
        return locationArr;
    }

    public Map<String, Order> getOrderIdOrderMap() {
        return orderIdOrderMap;
    }

    public Map<Integer, Order> getLocationOrderMap() {
        return locationOrderMap;
    }
}
