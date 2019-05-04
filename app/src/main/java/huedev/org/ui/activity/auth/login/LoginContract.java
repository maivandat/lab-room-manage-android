package huedev.org.ui.activity.auth.login;

import huedev.org.data.model.User;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface LoginContract {
    interface View extends BaseView{
        void getUser(User user);
        void logicFaild();
    }
    interface Presenter extends BasePresenter<View>{
        void login(String email, String password);
    }
}
