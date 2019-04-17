package huedev.org.data.source;

import huedev.org.data.model.User;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import io.reactivex.Single;

public interface UserDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListUserResponse> users();
        Single<UpdateUserReponse> update(String id, String username,
                                         String password, String name,
                                         String email, int role);
    }
}
