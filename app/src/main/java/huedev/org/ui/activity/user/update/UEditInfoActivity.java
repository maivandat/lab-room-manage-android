package huedev.org.ui.activity.user.update;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.activity.user.UserContact;
import huedev.org.ui.activity.user.UserPresenter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;

public class UEditInfoActivity extends BaseActivity implements View.OnClickListener, UserContact.View {
    LinearLayout linearChangePassword;
    Toolbar toolbar;
    Navigator navigator;
    EditText etFullName, etEmail, etOldPassword, etNewPassword, etCfNewPassword;
    Button btnEdit;
    TextView tvFullname, tvChangePassword;
    UserPresenter mUserPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_user);

        linearChangePassword = findViewById(R.id.linear_changePassword);
        navigator = new Navigator(this);
        toolbar = findViewById(R.id.toolbar_editUser);
        etFullName = findViewById(R.id.et_nameEditIFUser);
        etEmail = findViewById(R.id.et_emailIFEditUser);
        etOldPassword = findViewById(R.id.et_oldPassword);
        etNewPassword = findViewById(R.id.et_newPassword);
        etCfNewPassword = findViewById(R.id.et_cfNewPassword);
        tvFullname = findViewById(R.id.tv_fullname);
        tvChangePassword = findViewById(R.id.tv_changePassword);
        btnEdit = findViewById(R.id.btn_editIFUser);

        setupToolbar(toolbar, R.drawable.btn_back);
        init();

        tvChangePassword.setOnClickListener(this);
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
        mUserPresenter = new UserPresenter(this, userRepository, SchedulerProvider.getInstance());
        mUserPresenter.setView(this);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_editIFUser:
                inputUser();
                break;
            case R.id.tv_changePassword:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
                linearChangePassword.setVisibility(View.VISIBLE);
                linearChangePassword.setAnimation(animation);
                tvChangePassword.setVisibility(View.INVISIBLE);
                break;
            default:
                navigator.startActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public void usersList(List<User> userList) {

    }

    @Override
    public void user(User user) {
        AppPrefs.getInstance(this).putNameUser(user.getName());
        AppPrefs.getInstance(this).putEmailUser(user.getEmail());
        AppPrefs.getInstance(this).putPasswordUser(user.getPassword());
        finish();
    }

    @Override
    public void user(User user, Dialog dialog) {

    }

    @Override
    public void delUserSuccess(DialogInterface dialogInterface) {

    }

    @Override
    public void logicFaild() {
        Toast.makeText(this, StringHelper.getStringResourceByName("logic_faild", this), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EditFaild(Throwable err) {

    }

    @Override
    public void DelFaild(Throwable err) {

    }

    @Override
    public void logicSuccess() {

    }

    @Override
    public void oldPasswordFail() {
        Toast.makeText(this, "The old password is incorrect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newPasswordFail() {
        Toast.makeText(this, "New password does not match", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Lỗi! " + throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    public void inputUser(){
        String name = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String cfNewPassword = etCfNewPassword.getText().toString().trim();

        mUserPresenter.updateUser(name, email, oldPassword, newPassword, cfNewPassword);
    }
}
