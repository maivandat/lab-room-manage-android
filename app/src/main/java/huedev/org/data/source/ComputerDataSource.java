package huedev.org.data.source;

import huedev.org.data.model.User;
import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import io.reactivex.Single;
import retrofit2.Call;

public interface ComputerDataSource {
    interface LocalDataSource{
    }

    interface RemoteDataSource{
        Single<ListComputerResponse> computersByRoom();
    }
}
