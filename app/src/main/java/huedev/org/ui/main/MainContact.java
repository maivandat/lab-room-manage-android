package huedev.org.ui.main;

import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface MainContact {
    interface View extends BaseView {
        void logicSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void logic();
    }
}
