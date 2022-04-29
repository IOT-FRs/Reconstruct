package process.dataControl;

import databean.Order;
import databean.Vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputData {
    public InputData() {
    }

    public List<Order> readOrderFile(String orderFilePath) {
        /*
         * ��ȡ�������ݣ��ɹ�������������ʹ��
         * �ȶԶ������ݽ������зָ����ǵ�һ�У���count==0���������ݱ�ʾ����Ļ�����Ϣ���ݲ����������count>0,
         * ������ά��һ�������嵥
         * @param orderFilePath�������������ڵ����·��
         * */
        List<Order> orderDatas = new ArrayList<>();
        try {
            File infile = new File(orderFilePath);
            if (infile.isFile() && infile.exists()) {
                // judge the file exist or not
                InputStreamReader read = new InputStreamReader(new FileInputStream(infile), "GBK");
                //InputStreamReader���Խ�һ���ֽ�����������װ���ַ�������
                BufferedReader bufferedReader = new BufferedReader(read);
                //(read);//BufferedReader�ڶ�ȡ�ı��ļ�ʱ�����Ⱦ������ļ��ж����ַ����ݲ����뻺��������֮����ʹ��read()���������ȴӻ������н��ж�ȡ��
                String lineStr = null;
                int count=0;
                while ((lineStr = bufferedReader.readLine()) != null) {
                    if(count==0){

                    }
                    else {
                        Order order = new Order(lineStr);
                        orderDatas.add(order);
                    }
                    count++;
                    //System.out.println(lineStr);
                }
                read.close();
            } else {
                System.out.println("Not Find file " + orderFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDatas;
    }

    /*
     * @param vehicleFilePath������ƥ���ϵ�������ڵ����·��
     * @param orderFilePath�������������ڵ����·��
     * */
    public List<Vehicle> readVehicleFile(String vehicleFilePath) {
        /*
         * ��ȡ�����������ݲ���������ƥ���ϵ������ֵ�ɹ�������ѯʹ��
         * �ȶԳ���ƥ�����ݽ������зָ����ǵ�һ�У���count==0����ô������ά����һ�����������嵥�����count>0,
         * ������ά��һ�����������嵥
         * @param vehicleFilePath������ƥ���ϵ�������ڵ����·��
         * */
        List<Vehicle> vehicleDatas = new ArrayList<Vehicle>();
        try {
            File infile = new File(vehicleFilePath);
            if (infile.isFile() && infile.exists()) {
                // judge the file exist or not
                InputStreamReader read = new InputStreamReader(new FileInputStream(infile), "GBK");
                //InputStreamReader���Խ�һ���ֽ�����������װ���ַ�������
                BufferedReader bufferedReader = new BufferedReader(read);
                //(read);//BufferedReader�ڶ�ȡ�ı��ļ�ʱ�����Ⱦ������ļ��ж����ַ����ݲ����뻺��������֮����ʹ��read()���������ȴӻ������н��ж�ȡ��
                String lineStr = null;
                int count=0;
                while ((lineStr = bufferedReader.readLine()) != null) {
                    if(count==0){

                    }
                    else {
                        Vehicle vehicle = new Vehicle(lineStr);
                        vehicleDatas.add(vehicle);
                    }
                    count++;
                    //System.out.println(lineStr);
                }
                read.close();
            } else {
                System.out.println("Not Find file " + vehicleFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicleDatas;
    }


}
