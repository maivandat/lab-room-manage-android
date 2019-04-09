package huedev.org.ui.fragments.calendar;

import android.content.Context;

import java.util.ArrayList;

import huedev.org.data.model.Work;
import huedev.org.data.source.local.io.SerializableFileFactory;
import huedev.org.utils.AppConstants;

public class CalendarPresenter implements CalendarContact.Presenter{
    private Context mContext;
    private CalendarContact.View mView;
    private ArrayList<Work> listWork;
    SerializableFileFactory fileFactory;

    public CalendarPresenter(Context context) {
        this.mContext = context;
        this.listWork = new ArrayList<>();
        if (fileFactory.ReadFile(AppConstants.PATH, mContext).isEmpty()){
            this.listWork = new ArrayList<>();
        }else {
            this.listWork = fileFactory.ReadFile(AppConstants.PATH, mContext);
        }
    }

    @Override
    public void works(String name, String time) {
        listWork.add(new Work(name, time));
        mView.updateWork(listWork);
        fileFactory.SaveFile(listWork,AppConstants.PATH, mContext);
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
