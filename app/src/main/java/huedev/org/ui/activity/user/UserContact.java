package huedev.org.ui.activity.user;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.List;

import huedev.org.data.model.User;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface UserContact {
    interface View extends BaseView {
        void usersList(List<User> userList);
        void user(User user);
        void user(User user, Dialog dialog);
        void delUserSuccess(DialogInterface dialogInterface);
        void logicFaild();
        void EditFaild(Throwable err);
        void DelFaild(Throwable err);
        void logicSuccess();
        void oldPasswordFail();
        void newPasswordFail();
    }

    interface Presenter extends BasePresenter<View> {
        void users();
        void updateUser(String name, String email,
                        String oldPassword, String newPassword,
                        String confirmNewPassword);

        void updateUser(String id, String username,
                        String password, String name,
                        String email, int role, Dialog dialog);

        void deleteUser(String id, DialogInterface dialogInterface);
    }
}
