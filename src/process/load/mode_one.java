package process.load;

public class mode_one implements BasicMode{
    /*
     * ���ط�ʽһ��֧�����й����������Ҹû�����Ϊ������Ʒ��
     * */
    private String[] supportCargoType={"13��","5��ϴú","9��ϴ��ú","��ú","���ʷ�","��̿", "ұ��","ԭú","ĭú","������ú",
            "��̿","����","ú", "ú̿","��ú", "��ú��","��ú","������-ԭú","ƶ��ú", "��ú"};
    @Override
    public String getModeType(){
        return "mode_one";
    }

    @Override
    public void loadMachine(Object object) {
//        System.out.println("loading...");
        for (String cargoType:supportCargoType) {
            System.out.print(cargoType+",");
        }
        System.out.print(supportCargoType.length);
        System.out.println();
//        System.out.print(supportCargoType);
//        start load

    }

    @Override
    public String[] getSupportCargoType(){
        return this.supportCargoType;
    }
}
