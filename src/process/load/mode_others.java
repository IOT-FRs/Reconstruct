package process.load;

public class mode_others implements BasicMode{
    /*
     * ���ط�ʽ�ģ�֧�������������ط�ʽ��֧�ֵĻ��
     * */
    private String[] supportCargoType={"��������","¯��", };
    /*
    *"��","��ʯ��","����(����)","ľƬ",
            "ˮ��","����","��������","¯��",
            "�豸",
    * */
    @Override
    public String getModeType(){
        return "mode_others";
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
