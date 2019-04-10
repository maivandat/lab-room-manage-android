package huedev.org.ui.device;

import java.util.List;

import huedev.org.data.model.Device;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface DeviceConstract {
    interface View extends BaseView{
        void updateTempDeviceList(List<Device> deviceList);
    }
    interface Presenter extends BasePresenter<View>{
        void tempDevices();
    }
}
