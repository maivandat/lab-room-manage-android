package huedev.org.data.source;

import huedev.org.data.source.remote.response.auth.LoginResponse;
import io.reactivex.Single;

public interface LoginDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<LoginResponse> login(String email, String password);
    }
}
