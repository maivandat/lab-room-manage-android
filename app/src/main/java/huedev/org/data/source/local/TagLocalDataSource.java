package huedev.org.data.source.local;

import huedev.org.data.source.TagDataSource;

public class TagLocalDataSource implements TagDataSource.LocalDataSource {
    private static TagLocalDataSource instance;
    public static synchronized TagLocalDataSource getInstance() {
        if (instance == null) {
            instance = new TagLocalDataSource();
        }
        return instance;
    }
}
