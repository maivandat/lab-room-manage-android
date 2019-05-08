package huedev.org.ui.activity.device;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.data.model.TypeDevice;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.repository.TypeDeviceRepository;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.local.TypeDeviceLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.data.source.remote.TypeDeviceRemoteDataSource;
import huedev.org.ui.activity.computer.ComputerContract;
import huedev.org.ui.activity.computer.ComputerPresenter;
import huedev.org.ui.activity.type_device.TypeDeviceContact;
import huedev.org.ui.activity.type_device.TypeDevicePresenter;
import huedev.org.ui.adapter.DeviceAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class DeviceActivity extends BaseActivity implements DeviceContact.View,
        ComputerContract.View, TypeDeviceContact.View {
    ArrayList<Integer> arrayimg;
    TextView textNameCom;
    DevicePresenter mDevicePresenter;
    TypeDevicePresenter mTypeDevicePresenter;
    ComputerPresenter mComputerPresenter;
    DeviceAdapter deviceAdapter;
    RecyclerView recyclerView;
    List<Device> listDevices;
    List<TypeDevice> TDList;
    List<Computer> CPTList;
    Navigator navigator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        mapping();
        init();
    }

    private void init() {
        DeviceRepository deviceRepository = DeviceRepository.getInstance(
                DeviceLocalDataSource.getInstance(),
                DeviceRemoteDataSource.getInstance(this));

        ComputerRepository computerRepository = ComputerRepository.getInstance(
                ComputerLocalDataSource.getInstance(),
                ComputerRemoteDataSource.getInstance(this));

        TypeDeviceRepository typeDeviceRepository = TypeDeviceRepository.getInstance(
                TypeDeviceLocalDataSource.getInstance(),
                TypeDeviceRemoteDataSource.getInstance(this));

        mDevicePresenter = new DevicePresenter(this
                , deviceRepository
                , SchedulerProvider.getInstance()
        );

        mComputerPresenter = new ComputerPresenter(this,
                computerRepository,
                SchedulerProvider.getInstance());

        mTypeDevicePresenter = new TypeDevicePresenter(this,
                typeDeviceRepository,
                SchedulerProvider.getInstance());

        mDevicePresenter.setView(this);
        mComputerPresenter.setView(this);
        mTypeDevicePresenter.setView(this);
        mComputerPresenter.computersByRoomAll();
        mTypeDevicePresenter.listTD();

    }

    private void mapping() {
        textNameCom = findViewById(R.id.text_comtitle);
        recyclerView = findViewById(R.id.recycler_device);
        listDevices = new ArrayList<>();
        TDList = new ArrayList<>();
        CPTList = new ArrayList<>();
        navigator = new Navigator(this);
        arrayimg = new ArrayList<>();
    }

    @Override
    public void updateTempDeviceList(List<Device> deviceList) {
        String idComputer = navigator.getData().getString(AppConstants.ID_COMPUTER);
        String name  = navigator.getData().getString(AppConstants.ID_COM_NAME);
        if (deviceList.size() > 0) {
            for (Device device : deviceList){
                if (device.getComputersId().equals(idComputer)){
                    listDevices.add(device);
                }
            }
            recyclerView.setHasFixedSize(true);
            deviceAdapter = new DeviceAdapter(this,listDevices, TDList, CPTList, mDevicePresenter);
            recyclerView.setAdapter(deviceAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            deviceAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void createSuccess(Device device) {

    }

    @Override
    public void updateSucces(Device device) {

    }

    @Override
    public void logicUpdateFaid() {

    }

    @Override
    public void updateComputerList(List<Computer> computerList) {

    }

    @Override
    public void updateComputerListAll(List<Computer> computerList) {
        CPTList = computerList;
        mDevicePresenter.tempDevices();

    }

    @Override
    public void createSucess(Computer computer) {

    }

    @Override
    public void updateSuccess(Dialog dialog) {

    }

    @Override
    public void delSuccess(DialogInterface dialogInterface) {

    }

    @Override
    public void logicUpdateFaild(Dialog dialog) {

    }

    @Override
    public void logicCreateFaild() {

    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {
    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Lỗi!\n" +
                "" + throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void listTD(List<TypeDevice> listTD) {
        TDList = listTD;
    }
}
