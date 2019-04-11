package huedev.org.ui.fragments.calendar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Work;
import huedev.org.ui.MainActivity;
import huedev.org.ui.adapter.WorkAdapter;
import huedev.org.ui.auth.LoginActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.helpers.StringHelper;

public class CalendarFragment extends Fragment implements View.OnClickListener, CalendarContact.View {
    LinearLayout linearForm, linearWork;
    ImageButton ibAddWork;
    Button btnConfirm, btnCancel;
    RadioButton rbAM, rbPM;
    EditText etNameWork, etTime;
    RecyclerView rvWork;
    WorkAdapter workAdapter;
    TextView tvWorkEmpty, tvDate, tvWhoisthis;
    CalendarView calendarView;

    String date;

    CalendarContact.Presenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        linearForm = view.findViewById(R.id.linear_formaddwork);
        linearWork = view.findViewById(R.id.linear_work);
        ibAddWork = view.findViewById(R.id.ib_addwork);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnCancel = view.findViewById(R.id.btn_cancel);
        rbAM = view.findViewById(R.id.rb_timeAM);
        rbPM = view.findViewById(R.id.rb_timePM);
        etNameWork = view.findViewById(R.id.et_namework);
        etTime = view.findViewById(R.id.et_time);
        rvWork = view.findViewById(R.id.rv_work);
        tvWorkEmpty = view.findViewById(R.id.tv_workEmpty);
        tvDate = view.findViewById(R.id.tv_date);
        tvWhoisthis = view.findViewById(R.id.tv_whoisthis);
        calendarView = view.findViewById(R.id.cv_calendar);

        date = StringHelper.dateToString();
        init();
        mPresenter.dates(date, tvWorkEmpty);

        if (!AppPrefs.getInstance(getContext()).getApiToken().equals(AppConstants.API_TOKEN_DEFAULT)){
            linearWork.setVisibility(View.VISIBLE);
            tvWhoisthis.setVisibility(View.INVISIBLE);
        }else {
            linearWork.setVisibility(View.INVISIBLE);
            tvWhoisthis.setVisibility(View.VISIBLE);
        }

        calendarView.setOnDateChangeListener(onDateChangeListener);
        tvWhoisthis.setOnClickListener(this);
        ibAddWork.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    private void init() {

        mPresenter = new CalendarPresenter(getContext());
        mPresenter.setView(this);
    }

    private CalendarView.OnDateChangeListener onDateChangeListener = (calendarView, i, i1, i2) -> {
        date = StringHelper.dateToString(i2, i1 ,i);
        if (StringHelper.dateToString().equals(date)){
            tvDate.setText(StringHelper.getStringResourceByName("today", getContext()));
        }else {
            tvDate.setText(date);
        }
        mPresenter.dates(date, tvWorkEmpty);
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
                break;
            case R.id.btn_confirm:
                String name = etNameWork.getText().toString().trim();
                String time = etTime.getText().toString().trim();
                mPresenter.works(name, time, date, etTime, etNameWork, rbAM, tvWorkEmpty);
                break;
            case R.id.tv_whoisthis:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation
                        (getContext(),
                                R.anim.slide_left_in,
                                R.anim.slide_left_out);
                startActivity(intent, options.toBundle());
                break;
        }

    }

    @Override
    public void warningData() {
        Toast.makeText(getContext(),
                StringHelper.getStringResourceByName("warning_data", getContext()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requireData() {
        Toast.makeText(getContext(),
                StringHelper.getStringResourceByName("require_data", getContext()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getWorkOfDate(List<Work> workList) {
        workAdapter = new WorkAdapter(getContext(), (ArrayList<Work>) workList, tvWorkEmpty);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        rvWork.setLayoutManager(manager);
        rvWork.setAdapter(workAdapter);
        workAdapter.notifyDataSetChanged();
        if (!workList.isEmpty()){
            tvWorkEmpty.setText(StringHelper.getStringResourceByName(
                    "sumwork",
                    getContext()) + " " + workList.size());
        }
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
