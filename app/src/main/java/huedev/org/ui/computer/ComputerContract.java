package huedev.org.ui.computer;

import java.util.List;

import huedev.org.data.model.Computer;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface ComputerContract {
    interface View extends BaseView {
        void updateComputerList(List<Computer> computerList);
    }

    interface Presenter extends BasePresenter<View> {
        void computersByRoom();
    }
}
