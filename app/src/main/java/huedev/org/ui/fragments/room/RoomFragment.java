package huedev.org.ui.fragments.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

import huedev.org.data.model.Room;

public class RoomFragment extends Fragment implements RoomContract.View {
    RoomContract.Presenter mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setView(this);
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
