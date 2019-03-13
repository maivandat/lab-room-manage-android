package huedev.org.ui.auth;

import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public class LoginContract {
    interface View extends BaseView{}
    interface Presenter extends BasePresenter<View>{
        void login(String email, String password);
    }
}
