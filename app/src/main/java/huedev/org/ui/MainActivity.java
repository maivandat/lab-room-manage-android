package huedev.org.ui;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import huedev.org.R;
import huedev.org.ui.auth.login.LoginActivity;
import huedev.org.ui.auth.login.LoginPresenter;
import huedev.org.ui.auth.logout.LogoutContact;
import huedev.org.ui.auth.logout.LogoutPresenter;
import huedev.org.ui.base.BaseActivity;
import huedev.org.ui.fragments.MessengerFragment.MessengerFragment;
import huedev.org.ui.fragments.calendar.CalendarFragment;
import huedev.org.ui.fragments.feed.FeedFragment;
import huedev.org.ui.fragments.room.RoomFragment;
import huedev.org.ui.user.edit.UEditActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;



public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener, LogoutContact.View {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView mBtNavigation;
    Toolbar mToolbar;
    Navigator navigator;
    LinearLayout linearLayout;
    TextView tvNameUSer, tvPosition;
    LogoutPresenter logoutPresenter;
    Dialog dialog;
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
        logoutPresenter = new LogoutPresenter(this);

        mToolbar.setTitle("");
        setupToolbar(mToolbar, R.drawable.btn_menu);
        setNameUser();

        if (!AppPrefs.getInstance(this).getApiToken().equals(AppConstants.API_TOKEN_DEFAULT)){
            mNavigationView.getMenu().getItem(0).setVisible(true);
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(false);

        }else {
            mNavigationView.getMenu().getItem(0).setVisible(false);
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(true);
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.linear_container ,new RoomFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        logoutPresenter.setView(this);
        mToolbar.setNavigationOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBtNavigation.setOnNavigationItemSelectedListener(this);

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
                setupDialogAdd();
                break;
            case R.id.menu_notify:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDialogAdd() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_addroom);
        dialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
                mDrawerLayout.closeDrawers();
                navigator.startActivity(LoginActivity.class);
                finish();
                return true;
            case R.id.nav_start_logout:
                logoutPresenter.logout();
                return true;
            case R.id.nav_start_editInformation:
                navigator.startActivity(UEditActivity.class);
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


    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

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
