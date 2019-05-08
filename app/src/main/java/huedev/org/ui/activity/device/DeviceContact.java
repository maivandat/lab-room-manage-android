package huedev.org.ui.activity.device;

import java.util.List;

import huedev.org.data.model.Device;
import huedev.org.ui.base.activity.BasePresenter;
import huedev.org.ui.base.activity.BaseView;

public interface DeviceContact {
    interface View extends BaseView{
        void updateTempDeviceList(List<Device> deviceList);
        void createSuccess(Device device);
        void updateSucces(Device device);
        void logicUpdateFaid();
        void logicCreateFaild();
    }
    interface Presenter extends BasePresenter<View>{
        void tempDevices();
        void createDevice(String name, String desc, int status, int id_type_device, int id_computer);
        void updateDevice(String name, String desc, int status, int id_type_device, int id_computer);
        void delDevice(int id);
    }
}
