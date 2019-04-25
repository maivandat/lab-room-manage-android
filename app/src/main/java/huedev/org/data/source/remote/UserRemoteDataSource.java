package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.model.User;
import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.remote.api.ApiUser;
import huedev.org.data.source.remote.response.user.CreateUserReponse;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
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

    @Override
    public Single<CreateUserReponse> userItem(String name, String username, String password, String confirm_password, String email, String role) {
        return mApiUser.create(name, username, password, confirm_password, email, role);
    }

    @Override
    public Single<UpdateUserReponse> update(String id, String username,
                                            String password, String name,
                                            String email, int role) {
        return mApiUser.update(id, username, password, name, email, role);
    }

    @Override
    public Single<Void> delete(String id) {
        return mApiUser.delete(id);
    }
}
