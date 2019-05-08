package huedev.org.ui.activity.auth.login;

import android.content.Context;
import android.util.Log;

import com.google.common.base.Preconditions;

import huedev.org.data.repository.LoginRepository;
import huedev.org.data.source.remote.response.auth.LoginResponse;
import huedev.org.utils.AppPrefs;
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
    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()){
            mView.logicFaild();
        }else {
            mLoginRepository.login(username, password)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(loginResponse -> handleLoginSuccess(loginResponse, username, password),
                            error -> handleLoginFailed(error));
        }
    }

    private void handleLoginSuccess(LoginResponse loginResponse, String username, String password){
        AppPrefs.getInstance(mContext).putApiToken(loginResponse.data.getAccess_token());
        AppPrefs.getInstance(mContext).putIdUser(loginResponse.data.getId());
        AppPrefs.getInstance(mContext).putUserNameUser(username);
        AppPrefs.getInstance(mContext).putPasswordUser(password);
        AppPrefs.getInstance(mContext).putNameUser(loginResponse.data.getName());
        AppPrefs.getInstance(mContext).putEmailUser(loginResponse.data.getEmail());
        AppPrefs.getInstance(mContext).putRole(Integer.parseInt(loginResponse.data.getRole()));
        mView.getUser(loginResponse.data);
    }

    private void handleLoginFailed(Throwable error){
        Log.e("errr", error.toString());
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
