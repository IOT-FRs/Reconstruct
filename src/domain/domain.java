package domain;

import process.Simulation;

import java.text.ParseException;

public class domain {
    public static void main(String[] args) throws ParseException {
        //��ȡ����ִ�п�ʼʱ��
        long startTime = System.currentTimeMillis();
        Simulation simulation = new Simulation();
        simulation.start();

//        for (Order order:orderList) {
//            System.out.println(order.cargoType);
//        }
        //��ȡ����ִ�н���ʱ��
        long endTime = System.currentTimeMillis();
        //�����������ʱ��
        System.out.println("��������ʱ�䣺" + (endTime - startTime) + "ms");
        System.out.println("Just a breakpoint.");
    }
}
