package huedev.org.ui.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.repository.LoginRepository;
import huedev.org.data.source.local.LoginLocalDataSource;
import huedev.org.data.source.remote.LoginRemoteDataSource;
import huedev.org.ui.base.BaseActivity;
import huedev.org.utils.rx.SchedulerProvider;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void init(){
        LoginRepository loginRepository = LoginRepository.getInstance(LoginLocalDataSource.getInstance(),
                LoginRemoteDataSource.getInstance(this));
        mPresenter = new LoginPresenter(this, loginRepository, SchedulerProvider.getInstance());
        mPresenter.setView(this);
    }

    @Override
    public void showLoadingIndicator() {
        Toast.makeText(this, "Loading ...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideLoadingIndicator() {
        Toast.makeText(this, "Hiding ...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Error ...", Toast.LENGTH_LONG).show();
    }

    public void clickHandle(View view){
        mPresenter.login("","");
    }
}
