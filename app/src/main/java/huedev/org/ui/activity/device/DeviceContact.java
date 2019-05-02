package huedev.org.ui.activity.device;

import java.util.List;

import huedev.org.data.model.Device;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface DeviceContact {
    interface View extends BaseView{
        void updateTempDeviceList(List<Device> deviceList);
    }
    interface Presenter extends BasePresenter<View>{
        void tempDevices();
    }
}
