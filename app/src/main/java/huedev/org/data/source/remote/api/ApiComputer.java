package huedev.org.data.source.remote.api;

import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiComputer {
    @GET("rooms/{roomId}/detail")
    Single<ListComputerResponse> computersByRoom(int roomId);
}
