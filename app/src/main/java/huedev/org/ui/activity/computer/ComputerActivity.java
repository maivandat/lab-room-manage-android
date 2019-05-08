package huedev.org.ui.activity.computer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Device;
import huedev.org.data.model.Room;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.ui.activity.device.DeviceContact;
import huedev.org.ui.activity.device.DevicePresenter;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.adapter.ComputerAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.ui.fragments.room.RoomContact;
import huedev.org.ui.fragments.room.RoomPresenter;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;


public class ComputerActivity extends BaseActivity implements
        ComputerContract.View, View.OnClickListener, DeviceContact.View, RoomContact.View {

    ComputerPresenter mComputerPresenter;
    DevicePresenter mDevicePresenter;
    RoomPresenter mRoomPresenter;
    RecyclerView mRvComputer;
    ComputerAdapter mComputerAdapter;
    Toolbar tbComputer;
    Navigator navigator;
    List<Device> mDeviceList;
    List<Room> mRoomList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        navigator = new Navigator(this);
        tbComputer = findViewById(R.id.toolbar_computer);
        mRvComputer = findViewById(R.id.rv_computer);
        mDeviceList = new ArrayList<>();
        mRoomList = new ArrayList<>();
        tbComputer.setTitle("");
        setSupportActionBar(tbComputer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tbComputer.setNavigationIcon(R.drawable.btn_back);

        init();
        tbComputer.setNavigationOnClickListener(this);
    }

    public void init(){
        ComputerRepository computerRepository =
                ComputerRepository.getInstance(ComputerLocalDataSource.getInstance()
                        , ComputerRemoteDataSource.getInstance(this));
        DeviceRepository deviceRepository =
                DeviceRepository.getInstance(
                        DeviceLocalDataSource.getInstance(),
                        DeviceRemoteDataSource.getInstance(this));
        RoomRepository roomRepository =
                RoomRepository.getInstance(RoomLocalDataSource.getInstance()
                        , RoomRemoteDataSource.getInstance(this));

        mComputerPresenter = new ComputerPresenter(
                this
                , computerRepository
                , SchedulerProvider.getInstance());
        mDevicePresenter = new DevicePresenter(
                this,
                deviceRepository,
                SchedulerProvider.getInstance());
        mRoomPresenter = new RoomPresenter(
                this
                , roomRepository
                , SchedulerProvider.getInstance());

        mRoomPresenter.setView(this);
        mDevicePresenter.setView(this);
        mComputerPresenter.setView(this);

        mRoomPresenter.rooms();
        mDevicePresenter.tempDevices();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_computertop, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_computerReset){
            mComputerPresenter.computersByRoom();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateTempDeviceList(List<Device> deviceList) {
        mDeviceList = deviceList;
        mComputerPresenter.computersByRoom();
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
        mComputerAdapter = new ComputerAdapter(this, computerList, mDeviceList, mRoomList, mComputerPresenter);
        mRvComputer.setAdapter(mComputerAdapter);
        GridLayoutManager manager = new GridLayoutManager(
                this,
                2,
                GridLayoutManager.VERTICAL,
                false);
        mRvComputer.setLayoutManager(manager);
    }

    @Override
    public void updateComputerListAll(List<Computer> computerList) {

    }

    @Override
    public void createSucess(Computer computer) {

    }

    @Override
    public void updateSuccess(Dialog dialog) {
        dialog.dismiss();
        mComputerPresenter.computersByRoom();
    }

    @Override
    public void delSuccess(DialogInterface dialogInterface) {
        NotifyHelper.showSnackbar((View) dialogInterface, "Delete success", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void logicUpdateFaild(Dialog dialog) {
        NotifyHelper.showSnackbar(dialog.findViewById(R.id.btn_add),
                StringHelper.getStringResourceByName("logic_faild", this),
                Snackbar.LENGTH_SHORT);
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
    public void onClick(View view) {
        navigator.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }


    @Override
    public void updateRoomsList(List<Room> roomList) {
        mRoomList = roomList;
    }

    @Override
    public void updateRoomItem(Room room, Dialog dialog) {

    }

    @Override
    public void createRoomItem(Room room) {

    }

    @Override
    public void delRoomSuccess(DialogInterface dialogInterface) {

    }

    @Override
    public void delRoomFaild(Throwable err) {

    }

    @Override
    public void addRoomFaild(Throwable err) {

    }

    @Override
    public void updateRoomFaild(Throwable err) {

    }

    @Override
    public void logicSuccess() {

    }

    @Override
    public void logicFaild() {

    }

}
