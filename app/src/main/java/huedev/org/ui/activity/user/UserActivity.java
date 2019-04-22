package huedev.org.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.User;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.adapter.UserAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.rx.SchedulerProvider;

public class UserActivity extends BaseActivity implements View.OnClickListener, UserContact.View {
    FloatingActionButton fabAddUser;
    TextView tvTotalUser;
    RecyclerView rvUser;
    Toolbar mToolbar;
    UserPresenter mUserPresenter;
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
    }

    private void init() {
        UserRepository userRepository = UserRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance(this));
        mUserPresenter = new UserPresenter(
                this, userRepository, SchedulerProvider.getInstance());
        mUserPresenter.setView(this);
        mUserPresenter.users();

    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void usersList(List<User> userList) {
        tvTotalUser.setText("Total: " + userList.size() + "");
        UserAdapter adapter = new UserAdapter(this, userList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rvUser.setLayoutManager(manager);
        rvUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
    }
}
