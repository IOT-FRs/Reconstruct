package process.load;

public class mode_five implements BasicMode{
    /*
     * ���ط�ʽ�ģ�֧�����п��ɳ����Ŀ�ʯ��
     * */
    private String[] supportCargoType={"�ֳ���","��ˮ","�Ȼ�����Һ","�Ȼ�立�ˮ","�����","����Һ"};
    @Override
    public String getModeType(){
        return "mode_five";
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
