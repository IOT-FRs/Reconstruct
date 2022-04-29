package process.load;

public class mode_default implements BasicMode{
    /*
    * Ĭ�����ط�ʽ��֧�����л����Ϊһ�������������
    * */
    private String[] supportCargoType={"13��","5��ϴú","9��ϴ��ú","��ú","���ʷ�","������","���˷���","����ʯ��","���˷ۿ�","��̿",
            "ұ��","ԭú","Բ��","��","��ʯ��","����(����)","�ֳ���","ľƬ","��ˮ","�Ȼ�����Һ",
            "�Ȼ�立�ˮ", "ˮ��","ĭú","����","��������","¯��","������ú","��̿","����","ú",
            "ú̿","�׻ҿ�","�׻���","ʯ��ʯ","�ʯ","��ʯ","���Ƭ","����","ҤƤ��/�ۿ�","��ú",
            "��ú��","��ú","������-ԭú","�����","�Բ�������","���Ƹ�", "�豸","ƶ��ú","�ؽ��","�ؽ�ۣ����ۣ�",
            "�ֲ�","�ּ���","����Һ","������","���","��ú"};
    @Override
    public String getModeType(){
        return "mode_default";
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
