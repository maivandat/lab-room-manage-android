package huedev.org.ui.computer;

import android.content.Context;

import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.source.remote.response.computer.ListComputerResponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class ComputerPresenter implements ComputerContract.Presenter {
    private Context mContext;
    private ComputerContract.View mView;
    private ComputerRepository mComputerRepository;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    public ComputerPresenter(Context context,
                             ComputerRepository computerRepository,
                             BaseSchedulerProvider baseSchedulerProvider) {
        this.mContext = context;
        this.mComputerRepository = computerRepository;
        this.mBaseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void computersByRoom(int id) {
        mView.showLoadingIndicator();
        mComputerRepository.computersByRoom(id)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(computerResponse -> handleComputerSuccess(computerResponse),
                        error -> handleComputerFailed(error));
    }

    private void handleComputerSuccess(ListComputerResponse computerResponse){
        mView.hideLoadingIndicator();
        mView.updateComputerList(computerResponse.computersByRoom);
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
