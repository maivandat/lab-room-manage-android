package huedev.org.data.source.local;

public class ComputerLocalDataSource {
    private static ComputerLocalDataSource instance;
    public static synchronized ComputerLocalDataSource getInstance(){
        if(instance == null){
            instance = new ComputerLocalDataSource();
        }
        return instance;
    }
}
