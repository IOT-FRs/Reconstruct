package process.load;

public class mode_four implements BasicMode{
    /*
     * ���ط�ʽ�ģ�֧�����е��˻��
     * */
    private String[] supportCargoType={"�ؽ��","�ؽ�ۣ����ۣ�", "������","���",};
    @Override
    public String getModeType(){
        return "mode_four";
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
