package process.load;

public interface BasicMode {
    String getModeType();
    void loadMachine(Object object);
    String[] getSupportCargoType();
}
