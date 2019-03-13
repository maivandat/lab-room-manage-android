package huedev.org.ui.auth;

import android.content.Context;

import com.google.common.base.Preconditions;

import huedev.org.data.repository.LoginRepository;
import huedev.org.data.source.remote.response.auth.LoginResponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class LoginPresenter implements LoginContract.Presenter {

    private Context mContext;
    private LoginContract.View mView;
    private LoginRepository mLoginRepository;
    private BaseSchedulerProvider mSchedulerProvider;

    public LoginPresenter(Context context, LoginRepository loginRepository, BaseSchedulerProvider schedulerProvider){
        mContext = Preconditions.checkNotNull(context);
        mLoginRepository = Preconditions.checkNotNull(loginRepository);
        mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider);
    }

    @Override
    public void login(String email, String password) {
        mView.showLoadingIndicator();
        mLoginRepository.login(email, password)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(loginResponse -> handleLoginSuccess(loginResponse),
                        error -> handleLoginFailed(error));
    }

    private void handleLoginSuccess(LoginResponse loginResponse){

    }

    private void handleLoginFailed(Throwable error){

    }
    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
