package huedev.org.data.source.local;

import huedev.org.data.source.UserDataSource;

public class UserLocalDataSource implements UserDataSource.LocalDataSource {
    private static UserLocalDataSource instance;
    public static synchronized UserLocalDataSource getInstance(){
        if(instance == null){
            instance = new UserLocalDataSource();
        }
        return instance;
    }
}
