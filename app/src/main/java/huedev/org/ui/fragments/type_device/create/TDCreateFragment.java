package huedev.org.ui.fragments.type_device.create;

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

public class TDCreateFragment extends BaseFagment {

    EditText etTDTitle, etTDDesc;
    RadioGroup rgStatus;
    RadioButton rbActive, rbRepair, rbBroken;
    Button btnAdd;
    LinearLayout linearType;
    LinearLayout linearDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        etTDTitle = view.findViewById(R.id.et_titleCreate);
        etTDDesc = view.findViewById(R.id.et_descCreate);
        rgStatus = view.findViewById(R.id.rg_status);
        rbActive = view.findViewById(R.id.rb_active);
        rbRepair = view.findViewById(R.id.rb_repair);
        rbBroken = view.findViewById(R.id.rb_broken);
        btnAdd = view.findViewById(R.id.btn_add);
        linearType = view.findViewById(R.id.linear_type);
        linearDesc = view.findViewById(R.id.linear_desc);

        linearDesc.removeAllViews();
        linearType.removeAllViews();
        etTDTitle.setHint(StringHelper.getStringResourceByName("type_device_title", getContext()));
        return view;
    }
}
