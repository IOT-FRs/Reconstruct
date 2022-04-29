package process.load;

public class mode_five implements BasicMode{
    /*
     * 配载方式四：支持所有开采出来的矿石；
     * */
    private String[] supportCargoType={"抑尘剂","氨水","氯化钙溶液","氯化铵废水","脱硫剂","防冻液"};
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
