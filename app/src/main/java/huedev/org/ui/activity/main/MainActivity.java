package huedev.org.ui.activity.main;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import huedev.org.R;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.activity.add.AddActivity;
import huedev.org.ui.activity.auth.login.LoginActivity;
import huedev.org.ui.activity.auth.logout.LogoutContact;
import huedev.org.ui.activity.auth.logout.LogoutPresenter;
import huedev.org.ui.activity.user.UserActivity;
import huedev.org.ui.activity.user.UserPresenter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.ui.fragments.MessengerFragment.MessengerFragment;
import huedev.org.ui.fragments.calendar.CalendarFragment;
import huedev.org.ui.fragments.feed.FeedFragment;
import huedev.org.ui.fragments.room.RoomFragment;
import huedev.org.ui.activity.user.update.UEditInfoActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;


public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        LogoutContact.View {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView mBtNavigation;
    Toolbar mToolbar;
    Navigator navigator;
    LinearLayout linearLayout;
    TextView tvNameUSer, tvPosition;
    LogoutPresenter mLogoutPresenter;
    UserPresenter mUserPresenter;

    String name = "", role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.dl_main);
        mToolbar = findViewById(R.id.toolbar_main);
        linearLayout = findViewById(R.id.linear_container);
        mBtNavigation = findViewById(R.id.navigation_main);
        View headerLayout = mNavigationView.getHeaderView(0);
        tvNameUSer = headerLayout.findViewById(R.id.tv_nameUser);
        tvPosition = headerLayout.findViewById(R.id.tv_position);
        navigator = new Navigator(this);
        init();

        mToolbar.setTitle("");
        setupToolbar(mToolbar, R.drawable.btn_menu);

        setNameUser();

        setVisibleItemNavigation();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.linear_container ,new RoomFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        mToolbar.setNavigationOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBtNavigation.setOnNavigationItemSelectedListener(this);

    }

    public void setNameUser(){
        if (!AppPrefs.getInstance(this).getNameUser().isEmpty() && AppPrefs.getInstance(this).getRole() > -1){
            name = AppPrefs.getInstance(this).getNameUser();
            role = StringHelper.formatStringRole(AppPrefs.getInstance(this).getRole(), this);
        }else {
            name = StringHelper.getStringResourceByName("admin", this);
            role = StringHelper.formatStringRole(2, this);
        }

        tvNameUSer.setText(name);
        tvPosition.setText(role);
    }

    private void setVisibleItemNavigation() {
        if (!AppPrefs.getInstance(this).getApiToken().equals(AppConstants.API_TOKEN_DEFAULT)){
            mNavigationView.getMenu().getItem(0).setVisible(true);
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(true);
            mNavigationView.getMenu().getItem(3).setVisible(false);
        }else {
            mNavigationView.getMenu().getItem(0).setVisible(false);
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(false);
            mNavigationView.getMenu().getItem(3).setVisible(true);
        }
    }

    private void init() {
        mLogoutPresenter = new LogoutPresenter(this);
        UserRepository userRepository = UserRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance(this));
        mUserPresenter = new UserPresenter(this, userRepository, SchedulerProvider.getInstance());
        mLogoutPresenter.setView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_hometop, menu);
        if (AppPrefs.getInstance(this).getRole() == 2){
            menu.getItem(0).setVisible(false);
        }else {
            menu.getItem(0).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_room:
                navigator.startActivity(AddActivity.class);
                break;
            case R.id.menu_notify:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_bt_home:
                replaceFragment(new RoomFragment());
                return true;
            case R.id.nav_bt_calendar:
                replaceFragment(new CalendarFragment());
                return true;
            case R.id.nav_bt_feeds:
                replaceFragment(new FeedFragment());
                return true;
            case R.id.nav_bt_messenger:
                replaceFragment(new MessengerFragment());
                return true;
            case R.id.nav_start_login:
                menuItem.setChecked(false);
                mDrawerLayout.closeDrawers();
                navigator.startActivity(LoginActivity.class);
                finish();
                return true;
            case R.id.nav_start_logout:
                mLogoutPresenter.logout();
                return true;
            case R.id.nav_start_editInformation:
                menuItem.setChecked(false);
                Log.d("password", AppPrefs.getInstance(this).getPasswordUser());
                navigator.startActivity(UEditInfoActivity.class);
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.nav_start_user_manager:
                menuItem.setChecked(false);
                navigator.startActivity(UserActivity.class);
                mDrawerLayout.closeDrawers();
                return true;

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        mDrawerLayout.openDrawer(GravityCompat.START);
        setNameUser();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linear_container ,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_left_in,
                R.anim.slide_left_out);
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

    @Override
    public void logout() {
        mDrawerLayout.closeDrawers();
        navigator.startActivity(MainActivity.class);
    }
}
