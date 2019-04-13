package huedev.org.ui.auth;

import android.provider.ContactsContract;
import android.widget.CheckBox;

import huedev.org.data.model.User;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface LoginContract {
    interface View extends BaseView{
        void getUser(User user);
    }
    interface Presenter extends BasePresenter<View>{
        void login(String email, String password);
    }
}
