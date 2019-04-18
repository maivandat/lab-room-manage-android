package huedev.org.ui.auth.logout;

import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface LogoutContact {
    interface View extends BaseView {
        void logout();
    }
    interface Presenter extends BasePresenter<View> {
        void logout();
    }
}
