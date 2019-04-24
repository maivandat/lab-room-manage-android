package huedev.org.ui.fragments.room;

import android.app.Dialog;
import android.content.Context;

import com.google.common.base.Preconditions;

import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.remote.response.room.CreateRoomReponse;
import huedev.org.data.source.remote.response.room.ListRoomReponse;
import huedev.org.data.source.remote.response.room.UpdateRoomReponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class RoomPresenter implements RoomContact.Presenter {

    Context mContext;
    RoomContact.View mView;
    RoomRepository mRoomRepository;
    BaseSchedulerProvider mSchedulerProvider;

    public RoomPresenter(Context context, RoomRepository roomRepository,
                         BaseSchedulerProvider schedulerProvider) {
        this.mContext = Preconditions.checkNotNull(context);
        this.mRoomRepository = Preconditions.checkNotNull(roomRepository);
        this.mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider);
    }

    @Override
    public void createRoom(String name, String desc, String status) {

        if (name.isEmpty() || desc.isEmpty()){
            mView.logicFaild();
        }else {
            mRoomRepository.createRoom(name, desc, status)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(createRoomReponse -> handleAddRommSuccess(createRoomReponse),
                            error -> handleAddRommFailed(error));
        }

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

    @Override
    public void updateRoom(String id, String name, String desc, String status, Dialog dialog) {
        if (name.isEmpty() || desc.isEmpty()){
            mView.logicFaild();
        }else {
            mView.showLoadingIndicator();
            mRoomRepository.updateRoom(id, name, desc, status)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(updateRoomReponse -> handleUpdateRoomSuccess(updateRoomReponse, dialog),
                            err -> handleUpdateRoomFaild(err));
        }

    }

    @Override
    public void deleteRoom(String id) {
        mRoomRepository.deleteRoom(id)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(voidd -> handleDelRoomSuccess(),
                        err ->handleDelRoomFaild(err));
    }

    //Del Room

    private void handleDelRoomSuccess() {

    }

    private void handleDelRoomFaild(Throwable err) {
        mView.delRoomFaild(err);
    }

    //Add Room
    private void handleAddRommSuccess(CreateRoomReponse createRoomReponse) {
        mView.hideLoadingIndicator();
        mView.createRoomItem(createRoomReponse.room);
    }

    private void handleAddRommFailed(Throwable error) {
        mView.showLoginError(error);
    }

    //Update Room
    private void handleUpdateRoomSuccess(UpdateRoomReponse updateRoomReponse, Dialog dialog) {
        mView.hideLoadingIndicator();
        mView.updateRoomItem(updateRoomReponse.itemRoom, dialog);

    }

    private void handleUpdateRoomFaild(Throwable err) {
        mView.showLoginError(err);
    }

    // Get List Room
    private void handleRoomsSuccess(ListRoomReponse listRoomReponse){
        mView.hideLoadingIndicator();
        mView.updateRoomsList(listRoomReponse.roomList);
    }

    private void handleRoomsFailed(Throwable err){
        mView.showLoginError(err);
    }
    @Override
    public void setView(RoomContact.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
