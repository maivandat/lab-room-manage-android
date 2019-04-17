package huedev.org.ui.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Device;
import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.ui.adapter.DeviceAdapter;
import huedev.org.ui.base.BaseActivity;
import huedev.org.ui.computer.ComputerActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class DeviceActivity extends BaseActivity implements DeviceConstract.View{
    ArrayList<Integer> arrayimg;
    TextView textNameCom;
    DeviceConstract.Presenter presenter;
    DeviceAdapter deviceAdapter;
    RecyclerView recyclerView;
    List<Device> listDevices;
    Navigator navigator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        mapping();
        init();
    }

    private void init() {
        DeviceRepository deviceRepository = DeviceRepository.getInstance(DeviceLocalDataSource.getInstance()
        , DeviceRemoteDataSource.getInstance(this));
        presenter = new DevicePresenter(this
                , deviceRepository
                , SchedulerProvider.getInstance()
        );
        presenter.setView(this);
        presenter.tempDevices();
    }

    private void mapping() {
        textNameCom = findViewById(R.id.text_comtitle);
        recyclerView = findViewById(R.id.recycler_device);
        listDevices = new ArrayList<>();
        navigator = new Navigator(this);
        arrayimg = new ArrayList<>();
    }

    @Override
    public void updateTempDeviceList(List<Device> deviceList) {
        String idComputer = navigator.getData().getString(AppConstants.ID_COMPUTER);
        String name  = navigator.getData().getString(AppConstants.ID_COM_NAME);
        for (Device device : deviceList){
            if (device.getComputersId().equals(idComputer)){
                listDevices.add(device);
            }
        }
        recyclerView.setHasFixedSize(true);
        deviceAdapter = new DeviceAdapter(this,listDevices);
        recyclerView.setAdapter(deviceAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        deviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIndicator() {
        Toast.makeText(this, "Xin chào", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingIndicator() {
        Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Lỗi!\n" +
                "" + throwable.toString(), Toast.LENGTH_SHORT).show();
    }
}
