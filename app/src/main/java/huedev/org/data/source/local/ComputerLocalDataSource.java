package huedev.org.data.source.local;

import android.util.Log;

import huedev.org.data.source.ComputerDataSource;

public class ComputerLocalDataSource implements ComputerDataSource.LocalDataSource {
    private static ComputerLocalDataSource instance;
    public static synchronized ComputerLocalDataSource getInstance(){
        if(instance == null){
            instance = new ComputerLocalDataSource();
        }
        return instance;
    }
}
