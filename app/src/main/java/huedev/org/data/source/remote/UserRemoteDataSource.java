package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.remote.api.ApiUser;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {
    private static UserRemoteDataSource instance;
    private ApiUser mApiUser;

    public UserRemoteDataSource(ApiUser apiUser){
        mApiUser = apiUser;
    }

    public static synchronized UserRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new UserRemoteDataSource(AppServiceClient.getUserRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<ListUserResponse> users() {
        return mApiUser.users();
    }
}
