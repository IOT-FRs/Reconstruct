package process.load;

public class mode_default implements BasicMode{
    /*
    * 默认配载方式：支持所有货物，作为一般情况加以讨论
    * */
    private String[] supportCargoType={"13籽","5号洗煤","9号洗粒煤","中煤","介质粉","倒土方","倒运废铁","倒运石料","倒运粉矿","兰炭",
            "冶金焦","原煤","圆钢","土","土石方","尿素(化肥)","抑尘剂","木片","氨水","氯化钙溶液",
            "氯化铵废水", "水泥","沫煤","混料","清理垃圾","炉渣","炼焦余煤","焦炭","焦粉","煤",
            "煤炭","白灰块","白灰面","石灰石","矸石","矿石","硅钢片","硅铁","窑皮灰/粉矿","粉煤",
            "粉煤灰","精煤","纳林庙-原煤","脱硫剂","自产铁精矿","螺纹钢", "设备","贫瘦煤","重介粉","重介粉（铁粉）",
            "钢材","钢架子","防冻液","除尘灰","面灰","面煤"};
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
