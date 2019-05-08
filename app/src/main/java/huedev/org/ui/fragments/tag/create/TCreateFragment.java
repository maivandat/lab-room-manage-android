package huedev.org.ui.fragments.tag.create;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Device;
import huedev.org.data.model.Tag;
import huedev.org.data.repository.DeviceRepository;
import huedev.org.data.repository.TagRepository;
import huedev.org.data.source.local.DeviceLocalDataSource;
import huedev.org.data.source.local.TagLocalDataSource;
import huedev.org.data.source.remote.DeviceRemoteDataSource;
import huedev.org.data.source.remote.TagRemoteDataSource;
import huedev.org.ui.activity.device.DeviceContact;
import huedev.org.ui.activity.device.DevicePresenter;
import huedev.org.ui.activity.tag.TagContact;
import huedev.org.ui.activity.tag.TagPresenter;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.utils.helpers.ArrayHelper;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.WidgetHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.rx.SchedulerProvider;

public class TCreateFragment extends BaseFagment implements TagContact.View, DeviceContact.View {

    EditText etTagTitle;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    Spinner spinnerFirst;
    TextView tvTitleFirst;
    LinearLayout linearSecond, linearDesc;

    TagPresenter mTagPresenter;
    DevicePresenter mDevicePresenter;

    ArrayList<Device> mDeviceList ;
    int ID_DEVICE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        etTagTitle = view.findViewById(R.id.et_titleCreate);
        rgStatus = view.findViewById(R.id.rg_status);
        rbActive = view.findViewById(R.id.rb_active);
        rbRepair = view.findViewById(R.id.rb_repair);
        rbBroken = view.findViewById(R.id.rb_broken);
        btnAdd = view.findViewById(R.id.btn_add);
        spinnerFirst = view.findViewById(R.id.spiner_type_first);
        tvTitleFirst = view.findViewById(R.id.tv_tytpe_title_first);
        linearSecond = view.findViewById(R.id.linear_type_second);
        linearDesc = view.findViewById(R.id.linear_desc);
        mDeviceList = new ArrayList<>();

        linearDesc.removeAllViews();
        linearSecond.removeAllViews();
        tvTitleFirst.setText(StringHelper.getStringResourceByName("device", getContext()));
        etTagTitle.setHint(StringHelper.getStringResourceByName("tag_title", getContext()));

        init();

        spinnerFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ID_DEVICE = Integer.parseInt(mDeviceList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAdd.setOnClickListener(view1 -> {
            String value = etTagTitle.getText().toString().trim();
            mTagPresenter.createTag(value, ID_DEVICE);
        });
        return view;
    }

    private void init() {
        TagRepository tagRepository = TagRepository.getInstance(
                TagLocalDataSource.getInstance(),
                TagRemoteDataSource.getInstance(getContext()));

        DeviceRepository deviceRepository = DeviceRepository.getInstance(
                DeviceLocalDataSource.getInstance(),
                DeviceRemoteDataSource.getInstance(getContext()));

        mTagPresenter = new TagPresenter(
                getContext(),
                tagRepository,
                SchedulerProvider.getInstance());

        mDevicePresenter = new DevicePresenter(
                getContext(),
                deviceRepository,
                SchedulerProvider.getInstance());

        mDevicePresenter.setView(this);
        mTagPresenter.setView(this);
        mDevicePresenter.tempDevices();
    }

    @Override
    public void createSuccess(Tag tag) {
        NotifyHelper.showSnackbar(btnAdd,
                "Create " + tag.getValue() + " success",
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void updateTempDeviceList(List<Device> deviceList) {
        mDeviceList = (ArrayList<Device>) deviceList;
        WidgetHelper.setupSpinner(
                spinnerFirst,
                ArrayHelper.getNameArrayD((ArrayList<Device>) deviceList),
                getContext());
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
    public void logicCreateFaild() {
        NotifyHelper.showSnackbar(btnAdd,
                StringHelper.getStringResourceByName("logic_faild", getContext()),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void showLoginError(Throwable throwable) {
        NotifyHelper.showSnackbar(btnAdd,
                throwable.toString(),
                Snackbar.LENGTH_SHORT);
    }
}
