package huedev.org.ui.activity.computer;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import huedev.org.data.model.Computer;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class ComputerPresenter implements ComputerContract.Presenter {
    private Context mContext;
    private ComputerContract.View mView;
    private ComputerRepository mComputerRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    private Navigator navigator;

    public ComputerPresenter(Context context,
                             ComputerRepository computerRepository,
                             BaseSchedulerProvider baseSchedulerProvider) {
        this.mContext = context;
        this.mComputerRepository = computerRepository;
        this.mBaseSchedulerProvider = baseSchedulerProvider;
        this.navigator = new Navigator((Activity) mContext);
    }

    @Override
    public void computersByRoom() {
        mView.showLoadingIndicator();
        mComputerRepository.computersByRoom()
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(computerResponse -> handleComputerSuccess(computerResponse),
                        error -> handleComputerFailed(error));
    }

    private void handleComputerSuccess(ListComputerResponse computerResponse){
        mView.hideLoadingIndicator();
        List<Computer> computerList = new ArrayList<>();
        String id = navigator.getData().getString(AppConstants.ID_ROOM);
        for (Computer computer: computerResponse.computersByRoom) {
            if (computer.getRoomId().equals(id)){
                computerList.add(computer);
            }
        }
        mView.updateComputerList(computerList);
    }


    private void handleComputerFailed(Throwable error){
        mView.showLoginError(error);
    }

    @Override
    public void setView(ComputerContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
