package huedev.org.ui.fragments.device.create;

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
import android.widget.Spinner;
import android.widget.TextView;

import huedev.org.R;
import huedev.org.ui.base.fragment.BaseFagment;
import huedev.org.utils.helpers.StringHelper;

public class DCreateFragment extends BaseFagment {

    EditText etDeviceTitle, etDeviceDesc;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    Spinner spinnerFirst, spinnerSecond;
    TextView tvTitleFirst, tvTitleSecond;

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

        tvTitleFirst.setText(StringHelper.getStringResourceByName("type_device", getContext()));
        tvTitleSecond.setText(StringHelper.getStringResourceByName("computer_title", getContext()));
        etDeviceTitle.setHint(StringHelper.getStringResourceByName("device_title", getContext()));
        return view;
    }
}
