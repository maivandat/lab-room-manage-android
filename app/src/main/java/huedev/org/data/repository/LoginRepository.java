package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.LoginDataSource;
import huedev.org.data.source.local.LoginLocalDataSource;
import huedev.org.data.source.remote.LoginRemoteDataSource;
import huedev.org.data.source.remote.response.auth.LoginResponse;
import io.reactivex.Single;

public class LoginRepository implements LoginDataSource.LocalDataSource, LoginDataSource.RemoteDataSource {
    private static LoginRepository instance;

    @NonNull
    private LoginLocalDataSource mLoginLocalDataSource;

    @NonNull
    private LoginRemoteDataSource mLoginRemoteDataSource;

    private LoginRepository(@NonNull LoginLocalDataSource loginLocalDataSource, @NonNull LoginRemoteDataSource loginRemoteDataSource){
        mLoginLocalDataSource = loginLocalDataSource;
        mLoginRemoteDataSource = loginRemoteDataSource;
    }

    public static synchronized LoginRepository getInstance(@NonNull LoginLocalDataSource loginLocalDataSource, @NonNull LoginRemoteDataSource loginRemoteDataSource){
        if(instance == null){
            instance = new LoginRepository(loginLocalDataSource, loginRemoteDataSource);
        }
        return instance;
    }
    @Override
    public Single<LoginResponse> login(String email, String password) {
        return mLoginRemoteDataSource.login(email, password);
    }
}
