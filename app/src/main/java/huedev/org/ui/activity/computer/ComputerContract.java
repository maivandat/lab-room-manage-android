package huedev.org.ui.activity.computer;

import java.util.List;

import huedev.org.data.model.Computer;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface ComputerContract {
    interface View extends BaseView {
        void updateComputerList(List<Computer> computerList);
        void createSucess(Computer computer);
        void logicCreateFaild();
    }

    interface Presenter extends BasePresenter<View> {
        void computersByRoom();
        void createComputer(String name, String desc, int status, int room_id);
        void updateComputer();
        void deleteComputer();
    }
}
