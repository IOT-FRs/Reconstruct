package process.load;

public class mode_six implements BasicMode{
    /*
     * 配载方式四：支持所有粉状货物；
     * */
    private String[] supportCargoType={"倒土方","倒运废铁","倒运石料","倒运粉矿"};
    @Override
    public String getModeType(){
        return "mode_six";
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
