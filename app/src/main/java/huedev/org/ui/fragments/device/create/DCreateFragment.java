package huedev.org.ui.fragments.device.create;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
import huedev.org.ui.activity.device.DeviceContact;
import huedev.org.ui.activity.device.DevicePresenter;
import huedev.org.ui.activity.type_device.TypeDeviceContact;
import huedev.org.ui.activity.type_device.TypeDevicePresenter;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.utils.helpers.ArrayHelper;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.WidgetHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.rx.SchedulerProvider;

public class DCreateFragment extends BaseFagment
        implements TypeDeviceContact.View, ComputerContract.View, DeviceContact.View {

    EditText etDeviceTitle, etDeviceDesc;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    Spinner spinnerFirst, spinnerSecond;
    TextView tvTitleFirst, tvTitleSecond;

    ArrayList<TypeDevice> listTypeDevice;
    ArrayList<Computer> listComputer;

    TypeDeviceContact.Presenter mTDPresenter;
    ComputerPresenter mComputerPresenter;

    DevicePresenter mDevicePresenter;

    int idTD, idCPT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        etDeviceTitle = view.findViewById(R.id.et_titleCreate);
        etDeviceDesc = view.findViewById(R.id.et_descCreate);
        rgStatus = view.findViewById(R.id.rg_status);
        rbActive = view.findViewById(R.id.rb_active);
        rbRepair = view.findViewById(R.id.rb_repair);
        rbBroken = view.findViewById(R.id.rb_broken);
        btnAdd = view.findViewById(R.id.btn_add);
        spinnerFirst = view.findViewById(R.id.spiner_type_first);
        spinnerSecond = view.findViewById(R.id.spiner_type_second);
        tvTitleFirst = view.findViewById(R.id.tv_tytpe_title_first);
        tvTitleSecond = view.findViewById(R.id.tv_tytpe_title_second);

        listTypeDevice = new ArrayList<>();
        listComputer = new ArrayList<>();

        tvTitleFirst.setText(StringHelper.getStringResourceByName("type_device", getContext()));
        tvTitleSecond.setText(StringHelper.getStringResourceByName("computer_title", getContext()));
        etDeviceTitle.setHint(StringHelper.getStringResourceByName("device_title", getContext()));

        init();

        spinnerFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTD = listTypeDevice.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idCPT = Integer.parseInt(listComputer.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAdd.setOnClickListener(view1 -> {
            String name = etDeviceTitle.getText().toString().trim();
            String desc = etDeviceDesc.getText().toString().trim();
            int status = 0;
            if (rbActive.isChecked()){
                status = 0;
            }if (rbRepair.isChecked()){
                status = 1;
            }if (rbBroken.isChecked()){
                status = 2;
            }

            mDevicePresenter.createDevice(name, desc, status, idTD, idCPT);
        });



        return view;
    }

    private void init() {
        TypeDeviceRepository typeDeviceRepository = TypeDeviceRepository.getInstance(
                TypeDeviceLocalDataSource.getInstance(),
                TypeDeviceRemoteDataSource.getInstance(getContext()));

        mTDPresenter = new TypeDevicePresenter(
                getContext(),
                typeDeviceRepository,
                SchedulerProvider.getInstance());

        ComputerRepository computerRepository = ComputerRepository.getInstance(
                ComputerLocalDataSource.getInstance(),
                ComputerRemoteDataSource.getInstance(getContext()));

        mComputerPresenter = new ComputerPresenter(
                getContext(),
                computerRepository,
                SchedulerProvider.getInstance());

        DeviceRepository deviceRepository = DeviceRepository.getInstance(
                DeviceLocalDataSource.getInstance(),
                DeviceRemoteDataSource.getInstance(getContext())
        );

        mDevicePresenter = new DevicePresenter(
                getContext(),
                deviceRepository,
                SchedulerProvider.getInstance());

        mComputerPresenter.setView(this);
        mTDPresenter.setView(this);
        mDevicePresenter.setView(this);

        mTDPresenter.listTD();
        mComputerPresenter.computersByRoomAll();

    }

    @Override
    public void listTD(List<TypeDevice> listTD) {
        listTypeDevice = (ArrayList<TypeDevice>) listTD;
        WidgetHelper.setupSpinner(
                spinnerFirst,
                ArrayHelper.getNameArrayTD((ArrayList<TypeDevice>) listTD),
                getContext());
    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {
        dialog.show();
    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void showLoginError(Throwable throwable) {

    }

    @Override
    public void updateComputerList(List<Computer> computerList) {

    }

    @Override
    public void updateComputerListAll(List<Computer> computerList) {
        listComputer = (ArrayList<Computer>) computerList;
        WidgetHelper.setupSpinner(
                spinnerSecond,
                ArrayHelper.getNameArrayC((ArrayList<Computer>) computerList),
                getContext());
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
    public void updateTempDeviceList(List<Device> deviceList) {

    }

    @Override
    public void createSuccess(Device device) {
        NotifyHelper.showSnackbar(btnAdd,
                "Create " + device.getName() + " success",
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void updateSucces(Device device) {

    }

    @Override
    public void logicUpdateFaid() {

    }

    @Override
    public void logicCreateFaild() {
        NotifyHelper.showSnackbar(
                btnAdd,
                StringHelper.getStringResourceByName("logic_faild", getContext()),
                Snackbar.LENGTH_SHORT);
    }
}
