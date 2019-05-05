package huedev.org.data.source.local;

import huedev.org.data.source.TypeDeviceDataSource;

public class TypeDeviceLocalDataSource implements TypeDeviceDataSource.LocalDataSource {
    private static TypeDeviceLocalDataSource instance;
    public static synchronized TypeDeviceLocalDataSource getInstance() {
        if (instance == null){
            instance = new TypeDeviceLocalDataSource();
        }
        return instance;
    }
}
