package process.load;

public class LoadTest {
    public void test(){
        LoadPort loadPort=new LoadPort();
        loadPort.setBasicModeMap();
        loadPort.chooseModeToLoadMachine(new mode_one().getModeType(),new mode_one());
        loadPort.chooseModeToLoadMachine(new mode_two().getModeType(),new mode_two());
        loadPort.chooseModeToLoadMachine(new mode_three().getModeType(),new mode_three());
        loadPort.chooseModeToLoadMachine(new mode_four().getModeType(),new mode_four());
        loadPort.chooseModeToLoadMachine(new mode_five().getModeType(),new mode_five());
        loadPort.chooseModeToLoadMachine(new mode_six().getModeType(),new mode_six());
        loadPort.chooseModeToLoadMachine(new mode_seven().getModeType(),new mode_seven());
        loadPort.chooseModeToLoadMachine(new mode_eight().getModeType(),new mode_eight());
        loadPort.chooseModeToLoadMachine(new mode_nine().getModeType(),new mode_nine());
        loadPort.chooseModeToLoadMachine(new mode_ten().getModeType(),new mode_ten());
        loadPort.chooseModeToLoadMachine(new mode_others().getModeType(),new mode_others());
        loadPort.chooseModeToLoadMachine(new mode_default().getModeType(),new mode_default());
    }
}
