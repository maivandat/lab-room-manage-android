package huedev.org.ui.fragments.room;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

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

    //Add Room
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

    private void handleAddRommSuccess(CreateRoomReponse createRoomReponse) {
        mView.hideLoadingIndicator();
        mView.createRoomItem(createRoomReponse.room);
    }

    private void handleAddRommFailed(Throwable error) {
        mView.addRoomFaild(error);
    }
    //
    // Get List Room
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
    //
    //Update Room
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

    private void handleUpdateRoomSuccess(UpdateRoomReponse updateRoomReponse, Dialog dialog) {
        mView.hideLoadingIndicator();
        mView.updateRoomItem(updateRoomReponse.itemRoom, dialog);

    }

    private void handleUpdateRoomFaild(Throwable err) {
        mView.updateRoomFaild(err);
    }
    //
    //Del Room
    @Override
    public void deleteRoom(String id, DialogInterface dialogInterface) {
        mRoomRepository.deleteRoom(id)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(voidd -> handleDelRoomSuccess(dialogInterface),
                        err ->handleDelRoomFaild(err));
    }

    private void handleDelRoomSuccess(DialogInterface dialogInterface) {
        mView.delRoomSuccess(dialogInterface);
    }

    private void handleDelRoomFaild(Throwable err) {
        mView.delRoomFaild(err);
    }
    //
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
