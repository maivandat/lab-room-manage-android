package huedev.org.ui.activity.auth.register;

import android.app.Dialog;

import huedev.org.data.model.User;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface RegisterContact {
    interface View extends BaseView{
        void logicSuccess(User user);
        void logicSuccess(User user, Dialog dialog);
        void logicFaild();
    }

    interface Presenter extends BasePresenter<View>{
        void register(String content_type, String name, String username, String password, String cfPassword, String email);
        void register(String name, String username, String password, String cfPassword, String email, int role, Dialog dialog);
    }
}
