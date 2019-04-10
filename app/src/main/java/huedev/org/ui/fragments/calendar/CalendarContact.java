package huedev.org.ui.fragments.calendar;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import huedev.org.data.model.Work;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface CalendarContact {
    interface View extends BaseView {
        void warningData();
        void requireData();
        void getWorkOfDate(List<Work> workList);
    }

    interface Presenter extends BasePresenter<View> {
        void works(String name, String time, String date,
                   EditText etName, EditText etTime,
                   RadioButton rbAM, TextView tvWorkEmpty);

        void dates(String date, TextView tvWorkEmpty);
    }
}
