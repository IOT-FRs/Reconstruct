package process.load;

public class mode_others implements BasicMode{
    /*
     * 配载方式四：支持所有现有配载方式不支持的货物；
     * */
    private String[] supportCargoType={"清理垃圾","炉渣", };
    /*
    *"土","土石方","尿素(化肥)","木片",
            "水泥","混料","清理垃圾","炉渣",
            "设备",
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
