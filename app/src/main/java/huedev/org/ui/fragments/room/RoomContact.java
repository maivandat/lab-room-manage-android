package huedev.org.ui.fragments.room;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.List;

import huedev.org.data.model.Room;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface RoomContact {
    interface View extends BaseView {
        void updateRoomsList(List<Room> roomList);
        void updateRoomItem(Room room, Dialog dialog);
        void createRoomItem(Room room);
        void delRoomSuccess(DialogInterface dialogInterface);
        void delRoomFaild(Throwable err);
        void addRoomFaild(Throwable err);
        void updateRoomFaild(Throwable err);
        void logicSuccess();
        void logicFaild();

    }
    interface Presenter extends BasePresenter<View> {
        void createRoom(String name, String desc, String status);
        void rooms();
        void updateRoom(String id, String name, String desc, String status, Dialog dialog);
        void deleteRoom(String id, DialogInterface dialogInterface);
    }
}
