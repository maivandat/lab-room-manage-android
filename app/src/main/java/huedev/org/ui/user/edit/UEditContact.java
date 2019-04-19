package huedev.org.ui.user.edit;

import huedev.org.data.model.User;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface UEditContact {

    interface View extends BaseView{
        void user(User user);
        void logicFaild();
        void logicSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void updateUser(String name, String email);
        void updateUser(String oldPassword, String newPassword, String confirmNewPassword);
    }
}
