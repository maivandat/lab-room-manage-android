package huedev.org.data.source.local;

import huedev.org.data.source.LoginDataSource;

public class LoginLocalDataSource implements LoginDataSource.LocalDataSource {
    private static LoginLocalDataSource instance;
    public static synchronized LoginLocalDataSource getInstance(){
        if(instance == null){
            instance = new LoginLocalDataSource();
        }
        return instance;
    }
}
