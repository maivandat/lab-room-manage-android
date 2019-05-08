package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.TagDataSource;
import huedev.org.data.source.remote.api.ApiTags;
import huedev.org.data.source.remote.response.tag.CreateTagReponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class TagRemoteDataSource implements TagDataSource.RemoteDataSource {
    private static TagRemoteDataSource instance;
    private ApiTags mApiTags;

    public TagRemoteDataSource (ApiTags apiTags){
        this.mApiTags = apiTags;
    }

    public static synchronized TagRemoteDataSource getInstance(Context context){
        if (instance == null) {
            instance = new TagRemoteDataSource(AppServiceClient.getTagRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<CreateTagReponse> createTag(String value, int id_device) {
        return mApiTags.createTags(value, id_device);
    }
}
