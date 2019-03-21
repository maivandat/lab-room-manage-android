package huedev.org.data.source;

import huedev.org.data.source.remote.response.user.ListUserResponse;
import io.reactivex.Single;

public interface UserDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListUserResponse> users();
    }
}
