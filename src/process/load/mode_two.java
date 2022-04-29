package process.load;

public class mode_two implements BasicMode{
    /*
     * 配载方式二：支持所有液体货物；
     * */
    private String[] supportCargoType={"圆钢", "硅钢片","硅铁", "螺纹钢", "钢材","钢架子"};
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
