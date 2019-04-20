package huedev.org.ui.activity.auth.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.LoginRepository;
import huedev.org.data.source.local.LoginLocalDataSource;
import huedev.org.data.source.remote.LoginRemoteDataSource;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;

    private EditText etUsername, etPassword;
    private Button btnConfirm;
    private CheckBox cbRemember;
    Navigator navigator;

    String sUsername, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        navigator = new Navigator(this);

        etUsername = findViewById(R.id.et_usernameLogin);
        etPassword = findViewById(R.id.et_passwordLogin);
        btnConfirm = findViewById(R.id.btn_gologin);
        cbRemember = findViewById(R.id.cb_remeberLogin);

        init();

        btnConfirm.setOnClickListener(this);
    }

    private void init(){
        LoginRepository loginRepository = LoginRepository.getInstance(LoginLocalDataSource.getInstance(),
                LoginRemoteDataSource.getInstance(this));
        mPresenter = new LoginPresenter(this, loginRepository, SchedulerProvider.getInstance());
        mPresenter.setView(this);
    }



    @Override
    public void showLoadingIndicator() {
    }

    @Override
    public void getUser(User user) {
        navigator.startActivity(MainActivity.class);
    }

    @Override
    public void hideLoadingIndicator() {
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Error ...", Toast.LENGTH_LONG).show();
    }

    public void clickHandle(View view){
        sUsername = etUsername.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
        mPresenter.login(sUsername, sPassword);
    }

    @Override
    public void onClick(View view) {
        clickHandle(view);
    }

}
