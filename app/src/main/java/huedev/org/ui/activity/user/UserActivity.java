package huedev.org.ui.activity.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.activity.auth.register.RegisterContact;
import huedev.org.ui.activity.auth.register.RegisterPresenter;
import huedev.org.ui.adapter.UserAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.helpers.NotifyHelper;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.rx.SchedulerProvider;

public class UserActivity extends BaseActivity implements View.OnClickListener, UserContact.View, RegisterContact.View {
    FloatingActionButton fabAddUser;
    TextView tvTotalUser;
    RecyclerView rvUser;
    Toolbar mToolbar;
    UserPresenter mUserPresenter;
    RegisterPresenter mRegisterPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        fabAddUser = findViewById(R.id.fab_addUser);
        tvTotalUser = findViewById(R.id.tv_totalUser);
        rvUser = findViewById(R.id.rv_users);
        mToolbar = findViewById(R.id.toolbar_user);

        setupToolbar(mToolbar, R.drawable.btn_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();
        fabAddUser.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(this);
    }

    private void init() {
        UserRepository userRepository = UserRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance(this));
        mUserPresenter = new UserPresenter(
                this, userRepository, SchedulerProvider.getInstance());
        mRegisterPresenter = new RegisterPresenter(
                this,
                userRepository,
                SchedulerProvider.getInstance());
        mUserPresenter.setView(this);
        mRegisterPresenter.setView(this);
        mUserPresenter.users();


    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_addUser:
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.activity_add_user);

                LinearLayout linearLayout = dialog.findViewById(R.id.linear_title_add);
                linearLayout.removeAllViews();
                EditText etNameAddU = dialog.findViewById(R.id.et_nameAddUser);
                EditText etUsernameAddU = dialog.findViewById(R.id.et_usernameAddUser);
                EditText etPasswordAddU = dialog.findViewById(R.id.et_password_add_user);
                EditText etCfPasswordAddU = dialog.findViewById(R.id.et_cfpassword_add_user);
                EditText etEmaiAddU = dialog.findViewById(R.id.et_emailAddUser);
                RadioButton rbAdminAddU = dialog.findViewById(R.id.rb_admin_add_user);
                RadioButton rbTechniciansAddU = dialog.findViewById(R.id.rb_technicians_add_user);
                RadioButton rbMemberAddU = dialog.findViewById(R.id.rb_member_add_user);
                Button btnAddU = dialog.findViewById(R.id.btn_addUser);

                btnAddU.setText("Add");

                dialog.show();

                btnAddU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = etNameAddU.getText().toString().trim();
                        String username = etUsernameAddU.getText().toString().trim();
                        String password = etPasswordAddU.getText().toString().trim();
                        String cfPassword = etCfPasswordAddU.getText().toString().trim();
                        String email = etEmaiAddU.getText().toString().trim();
                        int role = 0;
                        if (rbAdminAddU.isChecked()){
                            role = 0;
                        }if (rbTechniciansAddU.isChecked()){
                            role = 1;
                        }if (rbMemberAddU.isChecked()){
                            role = 2;
                        }
                        mRegisterPresenter.register(name, username, password, cfPassword, email, role, dialog);
                    }
                });
                break;
            default:
                finish();
                break;
        }



    }

    @Override
    public void usersList(List<User> userList) {
        tvTotalUser.setText("Total: " + userList.size() + "");
        UserAdapter adapter = new UserAdapter(this, userList, fabAddUser, mUserPresenter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rvUser.setLayoutManager(manager);
        rvUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void user(User user) {

    }

    @Override
    public void user(User user, Dialog dialog) {
        dialog.dismiss();
        mUserPresenter.users();
    }

    @Override
    public void delUserSuccess(DialogInterface dialogInterface) {
        Toast.makeText(this, "abcd", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void logicSuccess(User user) {
    }

    @Override
    public void logicSuccess(User user, Dialog dialog) {
        Snackbar.make(fabAddUser,
                "Add user " + user.getName() + " success",
                Snackbar.LENGTH_SHORT).show();
        dialog.dismiss();
        mUserPresenter.users();
    }

    @Override
    public void logicFaild() {
        Snackbar.make(fabAddUser,
                StringHelper.getStringResourceByName("logic_faild", this),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void EditFaild(Throwable err) {
        NotifyHelper.logicFaild(err.toString(), this);
    }

    @Override
    public void DelFaild(Throwable err) {
        Log.e("erroDelUser", err.toString());
    }

    @Override
    public void logicSuccess() {

    }

    @Override
    public void oldPasswordFail() {

    }

    @Override
    public void newPasswordFail() {

    }

    @Override
    public void showLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void hideLoadingIndicator(Dialog dialog) {

    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_userReset:
                mUserPresenter.users();
                break;
        }
        return true;
    }
}
