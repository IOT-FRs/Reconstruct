package process.load;

public class mode_three implements BasicMode{
    /*
     * ���ط�ʽ����֧�����иֲĻ��
     * */
    private String[] supportCargoType={"�׻ҿ�","�׻���","ʯ��ʯ","�ʯ","��ʯ","ҤƤ��/�ۿ�","�Բ�������",};
    @Override
    public String getModeType(){
        return "mode_three";
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
