package huedev.org.ui.fragments.room;

import android.app.Activity;
import android.content.Context;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import huedev.org.data.model.Room;
import huedev.org.data.model.User;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class RoomPresenter implements RoomContract.Presenter {

    Context mContext;
    RoomContract.View mView;
    RoomRepository mRoomRepository;
    BaseSchedulerProvider mSchedulerProvider;

    public RoomPresenter(Context context, RoomRepository roomRepository,
                         BaseSchedulerProvider schedulerProvider) {
        this.mContext = Preconditions.checkNotNull(context);
        this.mRoomRepository = Preconditions.checkNotNull(roomRepository);
        this.mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider);
    }

    @Override
    public void rooms() {
        mView.showLoadingIndicator();
        mRoomRepository.rooms()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(listRoomResponse -> handleRoomsSuccess(listRoomResponse),
                        error -> handleRoomsFailed(error));
    }

    private void handleRoomsSuccess(ListRoomReponse listRoomReponse){
        mView.hideLoadingIndicator();
        mView.updateRoomsList(listRoomReponse.roomList);
    }

    private void handleRoomsFailed(Throwable err){
        mView.showLoginError(err);
    }
    @Override
    public void setView(RoomContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
