package huedev.org.ui.fragments.computer.create;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.model.Room;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.ui.activity.computer.ComputerContract;
import huedev.org.ui.activity.computer.ComputerPresenter;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.ui.fragments.room.RoomContact;
import huedev.org.ui.fragments.room.RoomPresenter;
import huedev.org.utils.helpers.ArrayHelper;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.rx.SchedulerProvider;

public class CCreateFragment extends BaseFagment implements
        RoomContact.View,
        ComputerContract.View, View.OnClickListener {

    EditText etComputerTitle, etComputerDesc;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    Spinner spinner;
    LinearLayout linearTypeSecond;
    RoomPresenter mRoomPresenter;
    ComputerPresenter mComputerPresenter;

    View view;
    String ROOM_ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        etComputerTitle = view.findViewById(R.id.et_titleCreate);
        etComputerDesc = view.findViewById(R.id.et_descCreate);
        rgStatus = view.findViewById(R.id.rg_status);
        rbActive = view.findViewById(R.id.rb_active);
        rbRepair = view.findViewById(R.id.rb_repair);
        rbBroken = view.findViewById(R.id.rb_broken);
        btnAdd = view.findViewById(R.id.btn_add);
        spinner = view.findViewById(R.id.spiner_type_first);
        linearTypeSecond = view.findViewById(R.id.linear_type_second);

        linearTypeSecond.removeAllViews();
        etComputerTitle.setHint(StringHelper.getStringResourceByName("computer_hint", getContext()));


        init();

        btnAdd.setOnClickListener(this);
        return view;
    }

    private void init() {
        RoomRepository roomRepository = RoomRepository.getInstance(
                RoomLocalDataSource.getInstance(),
                RoomRemoteDataSource.getInstance(getContext()));

        ComputerRepository computerRepository = ComputerRepository.getInstance(
                ComputerLocalDataSource.getInstance(),
                ComputerRemoteDataSource.getInstance(getContext())
        );

        mRoomPresenter = new RoomPresenter(
                getContext(),
                roomRepository,
                SchedulerProvider.getInstance());

        mComputerPresenter = new ComputerPresenter(
                getContext(),
                computerRepository,
                SchedulerProvider.getInstance());

        mRoomPresenter.setView(this);
        mComputerPresenter.setView(this);
        mRoomPresenter.rooms();
    }

    @Override
    public void updateRoomsList(List<Room> roomList) {

        ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                ArrayHelper.getNameArrayR((ArrayList<Room>) roomList));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ROOM_ID = roomList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    @Override
    public void showLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void showLoginError(Throwable throwable) {
        NotifyHelper.showSnackbar(
                view.findViewById(R.id.btn_add),
                throwable.toString(),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void updateComputerList(List<Computer> computerList) {

    }

    @Override
    public void updateComputerListAll(List<Computer> computerList) {

    }

    @Override
    public void createSucess(Computer computer) {
        NotifyHelper.showSnackbar(
                view.findViewById(R.id.btn_add),
                "Create Computer " + computer.getName(),
                Snackbar.LENGTH_SHORT);
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
        NotifyHelper.showSnackbar(
                view.findViewById(R.id.btn_add),
                StringHelper.getStringResourceByName("logic_faild", getContext()),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {

        String name = etComputerTitle.getText().toString().trim();
        String desc = etComputerDesc.getText().toString().trim();
        int status;

        if (rbActive.isChecked()){
            status = 0;
        }else if (rbRepair.isChecked()){
            status = 1;
        }else {
            status = 2;
        }

        mComputerPresenter.createComputer(name, desc, status, Integer.valueOf(ROOM_ID));
    }
}
