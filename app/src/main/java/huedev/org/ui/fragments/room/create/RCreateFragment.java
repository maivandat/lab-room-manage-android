package huedev.org.ui.fragments.room.create;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.List;
import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.ui.fragments.room.RoomContact;
import huedev.org.ui.fragments.room.RoomPresenter;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.rx.SchedulerProvider;

public class RCreateFragment extends BaseFagment implements RoomContact.View, View.OnClickListener {

    EditText etRoomTitle, etRoomDesc;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    LinearLayout linearType;
    RoomPresenter mRoomPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        etRoomTitle = view.findViewById(R.id.et_titleCreate);
        etRoomDesc = view.findViewById(R.id.et_descCreate);
        rgStatus = view.findViewById(R.id.rg_status);
        rbActive = view.findViewById(R.id.rb_active);
        rbRepair = view.findViewById(R.id.rb_repair);
        rbBroken = view.findViewById(R.id.rb_broken);
        btnAdd = view.findViewById(R.id.btn_add);
        linearType = view.findViewById(R.id.linear_type);

        linearType.removeAllViews();
        etRoomTitle.setHint(StringHelper.getStringResourceByName("room_title", getContext()));
        init();

        btnAdd.setOnClickListener(this);
        return view;
    }

    private void init() {
        RoomRepository roomRepository = RoomRepository.getInstance(RoomLocalDataSource.getInstance(), RoomRemoteDataSource.getInstance(getContext()));
        mRoomPresenter = new RoomPresenter(getContext(), roomRepository, SchedulerProvider.getInstance());
        mRoomPresenter.setView(this);
    }


    @Override
    public void updateRoomsList(List<Room> roomList) {

    }

    @Override
    public void updateRoomItem(Room room, Dialog dialog) {

    }

    @Override
    public void createRoomItem(Room room) {
        NotifyHelper.logicSuccess("Create " + room.getName() + " succsess", getContext());
    }

    @Override
    public void delRoomSuccess(DialogInterface dialogInterface) {

    }

    @Override
    public void delRoomFaild(Throwable err) {
        NotifyHelper.logicFaild(err.toString(), getContext());
    }

    @Override
    public void addRoomFaild(Throwable err) {
        NotifyHelper.logicFaild(err.toString(), getContext());
    }

    @Override
    public void updateRoomFaild(Throwable err) {
        NotifyHelper.logicFaild(err.toString(), getContext());
    }

    @Override
    public void logicSuccess() {

    }

    @Override
    public void logicFaild() {
        NotifyHelper.logicFaild(StringHelper.getStringResourceByName("logic_faild", getContext()), getContext());
    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void showLoginError(Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        String title = etRoomTitle.getText().toString().trim();
        String desc = etRoomDesc.getText().toString().trim();
        String status;

        if (rbActive.isChecked()){
            status = "Open";
        }else if (rbRepair.isChecked()){
            status = "Repair";
        }else {
            status = "Close";
        }

        mRoomPresenter.createRoom(title, desc, status);

    }
}
