package huedev.org.ui.activity.computer;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.ui.activity.device.DeviceContact;
import huedev.org.ui.activity.device.DevicePresenter;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.adapter.ComputerAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;


public class ComputerActivity extends BaseActivity implements
        ComputerContract.View, View.OnClickListener, DeviceContact.View {

    ComputerPresenter mComputerPresenter;
    DevicePresenter mDevicePresenter;
    RecyclerView mRvComputer;
    ComputerAdapter mComputerAdapter;
    Toolbar tbComputer;
    Navigator navigator;
    List<Device> mDeviceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        navigator = new Navigator(this);
        tbComputer = findViewById(R.id.toolbar_computer);
        mRvComputer = findViewById(R.id.rv_computer);
        mDeviceList = new ArrayList<>();
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
        mComputerPresenter = new ComputerPresenter(
                this
                , computerRepository
                , SchedulerProvider.getInstance());
        mDevicePresenter = new DevicePresenter(
                this,
                deviceRepository,
                SchedulerProvider.getInstance());
        mDevicePresenter.setView(this);
        mComputerPresenter.setView(this);
        mDevicePresenter.tempDevices();
        mComputerPresenter.computersByRoom();

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
    }

    @Override
    public void updateComputerList(List<Computer> computerList) {
        mComputerAdapter = new ComputerAdapter(this, computerList, mDeviceList);
        mRvComputer.setAdapter(mComputerAdapter);
        GridLayoutManager manager = new GridLayoutManager(
                this,
                2,
                GridLayoutManager.VERTICAL,
                false);
        mRvComputer.setLayoutManager(manager);
    }

    @Override
    public void createSucess(Computer computer) {

    }

    @Override
    public void logicCreateFaild() {

    }

    @Override
    public void showLoadingIndicator() {
    }

    @Override
    public void hideLoadingIndicator() {
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


}
