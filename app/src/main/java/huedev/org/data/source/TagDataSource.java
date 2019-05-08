package huedev.org.data.source;

import huedev.org.data.source.remote.response.tag.CreateTagReponse;
import io.reactivex.Single;

public interface TagDataSource {
    interface LocalDataSource {

    }

    interface RemoteDataSource {
        Single<CreateTagReponse> createTag(String value, int id_device);
    }
}
