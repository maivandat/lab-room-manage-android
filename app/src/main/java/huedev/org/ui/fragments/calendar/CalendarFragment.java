package huedev.org.ui.fragments.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Work;
import huedev.org.data.source.local.io.SerializableFileFactory;
import huedev.org.ui.adapter.WorkAdapter;
import huedev.org.utils.AppConstants;

public class CalendarFragment extends Fragment implements View.OnClickListener, CalendarContact.View {
    LinearLayout linearForm;
    ImageButton ibAddWork;
    Button btnConfirm, btnCancel;
    RadioButton rbAM, rbPM;
    EditText etNameWork, etTime;
    RecyclerView rvWork;
    WorkAdapter workAdapter;
    TextView tvWorkEmpty;
    CalendarContact.Presenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        linearForm = view.findViewById(R.id.linear_formaddwork);
        ibAddWork = view.findViewById(R.id.ib_addwork);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnCancel = view.findViewById(R.id.btn_cancel);
        rbAM = view.findViewById(R.id.rb_timeAM);
        rbPM = view.findViewById(R.id.rb_timePM);
        etNameWork = view.findViewById(R.id.et_namework);
        etTime = view.findViewById(R.id.et_time);
        rvWork = view.findViewById(R.id.rv_work);
        tvWorkEmpty = view.findViewById(R.id.tv_workEmpty);
        if (!SerializableFileFactory.ReadFile(AppConstants.PATH, getContext()).isEmpty()){
            ArrayList<Work> listWork = SerializableFileFactory.ReadFile(AppConstants.PATH, getContext());
            updateWork(listWork);
            tvWorkEmpty.setVisibility(View.INVISIBLE);
        }

        init();

        ibAddWork.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    private void init() {
        mPresenter = new CalendarPresenter(getContext());
        mPresenter.setView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_addwork:
                Animation animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_in);
                linearForm.setVisibility(View.VISIBLE);
                linearForm.setAnimation(animationIn);
                ibAddWork.setVisibility(View.INVISIBLE);
                rbAM.setChecked(true);
                break;
            case R.id.btn_cancel:
                Animation animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_out);
                linearForm.setAnimation(animationOut);
                linearForm.setVisibility(View.INVISIBLE);
                ibAddWork.setVisibility(View.VISIBLE);
                ibAddWork.setClickable(false);
                break;
            case R.id.btn_confirm:
                String name = etNameWork.getText().toString().trim();
                String time = etTime.getText().toString().trim();
                if (name.isEmpty() || time.isEmpty() || (name.isEmpty() && time.isEmpty())){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (Integer.parseInt(time) > 0 && Integer.parseInt(time) < 13){
                        if (rbAM.isChecked()){
                            time = time + " AM";
                        }else {
                            time = time + " PM";
                        }
                        mPresenter.works(name, time);
                        etTime.setText("");
                        etNameWork.setText("");
                    }else {
                        Toast.makeText(getContext(), "Không hợp lệ ! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    @Override
    public void updateWork(List<Work> workList) {
        workAdapter = new WorkAdapter(getContext(), (ArrayList<Work>) workList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvWork.setLayoutManager(manager);
        rvWork.setAdapter(workAdapter);
        workAdapter.notifyDataSetChanged();
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
}
