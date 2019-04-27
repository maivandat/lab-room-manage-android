package huedev.org.data.source;

import huedev.org.data.model.User;
import huedev.org.data.source.remote.response.user.CreateUserReponse;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import io.reactivex.Single;

public interface UserDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListUserResponse> users();
        Single<CreateUserReponse> userItem(String name, String username, String password,
                                           String confirm_password, String email, int role);
        Single<CreateUserReponse> userItem(String content_type, String name, String username,
                                           String password, String email, String confirm_password);
        Single<UpdateUserReponse> update(String id, String username,
                                         String password, String name,
                                         String email, int role);
        Single<Void> delete(String id);

    }
}
