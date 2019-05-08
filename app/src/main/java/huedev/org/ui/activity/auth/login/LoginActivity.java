package huedev.org.ui.activity.auth.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.LoginRepository;
import huedev.org.data.source.local.LoginLocalDataSource;
import huedev.org.data.source.remote.LoginRemoteDataSource;
import huedev.org.ui.activity.auth.register.RegisterActivity;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;

    private EditText etUsername, etPassword;
    private Button btnConfirm;
    private TextView tvRegister;
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
        tvRegister = findViewById(R.id.tv_registerAccount);

        init();

        btnConfirm.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    private void init(){
        LoginRepository loginRepository = LoginRepository.getInstance(LoginLocalDataSource.getInstance(),
                LoginRemoteDataSource.getInstance(this));
        mPresenter = new LoginPresenter(this, loginRepository, SchedulerProvider.getInstance());
        mPresenter.setView(this);
    }



    @Override
    public void showLoadingIndicator(Dialog dialog) {
    }

    @Override
    public void getUser(User user) {
        navigator.startActivity(MainActivity.class);
    }

    @Override
    public void logicFaild() {
        NotifyHelper.showSnackbar(findViewById(R.id.btn_gologin),
                StringHelper.getStringResourceByName("logic_faild", this),
                Snackbar.LENGTH_SHORT);
    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {
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
        switch (view.getId()){
            case R.id.btn_gologin:
                clickHandle(view);
                break;
            case R.id.tv_registerAccount:
                navigator.startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
