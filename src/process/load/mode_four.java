package process.load;

public class mode_four implements BasicMode{
    /*
     * 配载方式四：支持所有倒运货物；
     * */
    private String[] supportCargoType={"重介粉","重介粉（铁粉）", "除尘灰","面灰",};
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
