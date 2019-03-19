package huedev.org.ui.fragments.room;

import android.content.Context;

import com.google.common.base.Preconditions;

import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.remote.response.ListRoomResponse;
import huedev.org.utils.rx.SchedulerProvider;

public class RoomPresenter implements RoomContract.Presenter {

    Context mContext;
    RoomContract.View mView;
    RoomRepository mRoomRepository;
    SchedulerProvider mSchedulerProvider;

    public RoomPresenter(Context context, RoomRepository roomRepository,
                         SchedulerProvider schedulerProvider) {
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
                .subscribe(loginResponse -> handleRoomsSuccess(loginResponse),
                        error -> handleRoomsFailed(error));
    }

    private void handleRoomsSuccess(ListRoomResponse listRoomResponse){
        mView.updateRoomsList(listRoomResponse.roomList);
        mView.hideLoadingIndicator();
    }

    private void handleRoomsFailed(Throwable err){
        mView.hideLoadingIndicator();
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
