package huedev.org.ui.activity.user;

import java.util.List;

import huedev.org.data.model.User;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface UserContact {
    interface View extends BaseView {
        void usersList(List<User> userList);
    }

    interface Presenter extends BasePresenter<View> {
        void users();
    }
}
