package huedev.org.ui.activity.auth.register;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.activity.auth.login.LoginActivity;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterContact.View {
    EditText etFullName, etUsername, etPassword, etCfPassword, etEmail;
    Button btnRegister;
    LinearLayout linearAddRole;
    RegisterPresenter mRegisterPresenter;
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etFullName = findViewById(R.id.et_nameAddUser);
        etUsername = findViewById(R.id.et_usernameAddUser);
        etPassword = findViewById(R.id.et_password_add_user);
        etCfPassword = findViewById(R.id.et_cfpassword_add_user);
        etEmail = findViewById(R.id.et_emailAddUser);
        btnRegister = findViewById(R.id.btn_addUser);
        linearAddRole = findViewById(R.id.linear_add_status);
        linearAddRole.removeAllViews();
        navigator = new Navigator(this);
        init();
        btnRegister.setOnClickListener(this);

    }

    private void init() {
        UserRepository userRepository = UserRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance(this));
        mRegisterPresenter = new RegisterPresenter(
                this,
                userRepository,
                SchedulerProvider.getInstance());
        mRegisterPresenter.setView(this);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void onClick(View view) {
        String name = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String cfpassword = etCfPassword.getText().toString().trim();
        mRegisterPresenter.register("application/json", name, username,
                password, email, cfpassword);
    }

    @Override
    public void logicSuccess(User user) {
        Snackbar.make(btnRegister,
                "Register user " + user.getName() + " success",
                Snackbar.LENGTH_SHORT).show();
        navigator.startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void logicSuccess(User user, Dialog dialog) {

    }

    @Override
    public void logicFaild() {
        Snackbar.make(btnRegister,
                StringHelper.getStringResourceByName("logic_faild", this),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void showLoginError(Throwable throwable) {

    }
}
