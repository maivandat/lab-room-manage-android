package huedev.org.ui.activity.computer;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.List;

import huedev.org.data.model.Computer;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface ComputerContract {
    interface View extends BaseView {
        void updateComputerList(List<Computer> computerList);
        void updateComputerListAll(List<Computer> computerList);
        void createSucess(Computer computer);
        void updateSuccess(Dialog dialog);
        void delSuccess(DialogInterface dialogInterface);
        void logicUpdateFaild(Dialog dialog);
        void logicCreateFaild();
    }

    interface Presenter extends BasePresenter<View> {
        void computersByRoom();
        void computersByRoomAll();
        void createComputer(String name, String desc, int status, int room_id);
        void updateComputer(int id, String name, String desc, int status, int room_id, Dialog dialog);
        void deleteComputer(int id, DialogInterface dialogInterface);
    }
}
