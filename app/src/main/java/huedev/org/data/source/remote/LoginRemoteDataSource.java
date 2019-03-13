package huedev.org.data.source.remote;

import android.content.Context;

import huedev.org.data.source.LoginDataSource;
import huedev.org.data.source.remote.api.ApiAuth;
import huedev.org.data.source.remote.response.auth.LoginResponse;
import huedev.org.data.source.remote.service.AppServiceClient;
import io.reactivex.Single;

public class LoginRemoteDataSource implements LoginDataSource.RemoteDataSource {
    private static LoginRemoteDataSource instance;
    private ApiAuth mApiAuth;

    public LoginRemoteDataSource(ApiAuth apiAuth){
        mApiAuth = apiAuth;
    }

    public static synchronized LoginRemoteDataSource getInstance(Context context){
        if(instance == null){
            instance = new LoginRemoteDataSource(AppServiceClient.getLoginRemoteInstance(context));
        }
        return instance;
    }

    @Override
    public Single<LoginResponse> login(String email, String password) {
        return mApiAuth.login(email, password);
    }
}
