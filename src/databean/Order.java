package databean;

import com.sun.org.apache.xpath.internal.operations.Or;
import process.dataControl.DistanceUtils;

import java.text.ParseException;
import java.util.Calendar;

/**
 * @author xie
 * @date 2022.2.28
 */
public class Order implements Cloneable{
    /**
     * param orderId:�������
     * */
    public String orderId;
    /**
     * param orderType:�������ͣ�����ҵ���һ��ҵ��
     * */
    public String orderType;
    /**
     * param startPointName:װ��/���
     * */
    public String startPointName;
    /**
     * param startLongitude:��㾭��
     * */
    public double startLongitude;
    /**
     * param startLatitude:���γ��
     * */
    public double startLatitude;
    /**
     * param destinationName:ж��/�յ�
     * */
    public String destinationName;
    /**
     * param destLongitude:�յ㾭��
     * */
    public double destiLongitude;
    /**
     * param destLatitude:�յ�γ��
     * */
    public double destiLatitude;
    /**
     * param cargoType:��������
     * */
    public String cargoType;
    /**
     * param cargoName:��������
     * */
    public String cargoName;
    /**
     * param weight:����/����
     * */
    public double weight;
    /**
     * param startTime:װ����ʼ����
     * */
    public Calendar startTime;
    /**
     * param endTime:װ����ֹ����
     * */
    public Calendar endTime;
    /**
     * param emergency:�����̶�
     * */
    public int emergency;
    /**
     * param price:���ۣ�Ԫ/�ֻ�Ԫ/��
     * */
    public double price;
    /**
     * param distance:װж�����
     * */
    public double distance;

    /**
     * timeCost���ķ�ʱ��
     */
    public double timeCost;
    public Order(){

    }

    public Order(String lineStr) throws ParseException {
        String[] str = lineStr.split(",");
        this.orderId=str[0];
        this.startPointName=str[1];
        this.startLongitude=Double.parseDouble(str[2]);
        this.startLatitude=Double.parseDouble(str[3]);
        this.destinationName=str[4];
        this.destiLongitude=Double.parseDouble(str[5]);
        this.destiLatitude=Double.parseDouble(str[6]);
        this.cargoType=str[7];
        this.weight=Double.parseDouble(str[8])/1000;
        this.startTime=changeStringToCalendar("2021.10.14");
        //ת��������һ������Ԥ����
        this.endTime=changeStringToCalendar("2021.12.15");
        this.price=Double.parseDouble(str[11]);
        this.distance=new DistanceUtils().getDistance(
                this.startLongitude,
                this.startLatitude,
                this.destiLongitude,
                this.destiLatitude);
        this.timeCost=this.distance/30;
    }

    public Calendar changeStringToCalendar(String line) throws ParseException {
        
        Calendar basic=Calendar.getInstance();

        String[] str = line.split("\\.");
        basic.set(Calendar.YEAR,Integer.parseInt(str[0]));
        basic.set(Calendar.MONTH,Integer.parseInt(str[1]));
        basic.set(Calendar.DATE,Integer.parseInt(str[2]));
        basic.set(Calendar.HOUR_OF_DAY,0);
        basic.set(Calendar.MINUTE,0);
        basic.set(Calendar.SECOND,0);

//        System.out.println(line+":"+basic.get(Calendar.YEAR)+"."+basic.get(Calendar.MONTH)+"."+basic.get(Calendar.DATE));
        return basic;
    }

    public double changeStringToPosition(String line) throws ParseException {

        double position=-1;

        String str = line.substring(4,line.length());
        position=Double.parseDouble(str);

//        System.out.println(line+":"+basic.get(Calendar.YEAR)+"."+basic.get(Calendar.MONTH)+"."+basic.get(Calendar.DATE));
        return position;
    }

    public double changeStringToPrice(String line) throws ParseException {

        double price=-1;

        String str = line.substring(0,line.length()-3);
        price=Double.parseDouble(str);

//        System.out.println(line+":"+basic.get(Calendar.YEAR)+"."+basic.get(Calendar.MONTH)+"."+basic.get(Calendar.DATE));
        return price;
    }
    //����������ݵĿ�¡������ͨ�����ݿ���ʵ�ֲ�ͬ�Ĺ���
    @Override
    public Object clone() {
        Order order = null;
        try{
            order = (Order) super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public String toString(){
        return
                orderId+ "," +
                        startPointName + "," +
                        startLongitude+","+
                        startLatitude+","+
                        destinationName + "," +
                        destiLongitude+","+
                        destiLatitude+","+
                        cargoType + "," +
                        cargoName+ "," +
                        weight + "," +
                        startTime + "," +
                        endTime + "," +
                        price;
    }
    @Override
    public boolean equals(Object obj) {
        Order otherOrder = (Order) obj;
        return orderId.equals(otherOrder.orderId);
    }
}
