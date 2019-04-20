package huedev.org.ui.fragments.room.create;

import android.content.Context;

import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class RCreatePresenter implements RCreateContact.Presenter {

    Context mContext;
    RCreateContact.View mView;
    BaseSchedulerProvider mBaseSchedulerProvider;
    RoomRepository mRoomRepository;

    public RCreatePresenter(Context context, BaseSchedulerProvider baseSchedulerProvider, RoomRepository roomRepository) {
        this.mContext = context;
        this.mBaseSchedulerProvider = baseSchedulerProvider;
        this.mRoomRepository = roomRepository;
    }

    @Override
    public void createRoom(String name, String desc, String status) {
        if (name.isEmpty() || desc.isEmpty() || status.isEmpty()){
            mView.logicFaild();
        }else {
            mRoomRepository.createRoom(name, desc, status)
                    .subscribeOn(mBaseSchedulerProvider.io())
                    .observeOn(mBaseSchedulerProvider.ui())
                    .subscribe(createRoomReponse -> handleAddRommSuccess(createRoomReponse),
                            error -> handleAddRommFailed(error));
        }

    }

    private void handleAddRommSuccess(CreateRoomReponse createRoomReponse) {
        mView.hideLoadingIndicator();
        mView.logicCorrect(createRoomReponse.room);
    }

    private void handleAddRommFailed(Throwable error) {
       mView.showLoginError(error);
    }

    @Override
    public void setView(RCreateContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
