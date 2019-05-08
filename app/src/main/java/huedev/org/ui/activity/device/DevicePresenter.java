package huedev.org.ui.activity.device;

import android.content.Context;
import android.widget.Toast;

import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.source.remote.response.device.CreateDeviceReponse;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.utils.rx.BaseSchedulerProvider;


public class DevicePresenter implements DeviceContact.Presenter {
    private Context context;
    private DeviceContact.View mView;
    private DeviceRepository deviceRepository;
    private BaseSchedulerProvider baseSchedulerProvider;

    public DevicePresenter(Context context, DeviceRepository deviceRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.context = context;
        this.deviceRepository = deviceRepository;
        this.baseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void tempDevices() {

        deviceRepository.tempDevices()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(listTempDeviceResponse -> handleTempDeviceSuccess(listTempDeviceResponse)
                        , error -> handleTempDeviceFail(error));
    }

    @Override
    public void createDevice(String name, String desc,
                             int status, int id_type_device, int id_computer) {
        if (name.isEmpty() || desc.isEmpty()){
            mView.logicCreateFaild();
        }else {

            deviceRepository.createDevice(name, desc, status, id_type_device, id_computer)
                    .subscribeOn(baseSchedulerProvider.io())
                    .observeOn(baseSchedulerProvider.ui())
                    .subscribe(
                            createDeviceReponse -> handleCreateDeviceSuccess(createDeviceReponse),
                            err -> handleCreateDeviceFaild(err));
        }
    }
    // Update
    @Override
    public void updateDevice(String name, String desc, int status, int id_type_device, int id_computer) {

    }
    // Delete
    @Override
    public void delDevice(int id) {

    }

    private void handleCreateDeviceFaild(Throwable err) {
        mView.showLoginError(err);
    }

    private void handleCreateDeviceSuccess(CreateDeviceReponse createDeviceReponse) {
        mView.createSuccess(createDeviceReponse.deviceItem);
    }

    private void handleTempDeviceFail(Throwable error) {
        Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
    }

    private void handleTempDeviceSuccess(ListDeviceResponse listDeviceResponse) {

        mView.updateTempDeviceList(listDeviceResponse.deviceList);
    }

    @Override
    public void setView(DeviceContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
