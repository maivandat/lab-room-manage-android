package huedev.org.ui.user.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.MainActivity;
import huedev.org.ui.base.BaseActivity;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class UEditActivity extends BaseActivity implements View.OnClickListener, UEditContact.View {
    private Toolbar toolbar;
    private Navigator navigator;
    private EditText etFullName;
    private EditText etEmail;
    private Button btnEdit;
    private TextView tvFullname;

    private String name;
    private String email;

    UEditPresenter mUEditPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_user);
        navigator = new Navigator(this);
        toolbar = findViewById(R.id.toolbar_editUser);
        etFullName = findViewById(R.id.et_nameEditUser);
        etEmail = findViewById(R.id.et_emailEditUser);
        btnEdit = findViewById(R.id.btn_edit);
        tvFullname = findViewById(R.id.tv_fullname);

        setupToolbar(toolbar, R.drawable.btn_back);
        init();
        toolbar.setNavigationOnClickListener(this);
        btnEdit.setOnClickListener(this);

        etFullName.setText(AppPrefs.getInstance(this).getNameUser());
        etEmail.setText(AppPrefs.getInstance(this).getEmailUser());
        tvFullname.setText(AppPrefs.getInstance(this).getNameUser());

    }

    private void init() {
        UserRepository userRepository = UserRepository.getInstance(
                UserLocalDataSource.getInstance()
                , UserRemoteDataSource.getInstance(this));
        mUEditPresenter = new UEditPresenter(this, userRepository, SchedulerProvider.getInstance());
        mUEditPresenter.setView(this);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_edit:
                inputUser();
                break;
            default:
                navigator.startActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public void user(User user) {
        AppPrefs.getInstance(this).putNameUser(user.getName());
        AppPrefs.getInstance(this).putEmailUser(user.getEmail());
        finish();
    }

    @Override
    public void logicFaild() {

    }

    @Override
    public void logicSuccess() {

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showLoginError(Throwable throwable) {

    }

    public void inputUser(){
        name = etFullName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        mUEditPresenter.updateUser(name, email);
    }
}
