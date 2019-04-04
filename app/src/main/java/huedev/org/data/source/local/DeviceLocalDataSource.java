package huedev.org.data.source.local;

import huedev.org.data.source.DeviceDataSource;

public class DeviceLocalDataSource implements DeviceDataSource.LocalDataSource {
    private static DeviceLocalDataSource instance;
    public static synchronized DeviceLocalDataSource getInstance(){
        if(instance == null){
            instance = new DeviceLocalDataSource();
        }
        return instance;
    }
}
