package huedev.org.data.source.local;

public class DeviceLocalDataSource {
    private static DeviceLocalDataSource instance;
    public static synchronized DeviceLocalDataSource getInstance(){
        if(instance == null){
            instance = new DeviceLocalDataSource();
        }
        return instance;
    }
}
