package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.model.User;
import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.data.source.remote.response.user.CreateUserReponse;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import io.reactivex.Single;

public class UserRepository implements UserDataSource.LocalDataSource, UserDataSource.RemoteDataSource {
    private static UserRepository instance;

    @NonNull
    private UserLocalDataSource mUserLocalDataSource;

    @NonNull
    private UserRemoteDataSource mUserRemoteDataSource;

    private UserRepository(@NonNull UserLocalDataSource userLocalDataSource,
                           @NonNull UserRemoteDataSource userRemoteDataSource){
        mUserLocalDataSource = userLocalDataSource;
        mUserRemoteDataSource = userRemoteDataSource;
    }

    public static synchronized UserRepository getInstance(
            @NonNull UserLocalDataSource userLocalDataSource,
            @NonNull UserRemoteDataSource userRemoteDataSource){
        if(instance == null){
            instance = new UserRepository(userLocalDataSource, userRemoteDataSource);
        }
        return instance;
    }

    @Override
    public Single<ListUserResponse> users() {
        return mUserRemoteDataSource.users();
    }

    @Override
    public Single<CreateUserReponse> userItem(String name, String username, String password,
                                              String confirm_password, String email, int role) {
        return mUserRemoteDataSource.userItem(
                name, username, password,
                confirm_password, email, role);
    }

    @Override
    public Single<CreateUserReponse> userItem(String content_type, String name, String username,
                                              String password, String email,
                                              String confirm_password) {
        return mUserRemoteDataSource.userItem(
                content_type, name, username,
                password, email, confirm_password);
    }


    @Override
    public Single<UpdateUserReponse> update(String id, String username,
                                            String password, String name,
                                            String email, int role) {
        return mUserRemoteDataSource.update(id, username, password, name, email, role);
    }

    @Override
    public Single<Void> delete(String id) {
        return mUserRemoteDataSource.delete(id);
    }
}
