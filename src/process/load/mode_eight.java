package process.load;

public class mode_eight implements BasicMode{
    /*
     * 配载方式四：支持所有粉状货物；
     * */
    private String[] supportCargoType={"木片",};

    @Override
    public String getModeType(){
        return "mode_eight";
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
