package huedev.org.data.source;

import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import io.reactivex.Single;

public interface ComputerDataSource {
    interface LocalDataSource{

    }

    interface RemoteDataSource{
        Single<ListComputerResponse> computersByRoom(int id);
    }
}
