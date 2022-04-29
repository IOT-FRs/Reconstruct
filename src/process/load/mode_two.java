package process.load;

public class mode_two implements BasicMode{
    /*
     * ���ط�ʽ����֧������Һ����
     * */
    private String[] supportCargoType={"Բ��", "���Ƭ","����", "���Ƹ�", "�ֲ�","�ּ���"};
    @Override
    public String getModeType(){
        return "mode_two";
    }

    @Override
    public void loadMachine(Object object) {
//        System.out.println("loading...");
//        System.out.print(supportCargoType);
        for (String cargoType:supportCargoType) {
            System.out.print(cargoType+",");
        }
        System.out.print(supportCargoType.length);
        System.out.println();
//        start load
        
    }

    @Override
    public String[] getSupportCargoType(){
        return this.supportCargoType;
    }
}
