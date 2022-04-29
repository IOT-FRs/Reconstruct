package domain;

import process.Simulation;

import java.text.ParseException;

public class domain {
    public static void main(String[] args) throws ParseException {
        //获取程序执行开始时间
        long startTime = System.currentTimeMillis();
        Simulation simulation = new Simulation();
        simulation.start();

//        for (Order order:orderList) {
//            System.out.println(order.cargoType);
//        }
        //获取程序执行结束时间
        long endTime = System.currentTimeMillis();
        //输出程序运行时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
        System.out.println("Just a breakpoint.");
    }
}
