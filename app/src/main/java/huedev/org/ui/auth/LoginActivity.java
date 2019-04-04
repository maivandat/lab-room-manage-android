package huedev.org.ui.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.LoginRepository;
import huedev.org.data.source.local.LoginLocalDataSource;
import huedev.org.data.source.remote.LoginRemoteDataSource;
import huedev.org.ui.MainActivity;
import huedev.org.ui.base.BaseActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.rx.SchedulerProvider;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;

    private EditText etUsername, etPassword;
    private Button btnConfirm;

    String sUsername, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!AppPrefs.getInstance(this).getApiToken().equals(AppConstants.API_TOKEN_DEFAULT)){
            startActivity();
        }

        etUsername = findViewById(R.id.et_usernameLogin);
        etPassword = findViewById(R.id.et_passwordLogin);
        btnConfirm = findViewById(R.id.btn_gologin);

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
        Toast.makeText(this, "Loading ...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getUser(User user) {
        Toast.makeText(this, "Xin chào " + user.getName(), Toast.LENGTH_SHORT).show();
        startActivity();
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
        sUsername = etUsername.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
        mPresenter.login(sUsername, sPassword);
    }

    @Override
    public void onClick(View view) {
        clickHandle(view);
    }

    public void startActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
