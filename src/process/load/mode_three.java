package process.load;

public class mode_three implements BasicMode{
    /*
     * 配载方式三：支持所有钢材货物；
     * */
    private String[] supportCargoType={"白灰块","白灰面","石灰石","矸石","矿石","窑皮灰/粉矿","自产铁精矿",};
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
