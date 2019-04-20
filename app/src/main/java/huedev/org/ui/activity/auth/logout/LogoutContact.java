package huedev.org.ui.activity.auth.logout;

import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface LogoutContact {
    interface View extends BaseView {
        void logout();
    }
    interface Presenter extends BasePresenter<View> {
        void logout();
    }
}
