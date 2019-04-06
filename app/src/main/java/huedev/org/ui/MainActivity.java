package huedev.org.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import huedev.org.R;
import huedev.org.ui.adapter.ViewPagerAdapter;
import huedev.org.ui.auth.LoginActivity;
import huedev.org.ui.base.BaseActivity;
import huedev.org.ui.fragments.room.RoomFragment;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView mBtNavigation;
    Toolbar mToolbar;
    ViewPager mViewPagerMain;
    ViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.dl_main);
        mToolbar = findViewById(R.id.toolbar_main);
        mViewPagerMain = findViewById(R.id.viewPager_main);
        mBtNavigation = findViewById(R.id.navigation_main);
        mViewPagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());

        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.btn_menu);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (!AppPrefs.getInstance(this).getApiToken().equals(AppConstants.API_TOKEN_DEFAULT)){
            mNavigationView.getMenu().getItem(0).setVisible(true);
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(false);
        }else {
            mNavigationView.getMenu().getItem(0).setVisible(false);
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(true);
        }

        mViewPagerAdapter.addFragment(new RoomFragment(), "Home");

        mViewPagerMain.setAdapter(mViewPagerAdapter);
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
                mToolbar.setTitle("Home");
                mViewPagerAdapter.addFragment(new RoomFragment(), "Home");
                mViewPagerMain.setAdapter(mViewPagerAdapter);
                return true;
            case R.id.nav_bt_calendar:
                mToolbar.setTitle("Calendar");
                return true;
            case R.id.nav_bt_feeds:
                mToolbar.setTitle("Feeds");
                return true;
            case R.id.nav_bt_messenger:
                mToolbar.setTitle("Messenger");
                return true;
            case R.id.nav_start_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.slide_left_in, R.anim.slide_left_out);
                startActivity(intent, options.toBundle());
                mDrawerLayout.closeDrawers();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
}
