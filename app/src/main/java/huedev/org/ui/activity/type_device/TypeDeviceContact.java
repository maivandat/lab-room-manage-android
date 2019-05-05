package huedev.org.ui.activity.type_device;

import java.util.List;

import huedev.org.data.model.TypeDevice;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface TypeDeviceContact {
    interface View extends BaseView {
        void listTD(List<TypeDevice> listTD);
    }

    interface Presenter extends BasePresenter<View> {
        void listTD();
    }
}
