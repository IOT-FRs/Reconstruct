package process.load;

import java.util.Map;
import java.util.TreeMap;

public class LoadPort {
    private Map<String,BasicMode> basicModeMap=new TreeMap<>();
    public void chooseModeToLoadMachine(String mode_type,Object object){
        BasicMode basicMode=basicModeMap.get(mode_type);
        if(basicMode!=null){
            basicMode.loadMachine(object);
        }
    }
    public void setBasicModeMap(){
        basicModeMap.put(new mode_one().getModeType(),new mode_one());
        basicModeMap.put(new mode_two().getModeType(),new mode_two());
        basicModeMap.put(new mode_three().getModeType(),new mode_three());
        basicModeMap.put(new mode_four().getModeType(),new mode_four());
        basicModeMap.put(new mode_five().getModeType(),new mode_five());
        basicModeMap.put(new mode_six().getModeType(),new mode_six());
        basicModeMap.put(new mode_seven().getModeType(),new mode_seven());
        basicModeMap.put(new mode_eight().getModeType(),new mode_eight());
        basicModeMap.put(new mode_nine().getModeType(),new mode_nine());
        basicModeMap.put(new mode_ten().getModeType(),new mode_ten());
        basicModeMap.put(new mode_others().getModeType(),new mode_others());
//        basicModeMap.put(new mode_default().getModeType(),new mode_default());

    }

    public Map<String,BasicMode> getBasicModeMap(){
        return this.basicModeMap;
    }
}
