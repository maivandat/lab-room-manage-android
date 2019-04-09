package huedev.org.ui.fragments.calendar;

import java.util.List;

import huedev.org.data.model.Work;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface CalendarContact {
    interface View extends BaseView {
        void updateWork(List<Work> workList);
    }

    interface Presenter extends BasePresenter<View> {
        void works(String name, String time);
    }
}
