package huedev.org.ui.activity.main;

import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface MainContact {
    interface View extends BaseView {
        void logicSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void logic();
    }
}
