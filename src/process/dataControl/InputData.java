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
         * 获取订单数据，可供后续订单分配使用
         * 先对订单数据进行逐行分割，如果是第一行，即count==0，该行数据表示货物的基本信息，暂不做处理；如果count>0,
         * 则建立并维护一个订单清单
         * @param orderFilePath：订单数据所在的相对路径
         * */
        List<Order> orderDatas = new ArrayList<>();
        try {
            File infile = new File(orderFilePath);
            if (infile.isFile() && infile.exists()) {
                // judge the file exist or not
                InputStreamReader read = new InputStreamReader(new FileInputStream(infile), "GBK");
                //InputStreamReader可以将一个字节输入流包包装成字符输入流
                BufferedReader bufferedReader = new BufferedReader(read);
                //(read);//BufferedReader在读取文本文件时，会先尽量从文件中读入字符数据并置入缓冲区，而之后若使用read()方法，会先从缓冲区中进行读取。
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
     * @param vehicleFilePath：车货匹配关系数据所在的相对路径
     * @param orderFilePath：订单数据所在的相对路径
     * */
    public List<Vehicle> readVehicleFile(String vehicleFilePath) {
        /*
         * 获取车辆类型数据并建立车货匹配关系，返回值可供后续查询使用
         * 先对车货匹配数据进行逐行分割，如果是第一行，即count==0，那么建立并维护好一个货物类型清单；如果count>0,
         * 则建立并维护一个车辆类型清单
         * @param vehicleFilePath：车货匹配关系数据所在的相对路径
         * */
        List<Vehicle> vehicleDatas = new ArrayList<Vehicle>();
        try {
            File infile = new File(vehicleFilePath);
            if (infile.isFile() && infile.exists()) {
                // judge the file exist or not
                InputStreamReader read = new InputStreamReader(new FileInputStream(infile), "GBK");
                //InputStreamReader可以将一个字节输入流包包装成字符输入流
                BufferedReader bufferedReader = new BufferedReader(read);
                //(read);//BufferedReader在读取文本文件时，会先尽量从文件中读入字符数据并置入缓冲区，而之后若使用read()方法，会先从缓冲区中进行读取。
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
