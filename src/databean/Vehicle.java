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
    *vehicleNumber:���ƺţ���Ӧ����
    * */
    public String vehicleNumber;
    /**
     * vehicleType:��������
     * */
    public String vehicleType;
    /**
     * vehicleDetailedType:������������
     * */
    public String vehicleDetailedType;
    /**
     * licenseColor:������ɫ����ɫһ�������볧��ͬʱ����������һ��ҵ�������ǰ���ҵ����
     * */
    public String licenseColor;
    /**
     * vehicleDescription:���Ĳ���˵��
     * */
    public String vehicleDescription;
    /**
     * supportList:��֧��װ�صĻ���
     * */
    public List<String> supportList;
    /**
     * vehicleAverageSpeed:���䳵ƽ���ٶ�
     * */
    public double vehicleAverageSpeed;
    /**
     * vehicleLength:���䳵��������λ����
     * */
    public double vehicleLength;
    /**
     * vehicleWidth:���䳵������λ����
     * */
    public double vehicleWidth;
    /**
     * vehicleHeight:���䳵���ߣ���λ����
     * */
    public double vehicleHeight;
    /**
     * maxLoad:����������أ���λ����
     * */
    public double maxLoad;
    /**
     * presentLoad:����ǰװ�ػ������������λ����
     * */
    public double presentLoad;
    /*@resentOrderList:����ǰ��װ�صĻ����嵥*/
//    public List<Order> presentOrderList;
    /**
     * presentOrder:����ǰ��װ�صĻ���
     * */
    public Order presentOrder;
    /**
     * waitTime:��ǰ��ͣ��ʱ��
     * */
    public int waitTime;
    /**
     * presentLongitude:��ǰ����λ�ã�����
     * */
    public double presentLongitude;
    /**
     * presentLatitude:��ǰ����λ�ã�γ��
     * */
    public double presentLatitude;
    /**
     * clearEnergy:��Դ���ͣ��綯����Ȼ����ȼ�͡�����
     * */
    public boolean clearEnergy;
    /**
     * maxDistance:����������룬ǧ��/����
     * */
    public double maxDistance;


    /**
     * maxWorkTime:һ�����Ĺ���ʱ��
     * */
    public int maxWorkTime;
    /**
     * presentWorkTime:��ǰ����ʱ��
     * */
    public double presentWorkTime;
    /**
     * freeTime:ʣ�����ʱ��
     * */
    public double freeTime;
    /**
     * FC:��������
     * */
    public int FC;
    /**
     * chromosome:��Ӧ����·��
     * */
//    public int[] chromosome;
    public List<Integer> chromosome;
    /**
     * fitness:��Ӧ�ȣ�Ŀǰ�ǿ��ؾ���
     * */
    public double fitness;
    /**
     * reused:���ò��ԣ��жϸó����Ƿ��Ѽ�����ҵ����
     * */
    public boolean reused;

    /**
     * vehicleTypeLocationArr:��Ӧ�������͵ļ��ֳ���ִ�г����е�λ��
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
//        this.clearEnergy= "��Ȼ��".equals(str[7])||"�綯".equals(str[7])||"����".equals(str[7]);
        this.maxDistance="����".equals(str[8])?Double.POSITIVE_INFINITY:Double.parseDouble(str[8]);
        this.maxWorkTime=100;
        this.presentWorkTime=0;
        this.freeTime=this.maxWorkTime-this.presentWorkTime;
        this.FC=1000;
        this.chromosome=new ArrayList<>();
//        this.presentOrderList=new ArrayList<>();
    }

    private Boolean newClearEnergy(String energyType){
        String[] clearEnergyType={"��Ȼ��","�綯","����"};
        for (String str:clearEnergyType) {
            if(energyType.equals(str)){
                return true;
            }
        }
        return false;
    }


    private List<String> decodeSupportList(String cargoTypeList){
        List<String> stringList=new ArrayList<>();
        String[] strings=cargoTypeList.split("��");
        TreeMap<String,String> stringStringTreeMap=setStringStringTreeMap();
        for (String s:strings) {
            stringList.add(stringStringTreeMap.get(s));
        }
        return stringList;
    }


    private TreeMap<String,String> setStringStringTreeMap(){
        TreeMap<String,String> stringStringTreeMap=new TreeMap<>();
        stringStringTreeMap.put("��","mode_one");
        stringStringTreeMap.put("��","mode_two");
        stringStringTreeMap.put("��","mode_three");
        stringStringTreeMap.put("��","mode_four");
        stringStringTreeMap.put("��","mode_five");
        stringStringTreeMap.put("��","mode_six");
        stringStringTreeMap.put("��","mode_seven");
        stringStringTreeMap.put("��","mode_eight");
        stringStringTreeMap.put("��","mode_nine");
        stringStringTreeMap.put("��","mode_ten");
        stringStringTreeMap.put("��","mode_others");
        return stringStringTreeMap;
    }


    public List<Vehicle> run(int[] chromosome,List<Order> orderList, SetDataMap setDataMap){
        /*
        * �����еĶ������л����Ĵ���Ŀǰ�Ծͽ�ԭ����Ϊ��Ҫ�ɵ�����
        * @param chromosome:Ⱦɫ������
        * @param orderList:��ǰ������Ķ���
        * @param setDataMap:��صĻ�������
        * @return executeVehicleList:ʵ��ִ��������ҵ�ĳ���
        * �ȴ����еĳ�����ѡ��һ������������������Լ������������Լ����ƥ���ϵԼ�������������ҵ������ȥ
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
                //�³�����
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
//                    ��ȥ��;
                    vehicle=failedAndSelectVehicle(tmpOrder,vehicle,executeVehicleList,setDataMap);
                    tmpWorkTime = vehicle.presentWorkTime;
                    //������̽���ֲ�����������iֵ�Լ�1��
                    i--;
                    branchCount++;
                }else {
                    distance=freeAskDistance+tmpOrder.distance;
                    tmpWorkTime+=distance/vehicle.vehicleAverageSpeed;
                    if(tmpWorkTime<=vehicle.maxWorkTime){
//                        ��������
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
                        //������̽���ֲ�����������iֵ�Լ�1��
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
//      �ɳ����Ժ����ܲ�����ѡ���³�
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
        int low = 0,high = locationArr.length - 1,middle = 0;			//����middle
        int orderIdIndex=0;
        while(low <= high){
            middle = (low + high) / 2;
            if(locationArr[middle] > location){
                //�ȹؼ��ִ���ؼ�����������
                high = middle - 1;
                if(middle==0||(middle-1>0&&locationArr[middle-1]<=location)){
                    orderIdIndex=middle;
                    break;
                }
            }else if(locationArr[middle] <= location){
                //�ȹؼ���С��ؼ�����������
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

    //����������ݵĿ�¡������ͨ�����ݿ���ʵ�ֲ�ͬ�Ĺ���
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
