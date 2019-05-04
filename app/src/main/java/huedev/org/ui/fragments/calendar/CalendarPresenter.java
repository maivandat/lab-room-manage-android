package huedev.org.ui.fragments.calendar;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import huedev.org.data.model.Work;
import huedev.org.data.source.local.io.SerializableFileFactory;
import huedev.org.utils.AppConstants;
import huedev.org.utils.helpers.StringHelper;

public class CalendarPresenter implements CalendarContact.Presenter{
    private Context mContext;
    private CalendarContact.View mView;
    private ArrayList<Work> listWork;

    public CalendarPresenter(Context context) {
        this.mContext = context;
        this.listWork = new ArrayList<>();
        if (SerializableFileFactory.ReadFile(AppConstants.PATH, mContext).isEmpty()){
            this.listWork = new ArrayList<>();
        }else {
            this.listWork = SerializableFileFactory.ReadFile(AppConstants.PATH, mContext);
        }
    }

    @Override
    public void works(String name, String time,String date,
                      EditText etTime, EditText etNameWork,
                      RadioButton rbAM, TextView tvWorkEmpty) {
        if (name.isEmpty() || time.isEmpty() || (name.isEmpty() && time.isEmpty())){
            mView.requireData();
        }else {
            if (Integer.parseInt(time) > 0 && Integer.parseInt(time) < 13 || time.length() <= 2){
                if (rbAM.isChecked()){
                    time = time + " AM";
                }else {
                    time = time + " PM";
                }
                listWork.add(new Work(name, time, date));
                SerializableFileFactory.SaveFile(listWork,AppConstants.PATH, mContext);
                dates(date,tvWorkEmpty);
                etTime.setText("");
                etNameWork.setText("");
            }else {
                mView.warningData();
            }

        }

    }

    @Override
    public void dates(String date, TextView tvWorkEmpty) {
        List<Work> dsWorkOfDate = new ArrayList<>();
        if (!listWork.isEmpty()){
            for (Work work : listWork){
                if (work.getDate().equals(date)){
                    dsWorkOfDate.add(work);
                }
            }
            if (dsWorkOfDate.isEmpty()){
                tvWorkEmpty.setText(StringHelper.getStringResourceByName("work_empty", mContext));
            }
        }
        mView.getWorkOfDate(dsWorkOfDate);
    }


    @Override
    public void setView(CalendarContact.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
