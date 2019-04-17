package huedev.org.ui.device;

import android.content.Context;
import android.widget.Toast;

import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.source.remote.response.device.ListDeviceResponse;
import huedev.org.utils.rx.BaseSchedulerProvider;


public class DevicePresenter implements DeviceConstract.Presenter {
    private Context context;
    private DeviceConstract.View mView;
    private DeviceRepository deviceRepository;
    private BaseSchedulerProvider baseSchedulerProvider;

    public DevicePresenter(Context context, DeviceRepository deviceRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.context = context;
        this.deviceRepository = deviceRepository;
        this.baseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void tempDevices() {
        mView.showLoadingIndicator();
        deviceRepository.tempDevices()
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(listTempDeviceResponse -> handleTempDeviceSuccess(listTempDeviceResponse)
                        , error -> handleTempDeviceFail(error));
    }

    private void handleTempDeviceFail(Throwable error) {
        Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
    }

    private void handleTempDeviceSuccess(ListDeviceResponse listDeviceResponse) {
        mView.hideLoadingIndicator();
        mView.updateTempDeviceList(listDeviceResponse.deviceList);
    }

    @Override
    public void setView(DeviceConstract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
