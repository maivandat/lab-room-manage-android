package huedev.org.ui.fragments.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

import huedev.org.data.model.Room;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.utils.rx.SchedulerProvider;

public class RoomFragment extends Fragment implements RoomContract.View {
    RoomContract.Presenter mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    public void init(){
        RoomRepository roomRepository = RoomRepository.getInstance(RoomLocalDataSource.getInstance(),
                RoomRemoteDataSource.getInstance(getContext()));
        mPresenter = new RoomPresenter(getContext(), roomRepository, SchedulerProvider.getInstance());
        mPresenter.setView(this);
        mPresenter.rooms();
    }

    @Override
    public void updateRoomsList(List<Room> roomList) {

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showLoginError(Throwable throwable) {

    }
}
