package huedev.org.ui;

import android.app.ActivityOptions;
import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import huedev.org.R;
import huedev.org.ui.auth.LoginActivity;
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
        BottomNavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView mBtNavigation;
    Toolbar mToolbar;
    Navigator navigator;
    LinearLayout linearLayout;
    TextView tvNameUSer, tvPosition;
    String name = "abc", role = "123";

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


        mToolbar.setNavigationOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBtNavigation.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_hometop, menu);
        return super.onCreateOptionsMenu(menu);
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
                navigator.startActivity(LoginActivity.class);
                mDrawerLayout.closeDrawers();
                finish();
                return true;
            case R.id.nav_start_logout:
                AppPrefs.getInstance(this).putApiToken(AppConstants.API_TOKEN_DEFAULT);
                AppPrefs.getInstance(this).putIdUser(AppConstants.ID_USER_DEFAULT);
                AppPrefs.getInstance(this).putUserNameUser(AppConstants.USERNAME_DEFAULT);
                AppPrefs.getInstance(this).putPasswordUser(AppConstants.PASSWORD_DEFAULT);
                AppPrefs.getInstance(this).putNameUser(AppConstants.NAME_DEFAULT);
                AppPrefs.getInstance(this).putEmailUser(AppConstants.EMAIL_DEFAULT);
                AppPrefs.getInstance(this).putRole(AppConstants.ROLE_DEFAULT);
                mDrawerLayout.closeDrawers();
                navigator.startActivity(MainActivity.class);
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
}
