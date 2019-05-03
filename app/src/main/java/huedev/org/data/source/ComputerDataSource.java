package huedev.org.data.source;

import huedev.org.data.source.remote.response.computer.CreateCPTReponse;
import huedev.org.data.source.remote.response.computer.ListCPTResponse;
import io.reactivex.Single;

public interface ComputerDataSource {
    interface LocalDataSource{
    }

    interface RemoteDataSource{
        Single<ListCPTResponse> computersByRoom();
        Single<CreateCPTReponse> computerItem(String name, String desc,
                                              int status, int room_id);
    }

}
