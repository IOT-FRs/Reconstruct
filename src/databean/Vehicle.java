package databean;



import com.sun.org.apache.xpath.internal.operations.Or;
import process.dataControl.DistanceUtils;
import process.multiObject.SetDataMap;

import java.util.*;

/**
 * @author 19133
 */
public class Vehicle implements Cloneable{
    /*
    *vehicleNumber:车牌号，对应到车
    * */
    public String vehicleNumber;
    /**
     * vehicleType:车辆类型
     * */
    public String vehicleType;
    /**
     * vehicleDetailedType:车辆具体类型
     * */
    public String vehicleDetailedType;
    /**
     * licenseColor:车牌颜色，绿色一般允许入厂，同时区分了它是一般业务车辆还是包钢业务车辆
     * */
    public String licenseColor;
    /**
     * vehicleDescription:车的补充说明
     * */
    public String vehicleDescription;
    /**
     * supportList:车支持装载的货物
     * */
    public List<String> supportList;
    /**
     * vehicleAverageSpeed:运输车平均速度
     * */
    public double vehicleAverageSpeed;
    /**
     * vehicleLength:运输车车长，单位：米
     * */
    public double vehicleLength;
    /**
     * vehicleWidth:运输车车宽，单位：米
     * */
    public double vehicleWidth;
    /**
     * vehicleHeight:运输车车高，单位：米
     * */
    public double vehicleHeight;
    /**
     * maxLoad:车的最大载重，单位：吨
     * */
    public double maxLoad;
    /**
     * presentLoad:车当前装载货物的重量，单位：吨
     * */
    public double presentLoad;
    /*@resentOrderList:车当前所装载的货物清单*/
//    public List<Order> presentOrderList;
    /**
     * presentOrder:车当前所装载的货物
     * */
    public Order presentOrder;
    /**
     * waitTime:当前的停留时间
     * */
    public int waitTime;
    /**
     * presentLongitude:当前所在位置，经度
     * */
    public double presentLongitude;
    /**
     * presentLatitude:当前所在位置，纬度
     * */
    public double presentLatitude;
    /**
     * clearEnergy:能源类型，电动、天然气或燃油、柴油
     * */
    public boolean clearEnergy;
    /**
     * maxDistance:最大续航距离，千米/公里
     * */
    public double maxDistance;


    /**
     * maxWorkTime:一周最多的工作时间
     * */
    public int maxWorkTime;
    /**
     * presentWorkTime:当前工作时间
     * */
    public double presentWorkTime;
    /**
     * freeTime:剩余可用时间
     * */
    public double freeTime;
    /**
     * FC:出车费用
     * */
    public int FC;
    /**
     * chromosome:对应车的路线
     * */
//    public int[] chromosome;
    public List<Integer> chromosome;
    /**
     * fitness:适应度，目前是空载距离
     * */
    public double fitness;
    /**
     * reused:复用策略，判断该车辆是否已加入作业队列
     * */
    public boolean reused;

    /**
     * vehicleTypeLocationArr:对应车辆类型的几种车在执行车队中的位置
     * */
    private Map<String,List<Integer>> vehicleTypeLocationList=new TreeMap<>();

    public Vehicle(){
//        this.presentOrderList=new ArrayList<>();
        this.fitness=0;
    }


    public Vehicle(String lineStr){
        String[] str = lineStr.split(",");
        this.vehicleDetailedType=str[0];
        this.vehicleLength=Double.parseDouble(str[1]);
        this.vehicleWidth=Double.parseDouble(str[2]);
        this.vehicleHeight=Double.parseDouble(str[3]);
        this.maxLoad=Double.parseDouble(str[4]);
        this.supportList=decodeSupportList(str[5]);
        this.vehicleAverageSpeed=Double.parseDouble(str[6]);
        this.clearEnergy=newClearEnergy(str[7]);
//        this.clearEnergy= "天然气".equals(str[7])||"电动".equals(str[7])||"氢气".equals(str[7]);
        this.maxDistance="无限".equals(str[8])?Double.POSITIVE_INFINITY:Double.parseDouble(str[8]);
        this.maxWorkTime=100;
        this.presentWorkTime=0;
        this.freeTime=this.maxWorkTime-this.presentWorkTime;
        this.FC=1000;
        this.chromosome=new ArrayList<>();
//        this.presentOrderList=new ArrayList<>();
    }

    private Boolean newClearEnergy(String energyType){
        String[] clearEnergyType={"天然气","电动","氢气"};
        for (String str:clearEnergyType) {
            if(energyType.equals(str)){
                return true;
            }
        }
        return false;
    }


    private List<String> decodeSupportList(String cargoTypeList){
        List<String> stringList=new ArrayList<>();
        String[] strings=cargoTypeList.split("、");
        TreeMap<String,String> stringStringTreeMap=setStringStringTreeMap();
        for (String s:strings) {
            stringList.add(stringStringTreeMap.get(s));
        }
        return stringList;
    }


    private TreeMap<String,String> setStringStringTreeMap(){
        TreeMap<String,String> stringStringTreeMap=new TreeMap<>();
        stringStringTreeMap.put("Ⅰ","mode_one");
        stringStringTreeMap.put("Ⅱ","mode_two");
        stringStringTreeMap.put("Ⅲ","mode_three");
        stringStringTreeMap.put("Ⅳ","mode_four");
        stringStringTreeMap.put("Ⅴ","mode_five");
        stringStringTreeMap.put("Ⅵ","mode_six");
        stringStringTreeMap.put("Ⅶ","mode_seven");
        stringStringTreeMap.put("Ⅷ","mode_eight");
        stringStringTreeMap.put("Ⅸ","mode_nine");
        stringStringTreeMap.put("Ⅹ","mode_ten");
        stringStringTreeMap.put("Ⅺ","mode_others");
        return stringStringTreeMap;
    }


    public List<Vehicle> run(int[] chromosome,List<Order> orderList, SetDataMap setDataMap){
        /*
        * 对已有的订单进行基本的处理，目前以就近原则作为主要派单依据
        * @param chromosome:染色体序列
        * @param orderList:当前待分配的订单
        * @param setDataMap:相关的基本集合
        * @return executeVehicleList:实际执行运输作业的车队
        * 先从已有的车型中选择一辆车，如果满足基本的约束条件（载重约束和匹配关系约束），则加入作业车队中去
        * */
        long startTime=System.currentTimeMillis();
        List<Vehicle> executeVehicleList=new ArrayList<>();
        vehicleTypeLocationList=new TreeMap<>();
        for (Order order:orderList) {
            vehicleTypeLocationList.put(getRelatedVehicle(order.cargoType,setDataMap).vehicleDetailedType,new ArrayList<>());
        }
        Vehicle tmpVehicle=new Vehicle();
        int branchCount=0;
        double tmpWorkTime=0;
        Order tmpOrder=getRelatedOrder(chromosome[0],setDataMap);
        Vehicle vehicle=new Vehicle();
//        vehicle=selectVehicleStrategy(tmpOrder, executeVehicleList,setDataMap);
        vehicle=createVehicle(tmpOrder.cargoType,setDataMap);
        for (int i = 0; i < chromosome.length; i++) {
            tmpOrder=getRelatedOrder(chromosome[i], setDataMap);
            if(!loadable(tmpOrder.cargoType,vehicle,setDataMap)){
                if(!vehicle.reused){
                    executeVehicleList.add(vehicle);
//                    vehicleTypeLocationList.put(vehicle.vehicleDetailedType,new ArrayList<>());
                    vehicleTypeLocationList.get(vehicle.vehicleDetailedType).add(executeVehicleList.indexOf(vehicle));
                }
                vehicle=selectVehicleStrategy(tmpOrder, executeVehicleList,setDataMap);
                tmpWorkTime = vehicle.presentWorkTime;
                branchCount++;
            }

            if(tmpWorkTime==0){
                //新车访问
                tmpWorkTime+=tmpOrder.timeCost;
                vehicle.presentWorkTime = tmpWorkTime;
                vehicle.chromosome.add(chromosome[i]);
                vehicle.presentLongitude=tmpOrder.destiLongitude;
                vehicle.presentLatitude=tmpOrder.destiLatitude;
                addLastVehicle(i,chromosome.length,vehicle,executeVehicleList);
                branchCount++;
            }
            else {
                double distance=0,freeAskDistance=0;
                freeAskDistance=new DistanceUtils().getDistance(
                        vehicle.presentLongitude,
                        vehicle.presentLatitude,
                        tmpOrder.startLongitude,
                        tmpOrder.startLatitude);
                if(freeAskDistance>1000){
//                    舍去长途
                    vehicle=failedAndSelectVehicle(tmpOrder,vehicle,executeVehicleList,setDataMap);
                    tmpWorkTime = vehicle.presentWorkTime;
                    //经过试探发现不满足条件，i值自减1；
                    i--;
                    branchCount++;
                }else {
                    distance=freeAskDistance+tmpOrder.distance;
                    tmpWorkTime+=distance/vehicle.vehicleAverageSpeed;
                    if(tmpWorkTime<=vehicle.maxWorkTime){
//                        条件允许
                        vehicle.presentWorkTime = tmpWorkTime;
                        addLastVehicle(i,chromosome.length,vehicle,executeVehicleList);
                        vehicle.fitness+=freeAskDistance;
                        vehicle.chromosome.add(chromosome[i]);
                        vehicle.presentLongitude=tmpOrder.destiLongitude;
                        vehicle.presentLatitude=tmpOrder.destiLatitude;
                        addLastVehicle(i,chromosome.length,vehicle,executeVehicleList);
                        branchCount++;
                    }
                    else {
                        vehicle=failedAndSelectVehicle(tmpOrder,vehicle,executeVehicleList,setDataMap);
                        tmpWorkTime = vehicle.presentWorkTime;
                        //经过试探发现不满足条件，i值自减1；
                        i--;
                        branchCount++;
                        //break;
                    }
                }
            }
        }
        long runTime=System.currentTimeMillis();
//        System.out.println("the branchCount is:"+branchCount);
//        System.out.println("the runTime is:"+(runTime-startTime));
//        int tmpCount=0;
//        for (String str:vehicleTypeLocationList.keySet()) {
//            tmpCount+=vehicleTypeLocationList.get(str).size();
//        }
//        System.out.println(tmpCount);
        return executeVehicleList;
    }

    public Vehicle failedAndSelectVehicle(Order tmpOrder,Vehicle vehicle,List<Vehicle> executeVehicleList,SetDataMap setDataMap){
        if(!vehicle.reused){
            executeVehicleList.add(vehicle);
            vehicleTypeLocationList.get(vehicle.vehicleDetailedType).add(executeVehicleList.indexOf(vehicle));
            return selectVehicleStrategy(tmpOrder, executeVehicleList,setDataMap);
        }else {
//      旧车尝试后发现跑不动，选择新车
            return createVehicle(tmpOrder.cargoType,setDataMap);
        }
    }

    public Vehicle selectVehicleStrategy(Order tmpOrder,List<Vehicle> executeVehicleList,SetDataMap setDataMap){
        Vehicle vehicle=new Vehicle();
        vehicle=nearestStrategy(tmpOrder,executeVehicleList,setDataMap);
        return vehicle!=null?vehicle:createVehicle(tmpOrder.cargoType,setDataMap);
//        return createVehicle(tmpOrder.cargoType,setDataMap);
    }

    public Vehicle nearestStrategy(Order order,List<Vehicle> executeVehicleList,SetDataMap setDataMap){
        long startTime=System.currentTimeMillis();
        Vehicle vehicle=new Vehicle();
        String vehicleDetailedType=getRelatedVehicle(order.cargoType,setDataMap).vehicleDetailedType;
        List<Vehicle> vehicles=new ArrayList<>();
        DistanceUtils distanceUtils=new DistanceUtils();
        double minFreeAskDistance=Double.POSITIVE_INFINITY,tmpFreeTime=0,freeAskDistance=0;
        int minIndex=0;
        for (Integer integer:vehicleTypeLocationList.get(vehicleDetailedType)) {
            Vehicle v=executeVehicleList.get(integer);
            freeAskDistance = distanceUtils.getDistance(
                        v.presentLongitude,
                        v.presentLatitude,
                        order.startLongitude,
                        order.startLatitude);
            tmpFreeTime = v.maxWorkTime - v.presentWorkTime - freeAskDistance * Math.pow(v.vehicleAverageSpeed, -1) - order.timeCost;
            if (tmpFreeTime > 0) {
                if(minFreeAskDistance>freeAskDistance){
                    vehicles.add(v);
                    minFreeAskDistance=freeAskDistance;
                    minIndex=vehicles.indexOf(v);
                }
            }
        }
        long nearestStrategyTime=System.currentTimeMillis();
//        System.out.println("the nearestStrategyTime is:"+(nearestStrategyTime-startTime));
        if(vehicles.size()>0){
            vehicles.get(minIndex).reused=true;
            return vehicles.get(minIndex);
        }else {
            return null;
        }
    }

    public Vehicle createVehicle(String cargoType,SetDataMap setDataMap){
        Vehicle vehicle=new Vehicle();
        vehicle=(Vehicle)getRelatedVehicle(cargoType,setDataMap).clone();
        vehicle.chromosome=new ArrayList<>();
        vehicle.presentWorkTime=0;
        vehicle.fitness=0;
        vehicle.reused=false;
        return vehicle;
    }

    public void addLastVehicle(int i,int chromosomeLength,Vehicle vehicle,List<Vehicle> executeVehicleList){
        if (i == chromosomeLength - 1) {
            if(!vehicle.reused){
                vehicle.reused=true;
                executeVehicleList.add(vehicle);
                vehicleTypeLocationList.get(vehicle.vehicleDetailedType).add(executeVehicleList.indexOf(vehicle));
            }
        }
    }

    public Order getRelatedOrder(int location, SetDataMap setDataMap){
        int locationIndex=getOrderIdLocationFromLocationArr(location,setDataMap);
        return setDataMap.getLocationOrderMap().get(locationIndex);
    }

    public int getOrderIdLocationFromLocationArr(int location, SetDataMap setDataMap){
        int[] locationArr=setDataMap.getLocationArr();
        int low = 0,high = locationArr.length - 1,middle = 0;			//定义middle
        int orderIdIndex=0;
        while(low <= high){
            middle = (low + high) / 2;
            if(locationArr[middle] > location){
                //比关键字大则关键字在左区域
                high = middle - 1;
                if(middle==0||(middle-1>0&&locationArr[middle-1]<=location)){
                    orderIdIndex=middle;
                    break;
                }
            }else if(locationArr[middle] <= location){
                //比关键字小则关键字在右区域
                low = middle + 1;
            }
        }
        return locationArr[orderIdIndex];
    }

    public boolean loadable(String cargoType,Vehicle vehicle,SetDataMap setDataMap){
        for (Vehicle v:setDataMap.getCargoTypeVehiclesMap().get(cargoType)) {
            if (vehicle.vehicleDetailedType.equals(v.vehicleDetailedType)){
                return true;
            }
        }
        return false;

    }

    public Vehicle getRelatedVehicle(String cargoType,SetDataMap setDataMap){
        return setDataMap.getCargoTypeVehiclesMap().get(cargoType).get(0);
    }

    //允许进行数据的克隆，便于通过数据拷贝实现不同的功能
    @Override
    public Object clone() {
        Vehicle vehicle = null;
        try{
            vehicle = (Vehicle) super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    @Override
    public String toString(){
        return
                vehicleNumber+ "," +
                        vehicleType+ "," +
                        vehicleDetailedType+"," +
                        vehicleDescription+"," +
                        vehicleAverageSpeed+"," +
                        licenseColor+"," +
                        vehicleDescription+"," +
                        vehicleLength+"," +
                        vehicleWidth+"," +
                        vehicleHeight+"," +
                        maxLoad+"," +
                        clearEnergy+"," +
                        maxDistance
                ;
    }
    public int compareAccordingToMaxLoad(Vehicle vehicle){
        return (int) (this.maxLoad-vehicle.maxLoad);
    }

    public int compareAccordingToFreeTime(Vehicle vehicle){
        this.freeTime=this.maxWorkTime-this.presentWorkTime;
        vehicle.freeTime=vehicle.maxWorkTime-vehicle.presentWorkTime;
        return (int) (this.freeTime-vehicle.freeTime);
    }

}
