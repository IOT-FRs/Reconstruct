package process.load;

public class mode_one implements BasicMode{
    /*
     * 配载方式一：支持所有固体颗粒货物，且该货物作为大宗商品；
     * */
    private String[] supportCargoType={"13籽","5号洗煤","9号洗粒煤","中煤","介质粉","兰炭", "冶金焦","原煤","沫煤","炼焦余煤",
            "焦炭","焦粉","煤", "煤炭","粉煤", "粉煤灰","精煤","纳林庙-原煤","贫瘦煤", "面煤"};
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
