package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiComputer {
    @GET("rooms/{roomId}")
    Single<ListComputerResponse> computersByRoom (@Path("roomId")int roomId);
}
