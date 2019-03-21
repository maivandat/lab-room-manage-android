package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.data.source.remote.response.user.ListUserResponse;
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

    public static synchronized UserRepository getInstance(@NonNull UserLocalDataSource userLocalDataSource,
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
}
