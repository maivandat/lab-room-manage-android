package huedev.org.data.source.local;

import huedev.org.data.source.RoomDataSource;

public class RoomLocalDataSource implements RoomDataSource.LocalDataSource {
    private static RoomLocalDataSource instance;
    public static synchronized RoomLocalDataSource getInstance(){
        if(instance == null){
            instance = new RoomLocalDataSource();
        }
        return instance;
    }
}
