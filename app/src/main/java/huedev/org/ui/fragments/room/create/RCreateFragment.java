package huedev.org.ui.fragments.room.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class RCreateFragment extends BaseFagment implements RCreateContact.View, View.OnClickListener {

    EditText etRoomTitle, etRoomDesc;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    RCreatePresenter mRCreatePresenter;

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

        etRoomTitle.setHint("Room Title");
        init();

        btnAdd.setOnClickListener(this);
        return view;
    }

    private void init() {
        RoomRepository roomRepository = RoomRepository.getInstance(RoomLocalDataSource.getInstance(), RoomRemoteDataSource.getInstance(getContext()));
        mRCreatePresenter = new RCreatePresenter(getContext(), SchedulerProvider.getInstance(), roomRepository);
        mRCreatePresenter.setView(this);
    }

    @Override
    public void logicCorrect(Room room) {
        Toast.makeText(getContext(), "Tạo " + room.getName() + " thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logicFaild() {
        Toast.makeText(getContext(), "Bạn vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showLoginError(Throwable throwable) {

    }

    @Override
    public void onClick(View view) {
        String title = etRoomTitle.getText().toString().trim();
        String desc = etRoomDesc.getText().toString().trim();
        int status;

        if (rbActive.isChecked()){
            status = 0;
        }else if (rbRepair.isChecked()){
            status = 1;
        }else {
            status = 2;
        }

        String sStatus = StringHelper.formatStringStatus(status, getContext());
        mRCreatePresenter.createRoom(title, desc, sStatus);

    }
}
