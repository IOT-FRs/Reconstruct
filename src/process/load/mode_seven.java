package process.load;

public class mode_seven implements BasicMode{
    /*
     * ���ط�ʽ�ģ�֧�����з�״���
     * */
    private String[] supportCargoType={"��","��ʯ��", "ˮ��","����",};
    @Override
    public String getModeType(){
        return "mode_seven";
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
