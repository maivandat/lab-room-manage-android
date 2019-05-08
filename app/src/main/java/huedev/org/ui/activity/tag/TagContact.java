package huedev.org.ui.activity.tag;

import huedev.org.data.model.Tag;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface TagContact {
    interface View extends BaseView {
        void createSuccess(Tag tag);
        void logicCreateFaild();
    }

    interface Presenter extends BasePresenter<View> {
        void createTag(String value, int id_device);
    }
}
